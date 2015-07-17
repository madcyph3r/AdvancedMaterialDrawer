package de.madcyph3r.example.example.functionally;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.example.example.functionally.actionBarButtonsActivity.FragmentActionBarButtons;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.activity.MaterialNavNoHeaderActivity;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;
import de.madcyph3r.materialnavigationdrawer.menu.item.style.MaterialItemDevisor;

public class ActionBarButtonsActivity extends MaterialNavNoHeaderActivity {

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
        bundle.putString("instruction", "Open the drawer and choose the section \"Change Actionbar Buttons Section\". \" +\n" +
                "                \"Now you get a view, there you can change the actionbar button.");

        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Instruction", fragmentInstruction, "Change/Hide Actionbar Button"));
        menu.add(new MaterialItemDevisor());
        menu.add(new MaterialItemSectionFragment(this, "Change Actionbar Button",  new FragmentActionBarButtons(), "Change Actionbar Button"));

        //load menu
        this.loadMenu(menu);

        // load first MaterialItemSectionFragment in the menu, because there is no start position
        this.loadStartFragmentFromMenu(menu);
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }

    @TargetApi(11)
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuItem menuItem = null;

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            menuItem = menu.add(0, 0, 0, "HELP").setIcon(android.R.drawable.ic_menu_help);
            menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        } else {
            menuItem = menu.add(0, 0, 0, "HELP").setIcon(android.R.drawable.ic_menu_help);
        }

        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(drawer, "i'm a menu button", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return true;
    }
}