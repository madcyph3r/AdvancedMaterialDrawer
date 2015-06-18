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
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;

public class MainActivity extends MaterialNavigationDrawer {

    private MaterialNavigationDrawer drawer = null;

    private MaterialMenu menu;

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

        drawer = this;

        menu = new MaterialMenu();

        // create menu items
        MaterialSection section1 = this.newSection("Instruction", new FragmentInstruction(), false, menu);
        this.newDevisor(menu);
        this.newLabel("Examples", false, menu);
        this.newSection("Theme", new ThemeFragment(), false, menu).setSectionColor(Color.parseColor("#ff0000"));
        this.newSection("Drawer Types", new DrawerTypesFragment(), false, menu).setSectionColor(Color.parseColor("#D35400"));
        this.newSection("Header Types", new HeaderTypesFragment(), false, menu).setSectionColor(Color.parseColor("#01AEA1"));
        this.newSection("Head Item Types", new HeadItemTypesFragment(), false, menu).setSectionColor(Color.parseColor("#9B59B6"));
        this.newSection("Menu", new MenuFragment(), false, menu).setSectionColor(Color.parseColor("#3498DB"));
        this.newSection("Back Pattern", new BackPatternFragment(), false, menu).setSectionColor(Color.parseColor("#3CD876"));
        this.newSection("Listener", new ListenerFragment(), false, menu).setSectionColor(Color.parseColor("#9D8C84"));
        this.newSection("Functionally", new FunctionallyFragment(), false, menu).setSectionColor(Color.parseColor("#F1C40F"));

        //this.newSection("Some Usefull Tricks", new ThemeFragment(), false, menu).setSectionColor(Color.parseColor("#2D5C4F"));
        // tricks, z.B. white theme oder black theme, aber statusbar andere farbe.
        // section merken on orientation change und wieder anwählen
        // custom recycleview_header z.b. und die größe in der dimen setzen und hier wieder auslesen

        menu.setStartIndex(0);
        // set this menu
        this.setCustomMenu(menu);

        // actionbar overlay
        this.setActionBarOverlay(true);

        // set Pattern
        this.setBackPattern(MaterialNavigationDrawer.BACKPATTERN_LAST_SECTION);
    }

    /*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // save current section pos
        outState.putInt("currentSectionPos", menu.getSectionPosition(this.getCurrentSection()));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // reload the menu with the current section pos
        this.reloadMenu(savedInstanceState.getInt("currentSectionPos"));
    }*/
}