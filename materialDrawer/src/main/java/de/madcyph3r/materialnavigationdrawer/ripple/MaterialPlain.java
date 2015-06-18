package de.madcyph3r.materialnavigationdrawer.ripple;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import de.madcyph3r.materialnavigationdrawer.R;

/**
 * Created by hesk on 14/5/15.
 */
public class MaterialPlain extends FrameLayout {
    private Drawable normal, press;
    private View childView;

    public MaterialPlain(Context context) {
        this(context, null, 0);
    }

    public MaterialPlain(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public MaterialPlain(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setWillNotDraw(false);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaterialRippleLayout);
        normal = a.getDrawable(R.styleable.MaterialPlain_hkm_touch_normal);
        press = a.getDrawable(R.styleable.MaterialPlain_hkm_touch_press);
        a.recycle();
        try {
            if (normal == null) normal = getResources().getDrawable(R.drawable.normal);
            if (press == null) press = getResources().getDrawable(R.drawable.divider);
            setBackground(normal);
            requestLayout();
        } catch (Resources.NotFoundException e) {

        }
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getChildView() {
        return (T) childView;
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        if (childView == null) {
            throw new IllegalStateException("MaterialRippleLayout must have a child view to handle clicks");
        }
        childView.setOnClickListener(onClickListener);
    }

    @Override
    public final void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("MaterialRippleLayout can host only one child");
        }
        //noinspection unchecked
        childView = child;
        super.addView(child, index, params);
    }

    private boolean findClickableViewInChild(View view, int x, int y) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                final Rect rect = new Rect();
                child.getHitRect(rect);

                final boolean contains = rect.contains(x, y);
                if (contains) {
                    return findClickableViewInChild(child, x - rect.left, y - rect.top);
                }
            }
        } else if (view != childView) {
            return (view.isEnabled() && (view.isClickable() || view.isLongClickable() || view.isFocusableInTouchMode()));
        }

        return view.isFocusableInTouchMode();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return !findClickableViewInChild(childView, (int) event.getX(), (int) event.getY());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean superOnTouchEvent = super.onTouchEvent(event);
        if (!isEnabled() || !childView.isEnabled()) return superOnTouchEvent;

        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackground(normal);
                break;
            case MotionEvent.ACTION_DOWN:
                setBackground(press);
                break;
        }
        return true;
    }
}
