package de.madcyph3r.materialnavigationdrawer;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MaterialDrawerLayout extends DrawerLayout {
    private boolean multipaneSupport = false;

    public MaterialDrawerLayout(Context context) {
        super(context);
    }

    public MaterialDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(final MotionEvent ev) {
        if (multipaneSupport) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(final boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);

        multipaneSupport = disallowIntercept;
    }
}