package de.madcyph3r.example.tools;


import android.os.Bundle;
import android.widget.Toast;

import de.madcyph3r.example.FragmentIndex;
import de.madcyph3r.example.R;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;

public class SectionChangeListener extends MaterialNavigationDrawer {

    MaterialNavigationDrawer drawer = null;

    @Override
    public int headerType() {
        // set type. you get the available constant from MaterialNavigationDrawer class
        return MaterialNavigationDrawer.DRAWERHEADER_NO_HEADER;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        drawer = this;

        MaterialMenu menu = new MaterialMenu();

        // create sections
        MaterialSection section1 = this.newSection("Section 1", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentIndex(), false, menu);
        MaterialSection section2 = this.newSection("Section 2", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentIndex(), false, menu);

        // set this menu
        this.setCustomMenu(menu);
    }

    @Override
         public void onBeforeChangedSection(MaterialSection newSection) {
        if(getCurrentSection() != newSection) {
            Toast.makeText(this, "before change section", Toast.LENGTH_SHORT).show();
            this.getCurrentSection().unSelect();
        }
    }

    @Override
    public void onAfterChangedSection(MaterialSection newSection) {
        if(getCurrentSection() == newSection) {
            Toast.makeText(this, "after change section", Toast.LENGTH_SHORT).show();
        }
    }
}
