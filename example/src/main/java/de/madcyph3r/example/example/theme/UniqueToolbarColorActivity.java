package de.madcyph3r.example.example.theme;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import de.madcyph3r.example.R;
import de.madcyph3r.example.example.FragmentDummy;
import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;
import de.madcyph3r.materialnavigationdrawer.tools.RoundedCornersDrawable;

/**
 * Created by marc on 23.02.2015.
 */
public class UniqueToolbarColorActivity extends MaterialNavigationDrawer {

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
        bundle.putString("instruction", "This example shows only the defined toolbar color." +
                " The toolbar color doesn't change, even if a section has a section color, like 'section 1'. "
        + "Press on 'Section 1', and you will see, the toolbar color doesn't change. "
        + " See 'android:theme=\"@style/UniqueToolbarColorTheme\"' in the AndroidManifest.xml and the" +
                " part for this activity." +
        " The style is defined int the styles.xml with the name UniqueToolbarColorTheme.");

        drawer = this;

        // create menu
        MaterialMenu menu = new MaterialMenu();

        //create instruction fragment
        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // menu items
        MaterialSection instruction = this.newSection("Instruction", fragmentInstruction , false, menu);
        instruction.setFragmentTitle("Unique Toolbar Color");
        this.newDevisor(menu);
        this.newLabel("Label", false, menu);
        this.newSection("Section 1", new FragmentDummy(), false, menu).setSectionColor(Color.parseColor("#ff0000"));
        this.newSection("Section 2", new FragmentDummy(), false, menu);

        // use bitmap and make a circle photo
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_drawer_icon);
        final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);

        // set custom menu
        this.setCustomMenu(menu);
    }
}
