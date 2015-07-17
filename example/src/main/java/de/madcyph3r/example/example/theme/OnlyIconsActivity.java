package de.madcyph3r.example.example.theme;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import de.madcyph3r.example.R;
import de.madcyph3r.example.example.FragmentDummy;
import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.activity.MaterialNavNoHeaderActivity;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;

public class OnlyIconsActivity extends MaterialNavNoHeaderActivity {

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
        bundle.putString("instruction", "The Menu has Only Icons");

        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, this.getResources().getDrawable(R.drawable.app_drawer_icon) , true, new FragmentDummy(), "Fragment Section Icon Banner"));
        menu.add(new MaterialItemSectionFragment(this, this.getResources().getDrawable(R.drawable.ic_extension_black_36dp) , true, new FragmentDummy(), "Extension"));
        menu.add(new MaterialItemSectionFragment(this, this.getResources().getDrawable(R.drawable.ic_list_black_36dp) , true, new FragmentDummy(), "List"));
        menu.add(new MaterialItemSectionFragment(this, this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp) , true, new FragmentDummy(), "Favorite").setSectionColor(Color.parseColor("#ff0858")));


        // load menu
        this.loadMenu(menu);

        // load the MaterialItemSectionFragment, from the given startIndex
        this.loadStartFragmentFromMenu(menu);
    }

    @Override
    public int drawerWidthInit() {
        return 70; // given in DP
    };

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }
}