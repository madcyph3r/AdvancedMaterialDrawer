package de.madcyph3r.example.example.theme;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import de.madcyph3r.example.R;
import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.example.example.theme.actionBarOverlayActivity.FragmentActionBarOverlay;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;

public class ActionBarOverlayActivity extends MaterialNavigationDrawer {

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
        bundle.putString("instruction", "Open the menu and press the section 'Show Overlay', to see it."
                + " To add overlay support, see 'android:theme=\"@style/ActionBarOverlayTheme\"' in the AndroidManifest.xml and the" +
                " part for this activity." +
                " The style is defined in the styles.xml with the name ActionBarOverlayTheme. " +
                "Or set it on runtime. For this, see the source code from this example.");

        drawer = this;

        // create menu
        MaterialMenu menu = new MaterialMenu();

        //create instruction fragment
        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create items
        MaterialSection instruction = this.newSection("Instruction", fragmentInstruction , false, menu);
        instruction.setFragmentTitle("Actionbar Overlay");
        this.newDevisor(menu);
        this.newSection("Show Overlay", new FragmentActionBarOverlay(), false, menu);

        menu.setStartIndex(0);
        // set this menu
        this.setCustomMenu(menu);
    }


    // set toolbar overlay and alpha at runtime
    /*@Override
    public void afterInit(Bundle b) {
        // set overlay and Alpha
        this.setToolbarOverlay(true);
        this.setToolbarAlpha(0.5F);

        // or set it in the style with
        //
        // <item name="actionBarOverlay">false</item>
        // <item name="actionBarOverlayAlpha">1.0</item>

    }*/
}