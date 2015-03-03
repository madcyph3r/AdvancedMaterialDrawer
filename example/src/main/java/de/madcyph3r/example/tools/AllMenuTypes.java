package de.madcyph3r.example.tools;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import de.madcyph3r.example.FragmentIndex;
import de.madcyph3r.example.R;
import de.madcyph3r.example.Settings;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialDevisor;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionOnClickListener;
import de.madcyph3r.materialnavigationdrawer.tools.RoundedCornersDrawable;

/**
 * Created by marc on 23.02.2015.
 */
public class AllMenuTypes extends MaterialNavigationDrawer {

    MaterialNavigationDrawer drawer = null;

    @Override
    public int headerType() {
        // set type. you get the available constant from MaterialNavigationDrawer class
        return MaterialNavigationDrawer.DRAWERHEADER_HEADITEMS;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        drawer = this;

        MaterialMenu menu = new MaterialMenu();

        //create intent for settings activity
        Intent i = new Intent(this, Settings.class);

        // create sections
        MaterialSection section1 = this.newSection("Start Fragment", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentIndex(), false, menu);
        MaterialSection section2 = this.newSection("Start Activity", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), i, false, menu);
        // add devisor
        this.newDevisor(menu);
        // section with own in click listener
        this.newLabel("label", false, menu);
        MaterialSection section3 = this.newSection("On Click listener", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), i, false, menu);
        section3.setOnClickListener(new MaterialSectionOnClickListener() {
            @Override
            public void onClick(MaterialSection section, View view) {
                Toast.makeText(drawer, "on click listener ;)", Toast.LENGTH_LONG).show();
                section.unSelect();
            }
        });

        MaterialSection section7 = this.newSection("Start Fragment Notification", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentIndex(), false, menu).setSectionColor(Color.parseColor("#ff0858"));
        section7.setNotifications(20);

        MaterialSection section4 = this.newSection("Start Fragment No Icon", new FragmentIndex(), false, menu);

        MaterialSection section5 = this.newSection("Start Fragment Red Color", new FragmentIndex(), false, menu).setSectionColor(Color.parseColor("#ff0000"));

        this.newLabel("label bottom", true, menu);
        MaterialSection section6 = this.newSection("Start Fragment Bottom", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentIndex(), true, menu);

        // use bitmap and make a circle photo
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_drawer_icon);
        final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);

        // create Head Item
        MaterialHeadItem headItem = new MaterialHeadItem(this, "F HeadItem", "F Subtitle", drawableAppIcon, R.drawable.mat5, menu, 0);

        // add head Item (menu will be loaded automatically)
        this.addHeadItem(headItem);
    }
}
