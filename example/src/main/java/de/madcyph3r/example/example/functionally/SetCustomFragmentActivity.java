package de.madcyph3r.example.example.functionally;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import de.madcyph3r.example.R;
import de.madcyph3r.example.example.FragmentDummy;
import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;

/**
 * Created by marc on 23.02.2015.
 */
public class SetCustomFragmentActivity extends MaterialNavigationDrawer {

    // info: see manifest for the dark theme

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
    public void init(Bundle savedInstanceState) {

        drawer = this;

        // create menu
        MaterialMenu menu = new MaterialMenu();

        this.newSection("Section", new FragmentDummy(), false, menu);
        // menu items
        this.newDevisor(menu);
        this.newLabel("Label", false, menu);
        this.newSection("Section 2", new FragmentDummy(), false, menu);

        // set custom menu
        this.setCustomMenu(menu);

        // don't load a standard fragment
        this.setLoadFragmentOnStart(false);
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

        Bundle bundle = new Bundle();
        bundle.putString("instruction", "This activity starts with a custom fragment. " +
                "It's not from the menu.");

        //create instruction fragment
        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // set custom fragment
        this.setCustomFragment(fragmentInstruction, "Custom Fragment Instruction");
    }

    @Override
    protected int getNewIntentRequestCode(Class clazz) {
        return 0;
    }
}
