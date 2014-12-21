package de.madcyph3r.example;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;

import de.madcyph3r.materialnavigationdrawer.item.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.item.MaterialHeadItemListener;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawerListener;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialDevisor;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialSection;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialSectionListener;
import de.madcyph3r.materialnavigationdrawer.tools.RoundedCornersDrawable;

public class MainActivity extends MaterialNavigationDrawer implements MaterialNavigationDrawerListener {

    MaterialNavigationDrawer drawer = null;

    @Override
    public void init(Bundle savedInstanceState) {

        drawer = this;

        // set drawer menu width in dp
        this.setDrawerDPWidth(300);



        // create HeadItem 1 with menu

        // create menu
        MaterialMenu menu1 = new MaterialMenu();
        // create sections for menu
        MaterialSection section1 = this.newSection("Favoriten", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentIndex(), false);
        MaterialSection section2 = this.newSection("Verfügbare Tools", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentIndex(), false);
        // no new Fragment or Activity. need own listener.
        MaterialSection section3 = this.newSection("Tools Erwerben", this.getResources().getDrawable(R.drawable.ic_extension_black_36dp), false).setSectionColor((Color.parseColor("#ff9800")));

        section3.setNotifications(33);
        // set on click listener for section 3
        section3.setOnClickListener(new MaterialSectionListener() {
            @Override
            public void onClick(MaterialSection section) {
                unSelectOldSection(section);

                // on click action for section 3
                Context context = getApplicationContext();
                CharSequence text = "Tools erwerben toast";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                drawer.closeDrawer();
            }
        });

        Intent intentSettings = new Intent(this, Settings.class);
        // create settings section. with bottom true. it will be shown on the bottom of the drawer
        MaterialSection settingsSection = this.newSection("Settings", this.getResources().getDrawable(R.drawable.ic_settings_black_24dp), intentSettings, true);

        // add sections to the menu
        menu1.getSections().add(section1);
        menu1.getSections().add(section2);
        menu1.getSections().add(section3);
        menu1.getSections().add(new MaterialDevisor());
        menu1.getSections().add(settingsSection);

        // create TextDrawable with lable F and color blue
        TextDrawable headPhoto = TextDrawable.builder()
                .buildRound("F", Color.BLUE);

        // create headItem1 and add menu1
        MaterialHeadItem headItem1 = new MaterialHeadItem("F HeadItem", "F Subtitle", headPhoto, this.getResources().getDrawable(R.drawable.mat5), menu1, 0);
        // add headItem1 to the drawer
        this.addHeadItem(headItem1);
        headItem1.setCloseDrawerOnChanged(true);




        // Create HeadItem 2 without menu

        // use bitmap and make a circle photo
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_drawer_icon);
        final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);

        // create head item, don't give a own menu
        MaterialHeadItem headItem2 = new MaterialHeadItem("Material Navigation Drawer", "version 1.0", drawableAppIcon, this.getResources().getDrawable(R.drawable.mat6));
        this.addHeadItem(headItem2);

        // listener for the avatar (photo) click
        headItem2.setOnClickListener(new MaterialHeadItemListener() {
            @Override
            public void onClick(MaterialHeadItem headItem) {
                Toast.makeText(getApplicationContext(), "This is a avatar toast", Toast.LENGTH_LONG).show();
                //addDivisor();
            }

            @Override
            public void onBackgroundClick(MaterialHeadItem headItem) {
                Toast.makeText(getApplicationContext(), "on headItem2 background", Toast.LENGTH_LONG).show();
            }
        });

        headItem2.setCloseDrawerOnChanged(false);





        // Create HeadItem 3 with menu

        // create menu
        MaterialMenu menu2 = new MaterialMenu();
        // create sections for menu
        MaterialSection section4 = this.newSection("Favoriten", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentIndex(), false);
        MaterialSection section5 = this.newSection("Kaufen", this.getResources().getDrawable(R.drawable.ic_extension_black_36dp), new FragmentIndex(), false);
        section5.setSectionColor(Color.parseColor("#ff9800"));
        // add sections to menu
        menu2.getSections().add(section4);
        menu2.getSections().add(section5);

        // create TextDrawable with lable M and color gray
        TextDrawable headPhoto3 = TextDrawable.builder()
                .buildRound("M", Color.GRAY);
        // (1): use second menu as start menu for this headitem
        MaterialHeadItem headItem3 = new MaterialHeadItem("M HeadItem", "subtitle", headPhoto3, this.getResources().getDrawable(R.drawable.mat5), menu2, 1);
        this.addHeadItem(headItem3);



        // set this class as onchangedListener
        this.setOnChangedListener(this);
    }

    @Override
    public void onBeforeChangedHeadItem(MaterialHeadItem newHeadItem) {
        //Toast.makeText(getApplicationContext(), "on changed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAfterChangedHeadItem(MaterialHeadItem newHeadItem) {

    }

}
