package de.madcyph3r.example.example.listener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import de.madcyph3r.example.R;
import de.madcyph3r.example.example.FragmentDummy;
import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;

/**
 * Created by marc on 23.02.2015.
 */
public class SectionChangeListenerActivity extends MaterialNavigationDrawer {

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
        bundle.putString("instruction", "Open the drawer and choose a section. You will get " +
                "a before and after change toast message.");

        drawer = this;

        // create menu
        MaterialMenu menu = new MaterialMenu();

        //create instruction fragment
        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // menu items
        MaterialSection instruction = this.newSection("Instruction", fragmentInstruction , false, menu);
        instruction.setFragmentTitle("Section Change Listener");
        this.newDevisor(menu);
        this.newLabel("Label", false, menu);
        this.newSection("Section", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentDummy(), false, menu);

        // set custom menu
        this.setCustomMenu(menu);
    }


    MaterialSection tmpSection = null;

    // before section change listener
    @Override
    public void onBeforeChangeSection(MaterialSection newSection) {
        // save the current menu, before change. needed for onAfterChangeSection
        tmpSection = getCurrentSection();
        if(getCurrentSection() != newSection) {
            Toast.makeText(this, "before change section", Toast.LENGTH_SHORT).show();
        }
    }

    // after section change listener
    @Override
    public void onAfterChangeSection(MaterialSection newSection) {
        // check, if the section really changed
        if(getCurrentSection() == newSection && newSection != tmpSection) {
            Toast.makeText(this, "after change section", Toast.LENGTH_SHORT).show();
        }
    }
}
