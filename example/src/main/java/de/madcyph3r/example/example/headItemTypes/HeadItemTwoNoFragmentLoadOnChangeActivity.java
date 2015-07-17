package de.madcyph3r.example.example.headItemTypes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.amulyakhare.textdrawable.TextDrawable;

import de.madcyph3r.example.R;
import de.madcyph3r.example.example.FragmentDummy;
import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.activity.MaterialNavHeadItemActivity;
import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;
import de.madcyph3r.materialnavigationdrawer.tools.RoundedCornersDrawable;

public class HeadItemTwoNoFragmentLoadOnChangeActivity extends MaterialNavHeadItemActivity {

    MaterialNavigationDrawer drawer = null;

    @Override
    protected boolean finishActivityOnNewIntent() {
        return false;
    }

    @Override
    protected int getNewIntentRequestCode(Class clazz) {
        return 0;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        drawer = this;

        // add head Item (menu will be loaded automatically)
        this.addHeadItem(getHeadItem1());
        this.addHeadItem(getHeadItem2());

        // load menu
        this.loadMenu(getCurrentHeadItem().getMenu());

        // load the MaterialItemSectionFragment, from the given startIndex
        this.loadStartFragmentFromMenu(getCurrentHeadItem().getMenu());
    }

    private MaterialHeadItem getHeadItem1() {

        // information text for the fragment
        Bundle bundle = new Bundle();
        bundle.putString("instruction", "If you change the head item, the fragment doesn't change.\" +\n" +
                "                \" Useful, if you don't want to replace the current fragment on a new head item.");

        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Instruction", fragmentInstruction, "Fragment Doesn't Change On HeadItem Change (Two Items)"));
        menu.add(new MaterialItemSectionFragment(this, "Section 1", new FragmentDummy(), "Section 1"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));
        menu.add(new MaterialItemSectionFragment(this, "Section 3", new FragmentDummy(), "Section 3"));

        // create Head Item
        // use bitmap and make a circle photo
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head_item_icon);
        final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);
        MaterialHeadItem headItem = new MaterialHeadItem(this, "A HeadItem", "A Subtitle", drawableAppIcon, R.drawable.mat1, menu);

        // don't change fragment on change
        headItem.setLoadFragmentOnChanged(false);

        return headItem;
    }

    // head item 2 has no menu You can use this for an avatar click
    private MaterialHeadItem getHeadItem2() {

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Section 1", new FragmentDummy(), "Section 1"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));
        menu.add(new MaterialItemSectionFragment(this, "Section 3", new FragmentDummy(), "Section 3"));

        // create Head Item
        TextDrawable headPhoto = TextDrawable.builder()
                .buildRound("B", Color.BLUE);

        MaterialHeadItem headItem = new MaterialHeadItem(this, "B HeadItem", "B Subtitle", headPhoto, R.drawable.mat6, menu);

        // don't change fragment on change
        headItem.setLoadFragmentOnChanged(false);

        return headItem;
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }
}
