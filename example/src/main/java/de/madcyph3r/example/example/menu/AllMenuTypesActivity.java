package de.madcyph3r.example.example.menu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import de.madcyph3r.example.DummyActivity;
import de.madcyph3r.example.R;
import de.madcyph3r.example.example.FragmentDummy;
import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionOnClickListener;
import de.madcyph3r.materialnavigationdrawer.tools.RoundedCornersDrawable;

/**
 * Created by marc on 23.02.2015.
 */
public class AllMenuTypesActivity extends MaterialNavigationDrawer {

    MaterialNavigationDrawer drawer = null;

    @Override
    public int headerType() {
        // set type. you get the available constant from MaterialNavigationDrawer class
        return MaterialNavigationDrawer.DRAWERHEADER_HEADITEMS;
    }

    // not needed for this example, but i don't want to close the activity, if you select
    // 'Start Activity'. so you come back to this activity if you press the back button ;).
    @Override
    public boolean finishActivityOnNewIntent() {
        return false;
    }

    @Override
    protected int getNewIntentRequestCode(Class clazz) {
        return 0;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        Bundle bundle = new Bundle();
        bundle.putString("instruction", "This example shows all menu items. " +
                "Open the drawer and see ;).");

        drawer = this;

        MaterialMenu menu = new MaterialMenu();

        //create intent for settings activity
        Intent i = new Intent(this, DummyActivity.class);

        //create instruction fragment
        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu items
        MaterialSection instruction = this.newSection("Instruction", new FragmentInstruction(), false, menu);
        instruction.setFragmentTitle("All Menu Types");
        this.newSection("Start Activity", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), i, false, menu);
        // add devisor
        this.newDevisor(menu);
        // section with own in click listener
        this.newLabel("label", false, menu);

        MaterialSection sectionListener = this.newSection("On Click listener", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), false, menu);
        sectionListener.setOnClickListener(new MaterialSectionOnClickListener() {
            @Override
            public void onClick(MaterialSection section, View view) {
                Toast.makeText(drawer, "on click listener ;)", Toast.LENGTH_LONG).show();
                section.unSelect();
            }
        });

        MaterialSection sectionNoti = this.newSection("Start Fragment Notification", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentDummy(), false, menu).setSectionColor(Color.parseColor("#ff0858"));
        sectionNoti.setNotifications(20);

        this.newSection("Start Fragment No Icon", new FragmentDummy(), false, menu);

        this.newSection("Section Red Color", new FragmentDummy(), false, menu).setSectionColor(Color.parseColor("#ff0000"));

        this.newLabel("label bottom", true, menu);
        this.newSection("Start Fragment Bottom", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentDummy(), true, menu);

        // use bitmap and make a circle photo
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_drawer_icon);
        final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);

        // create Head Item
        MaterialHeadItem headItem = new MaterialHeadItem(this, "F HeadItem", "F Subtitle", drawableAppIcon, R.drawable.mat5, menu);

        // add head Item (menu will be loaded automatically)
        this.addHeadItem(headItem);

    }
}
