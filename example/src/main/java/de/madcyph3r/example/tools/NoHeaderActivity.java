package de.madcyph3r.example.tools;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;

import de.madcyph3r.example.FragmentIndex;
import de.madcyph3r.example.R;
import de.madcyph3r.example.Settings;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawerListener;
import de.madcyph3r.materialnavigationdrawer.item.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.item.MaterialHeadItemListener;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialDevisor;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialSection;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialSectionListener;
import de.madcyph3r.materialnavigationdrawer.tools.RoundedCornersDrawable;

public class NoHeaderActivity extends MaterialNavigationDrawer {

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
        MaterialSection section1 = this.newSection("Section 1", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentIndex(), false, menu);
        MaterialSection section2 = this.newSection("Section 2", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentIndex(), false, menu);

        // set this menu
        this.setCustomMenu(menu);
    }
}
