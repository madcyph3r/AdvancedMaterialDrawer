package de.madcyph3r.example.example.headerTypes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import de.madcyph3r.example.R;
import de.madcyph3r.example.example.FragmentDummy;
import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;

public class CustomHeaderBelowToolbarActivity extends MaterialNavigationDrawer {

    MaterialNavigationDrawer drawer = null;

    @Override
    public int headerType() {
        // set type. you get the available constant from MaterialNavigationDrawer class
        return MaterialNavigationDrawer.DRAWERHEADER_CUSTOM;
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

        // see AndroidManifest.xml and styles.xml, for belowToolbar. And example_custom_header_below_toolbar

        Bundle bundle = new Bundle();
        bundle.putString("instruction", "This example has an custom header in the drawer. " +
                "The drawer is shown under the toolbar. " +
                "See the method headerType in the source code. And don't forget to call " +
                "setCustomMenu(), to set your menu and setDrawerHeaderImage(), to set your image.");

        drawer = this;

        MaterialMenu menu = new MaterialMenu();

        //create instruction fragment
        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // menu items
        MaterialSection instruction = this.newSection("Instruction", fragmentInstruction , false, menu);
        instruction.setFragmentTitle("Custom Header Below Toolbar");
        this.newDevisor(menu);
        this.newLabel("Label", false, menu);
        this.newSection("Section", new FragmentDummy(), false, menu);

        // set start index
        menu.setStartIndex(0);

        // set this menu
        this.setCustomMenu(menu);

        // create and set the recycleview_header
        View view = LayoutInflater.from(this).inflate(R.layout.example_custom_header_below_toolbar,null);
        this.setDrawerHeaderCustom(view);

    }
}