package de.madcyph3r.example.example.theme;

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
public class KitKatTranslucentStatusBarActivity extends MaterialNavigationDrawer {

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
    protected int getNewIntentRequestCode(Class clazz) {
        return 0;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        Bundle bundle = new Bundle();
        bundle.putString("instruction", "This example shows a translucent statusbar, if you running kitkat. You can set the status bar in the " +
                "to the application or activity tag in the AndroidManifest.xml . " +
                "See android:theme=\"@style/MaterialNavigationDrawerTheme.TranslucentKitKatStatusBar\" in the " +
                "activity tag, for this activity.");

        drawer = this;

        // create menu
        MaterialMenu menu = new MaterialMenu();

        //create instruction fragment
        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // menu items
        MaterialSection instruction = this.newSection("Instruction", fragmentInstruction , false, menu);
        instruction.setFragmentTitle("KitKat Translucent Statusbar");
        this.newDevisor(menu);
        this.newLabel("Label", false, menu);
        this.newSection("Section", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentDummy(), false, menu);

        // set custom menu
        this.setCustomMenu(menu);
    }
}
