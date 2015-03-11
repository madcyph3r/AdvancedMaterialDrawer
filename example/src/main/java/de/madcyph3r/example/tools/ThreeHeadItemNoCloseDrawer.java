package de.madcyph3r.example.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import com.amulyakhare.textdrawable.TextDrawable;

import de.madcyph3r.example.FragmentIndex;
import de.madcyph3r.example.R;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;
import de.madcyph3r.materialnavigationdrawer.tools.RoundedCornersDrawable;

/**
 * Created by marc on 23.02.2015.
 */
public class ThreeHeadItemNoCloseDrawer extends MaterialNavigationDrawer {

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
        this.addHeadItem(getHeadItem3());
        this.addHeadItem(getHeadItem4());

        this.setHeadItemSwitchShowForce(true);
    }

    private MaterialHeadItem getHeadItem1() {
        MaterialMenu menu = new MaterialMenu();

        // create section
        MaterialSection section1 = this.newSection("Section 1 (Head 1)", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentIndex(), false, menu);
        MaterialSection section2 = this.newSection("Section 2 (Head 1)", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentIndex(), false, menu);

        // use bitmap and make a circle photo
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_drawer_icon);
        final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);

        // create Head Item
        MaterialHeadItem headItem = new MaterialHeadItem(this, "A HeadItem", "A Subtitle", drawableAppIcon, R.drawable.mat5, menu);
        headItem.setCloseDrawerOnChanged(false);
        return headItem;
    }

    private MaterialHeadItem getHeadItem2() {
        MaterialMenu menu = new MaterialMenu();

        // create section
        MaterialSection section1 = this.newSection("Section 1 (Head 2)", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentIndex(), false, menu);
        MaterialSection section2 = this.newSection("Section 2", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentIndex(), false, menu);

        // create icon
        TextDrawable headPhoto = TextDrawable.builder()
                .buildRound("B", Color.BLUE);

        // create Head Item (Start index is section 2)
        MaterialHeadItem headItem = new MaterialHeadItem(this, "B HeadItem", "B Subtitle", headPhoto, R.drawable.mat6, menu);
        headItem.setCloseDrawerOnChanged(false);
        return headItem;
    }

    private MaterialHeadItem getHeadItem3() {
        MaterialMenu menu = new MaterialMenu();

        // create section
        MaterialSection section1 = this.newSection("Section 1 (Head 3)", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentIndex(), false, menu);
        MaterialSection section2 = this.newSection("Section 2", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentIndex(), false, menu);

        // create icon
        TextDrawable headPhoto = TextDrawable.builder()
                .buildRound("C", Color.GRAY);

        // create Head Item (Start index is section 2)
        MaterialHeadItem headItem = new MaterialHeadItem(this, "C HeadItem", "C Subtitle", headPhoto, R.drawable.mat6, menu);
        headItem.setCloseDrawerOnChanged(false);
        return headItem;
    }

    private MaterialHeadItem getHeadItem4() {
        MaterialMenu menu = new MaterialMenu();

        // create section
        MaterialSection section1 = this.newSection("Section 1 (Head 4)", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentIndex(), false, menu);
        MaterialSection section2 = this.newSection("Section 2", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentIndex(), false, menu);

        // create icon
        TextDrawable headPhoto = TextDrawable.builder()
                .buildRound("D", Color.GRAY);

        // create Head Item (Start index is section 2)
        MaterialHeadItem headItem = new MaterialHeadItem(this, "D HeadItem", "D Subtitle", headPhoto, R.drawable.mat6, menu);
        headItem.setCloseDrawerOnChanged(false);
        return headItem;
    }
}
