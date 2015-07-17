package de.madcyph3r.example.example.functionally;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import de.madcyph3r.example.example.functionally.noClosePrevDrawerActivity.NoCloseActivity;
import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.activity.MaterialNavNoHeaderActivity;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionActivity;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;
import de.madcyph3r.materialnavigationdrawer.menu.item.style.MaterialItemDevisor;

/**
 * Created by marc on 23.02.2015.
 */
public class NoClosePrevDrawerActivity_Activity extends MaterialNavNoHeaderActivity {

    MaterialNavigationDrawer drawer = null;

    // close drawer activity
    @Override
    public boolean finishActivityOnNewIntent() {
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
        bundle.putString("instruction", "Open the drawer and choose the 'start activity' section. Then press back, \" +\n" +
                "                \"you will get back to this activity.");

        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Instruction", fragmentInstruction, "No Close Previous Drawer Activity"));
        menu.add(new MaterialItemDevisor());
        menu.add(new MaterialItemSectionActivity(this, "start Activity", new Intent(this, NoCloseActivity.class)));

        // load menu
        this.loadMenu(menu);

        // load first MaterialItemSectionFragment in the menu, because there is no start position
        this.loadStartFragmentFromMenu(menu);
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }
}