package de.madcyph3r.example.example.drawerTypes;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import de.madcyph3r.example.example.FragmentDummy;
import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.activity.MaterialNavNoHeaderActivity;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;
import de.madcyph3r.materialnavigationdrawer.menu.item.style.MaterialItemDevisor;
import de.madcyph3r.materialnavigationdrawer.menu.item.style.MaterialItemLabel;

public class MultiPaneSupportBelowToolbarActivity extends MaterialNavNoHeaderActivity {

    // info: see manifest for the tablet support

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
        bundle.putString("instruction", "If you have a tablet, the drawer will be always shown under the toolbar.\" +\n" +
                "                \" See the styles.xml for the tablet and below toolbar support.");

        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Instruction", fragmentInstruction, "MultiPane (Tablet) And Below ToolBar Support"));
        menu.add(new MaterialItemDevisor());
        menu.add(new MaterialItemLabel(this, "Sections"));
        menu.add(new MaterialItemSectionFragment(this, "Section 1", new FragmentDummy(), "Section 1"));

        //load menu
        this.loadMenu(menu);

        // load first MaterialItemSectionFragment in the menu, because there is no start position
        this.loadStartFragmentFromMenu(menu);
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }
}