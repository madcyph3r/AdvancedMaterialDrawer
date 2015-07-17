package de.madcyph3r.example.example.menu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import de.madcyph3r.example.DummyActivity;
import de.madcyph3r.example.R;
import de.madcyph3r.example.example.FragmentDummy;
import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.activity.MaterialNavNoHeaderActivity;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.custom.MaterialItemCustom;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSection;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionOnClick;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionActivity;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionOnClickListener;
import de.madcyph3r.materialnavigationdrawer.menu.item.style.MaterialItemDevisor;
import de.madcyph3r.materialnavigationdrawer.menu.item.style.MaterialItemLabel;

public class AllMenuTypesActivity extends MaterialNavNoHeaderActivity {

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
        bundle.putString("instruction", "This example shows all menu items. " +
                "Open the drawer and see ;).");

        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu
        MaterialMenu menu = new MaterialMenu();

        menu.add(new MaterialItemSectionFragment(this, "Instruction", fragmentInstruction, "All Menu Types"));
        menu.add(new MaterialItemDevisor());
        menu.add(new MaterialItemCustom(this, R.layout.custom_section));
        menu.add(new MaterialItemLabel(this, "Label"));
        menu.add(new MaterialItemSectionFragment(this, "Fragment Section", new FragmentDummy(), "Fragment Section"));
        MaterialItemSectionFragment secNoti = new MaterialItemSectionFragment(this, "Fragment Section Notification", new FragmentDummy(), "Fragment Section Notification");
        secNoti.setNotifications(20);
        menu.add(secNoti);
        menu.add(new MaterialItemSectionFragment(this, "Fragment Section Icon", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentDummy(), "Fragment Section Icon"));
        MaterialItemSectionFragment iconBanner = new MaterialItemSectionFragment(this, this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp) , true, new FragmentDummy(), "Fragment Section Icon Banner");
        //iconBanner.getIconView().setScaleType(ImageView.ScaleType.CENTER);  edit the iconView to your needs
        menu.add(iconBanner);
        menu.add(new MaterialItemSectionFragment(this, "Fragment Section Color Red", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp) , new FragmentDummy(), "Fragment Section Color Red").setSectionColor(Color.parseColor("#ff0858")));
        menu.add(new MaterialItemDevisor());
        menu.add(new MaterialItemSectionActivity(this, "Activity Section", new Intent(this, DummyActivity.class)));
        MaterialItemSectionOnClick onClickSection = new MaterialItemSectionOnClick(this, "OnClick Section");
        onClickSection.setOnSectionClickListener(new MaterialSectionOnClickListener() {
            @Override
            public void onClick(MaterialItemSection section, View v) {
                Toast.makeText(drawer,"OnClickSection", Toast.LENGTH_SHORT).show();
            }
        });
        menu.add(onClickSection);
        MaterialItemSectionFragment bottom = new MaterialItemSectionFragment(this, "Fragment Bottom Section", new FragmentDummy(), "Fragment Bottom Section");
        bottom.setBottom(true);
        menu.add(bottom);

        // load menu
        this.loadMenu(menu);

        // load the MaterialItemSectionFragment, from the given startIndex
        this.loadStartFragmentFromMenu(menu);
    }

    // to handle the custom section
    @Override
    public void afterInit(Bundle savedInstanceState) {
        CheckBox cb = (CheckBox)findViewById(R.id.custom_layout_checkBox);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                Toast.makeText(drawer, "Checked: " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
    }
}