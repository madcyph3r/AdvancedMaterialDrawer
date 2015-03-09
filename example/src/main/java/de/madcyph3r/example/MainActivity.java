package de.madcyph3r.example;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.madcyph3r.example.tools.AllMenuTypes;
import de.madcyph3r.example.tools.BackPatternCustom;
import de.madcyph3r.example.tools.BackPatternDefault;
import de.madcyph3r.example.tools.BackPatternStartIndex;
import de.madcyph3r.example.tools.ClosePreviousActivity;
import de.madcyph3r.example.tools.CustomHeaderActivity;
import de.madcyph3r.example.tools.FiveHeadItem;
import de.madcyph3r.example.tools.FiveHeadItemExtraMenu;
import de.madcyph3r.example.tools.HeadItemAvatarBackgroundClick;
import de.madcyph3r.example.tools.HeadItemStartSecSection;
import de.madcyph3r.example.tools.ImageHeaderActivity;
import de.madcyph3r.example.tools.NoClosePreviousActivity;
import de.madcyph3r.example.tools.NoHeaderActivity;
import de.madcyph3r.example.tools.OneHeadItem;
import de.madcyph3r.example.tools.OneHeadItemDark;
import de.madcyph3r.example.tools.OneHeadItemMyTheme;
import de.madcyph3r.example.tools.OneHeadItemLight;
import de.madcyph3r.example.tools.OneHeadItemOwnDrawerWidth;
import de.madcyph3r.example.tools.OneHeadItemOwnFragment;
import de.madcyph3r.example.tools.OneHeadItemReloadMenu;
import de.madcyph3r.example.tools.SectionChangeListener;
import de.madcyph3r.example.tools.ThreeHeadItem;
import de.madcyph3r.example.tools.ThreeHeadItemNoCloseDrawer;
import de.madcyph3r.example.tools.TwoHeadItemChangeListenerDrawerListener;
import de.madcyph3r.example.tools.TwoHeadItemExtraMenu;
import de.madcyph3r.example.tools.TwoHeadItemNoFragementLoadOnChange;
import de.madcyph3r.example.tools.TwoHeadItemOneMenu;
import de.madcyph3r.example.tools.TwoHeadItemRemoveAddNewHeadItem;

public class MainActivity extends ActionBarActivity {

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx= this;

        setContentView(R.layout.mainactivity);
        ListView lv = (ListView) findViewById(R.id.listView);

        ArrayList<String> listValues = new ArrayList<String>();
        listValues.add("no header");
        listValues.add("one head item");
        listValues.add("three head items");
        listValues.add("five head items");
        listValues.add("head item start with second section");
        listValues.add("head item with avatar und background listener");
        listValues.add("head item change: don't close drawer on change");
        listValues.add("two head items: first has menu, second not");
        listValues.add("load own fragment on start");
        listValues.add("head item change: don't load fragment on change");
        listValues.add("all Menu (section) types");
        listValues.add("back pattern start index");
        listValues.add("back pattern normal back (default)");
        listValues.add("back pattern custom");
        listValues.add("add/remove head item at runtime");
        listValues.add("five head items with extra menu");
        listValues.add("two head items: force to show head item switcher (has extra menu)");
        listValues.add("drawer own DP width");
        listValues.add("dark theme");
        listValues.add("light theme");
        listValues.add("my own theme (green actionbar, etc.)");
        listValues.add("custom header");
        listValues.add("image header");
        listValues.add("two head item: on change listener and drawer listener");
        listValues.add("add and remove sections dynamically");
        listValues.add("close drawer activity");
        listValues.add("no close drawer actvity");
        listValues.add("section change listener");
        // on drawer open close und head item change listener hinzufügen

        // initiate the listadapter

