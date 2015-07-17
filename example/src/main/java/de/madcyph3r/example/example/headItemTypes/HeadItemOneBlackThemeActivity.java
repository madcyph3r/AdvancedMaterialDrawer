package de.madcyph3r.example.example.headItemTypes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import de.madcyph3r.example.R;
import de.madcyph3r.example.example.FragmentDummy;
import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.activity.MaterialNavHeadItemActivity;
import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;
import de.madcyph3r.materialnavigationdrawer.menu.item.style.MaterialItemDevisor;
import de.madcyph3r.materialnavigationdrawer.menu.item.style.MaterialItemLabel;
import de.madcyph3r.materialnavigationdrawer.tools.RoundedCornersDrawable;

public class HeadItemOneBlackThemeActivity extends MaterialNavHeadItemActivity {

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

        // information text for the fragment
        Bundle bundle = new Bundle();
        bundle.putString("instruction", "This example shows the head item style with the white theme.");

        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Instruction", fragmentInstruction, "HeadItem Black Style (One Item)"));
        menu.add(new MaterialItemDevisor());
        menu.add(new MaterialItemSectionFragment(this, "Section 1", new FragmentDummy(), "Section 1"));
        menu.add(new MaterialItemLabel(this, "other sections"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));
        menu.add(new MaterialItemSectionFragment(this, "Section 3", new FragmentDummy(), "Section 3"));

        // create Head Item
        // use bitmap and make a circle photo
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head_item_icon);
        final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);
        MaterialHeadItem headItem = new MaterialHeadItem(this, "My HeadItem", "My Subtitle", drawableAppIcon, R.drawable.mat1, menu);
        this.addHeadItem(headItem);

        // load menu
        this.loadMenu(getCurrentHeadItem().getMenu());

        // load the MaterialItemSectionFragment, from the given startIndex
        this.loadStartFragmentFromMenu(getCurrentHeadItem().getMenu());
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }
}