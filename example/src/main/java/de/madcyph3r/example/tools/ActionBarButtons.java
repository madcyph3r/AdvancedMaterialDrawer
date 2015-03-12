package de.madcyph3r.example.tools;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import de.madcyph3r.example.tools.actionBarButtons.FragmentActionBarButtons;
import de.madcyph3r.example.R;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionOnClickListener;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;

/**
 * Created by marc on 23.02.2015.
 */
public class ActionBarButtons extends MaterialNavigationDrawer {

    MaterialNavigationDrawer drawer = null;

    @Override
    public int headerType() {
        // set type. you get the available constant from MaterialNavigationDrawer class
        return MaterialNavigationDrawer.DRAWERHEADER_NO_HEADER;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        drawer = this;

        MaterialMenu menu = new MaterialMenu();

        // first section is loaded
        MaterialSection section1 = this.newSection("Hide drawer icon and lock the drawer", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), false, menu);
        section1.setOnClickListener(new MaterialSectionOnClickListener() {
            @Override
            public void onClick(MaterialSection section, View v) {
                // hide the drawer button and lock the drawer to close
                drawer.showActionBarMenuIcon(MaterialNavigationDrawer.ActionBarMenuItem.NONE);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
        });

        setCustomMenu(menu);

        // set load fragment on start to false and then set your own fragment
        this.setLoadFragmentOnStart(false);
        this.setCustomFragment(new FragmentActionBarButtons(), "title");
    }

    @TargetApi(11)
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuItem menuItem = null;

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            menuItem = menu.add(0, 0, 0, "HELP").setIcon(android.R.drawable.ic_menu_help);
            menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        } else {
            menuItem = menu.add(0, 0, 0, "HELP").setIcon(android.R.drawable.ic_menu_help);
        }

        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(drawer, "i'm a menu button", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return true;
    }

}
