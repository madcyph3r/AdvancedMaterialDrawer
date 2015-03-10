package de.madcyph3r.example.tools;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import de.madcyph3r.example.FragmentIndex;
import de.madcyph3r.example.R;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;

/**
 * Created by marc on 10.03.2015.
 */
public class OwnActionBarFont  extends MaterialNavigationDrawer {

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
    public void afterInit() {
        if(getCurrentSection() != null)
            setActionBarTitle(getCurrentSection().getTitle());
        else
            setActionBarTitle("MyTitle");
    }

    @Override
    public void onBeforeChangedSection(MaterialSection newSection) {

    }

    @Override
    public void onAfterChangedSection(MaterialSection newSection) {
        if(getCurrentSection() == newSection) {
            setActionBarTitle(newSection.getTitle());
        }
    }

    private void setActionBarTitle(String actionBarTitle) {
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_action_bar, null);

        Typeface tf = Typeface.createFromAsset(getAssets(),"font/BlackwoodCastle.ttf");

        TextView title1 = (TextView)v.findViewById(R.id.titleFragment1);
        title1.setTypeface(tf);

        TextView title2 = (TextView)v.findViewById(R.id.titleFragment2);
        title2.setTypeface(tf);

        int split = (actionBarTitle.length() / 2);

        title1.setText(actionBarTitle.substring(0, split));
        title2.setText(actionBarTitle.substring(split));

        //assign the view to the actionbar
        this.getSupportActionBar().setCustomView(v);
    }
}