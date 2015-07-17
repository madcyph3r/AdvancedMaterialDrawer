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
import de.madcyph3r.example.example.FragmentDummy;
import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.materialnavigationdrawer.activity.MaterialNavHeadItemActivity;
import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionOnClick;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSection;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionOnClickListener;
import de.madcyph3r.materialnavigationdrawer.tools.RoundedCornersDrawable;

public class AddRemoveHeadItemRuntimeActivity extends MaterialNavHeadItemActivity {

    MaterialNavHeadItemActivity drawer = null;

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

        // load first MaterialItemSectionFragment in the menu from the current head item
        this.loadStartFragmentFromMenu(getCurrentHeadItem().getMenu());
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }

    private MaterialHeadItem getHeadItem1() {

        // information text for the fragment
        Bundle bundle = new Bundle();
        bundle.putString("instruction", "Here you can add new head-items and/or remove existing head-items. " +
                "Open the menu and choose the action you want.");

        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create sections
        MaterialItemSectionFragment instruction = new MaterialItemSectionFragment(this, "Instruction", fragmentInstruction, "Add Remove HeadItem At Runtime");
        MaterialItemSectionOnClick addSection = new MaterialItemSectionOnClick(this, "Add Head Item");
        MaterialItemSectionOnClick removeSection = new MaterialItemSectionOnClick(this, "Remove Last Head Item");

        // add OnClickListener to MaterialItemSectionOnClick sections
        addSection.setOnSectionClickListener(new MaterialSectionOnClickListener() {
            @Override
            public void onClick(MaterialItemSection section, View view) {
                // add headItem, true is needed for view change
                drawer.addHeadItem(getHeadItem3(), true);
            }
        });
        removeSection.setOnSectionClickListener(new MaterialSectionOnClickListener() {
            @Override
            public void onClick(MaterialItemSection section, View view) {
                if (drawer.getHeadItemManager().size() > 1)
                    drawer.removeHeadItem(drawer.getHeadItemManager().size() - 1);
                else
                    Toast.makeText(drawer, "You can't remove all head-items.", Toast.LENGTH_SHORT).show();
            }
        });

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(instruction);
        menu.add(addSection);
        menu.add(removeSection);

        // create headItem
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head_item_icon);
        final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);
        MaterialHeadItem headItem = new MaterialHeadItem(this, "A HeadItem", "A Subtitle", drawableAppIcon, R.drawable.mat1, menu);

        return headItem;
    }

    private MaterialHeadItem getHeadItem2() {

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Section 1", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentDummy(), "Section 1"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));


        // create headItem
        TextDrawable headPhoto = TextDrawable.builder()
                .buildRound("B", Color.BLUE);
        MaterialHeadItem headItem = new MaterialHeadItem(this, "B HeadItem No Menu", "B Subtitle", headPhoto, R.drawable.mat6, menu);

        return headItem;
    }

    private int headCount = 3;
    private MaterialHeadItem getHeadItem3() {

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Section 1 (Head " + headCount + ")", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentDummy(), "Section 1 (Head " + headCount + ")"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2 (Head " + headCount + ")", new FragmentDummy(), "Section 2 (Head " + headCount + ")"));

        // create headItem
        TextDrawable headPhoto = TextDrawable.builder()
                .buildRound(headCount+"", Color.BLUE);
        MaterialHeadItem headItem = new MaterialHeadItem(this, headCount+" HeadItem No Menu", headCount+" Subtitle", headPhoto, R.drawable.mat6, menu);

        // counter for the new headItem
        headCount++;

        return headItem;
    }
}
