package de.madcyph3r.example.tools;

import android.content.Intent;
import android.os.Bundle;

import de.madcyph3r.example.FragmentIndex;
import de.madcyph3r.example.R;
import de.madcyph3r.example.SecondActivity;
import de.madcyph3r.example.Settings;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;

/**
 * Created by marc on 23.02.2015.
 */
public class NoClosePreviousActivity extends MaterialNavigationDrawer {

    MaterialNavigationDrawer drawer = null;

    @Override
    public boolean finishActivityOnNewIntent() {
        return false;
    }

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
        this.newSection("Start Fragment", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentIndex(), false, menu);
        this.newSection("No close drawer activity", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new Intent(this, SecondActivity.class), false, menu);


        drawer.setCustomMenu(menu);

    }
}
