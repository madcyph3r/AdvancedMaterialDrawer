package de.madcyph3r.example.example.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import de.madcyph3r.example.example.FragmentDummy;
import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.activity.MaterialNavNoHeaderActivity;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionOnClickListener;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSection;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionOnClick;
import de.madcyph3r.materialnavigationdrawer.menu.item.style.MaterialItemDevisor;


public class AddRemoveMenuItemsActivity extends MaterialNavNoHeaderActivity {

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
        bundle.putString("instruction", "Remove and add menu items at runtime. Open the menu and then " +
                "you can choose your action.");

        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu
        final MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Instruction", fragmentInstruction, "Add And Remove Menu Items"));
        menu.add(new MaterialItemSectionFragment(this, "Section 1", new FragmentDummy(), "Section 1"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));

        MaterialItemSectionOnClick section3 = new MaterialItemSectionOnClick(this, "add two sections and one devisor");
        menu.add(section3);
        section3.setOnSectionClickListener(new MaterialSectionOnClickListener() {
            @Override
            public void onClick(MaterialItemSection section, View v) {
                Toast.makeText(drawer, "added two section", Toast.LENGTH_SHORT).show();

                // add two items on the first position
                menu.add(new MaterialItemDevisor());
                menu.add(0, new MaterialItemSectionFragment(drawer, "added two", new FragmentDummy(), "added two"));
                menu.add(0, new MaterialItemSectionFragment(drawer, "added one", new FragmentDummy(), "added one"));

                // reloads the current menu, this is "menu"
                reloadMenu();
            }
        });

        MaterialItemSectionOnClick section4 = new MaterialItemSectionOnClick(this, "add one section at the end");
        menu.add(section4);
        section4.setOnSectionClickListener(new MaterialSectionOnClickListener() {
            @Override
            public void onClick(MaterialItemSection section, View v) {
                Toast.makeText(drawer, "added one section", Toast.LENGTH_SHORT).show();

                // add one item on the last position
                menu.add(new MaterialItemDevisor());
                menu.add(new MaterialItemSectionFragment(drawer, "added one", new FragmentDummy(), "added one"));

                // reloads the current menu, this is "menu"
                reloadMenu();
            }
        });

        MaterialItemSectionOnClick section5 = new MaterialItemSectionOnClick(this, "remove first menu item");
        menu.add(section5);
        section5.setOnSectionClickListener(new MaterialSectionOnClickListener() {
            @Override
            public void onClick(MaterialItemSection section, View v) {

                if (getCurrentMenu().getItems().size() > 1) {
                    Toast.makeText(drawer, "removed first menu item", Toast.LENGTH_SHORT).show();

                    // remove first menu item
                    getCurrentMenu().getItems().remove(0);

                    // reloads the current menu, this is "menu"
                    reloadMenu();
                }


            }
        });

        // load menu
        this.loadMenu(menu);

        // load the MaterialItemSectionFragment, from the given startIndex
        this.loadStartFragmentFromMenu(menu);
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }
}