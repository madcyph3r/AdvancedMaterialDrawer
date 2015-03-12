package de.madcyph3r.example.tools;


import android.os.Bundle;

import de.madcyph3r.example.FragmentIndex;
import de.madcyph3r.example.R;
import de.madcyph3r.example.tools.actionBarOverlayActivity.FragmentActionBarOverlay;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;

public class ActionBarOverlayActivity extends MaterialNavigationDrawer {

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

        // create sections
        MaterialSection section1 = this.newSection("Section 1", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentActionBarOverlay(), false, menu);
        MaterialSection section2 = this.newSection("Section 2", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentIndex(), false, menu);

        menu.setStartIndex(0);
        // set this menu
        this.setCustomMenu(menu);
    }

    @Override
    public void afterInit(Bundle b) {
        // set overlay and Alpha
        this.setToolbarOverlay(true);
        this.setToolbarAlpha(0.5F);

        // or set it in the style with
        /*
        <item name="actionBarOverlay">false</item>
        <item name="actionBarOverlayAlpha">1.0</item>
        */
    }
}