package de.madcyph3r.example.example.listener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import de.madcyph3r.example.example.FragmentDummy;
import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.activity.MaterialNavNoHeaderActivity;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionChangeListener;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSection;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;

public class SectionChangeListenerActivity extends MaterialNavNoHeaderActivity {

    MaterialNavigationDrawer drawer = null;

    @Override
    protected boolean finishActivityOnNewIntent() {
        return false;
    }

    @Override
    protected int getNewIntentRequestCode(Class clazz) {
        return 0;
    }

    private MaterialItemSection tmpSection = null;

    @Override
    public void init(Bundle savedInstanceState) {

        drawer = this;

        drawer.setSectionChangeListener(new MaterialSectionChangeListener() {
            @Override
            public void onBeforeChangeSection(MaterialItemSection newSection) {
                // save the current menu, before change. needed for onAfterChangeSection
                tmpSection = getCurrentSectionFragment();
                if(getCurrentSectionFragment() != newSection) {
                    Toast.makeText(drawer, "before change section", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onAfterChangeSection(MaterialItemSection newSection) {
                // check, if the section really changed
                if(getCurrentSectionFragment() == newSection && newSection != tmpSection) {
                    Toast.makeText(drawer, "after change section", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // information text for the fragment
        Bundle bundle = new Bundle();
        bundle.putString("instruction", "Open the drawer and choose a section. You will get " +
                "a before and after change toast message.");

        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Instruction", fragmentInstruction, "Section Change Listener"));
        menu.add(new MaterialItemSectionFragment(this, "Section 1", new FragmentDummy(), "Section 1"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));
        menu.add(new MaterialItemSectionFragment(this, "Section 3", new FragmentDummy(), "Section 3"));

        // load menu
        this.loadMenu(menu);

        // load the MaterialItemSectionFragment, from the given startIndex
        this.loadStartFragmentFromMenu(menu);
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }
}