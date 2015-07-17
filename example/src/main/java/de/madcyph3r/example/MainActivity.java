package de.madcyph3r.example;

import android.graphics.Color;
import android.os.Bundle;

import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.example.fragment.BackPatternFragment;
import de.madcyph3r.example.fragment.DrawerTypesFragment;
import de.madcyph3r.example.fragment.FunctionallyFragment;
import de.madcyph3r.example.fragment.HeadItemTypesFragment;
import de.madcyph3r.example.fragment.HeaderTypesFragment;
import de.madcyph3r.example.fragment.ListenerFragment;
import de.madcyph3r.example.fragment.MenuFragment;
import de.madcyph3r.example.fragment.ThemeFragment;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.activity.MaterialNavNoHeaderActivity;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;
import de.madcyph3r.materialnavigationdrawer.menu.item.style.MaterialItemDevisor;
import de.madcyph3r.materialnavigationdrawer.menu.item.style.MaterialItemLabel;

public class MainActivity extends MaterialNavNoHeaderActivity {

    private MaterialNavigationDrawer drawer = null;

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

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Instruction", new FragmentInstruction(), "Instruction"));
        menu.add(new MaterialItemDevisor());
        menu.add(new MaterialItemLabel(this, "Examples"));
        menu.add(new MaterialItemSectionFragment(this, "Theme", new ThemeFragment(), "Theme").setSectionColor(Color.parseColor("#ff0000")));
        menu.add(new MaterialItemSectionFragment(this, "Drawer Types", new DrawerTypesFragment(), "Drawer Types").setSectionColor(Color.parseColor("#D35400")));
        menu.add(new MaterialItemSectionFragment(this, "Header Types", new HeaderTypesFragment(), "Header Types").setSectionColor(Color.parseColor("#01AEA1")));
        menu.add(new MaterialItemSectionFragment(this, "Head Item Types", new HeadItemTypesFragment(), "Header Types").setSectionColor(Color.parseColor("#9B59B6")));
        menu.add(new MaterialItemSectionFragment(this, "Menu", new MenuFragment(), "Menu").setSectionColor(Color.parseColor("#3498DB")));
        menu.add(new MaterialItemSectionFragment(this, "Back Pattern", new BackPatternFragment(), "Back Pattern").setSectionColor(Color.parseColor("#3CD876")));
        menu.add(new MaterialItemSectionFragment(this, "Listener", new ListenerFragment(), "Listener").setSectionColor(Color.parseColor("#9D8C84")));
        menu.add(new MaterialItemSectionFragment(this, "Functionally", new FunctionallyFragment(), "Functionally").setSectionColor(Color.parseColor("#F1C40F")));

        // actionbar overlay
        this.setActionBarOverlay(true);

        // load menu
        this.loadMenu(menu);

        // load first MaterialItemSectionFragment in the menu, because there is no start position
        this.loadStartFragmentFromMenu(menu);

        // set back pattern
        this.setBackPattern(MaterialNavigationDrawer.BACKPATTERN_LAST_SECTION_FRAGMENT);
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }
}