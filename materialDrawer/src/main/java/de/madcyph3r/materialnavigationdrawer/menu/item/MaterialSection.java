package de.madcyph3r.materialnavigationdrawer.menu.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionChangeListener;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionOnClickListener;
import de.madcyph3r.materialnavigationdrawer.R;
import de.madcyph3r.materialnavigationdrawer.ripple.MaterialPlain;
import de.madcyph3r.materialnavigationdrawer.ripple.MaterialRippleLayout;
import de.madcyph3r.materialnavigationdrawer.ripple.MaterialRippleLayoutNineOld;

public class MaterialSection<Fragment, customTextView extends TextView> implements View.OnTouchListener, View.OnClickListener {

    public static final int TARGET_FRAGMENT = 0;
    public static final int TARGET_ACTIVITY = 1;
    public static final int TARGET_CLICK = 2;

    private View view;
    private customTextView text;
    private customTextView notifications;
    private ImageView icon;
    private MaterialSectionOnClickListener listener;
    private MaterialSectionChangeListener changeListener;

    private boolean isSelected;
    private int targetType;
    private int sectionColor;
    private boolean fillIconColor;

    private boolean bottom;

    private boolean hasSectionColor;
    private boolean hasColorDark;
    //private int colorPressed;
    private int colorUnpressed;
    private int colorSelected;
    private int colorOnPressHighlight;
    private int iconColor;
    private int colorDark;
    private int textColor;
    private int notificationColor;

    private int numberNotifications;

    private String title;
    private String fragmentTitle;

    private Fragment targetFragment;
    private Intent targetIntent;

    private boolean hasIcon = false, sectiondivided;


    public MaterialSection(Context ctx, boolean hasIcon, int target, boolean bottom, MaterialSectionChangeListener changeListener, boolean fullBanner) {
        init(ctx, hasIcon, target, bottom, changeListener, fullBanner);
    }

    private int getItemLayout(TypedArray values, int defaultResId) {
        int resId = values.getResourceId(R.styleable.MaterialSection_section_item_layout, -1);
        if (resId == -1) {
            Log.d("not", "got -1");
            return defaultResId;
        } else {
            Log.d("not", "resId > -1");
            return resId;
        }
    }

    @SuppressLint("WrongViewCast")
    private void init(Context ctx, boolean hasIcon, int target, boolean bottom, MaterialSectionChangeListener changeListener, boolean fullWidthIcon) {

        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        /**
         * theme location
         */
        Resources.Theme theme = ctx.getTheme();
        TypedValue typedValue = new TypedValue();
        theme.resolveAttribute(R.attr.sectionStyle, typedValue, true);
        TypedArray values = theme.obtainStyledAttributes(typedValue.resourceId, R.styleable.MaterialSection);

        /**
         * the title from the fragments
         */
        fragmentTitle = null;

        this.changeListener = changeListener;
        this.bottom = bottom;
        this.hasIcon = hasIcon;


        if (!hasIcon) {
            if (currentApiVersion >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                view = LayoutInflater.from(ctx).inflate(getItemLayout(values, R.layout.layout_material_section), null);
            } else {
                view = LayoutInflater.from(ctx).inflate(getItemLayout(values, R.layout.layout_material_section_nine_old), null);
            }
        } else {
            if (!fullWidthIcon) {
                if (currentApiVersion >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    view = LayoutInflater.from(ctx).inflate(getItemLayout(values, R.layout.layout_material_section_icon), null);
                } else {
                    view = LayoutInflater.from(ctx).inflate(getItemLayout(values, R.layout.layout_material_section_icon_nine_old), null);
                }
            } else {
                view = LayoutInflater.from(ctx).inflate(R.layout.layout_material_section_full_image, null);
            }
            icon = (ImageView) view.findViewById(R.id.section_icon);
        }


        notifications = (customTextView) view.findViewById(R.id.section_notification);
        text = (customTextView) view.findViewById(R.id.section_text);


        int rippleColor = values.getColor(R.styleable.MaterialSection_sectionRippleColor, 0x16000000);

        if (view.findViewById(R.id.section_ripple) instanceof MaterialRippleLayout) {
            MaterialRippleLayout ilayout = (MaterialRippleLayout) view.findViewById(R.id.section_ripple);
            ilayout.setRippleColor(rippleColor);
            ilayout.setOnClickListener(this);
            ilayout.setOnTouchListener(this);
        } else if (view.findViewById(R.id.section_ripple) instanceof MaterialRippleLayoutNineOld) {
            MaterialRippleLayoutNineOld ilayout = (MaterialRippleLayoutNineOld) view.findViewById(R.id.section_ripple);
            ilayout.setRippleColor(rippleColor);
            ilayout.setOnClickListener(this);
            ilayout.setOnTouchListener(this);
        } else if (view.findViewById(R.id.section_ripple) instanceof MaterialPlain) {
            MaterialPlain ilayout = (MaterialPlain) view.findViewById(R.id.section_ripple);
            ilayout.setOnClickListener(this);
            ilayout.setOnTouchListener(this);
        } else if (view.findViewById(R.id.section_relative_layout) instanceof RelativeLayout) {
            RelativeLayout ilayout = (RelativeLayout) view.findViewById(R.id.section_relative_layout);
            ilayout.setOnClickListener(this);
            ilayout.setOnTouchListener(this);
        }

        //colorPressed = values.getColor(R.styleable.MaterialSection_sectionBackgroundColorPressed, 0x16000000);
        colorUnpressed = values.getColor(R.styleable.MaterialSection_sectionBackgroundColor, 0x00FFFFFF);
        colorSelected = values.getColor(R.styleable.MaterialSection_sectionBackgroundColorSelected, 0x0A000000);
        colorOnPressHighlight = values.getColor(R.styleable.MaterialSection_sectionOnPressHighLightColor, 0x0A000000);

        iconColor = values.getColor(R.styleable.MaterialSection_sectionColorIcon, 0x000);
        textColor = values.getColor(R.styleable.MaterialSection_sectionColorText, 0x000);
        notificationColor = values.getColor(R.styleable.MaterialSection_sectionColorNotification, 0x000);
        sectiondivided = values.getBoolean(R.styleable.MaterialSection_sectionDivider, false);

        // set text color into the view
        if (textColor != 0x000) {
            text.setTextColor(textColor);
        }
        if (notificationColor != 0x000) {
            notifications.setTextColor(notificationColor);
        }

        isSelected = false;
        hasSectionColor = false;
        hasColorDark = false;
        targetType = target;
        numberNotifications = 0;
        fillIconColor = true;
    }


