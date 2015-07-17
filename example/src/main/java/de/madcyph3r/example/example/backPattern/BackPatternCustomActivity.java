package de.madcyph3r.example.example.backPattern;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import de.madcyph3r.example.example.FragmentDummy;
import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.activity.MaterialNavNoHeaderActivity;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSection;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;

public class BackPatternCustomActivity extends MaterialNavNoHeaderActivity {

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
        bundle.putString("instruction", "This example shows a custom back pattern. " +
                "Go to 'Section 2' and then press back. It will open section 1 and then instruction and then" +
                " it goes back to the latest activity.");

        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Instruction", fragmentInstruction, "Custom Back Pattern"));
        menu.add(new MaterialItemSectionFragment(this, "Section 1", new FragmentDummy(), "Section 1"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));

        // load menu
        this.loadMenu(menu);

        // load first MaterialItemSectionFragment in the menu, because there is no start position
        this.loadStartFragmentFromMenu(menu);

        // set back pattern
        this.setBackPattern(MaterialNavigationDrawer.BACKPATTERN_CUSTOM);
    }

    @Override
    protected MaterialItemSection backToSection(MaterialItemSection currentSection) {

        // use getCurrentMenu().getSectionPosition, it doesn't considered devisor and label in the list
        // quivalent is getCurrentMenu().getSection(int position)

        // or use getCurrentMenu().getRealSectionPosition, it does considered devisor and label in the list
        // equivalent is getCurrentMenu().getSectionFromRealPosition(int position)
        MaterialItemSection section;
        switch(getCurrentMenu().getSectionPosition(currentSection)) {
            case 3:
                section = getCurrentMenu().getSection(2);
                this.changeToolbarColor((MaterialItemSection) section); // remember to change the toolbar color
                break;
            case 2:
                section = getCurrentMenu().getSection(1);
                this.changeToolbarColor((MaterialItemSection) section); // remember to change the toolbar color
                break;
            case 1:
                section = getCurrentMenu().getSection(0);
                this.changeToolbarColor((MaterialItemSection) section); // remember to change the toolbar color
                break;
            default:
                section = super.backToSection(currentSection); // exit from activity
                break;
        }

        return section;
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }
}
