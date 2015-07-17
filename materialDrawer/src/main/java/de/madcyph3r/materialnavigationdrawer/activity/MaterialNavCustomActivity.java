package de.madcyph3r.materialnavigationdrawer.activity;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.R;


public abstract class MaterialNavCustomActivity extends MaterialNavigationDrawer {

    // global vars view custom
    private LinearLayout customHeader;


    protected void afterSetContentView() {
        initHeaderView();
    }

    @Override
    /**
     * Do not Override this method!!!
     * Use init() instead, see example app
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // DEVELOPER CALL TO INIT
        init(savedInstanceState);

        afterInit(savedInstanceState);
    }

    protected void setContentView() {
        if (belowToolbar)
            setContentView(R.layout.activity_material_navigation_drawer_customheader_below_toolbar);
        else
            setContentView(R.layout.activity_material_navigation_drawer_customheader);
    }

    protected void initDrawerPrivateType(int width) {
        int heightHeader = 0;

        Resources r = getResources();
        heightHeader = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, headerDPHeight, r.getDisplayMetrics());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            if (!belowToolbar)
                heightHeader += (int) (24 * displayDensity);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT && kitkatTraslucentStatusbar)
            if (!belowToolbar)
                heightHeader += (int) (25 * displayDensity);

        customHeader.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, heightHeader));
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("deprecation")
    private void initHeaderView() {
        customHeader = (LinearLayout) this.findViewById(R.id.drawer_header);
    }

    @Override
    public abstract void init(Bundle savedInstanceState);

    public void setCustomHeader(View view, int headerDPHeight) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        customHeader.addView(view, params);

        this.headerDPHeight = headerDPHeight;
    }

}