    public void select() {
        isSelected = true;
        view.setBackgroundColor(colorSelected);
        if (hasSectionColor) {
            text.setTextColor(iconColor);
        }
    }

    public void unSelect() {
        isSelected = false;
        view.setBackgroundColor(colorUnpressed);

        if (hasSectionColor) {
            text.setTextColor(textColor);
        }
    }

    /*public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }*/

    public void setOnClickListener(final MaterialSectionOnClickListener listener) {
        this.listener = listener;
    }

    public View getView() {
        return view;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.text.setText(title);
    }

    public void setIcon(Drawable icon) {
        this.icon.setImageDrawable(icon);
        if (fillIconColor)
            this.icon.setColorFilter(iconColor);
    }

    public void setIcon(Bitmap icon) {
        this.icon.setImageBitmap(icon);
        if (fillIconColor)
            this.icon.setColorFilter(iconColor);
    }

    public void setTarget(Fragment target) {
        this.targetFragment = target;
    }

    public void setTarget(Intent intent) {
        this.targetIntent = intent;
    }

    public int getTarget() {
        return targetType;
    }

    public Fragment getTargetFragment() {
        return targetFragment;
    }

    public Intent getTargetIntent() {
        return targetIntent;
    }

    public boolean hasSectionColorDark() {
        return hasColorDark;
    }

    public MaterialSection setSectionColor(int color) {

        sectionColor = color;
        iconColor = color;
        if (icon != null)
            icon.setColorFilter(sectionColor);

        hasSectionColor = true;


        return this;
    }

    public int getSectionColorDark() {
        return colorDark;
    }

    public MaterialSection setSectionColor(int color, int colorDark) {
        setSectionColor(color);
        hasColorDark = true;
        this.colorDark = colorDark;

        return this;
    }

    public boolean hasSectionColor() {
        return hasSectionColor;
    }

    public int getSectionColor() {
        return iconColor;
    }

    /**
     * Set the number of notification for this section
     *
     * @param notifications the number of notification active for this section
     * @return this section
     */
    public MaterialSection setNotifications(int notifications) {
        String textNotification;

        textNotification = String.valueOf(notifications);

        if (notifications < 1) {
            textNotification = "";
        }
        if (notifications > 99) {
            textNotification = "99+";
        }

        this.notifications.setText(textNotification);
        numberNotifications = notifications;

        return this;
    }

    public int getNotifications() {
        return numberNotifications;
    }

    public boolean isBottom() {
        return bottom;
    }

    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }

    public boolean isFillIconColor() {
        return fillIconColor;
    }

    public void setFillIconColor(boolean fillIconColor) {
        this.fillIconColor = fillIconColor;
    }

    public TextView getText() {
        return text;
    }

    public ImageView getIcon() {
        return icon;
    }

    public boolean isHasIcon() {
        return hasIcon;
    }

    @Override
    public void onClick(View v) {
        //isSelected = true;
        //view.setBackgroundColor(colorSelected);

        /*if (hasSectionColor) {
            text.setTextColor(iconColor);
        }*/

        if (listener != null) {
            final MaterialSection section = this;
            //view.playSoundEffect(android.view.SoundEffectConstants.CLICK);
            changeListener.onBeforeChangeSection(this);
            listener.onClick(section, v);
            changeListener.onAfterChangeSection(this);
        }
    }

    public String getFragmentTitle() {
        if (fragmentTitle == null || fragmentTitle.length() == 0)
            return title;
        else
            return fragmentTitle;
    }

    public void setFragmentTitle(String fragmentTitle) {
        this.fragmentTitle = fragmentTitle;
    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int a = event.getActionMasked();
        switch (a) {
            case MotionEvent.ACTION_DOWN:
                view.setBackgroundColor(colorOnPressHighlight);
                return true;
            case MotionEvent.ACTION_CANCEL:
                if (isSelected)
                    view.setBackgroundColor(colorSelected);
                else
                    view.setBackgroundColor(colorUnpressed);
                return true;
            case MotionEvent.ACTION_UP:
                checkrefresh();
                return false;
            default:
                return false;
        }
    }


    public void checkrefresh() {
        if (!isSelected) view.setBackgroundColor(colorUnpressed);
    }
}
