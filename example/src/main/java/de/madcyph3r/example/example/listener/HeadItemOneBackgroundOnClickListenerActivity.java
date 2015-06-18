package de.madcyph3r.example.example.listener;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import de.madcyph3r.example.R;
import de.madcyph3r.example.example.FragmentDummy;
import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialHeadItemBackgroundOnClickListener;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;
import de.madcyph3r.materialnavigationdrawer.tools.RoundedCornersDrawable;

/**
 * Created by marc on 23.02.2015.
 */
public class HeadItemOneBackgroundOnClickListenerActivity extends MaterialNavigationDrawer {

    MaterialNavigationDrawer drawer = null;

    @Override
    public int headerType() {
        // set type. you get the available constant from MaterialNavigationDrawer class
        return MaterialNavigationDrawer.DRAWERHEADER_HEADITEMS;
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
        bundle.putString("instruction", "Open the drawer and click on the background image from the head item.");

        drawer = this;

        MaterialMenu menu = new MaterialMenu();

        //create instruction fragment
        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu items
        MaterialSection instruction = this.newSection("Instruction", fragmentInstruction , false, menu);
        instruction.setFragmentTitle("Head Item Style (One Item), Background OnClick Listener");
        this.newDevisor(menu);
        this.newLabel("Label", false, menu);
        this.newSection("Section", new FragmentDummy(), false, menu);

        // use bitmap and make a circle photo
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_drawer_icon);
        final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);

        // create Head Item
        MaterialHeadItem headItem = new MaterialHeadItem(this, "My HeadItem", "My Subtitle", drawableAppIcon, R.drawable.mat5, menu);

        // set Avatar on click listener
        headItem.setBackgroundOnClickListener(new MaterialHeadItemBackgroundOnClickListener() {
            @Override
            public void onClick(MaterialHeadItem headItem) {
                Toast.makeText(drawer, "background on click", Toast.LENGTH_LONG).show();
            }
        });

        // add head Item (menu will be loaded automatically)
        this.addHeadItem(headItem);
    }
}
