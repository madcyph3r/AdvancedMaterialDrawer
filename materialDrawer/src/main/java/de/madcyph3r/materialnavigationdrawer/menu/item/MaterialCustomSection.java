package de.madcyph3r.materialnavigationdrawer.menu.item;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import de.madcyph3r.materialnavigationdrawer.R;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionChangeListener;

/**
 * Created by marc on 11.07.2015.
 */
public class MaterialCustomSection {

    private View view;

    public MaterialCustomSection(Context ctx, int resID) {
        init(ctx, resID);
    }

    private void init(Context ctx, int res) {
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        /**
         * theme location
         */
        Resources.Theme theme = ctx.getTheme();
        TypedValue typedValue = new TypedValue();
        theme.resolveAttribute(R.attr.sectionStyle, typedValue, true);
        TypedArray values = theme.obtainStyledAttributes(typedValue.resourceId, R.styleable.MaterialSection);

        view = LayoutInflater.from(ctx).inflate(getItemLayout(values, res), null);
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

    public View getView() {
        return view;
    }
}
