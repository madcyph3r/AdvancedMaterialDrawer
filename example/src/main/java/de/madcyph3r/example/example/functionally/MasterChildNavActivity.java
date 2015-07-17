package de.madcyph3r.example.example.functionally;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.example.example.functionally.masterChildNavActivity.MasterFragment;
import de.madcyph3r.example.example.functionally.masterChildNavActivity.MasterFragment2;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.activity.MaterialNavNoHeaderActivity;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;
import de.madcyph3r.materialnavigationdrawer.menu.item.style.MaterialItemDevisor;

public class MasterChildNavActivity extends MaterialNavNoHeaderActivity {

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
        bundle.putString("instruction", "This example shows a master child navigation. At the child in 'Master 2' you can open the menu with sliding." +
                "At the child in 'Master 1' the menu is locked. You can't open it.");

        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Instruction", fragmentInstruction, "Master Child Navigation"));
        menu.add(new MaterialItemDevisor());
        menu.add(new MaterialItemSectionFragment(this, "Master 1", new MasterFragment(), "Master 1"));
        menu.add(new MaterialItemSectionFragment(this, "Master 2", new MasterFragment2(), "Master 2"));

        // load menu
        this.loadMenu(menu);

        // load first MaterialItemSectionFragment in the menu, because there is no start position
        this.loadStartFragmentFromMenu(menu);
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }
}