        StableArrayAdapter mAdapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, listValues);
        // assign the list adapter

        lv.setAdapter(mAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    Intent i = new Intent(ctx, NoHeaderActivity.class);
                    startActivity(i);
                } else if(position == 1) {
                    Intent i = new Intent(ctx, OneHeadItem.class);
                    startActivity(i);
                } else if(position == 2) {
                    Intent i = new Intent(ctx, ThreeHeadItem.class);
                    startActivity(i);
                } else if(position == 3) {
                    Intent i = new Intent(ctx, FiveHeadItem.class);
                    startActivity(i);
                } else if(position == 4) {
                    Intent i = new Intent(ctx, HeadItemStartSecSection.class);
                    startActivity(i);
                } else if(position == 5) {
                    Intent i = new Intent(ctx, HeadItemAvatarBackgroundClick.class);
                    startActivity(i);
                } else if(position == 6) {
                    Intent i = new Intent(ctx, ThreeHeadItemNoCloseDrawer.class);
                    startActivity(i);
                } else if(position == 7) {
                    Intent i = new Intent(ctx, TwoHeadItemOneMenu.class);
                    startActivity(i);
                } else if(position == 8) {
                    Intent i = new Intent(ctx, OneHeadItemOwnFragment.class);
                    startActivity(i);
                } else if(position == 9) {
                    Intent i = new Intent(ctx, TwoHeadItemNoFragementLoadOnChange.class);
                    startActivity(i);
                } else if(position == 10) {
                    Intent i = new Intent(ctx, AllMenuTypes.class);
                    startActivity(i);
                } else if(position == 11) {
                    Intent i = new Intent(ctx, BackPatternStartIndex.class);
                    startActivity(i);
                } else if(position == 12) {
                    Intent i = new Intent(ctx, BackPatternDefault.class);
                    startActivity(i);
                } else if(position == 13) {
                    Intent i = new Intent(ctx, BackPatternCustom.class);
                    startActivity(i);
                } else if(position == 14) {
                    Intent i = new Intent(ctx, TwoHeadItemRemoveAddNewHeadItem.class);
                    startActivity(i);
                } else if(position == 15) {
                    Intent i = new Intent(ctx, FiveHeadItemExtraMenu.class);
                    startActivity(i);
                } else if(position == 16) {
                    Intent i = new Intent(ctx, TwoHeadItemExtraMenu.class);
                    startActivity(i);
                } else if(position == 17) {
                    Intent i = new Intent(ctx, OneHeadItemOwnDrawerWidth.class);
                    startActivity(i);
                } else if(position == 18) {
                    Intent i = new Intent(ctx, OneHeadItemDark.class);
                    startActivity(i);
                } else if(position == 19) {
                    Intent i = new Intent(ctx, OneHeadItemLight.class);
                    startActivity(i);
                } else if(position == 20) {
                    Intent i = new Intent(ctx, OneHeadItemMyTheme.class);
                    startActivity(i);
                } else if(position == 21) {
                    Intent i = new Intent(ctx, CustomHeaderActivity.class);
                    startActivity(i);
                } else if(position == 22) {
                    Intent i = new Intent(ctx, ImageHeaderActivity.class);
                    startActivity(i);
                } else if(position == 23) {
                    Intent i = new Intent(ctx, TwoHeadItemChangeListenerDrawerListener.class);
                    startActivity(i);
                }  else if(position == 24) {
                    Intent i = new Intent(ctx, OneHeadItemReloadMenu.class);
                    startActivity(i);
                }  else if(position == 25) {
                     Intent i = new Intent(ctx, ClosePreviousActivity.class);
                    startActivity(i);
                } else if(position == 26) {
                    Intent i = new Intent(ctx, NoClosePreviousActivity.class);
                    startActivity(i);
                }  else if(position == 27) {
                    Intent i = new Intent(ctx, SectionChangeListener.class);
                    startActivity(i);
                }
            }
        });

    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}




   // MaterialNavigationDrawer drawer = null;



