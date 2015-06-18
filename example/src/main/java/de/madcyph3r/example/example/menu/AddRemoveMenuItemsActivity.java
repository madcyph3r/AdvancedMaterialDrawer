package de.madcyph3r.example.example.menu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import de.madcyph3r.example.R;
import de.madcyph3r.example.example.FragmentDummy;
import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionOnClickListener;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;
import de.madcyph3r.materialnavigationdrawer.tools.RoundedCornersDrawable;

/**
 * Created by marc on 23.02.2015.
 */
public class AddRemoveMenuItemsActivity extends MaterialNavigationDrawer {

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
        bundle.putString("instruction", "Remove and add menu items at runtime. Open the menu and then " +
                "you can choose your action.");

        drawer = this;

        final MaterialMenu menu = new MaterialMenu();

        //create instruction fragment
        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu items
        MaterialSection instruction = this.newSection("Instruction", new FragmentInstruction(), false, menu);
        instruction.setFragmentTitle("Add And Remove Menu Items");

        this.newSection("Section 1", new FragmentDummy(), false, menu);
        this.newSection("Section 2", new FragmentDummy(), false, menu);
        this.newDevisor(menu);
        MaterialSection section3 = this.newSection("add two sections and one devisor", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentInstruction(), false, menu);

        section3.setOnClickListener(new MaterialSectionOnClickListener() {
            @Override
            public void onClick(MaterialSection section, View v) {
                Toast.makeText(drawer, "added two section", Toast.LENGTH_SHORT).show();

                drawer.newSection("added one", new FragmentDummy(), false, menu);
                drawer.newSection("added two", new FragmentDummy(), false, menu, 0);
                drawer.newDevisor(menu, 1);

                //reloadMenu(int loadSectionPosition) and load the section on the given position (loadSectionPosition)
                // to get a position use this method: menu.getSection(int position); not menu.getSectionFromRealPosition(int position);
                // or insert -1, to load the first section with a fragment

                // this methods reloads only the menu, but does not load a new section
                reloadMenu();
            }
        });

        MaterialSection section4 = this.newSection("add one section at the end", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentInstruction(), false, menu);

        section4.setOnClickListener(new MaterialSectionOnClickListener() {
            @Override
            public void onClick(MaterialSection section, View v) {
                Toast.makeText(drawer, "added one section", Toast.LENGTH_SHORT).show();

                // last true make the refresh possible. then you don't must call reloadMenu(); method
                drawer.newSection("added one", new FragmentDummy(), false, menu, true);
            }
        });

        MaterialSection section5 = this.newSection("remove first menu item", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentInstruction(), false, menu);

        section5.setOnClickListener(new MaterialSectionOnClickListener() {
            @Override
            public void onClick(MaterialSection section, View v) {

                //getCurrentMenu().getRealSectionPosition(yourSection), this give you the real position of an item, to remove it
                if(getCurrentMenu().getItems().size() > 1 ) {
                    Toast.makeText(drawer, "removed first menu item", Toast.LENGTH_SHORT).show();

                    Object o = getCurrentMenu().getItem(0);
                    getCurrentMenu().removeItem(0); // can remove devisor and labels too
                    if (o instanceof MaterialSection && o == getCurrentSection()) {
                        reloadMenu(-1); // load the first fragment with an section, because the current section is removed
                        // reloadMenu(int loadSectionPosition) give here the position of a section.
                        // you can use this method to get the section position: menu.getSectionPosition(...)
                        // do not use menu.getRealSectionPosition(...);
                    } else {
                        reloadMenu();
                    }
                } else {
                    Toast.makeText(drawer, "you shoud have one item", Toast.LENGTH_SHORT).show();
                }
            }
        });

        MaterialSection section6 = this.newSection("remove second menu section", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentInstruction(), false, menu);

        section6.setOnClickListener(new MaterialSectionOnClickListener() {
            @Override
            public void onClick(MaterialSection section, View v) {


                MaterialSection secSection = getCurrentMenu().getSection(1); // get second section
                if(secSection != null) {
                    Toast.makeText(drawer, "remove second menu section", Toast.LENGTH_SHORT).show();

                    int realPos = getCurrentMenu().getRealSectionPosition(secSection);
                    getCurrentMenu().removeItem(realPos); // can remove devisor and labels too
                    if (secSection == getCurrentSection()) {
                        reloadMenu(-1); // load the first fragment with an section, because the current section is removed
                    } else {
                        reloadMenu();
                    }
                } else {
                    Toast.makeText(drawer, "no section is available, to remove", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // set menu
        this.setCustomMenu(menu);
    }
}
