package de.madcyph3r.example.example.theme;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import de.madcyph3r.example.R;
import de.madcyph3r.example.example.FragmentDummy;
import de.madcyph3r.example.example.FragmentInstruction;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;

/**
 * Created by marc on 10.03.2015.
 */
public class ActionBarOwnFontActivity extends MaterialNavigationDrawer {

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
    public void init(Bundle savedInstanceState) {

        Bundle bundle = new Bundle();
        bundle.putString("instruction", "See the actionbar, it has a custom title with a custom font.");

        drawer = this;

        // create menu
        MaterialMenu menu = new MaterialMenu();

        //create instruction fragment
        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create items
        MaterialSection instruction = this.newSection("Instruction", fragmentInstruction , false, menu);
        instruction.setFragmentTitle("Actionbar Own Front");
        this.newDevisor(menu);
        this.newLabel("Label", false, menu);
        this.newSection("Section", new FragmentDummy(), false, menu);

        // set this menu
        this.setCustomMenu(menu);
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {
        if(getCurrentSection() != null)
            setActionBarTitle(getCurrentSection().getTitle());
        else
            setActionBarTitle("MyTitle"); // not really needed here, because this example has always a current section

    }

    @Override
    protected int getNewIntentRequestCode(Class clazz) {
        return 0;
    }

    @Override
    public void onBeforeChangeSection(MaterialSection newSection) {

    }

    @Override
    public void onAfterChangeSection(MaterialSection newSection) {
        if(getCurrentSection() == newSection) {
            // change the actionbar title after every change
            setActionBarTitle(newSection.getTitle());
        }
    }

    private void setActionBarTitle(String actionBarTitle) {
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.example_action_bar_own_font, null);

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