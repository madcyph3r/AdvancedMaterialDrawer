package de.madcyph3r.materialnavigationdrawer.activity;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.R;

public abstract class MaterialNavImageActivity extends MaterialNavigationDrawer {

    // global vars view custom
    private LinearLayout imageHeader;


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

        imageHeader.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, heightHeader));
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("deprecation")
    private void initHeaderView() {
        imageHeader = (LinearLayout) this.findViewById(R.id.drawer_header);
    }

    private void setImageHeader(Bitmap background, ImageView.ScaleType scaleType) {
        ImageView image = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        image.setScaleType(scaleType);
        image.setImageBitmap(background);

        imageHeader.addView(image, params);
    }

    public void setImageHeader(Bitmap background, int headerDPHeight, ImageView.ScaleType scaleType) {
        setImageHeader(background, scaleType);

        this.headerDPHeight = headerDPHeight;
    }

    private void setImageHeader(Drawable background, ImageView.ScaleType scaleType) {

        ImageView image = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        image.setScaleType(scaleType);
        image.setImageDrawable(background);

        imageHeader.addView(image, params);
    }

    public void setImageHeader(Drawable background, int headerDPHeight, ImageView.ScaleType scaleType) {
        setImageHeader(background, scaleType);

        this.headerDPHeight = headerDPHeight;
    }

    @Override
    public abstract void init(Bundle savedInstanceState);

}
