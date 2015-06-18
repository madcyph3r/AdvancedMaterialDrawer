package de.madcyph3r.materialnavigationdrawer.custom;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.DimenRes;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

/**
 * Created by hesk on 29/5/15.
 */
public class DefaultDrawerListener implements DrawerLayout.DrawerListener {
    protected final int widthdrawer;

    public DefaultDrawerListener(int customDrawerWidth) {
        widthdrawer = customDrawerWidth;
    }


    /**
     * Called when a drawer's position changes.
     *
     * @param drawerView  The child view that was moved
     * @param slideOffset The new offset of this drawer within its range, from 0-1
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        drawerView.setTranslationX(widthdrawer * slideOffset);
    }

    /**
     * Called when a drawer has settled in a completely open state.
     * The drawer is interactive at this point.
     *
     * @param drawerView Drawer view that is now open
     */
    @Override
    public void onDrawerOpened(View drawerView) {

    }

    /**
     * Called when a drawer has settled in a completely closed state.
     *
     * @param drawerView Drawer view that is now closed
     */
    @Override
    public void onDrawerClosed(View drawerView) {

    }

    /**
     * Called when the drawer motion state changes. The new state will
     * be one of {@link #STATE_IDLE}, {@link #STATE_DRAGGING} or {@link #STATE_SETTLING}.
     *
     * @param newState The new drawer motion state
     */
    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
