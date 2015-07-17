package de.madcyph3r.materialnavigationdrawer.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.R;

public abstract class MaterialNavNoHeaderActivity extends MaterialNavigationDrawer {

    // global vars view custom
    private LinearLayout noHeader;


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

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT || (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT && !kitkatTraslucentStatusbar)) {
            heightHeader = 0;
        } else {
            // kitkat traslucentstatusbar and lollipop has 25 dp
            if (!belowToolbar)
                heightHeader = (int) (25 * displayDensity);
            else
                heightHeader = 0;
        }
        noHeader.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, heightHeader));
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("deprecation")
    private void initHeaderView() {
        noHeader = (LinearLayout) this.findViewById(R.id.drawer_header);
    }

    @Override
    public abstract void init(Bundle savedInstanceState);

}
