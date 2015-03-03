package de.madcyph3r.materialnavigationdrawer;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

import de.madcyph3r.materialnavigationdrawer.tools.Utils;

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

    public void setMultipaneSupport(boolean support) {
        if(Utils.isTablet(this.getResources())) {
            // custom implementation only for tablets
            multipaneSupport = support;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if(multipaneSupport) {
            return false;
        }
        else {
            return super.onKeyUp(keyCode, event);
        }
    }
}