/*    @Override
    public void init(Bundle savedInstanceState) {

        drawer = this;

        // create menu

        // Style dark
        // style light
        // image header
        // customheader header





        /*

        MaterialMenu menu = new MaterialMenu();



        // create HeadItem 1 with menu

        // create menu
        MaterialMenu menu1 = new MaterialMenu();
        // create sections for menu
        MaterialSection section1 = this.newSection("Favoriten 1", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentIndex(), false);
        MaterialSection section2 = this.newSection("Verfügbare Tools", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentIndex(), false).setSectionColor((Color.parseColor("#ff9800")));
        // no new Fragment or Activity. need own listener.
        MaterialSection section3 = this.newSection("Tools Erwerben", this.getResources().getDrawable(R.drawable.ic_extension_black_36dp), false).setSectionColor((Color.parseColor("#ff9800")));


        section3.setNotifications(33);
        // set on click listener for section 3
        section3.setOnClickListener(new MaterialSectionListener() {
            @Override
            public void onClick(MaterialSection section) {

                // on click action for section 3
                Context context = getApplicationContext();
                CharSequence text = "Tools erwerben toast";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                section.unSelect();

                drawer.closeDrawer();
            }
        });

        Intent intentSettings = new Intent(this, Settings.class);
        // create settings section. with bottom true. it will be shown on the bottom of the drawer
        MaterialSection settingsSection = this.newSection("Settings", this.getResources().getDrawable(R.drawable.ic_settings_black_24dp), intentSettings, true);

        // add sections to the menu
        menu1.getItems().add(section1);
        menu1.getItems().add(new MaterialDevisor());
        menu1.getItems().add(section2);
        menu1.getItems().add(section3);
        menu1.getItems().add(new MaterialDevisor());
        menu1.getItems().add(settingsSection);

        // create TextDrawable with lable F and color blue
        TextDrawable headPhoto = TextDrawable.builder()
                .buildRound("F", Color.BLUE);

        // create headItem1 and add menu1
        MaterialHeadItem headItem1 = new MaterialHeadItem(this, "F HeadItem", "F Subtitle", headPhoto, R.drawable.mat5, menu1, 0);
        // add headItem1 to the drawer
        //this.addHeadItem(headItem1);
        headItem1.setCloseDrawerOnChanged(false);




        // Create HeadItem 2 without menu

        // use bitmap and make a circle photo
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_drawer_icon);
        final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);

        // create head item, don't give a own menu
        MaterialHeadItem headItem2 = new MaterialHeadItem(this, "Material Navigation Drawer", "version 1.0", drawableAppIcon, R.drawable.mat6, menu1, 0);
       // this.addHeadItem(headItem2);

        // listener for the avatar (photo) click
        headItem2.setOnClickListener(new MaterialHeadItemListener() {
            @Override
            public void onClick(MaterialHeadItem headItem) {
                Toast.makeText(getApplicationContext(), "This is a avatar toast", Toast.LENGTH_LONG).show();
                //addDivisor();
            }

            @Override
            public void onBackgroundClick(MaterialHeadItem headItem) {
                Toast.makeText(getApplicationContext(), "on headItem2 background", Toast.LENGTH_LONG).show();
            }
        });

        headItem2.setCloseDrawerOnChanged(false);


        // Create HeadItem 3 with menu

        // create menu
        MaterialMenu menu2 = new MaterialMenu();
        // create sections for menu
        MaterialSection section4 = this.newSection("Favoriten", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentIndex(), false);
        MaterialSection section5 = this.newSection("Kaufen", this.getResources().getDrawable(R.drawable.ic_extension_black_36dp), new FragmentIndex(), false);
        section5.setSectionColor(Color.parseColor("#ff9800"));
        // add sections to menu
        menu2.getItems().add(section4);
        menu2.getItems().add(section5);

        // create TextDrawable with lable M and color gray
        TextDrawable headPhoto3 = TextDrawable.builder()
                .buildRound("M", Color.GRAY);
        // (1): use second menu as start menu for this headitem
        MaterialHeadItem headItem3 = new MaterialHeadItem(this, "M HeadItem", "subtitle", headPhoto3, R.drawable.mat5, menu2, 1);
        //headItem3.setCloseDrawerOnClick(false);
        //this.addHeadItem(headItem3);

        TextDrawable headPhoto4 = TextDrawable.builder()
                .buildRound("x", Color.GRAY);
        MaterialHeadItem headItem4 = new MaterialHeadItem(this, "headitem 4", "subtitle", headPhoto4, R.drawable.mat5, menu2, 0);
        //this.addHeadItem(headItem4);

        MaterialMenu menu5 = new MaterialMenu();
        menu5.getItems().add(section3);
        MaterialHeadItem headItem5 = new MaterialHeadItem(this, "headitem 5", "subtitle", drawableAppIcon, R.drawable.mat5, menu5, 0);
        //this.addHeadItem(headItem5);
        //this.addHeadItem(headItem2);


        this.setCustomMenu(menu2);

        // set this class as onchangedListener
        this.setOnChangedListener(this);*/
   /* }

    @Override
    public int headerType() {
        return MaterialNavigationDrawer.DRAWERHEADER_NO_HEADER;
    }

    @Override
    public void onBeforeChangedHeadItem(MaterialHeadItem newHeadItem) {
        //Toast.makeText(getApplicationContext(), "on changed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAfterChangedHeadItem(MaterialHeadItem newHeadItem) {

    }*/

//}
