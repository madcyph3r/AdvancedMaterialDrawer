package de.madcyph3r.example.example.functionally;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.example.example.functionally.actionBarButtons.FragmentActionBarButtons;
import de.madcyph3r.example.example.functionally.masterChildNavActivity.MasterFragment;
import de.madcyph3r.example.example.functionally.masterChildNavActivity.MasterFragment2;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;

/**
 * Created by marc on 01.04.2015.
 */
public class MasterChildNavActivity extends MaterialNavigationDrawer {

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
        bundle.putString("instruction", "This example shows a master child navigation. At the child in 'Master 2' you can open the menu with sliding." +
                "At the child in 'Master 1' the menu is locked. You can't open it.");

        drawer = this;

        // create menu
        MaterialMenu menu = new MaterialMenu();

        //create instruction fragment
        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // menu items
        MaterialSection instruction = this.newSection("Instruction", fragmentInstruction, false, menu);
        instruction.setFragmentTitle("Master Child Navigation");
        this.newDevisor(menu);
        this.newLabel("Label", false, menu);
        MaterialSection section = this.newSection("Master 1", new MasterFragment(), false, menu);
        this.newSection("Master 2", new MasterFragment2(), false, menu);

        setCustomMenu(menu);
    }
}
