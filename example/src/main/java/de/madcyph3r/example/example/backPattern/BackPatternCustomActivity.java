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
public class BackPatternCustomActivity extends MaterialNavigationDrawer {

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
        bundle.putString("instruction", "This example shows a custom back pattern. " +
                "Go to 'Section 2' and then press back. It will open section 1 and then instruction and then" +
                " it goes back to the latest activity.");

        drawer = this;

        MaterialMenu menu = new MaterialMenu();

        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu items
        MaterialSection instruction = this.newSection("Instruction", fragmentInstruction , false, menu);
        instruction.setFragmentTitle("Custom Back Pattern");
        this.newSection("Section 1", new FragmentDummy(), false, menu);
        this.newSection("Section 2", new FragmentDummy(), false, menu);

        // set menu
        this.setCustomMenu(menu);

        // set pattern
        this.setBackPattern(MaterialNavigationDrawer.BACKPATTERN_CUSTOM);
    }

    @Override
    protected MaterialSection backToSection(MaterialSection currentSection) {

        // use getCurrentMenu().getSectionPosition, it doesn't considered devisor and label in the list
        // quivalent is getCurrentMenu().getSection(int position)

        // or use getCurrentMenu().getRealSectionPosition, it does considered devisor and label in the list
        // equivalent is getCurrentMenu().getSectionFromRealPosition(int position)
        MaterialSection section;
        switch(getCurrentMenu().getSectionPosition(currentSection)) {
            case 3:
                section = getCurrentMenu().getSection(2);
                this.changeToolbarColor((MaterialSection) section); // remember to change the toolbar color
                break;
            case 2:
                section = getCurrentMenu().getSection(1);
                this.changeToolbarColor((MaterialSection) section); // remember to change the toolbar color
                break;
            case 1:
                section = getCurrentMenu().getSection(0);
                this.changeToolbarColor((MaterialSection) section); // remember to change the toolbar color
                break;
            default:
                section = super.backToSection(currentSection); // exit from activity
                break;
        }

        return section;
    }
}
