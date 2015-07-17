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
import de.madcyph3r.materialnavigationdrawer.activity.MaterialNavNoHeaderActivity;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionChangeListener;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSection;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;

public class ActionBarOwnFontActivity extends MaterialNavNoHeaderActivity {

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

        drawer.setSectionChangeListener(new MaterialSectionChangeListener() {
            @Override
            public void onBeforeChangeSection(MaterialItemSection newSection) {

            }

            @Override
            public void onAfterChangeSection(MaterialItemSection newSection) {
                setActionBarTitle(getCurrentSectionFragment().getTitle());
            }
        });

        // information text for the fragment
        Bundle bundle = new Bundle();
        bundle.putString("instruction", "See the actionbar, it has a custom title with a custom font.");

        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Instruction", fragmentInstruction, "Actionbar Own Front"));
        menu.add(new MaterialItemSectionFragment(this, "Section 1", new FragmentDummy(), "Section 1"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));
        menu.add(new MaterialItemSectionFragment(this, "Section 3", new FragmentDummy(), "Section 3"));

        // load menu
        this.loadMenu(menu);

        // load the MaterialItemSectionFragment, from the given startIndex
        this.loadStartFragmentFromMenu(menu);
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {
        if(getCurrentSectionFragment() != null)
            setActionBarTitle(getCurrentSectionFragment().getTitle());
        else
            setActionBarTitle("MyTitle"); // not really needed here, because this example has always a current section
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