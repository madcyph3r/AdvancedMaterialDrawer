package de.madcyph3r.example.tools;


import android.os.Bundle;

import de.madcyph3r.example.FragmentIndex;
import de.madcyph3r.example.R;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialSection;

public class ImageHeaderActivity extends MaterialNavigationDrawer {

    MaterialNavigationDrawer drawer = null;

    @Override
    public int headerType() {
        // set type. you get the available constant from MaterialNavigationDrawer class
        return MaterialNavigationDrawer.DRAWERHEADER_IMAGE;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        // important, don't forget ti set the right header type

        drawer = this;

        MaterialMenu menu = new MaterialMenu();

        // create sections
        MaterialSection section1 = this.newSection("Section 1", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentIndex(), false, menu);
        MaterialSection section2 = this.newSection("Section 2", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentIndex(), false, menu);

        // set this menu
        this.setCustomMenu(menu);

        // set image for header
        this.setDrawerHeaderImage(getResources().getDrawable(R.drawable.mat6));
    }
}
