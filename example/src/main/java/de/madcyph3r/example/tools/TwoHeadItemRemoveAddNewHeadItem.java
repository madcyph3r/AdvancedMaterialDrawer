package de.madcyph3r.example.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;

import de.madcyph3r.example.FragmentIndex;
import de.madcyph3r.example.R;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialHeadItemListener;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionOnClickListener;
import de.madcyph3r.materialnavigationdrawer.tools.RoundedCornersDrawable;

/**
 * Created by marc on 23.02.2015.
 */
public class TwoHeadItemRemoveAddNewHeadItem extends MaterialNavigationDrawer {

    MaterialNavigationDrawer drawer = null;

    @Override
    public int headerType() {
        // set type. you get the available constant from MaterialNavigationDrawer class
        return MaterialNavigationDrawer.DRAWERHEADER_HEADITEMS;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        drawer = this;

        // add head Item (menu will be loaded automatically)
        this.addHeadItem(getHeadItem1());
        this.addHeadItem(getHeadItem2());
    }

    private MaterialHeadItem getHeadItem1() {
        MaterialMenu menu = new MaterialMenu();

        // create section
        MaterialSection section1 = this.newSection("Section 1 (Head 1)", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentIndex(), false, menu);
        MaterialSection section2 = this.newSection("Add Head Item", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), false, menu);
        MaterialSection section3 = this.newSection("Remove Last Head Item", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), false, menu);

        section2.setOnClickListener(new MaterialSectionOnClickListener() {
            @Override
            public void onClick(MaterialSection section, View view) {
                // add headItem, true is needed for view change
                drawer.addHeadItem(getHeadItem3(), true);
            }
        });

        section3.setOnClickListener(new MaterialSectionOnClickListener() {
            @Override
            public void onClick(MaterialSection section, View view) {
                drawer.removeHeadItem(drawer.getHeadItemManager().size()-1);
            }
        });

        // use bitmap and make a circle photo
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_drawer_icon);
        final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);

        // create Head Item (on start: Section 1 (Head 1) will be get loaded, if you don't want this, see "Load own Fragment on start")
        MaterialHeadItem headItem = new MaterialHeadItem(this, "A HeadItem", "A Subtitle", drawableAppIcon, R.drawable.mat5, menu);

        headItem.setOnClickListener(new MaterialHeadItemListener() {
            @Override
            public void onClick(MaterialHeadItem headItem) {

            }

            @Override
            public void onBackgroundClick(MaterialHeadItem headItem) {
                Toast.makeText(drawer, "background click", Toast.LENGTH_LONG).show();
            }
        });

        return headItem;
    }

    private MaterialHeadItem getHeadItem2() {
        MaterialMenu menu = new MaterialMenu();

        // create section
        MaterialSection section1 = this.newSection("Section 1 (Head 2)", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentIndex(), false, menu);
        MaterialSection section2 = this.newSection("Section 2 (Head 2)", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentIndex(), false, menu);

        // create icon
        TextDrawable headPhoto = TextDrawable.builder()
                .buildRound("B", Color.BLUE);

        // create Head Item (Start index is section 2)
        MaterialHeadItem headItem = new MaterialHeadItem(this, "B HeadItem No Menu", "B Subtitle", headPhoto, R.drawable.mat6, menu);
        return headItem;
    }

    private int headCount = 1;
    private MaterialHeadItem getHeadItem3() {
        MaterialMenu menu = new MaterialMenu();

        // create section
        MaterialSection section1 = this.newSection("Section 1 (Head 3)", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentIndex(), false, menu);
        MaterialSection section2 = this.newSection("Section 2 (Head 3)", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentIndex(), false, menu);

        // create icon
        TextDrawable headPhoto = TextDrawable.builder()
                .buildRound(headCount+"", Color.BLUE);

        // create Head Item (Start index is section 2)
        MaterialHeadItem headItem = new MaterialHeadItem(this, headCount+" HeadItem No Menu", headCount+" Subtitle", headPhoto, R.drawable.mat6, menu);

        headCount++;
        return headItem;
    }
}
