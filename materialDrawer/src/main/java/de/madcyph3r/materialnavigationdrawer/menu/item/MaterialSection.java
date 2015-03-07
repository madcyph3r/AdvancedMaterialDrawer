package de.madcyph3r.materialnavigationdrawer.menu.item;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionOnClickListener;
import de.madcyph3r.materialnavigationdrawer.R;

public class MaterialSection<Fragment> implements View.OnTouchListener {

    public static final int TARGET_FRAGMENT = 0;
    public static final int TARGET_ACTIVITY = 1;
    public static final int TARGET_CLICK = 2;

    private int position;
    private View view;
    private TextView text;
    private TextView notifications;
    private ImageView icon;
    private MaterialSectionOnClickListener listener;
    private boolean isSelected;
    private int targetType;
    private int sectionColor;
    private boolean fillIconColor;

    private boolean bottom;

    private boolean hasSectionColor;
    private boolean hasColorDark;
    private int colorPressed;
    private int colorUnpressed;
    private int colorSelected;
    private int iconColor;
    private int colorDark;
    private int textColor;
    private int notificationColor;

    private int numberNotifications;

    private String title;

    private Fragment targetFragment;
    private Intent targetIntent;

    private boolean hasIcon = false;

    public MaterialSection(Context ctx, boolean hasIcon, int target, boolean bottom) {

        this.bottom = bottom;
        this.hasIcon = hasIcon;

        if (!hasIcon) {
            view = LayoutInflater.from(ctx).inflate(R.layout.layout_material_section, null);

            text = (TextView) view.findViewById(R.id.section_text);
            notifications = (TextView) view.findViewById(R.id.section_notification);
        } else {
            view = LayoutInflater.from(ctx).inflate(R.layout.layout_material_section_icon, null);

            text = (TextView) view.findViewById(R.id.section_text);
            icon = (ImageView) view.findViewById(R.id.section_icon);
            notifications = (TextView) view.findViewById(R.id.section_notification);
        }

        view.setOnTouchListener(this);


        Resources.Theme theme = ctx.getTheme();
        TypedValue typedValue = new TypedValue();
        theme.resolveAttribute(R.attr.sectionStyle, typedValue, true);
        TypedArray values = theme.obtainStyledAttributes(typedValue.resourceId, R.styleable.MaterialSection);

        colorPressed = values.getColor(R.styleable.MaterialSection_sectionBackgroundColorPressed, 0x16000000);
        colorUnpressed = values.getColor(R.styleable.MaterialSection_sectionBackgroundColor, 0x00FFFFFF);
        colorSelected = values.getColor(R.styleable.MaterialSection_sectionBackgroundColorSelected, 0x0A000000);

        iconColor = values.getColor(R.styleable.MaterialSection_sectionColorIcon, 0x000);
        textColor = values.getColor(R.styleable.MaterialSection_sectionColorText, 0x000);
        notificationColor = values.getColor(R.styleable.MaterialSection_sectionColorNotification, 0x000);

        // set text color into the view
        if (textColor != 0x000) {
            text.setTextColor(textColor);
        }
        if (notificationColor != 0x000) {
            notifications.setTextColor(notificationColor);
        }

/*
            colorPressed = Color.parseColor("#16000000");
        colorUnpressed = Color.parseColor("#00FFFFFF");
        colorSelected = Color.parseColor("#0A000000");*/
        //iconColor = Color.rgb(98, 98, 98);
        isSelected = false;
        hasSectionColor = false;
        hasColorDark = false;
        targetType = target;
        numberNotifications = 0;
        fillIconColor = true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            view.setBackgroundColor(colorPressed);
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_CANCEL) {
            if (isSelected)
                view.setBackgroundColor(colorSelected);
            else
                view.setBackgroundColor(colorUnpressed);

            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            isSelected = true;
            view.setBackgroundColor(colorSelected);

            if (hasSectionColor) {
                text.setTextColor(iconColor);
            }

            if (listener != null) {
                final MaterialSection section = this;
                view.playSoundEffect(android.view.SoundEffectConstants.CLICK);
                listener.onClick(section, v);

                /*v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick(section, v);
                    }
                });*/
            }

            return true;
        }

        return false;
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
}
