package de.madcyph3r.example.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import de.madcyph3r.example.FragmentIndex;
import de.madcyph3r.example.R;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionOnClickListener;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;
import de.madcyph3r.materialnavigationdrawer.tools.RoundedCornersDrawable;

/**
 * Created by marc on 23.02.2015.
 */
public class OneHeadItemReloadMenu extends MaterialNavigationDrawer {

    MaterialNavigationDrawer drawer = null;

    @Override
    public int headerType() {
        // set type. you get the available constant from MaterialNavigationDrawer class
        return MaterialNavigationDrawer.DRAWERHEADER_HEADITEMS;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        drawer = this;

        final MaterialMenu menu = new MaterialMenu();

        // first section is loaded
        MaterialSection section1 = this.newSection("Section 1", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentIndex(), false, menu);
        MaterialSection section2 = this.newSection("Section 2", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentIndex(), false, menu);
        this.newDevisor(menu);
        MaterialSection section3 = this.newSection("add two sections and one devisor", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentIndex(), false, menu);

        section3.setOnClickListener(new MaterialSectionOnClickListener() {
            @Override
            public void onClick(MaterialSection section, View v) {
                Toast.makeText(drawer, "added two section", Toast.LENGTH_SHORT).show();

                drawer.newSection("added one", drawer.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentIndex(), false, menu);
                drawer.newSection("added two", drawer.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentIndex(), false, menu, 0);
                drawer.newDevisor(menu, 1);

                //reloadMenu(int loadSectionPosition) and load the section on the given position (loadSectionPosition)
                // to get a position use this method: menu.getSection(int position); not menu.getSectionFromRealPosition(int position);
                // or insert -1, to load the first section with a fragment

                // this methods reloads only the menu, but does not load a new section
                reloadMenu();
            }
        });

        MaterialSection section4 = this.newSection("add one section at the end", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentIndex(), false, menu);

        section4.setOnClickListener(new MaterialSectionOnClickListener() {
            @Override
            public void onClick(MaterialSection section, View v) {
                Toast.makeText(drawer, "added one section", Toast.LENGTH_SHORT).show();

                // last true make the refresh possible. then you don't must call reloadMenu(); method
                drawer.newSection("added one", drawer.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentIndex(), false, menu, true);
            }
        });

        MaterialSection section5 = this.newSection("remove first menu item", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentIndex(), false, menu);

        section5.setOnClickListener(new MaterialSectionOnClickListener() {
            @Override
            public void onClick(MaterialSection section, View v) {

                //getCurrentMenu().getRealSectionPosition(yourSection), this give you the real position of an item, to remove it
                if(getCurrentMenu().getItems().size() > 1 ) {
                    Toast.makeText(drawer, "removed first menu item", Toast.LENGTH_SHORT).show();

                    Object o = getCurrentMenu().getItem(0);
                    getCurrentMenu().removeItem(0); // can remove devisor and labels too
                    if (o instanceof MaterialSection && o == getCurrentSection()) {
                        reloadMenu(-1); // load the first fragment with an section, because the current section is removed
                    } else {
                        reloadMenu();
                    }
                } else {
                    Toast.makeText(drawer, "you shoud have one item", Toast.LENGTH_SHORT).show();
                }
            }
        });

        MaterialSection section6 = this.newSection("remove second menu section", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentIndex(), false, menu);

        section6.setOnClickListener(new MaterialSectionOnClickListener() {
            @Override
            public void onClick(MaterialSection section, View v) {


                MaterialSection secSection = getCurrentMenu().getSection(1); // get second section
                if(secSection != null) {
                    Toast.makeText(drawer, "remove second menu section", Toast.LENGTH_SHORT).show();

                    int realPos = getCurrentMenu().getRealSectionPosition(secSection);
                    getCurrentMenu().removeItem(realPos); // can remove devisor and labels too
                    if (secSection == getCurrentSection()) {
                        reloadMenu(-1); // load the first fragment with an section, because the current section is removed
                    } else {
                        reloadMenu();
                    }
                } else {
                    Toast.makeText(drawer, "no section is available, to remove", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // use bitmap and make a circle photo
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_drawer_icon);
        final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);

        // create Head Item
        MaterialHeadItem headItem = new MaterialHeadItem(this, "F HeadItem", "F Subtitle", drawableAppIcon, R.drawable.mat5, menu, 0);

        // add head Item (menu will be loaded automatically)
        this.addHeadItem(headItem);
    }
}
