package de.madcyph3r.example.example.functionally;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;

import de.madcyph3r.example.R;
import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionOnClickListener;
import de.madcyph3r.materialnavigationdrawer.tools.RoundedCornersDrawable;

/**
 * Created by marc on 23.02.2015.
 */
public class AddRemoveHeadItemRuntimeActivity extends MaterialNavigationDrawer {

    MaterialNavigationDrawer drawer = null;

    @Override
    public int headerType() {
        // set type. you get the available constant from MaterialNavigationDrawer class
        return MaterialNavigationDrawer.DRAWERHEADER_HEADITEMS;
    }

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
    }

    private MaterialHeadItem getHeadItem1() {

        Bundle bundle = new Bundle();
        bundle.putString("instruction", "Here you can add new head-items and/or remove existing head-items. " +
                "Open the menu and choose the action you want.");

        // create menu
        MaterialMenu menu = new MaterialMenu();

        //create instruction fragment
        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create items
        MaterialSection instruction = this.newSection("Instruction", fragmentInstruction , false, menu);
        instruction.setFragmentTitle("Add Remove HeadItem At Runtime");
        MaterialSection addSection = this.newSection("Add Head Item", false, menu);
        MaterialSection removeSection = this.newSection("Remove Last Head Item", false, menu);

        addSection.setOnClickListener(new MaterialSectionOnClickListener() {
            @Override
            public void onClick(MaterialSection section, View view) {
                // add headItem, true is needed for view change
                drawer.addHeadItem(getHeadItem3(), true);
            }
        });

        removeSection.setOnClickListener(new MaterialSectionOnClickListener() {
            @Override
            public void onClick(MaterialSection section, View view) {
                if (drawer.getHeadItemManager().size() > 1)
                    drawer.removeHeadItem(drawer.getHeadItemManager().size() - 1);
                else
                    Toast.makeText(drawer, "You can't remove all head-items.", Toast.LENGTH_SHORT).show();
            }
        });

        // use bitmap and make a circle photo
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_drawer_icon);
        final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);

        // create Head Item (on start: Section 1 (Head 1) will be get loaded, if you don't want this, see "Load own Fragment on start")
        MaterialHeadItem headItem = new MaterialHeadItem(this, "A HeadItem", "A Subtitle", drawableAppIcon, R.drawable.mat5, menu);

        return headItem;
    }

    private MaterialHeadItem getHeadItem2() {

        // create menu
        MaterialMenu menu = new MaterialMenu();

        // create items
        this.newSection("Section 1 (Head 2)", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentInstruction(), false, menu);
        this.newSection("Section 2 (Head 2)", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentInstruction(), false, menu);

        // create icon
        TextDrawable headPhoto = TextDrawable.builder()
                .buildRound("B", Color.BLUE);

        // create Head Item (Start index is section 2)
        MaterialHeadItem headItem = new MaterialHeadItem(this, "B HeadItem No Menu", "B Subtitle", headPhoto, R.drawable.mat6, menu);
        return headItem;
    }

    private int headCount = 3;
    private MaterialHeadItem getHeadItem3() {

        // create menu
        MaterialMenu menu = new MaterialMenu();

        // create items
        this.newSection("Section 1 (Head "+headCount+")", new FragmentInstruction(), false, menu);
        this.newSection("Section 2 (Head "+headCount+")", new FragmentInstruction(), false, menu);

        // create icon
        TextDrawable headPhoto = TextDrawable.builder()
                .buildRound(headCount+"", Color.BLUE);

        // create Head Item (Start index is section 2)
        MaterialHeadItem headItem = new MaterialHeadItem(this, headCount+" HeadItem No Menu", headCount+" Subtitle", headPhoto, R.drawable.mat6, menu);

        headCount++;
        return headItem;
    }
}
