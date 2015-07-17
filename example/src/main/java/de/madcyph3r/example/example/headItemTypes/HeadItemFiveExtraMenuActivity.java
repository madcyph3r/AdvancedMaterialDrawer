package de.madcyph3r.example.example.headItemTypes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;

import de.madcyph3r.example.R;
import de.madcyph3r.example.example.FragmentDummy;
import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.activity.MaterialNavHeadItemActivity;
import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionOnClickListener;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSection;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionOnClick;
import de.madcyph3r.materialnavigationdrawer.tools.RoundedCornersDrawable;

public class HeadItemFiveExtraMenuActivity extends MaterialNavHeadItemActivity {

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
        this.addHeadItem(getHeadItem3());
        this.addHeadItem(getHeadItem4());
        this.addHeadItem(getHeadItem5());

        // load menu
        this.loadMenu(getCurrentHeadItem().getMenu());

        // load the MaterialItemSectionFragment, from the given startIndex
        this.loadStartFragmentFromMenu(getCurrentHeadItem().getMenu());

        // create switcher extra menu
        MaterialItemSectionOnClick section = new MaterialItemSectionOnClick(this, "do something");
        section.setOnSectionClickListener(new MaterialSectionOnClickListener() {
            @Override
            public void onClick(MaterialItemSection section, View view) {
                Toast.makeText(drawer, " do something here", Toast.LENGTH_LONG).show();
            }
        });
        MaterialMenu menu = new MaterialMenu();
        menu.add(section);

        // add menu to switcher
        this.setSwitcherExtraMenu(menu, false);

        // show switch button, even if only one head item
        this.setSwitchShowForce(true);

    }

    private MaterialHeadItem getHeadItem1() {

        // information text for the fragment
        Bundle bundle = new Bundle();
        bundle.putString("instruction", "This example shows the head item style with five items. \" +\n" +
                "                \"The first three head items ar shown with a picture. To get the other head items, \" +\n" +
                "                \"press the down arrow button in the header. \" +\n" +
                "                \"Under the items, you see the extra menu.");

        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Instruction", fragmentInstruction, "Switcher Extra Menu (Five Items)"));
        menu.add(new MaterialItemSectionFragment(this, "Section 1", new FragmentDummy(), "Section 1"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));
        menu.add(new MaterialItemSectionFragment(this, "Section 3", new FragmentDummy(), "Section 3"));


        // create Head Item
        // use bitmap and make a circle photo
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head_item_icon);
        final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);
        MaterialHeadItem headItem = new MaterialHeadItem(this, "A HeadItem", "A Subtitle", drawableAppIcon, R.drawable.mat1, menu);

        return headItem;
    }

    private MaterialHeadItem getHeadItem2() {

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Section 1 (Head 2)", new FragmentDummy(), "Section 1 (Head 2)"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));

        // create Head Item
        TextDrawable headPhoto = TextDrawable.builder()
                .buildRound("B", Color.BLUE);

        MaterialHeadItem headItem = new MaterialHeadItem(this, "B HeadItem", "B Subtitle", headPhoto, R.drawable.mat6, menu);

        return headItem;
    }

    private MaterialHeadItem getHeadItem3() {

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Section 1 (Head 3)", new FragmentDummy(), "Section 1 (Head 3)"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));

        // create Head Item
        TextDrawable headPhoto = TextDrawable.builder()
                .buildRound("C", Color.GRAY);
        MaterialHeadItem headItem = new MaterialHeadItem(this, "C HeadItem", "C Subtitle", headPhoto, R.drawable.mat6, menu);

        return headItem;
    }

    private MaterialHeadItem getHeadItem4() {

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Section 1 (Head 4)", new FragmentDummy(), "Section 1 (Head 4)"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));

        // create Head Item
        TextDrawable headPhoto = TextDrawable.builder()
                .buildRound("D", Color.GRAY);
        MaterialHeadItem headItem = new MaterialHeadItem(this, "D HeadItem", "D Subtitle", headPhoto, R.drawable.mat6, menu);

        return headItem;
    }

    private MaterialHeadItem getHeadItem5() {

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Section 1 (Head 5)", new FragmentDummy(), "Section 1 (Head 5)"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));

        // create Head Item
        TextDrawable headPhoto = TextDrawable.builder()
                .buildRound("E", Color.GRAY);
        MaterialHeadItem headItem = new MaterialHeadItem(this, "E HeadItem", "E Subtitle", headPhoto, R.drawable.mat6, menu);

        return headItem;
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }
}