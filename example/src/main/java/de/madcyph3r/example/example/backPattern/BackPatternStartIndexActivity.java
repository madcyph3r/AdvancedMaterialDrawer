package de.madcyph3r.example.example.backPattern;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import de.madcyph3r.example.R;
import de.madcyph3r.example.example.FragmentDummy;
import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;
import de.madcyph3r.materialnavigationdrawer.tools.RoundedCornersDrawable;

/**
 * Created by marc on 23.02.2015.
 */
public class BackPatternStartIndexActivity extends MaterialNavigationDrawer {

    MaterialNavigationDrawer drawer = null;

    @Override
    public int headerType() {
        // set type. you get the available constant from MaterialNavigationDrawer class
        return MaterialNavigationDrawer.DRAWERHEADER_NO_HEADER;
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

        Bundle bundle = new Bundle();
        bundle.putString("instruction", "On the back button the start section will be selected and opened." +
                " If the start section open, it will call super.onBackPressed() method.");

        drawer = this;

        MaterialMenu menu = new MaterialMenu();

        //create instruction fragment
        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu items
        MaterialSection instruction = this.newSection("Instruction", new FragmentInstruction(), false, menu);
        instruction.setFragmentTitle("StartIndex Back Pattern");
        this.newSection("Section 1", new FragmentDummy(), false, menu);
        this.newSection("Section 2", new FragmentDummy(), false, menu);
        this.newSection("Section 3", new FragmentDummy(), false, menu);

        // set start section to instruction
        menu.setStartIndex(0);

        // set menu
        this.setCustomMenu(menu);

        // set Pattern
        this.setBackPattern(MaterialNavigationDrawer.BACKPATTERN_BACK_TO_START_INDEX);
    }
}
