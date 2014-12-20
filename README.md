AdvancedMaterialDrawer
======================

A Material Drawer implementation (Like gMail App)

Based on neokree MaterialDrawer (https://github.com/neokree/MaterialNavigationDrawer)

It requires 11+ API and android support v7 (Toolbar)<br>

### New Features
- HeadItem change animation
- Every headitem can has a own menu
- Implement your own on click listener for every section
- replace bitmap with drawable, so you can use other libs like textDrawable (reduce memory useage)
- and other options

### Example APK
- Download

### Usage
- For all options see the example (MainActivity)

Your activity (in the Example its the MainActivity)
```java
public class MainActivity extends MaterialNavigationDrawer implements MaterialNavigationDrawerListener {

    MaterialNavigationDrawer drawer = null;

    @Override
    public void init(Bundle savedInstanceState) {

        drawer = this;

        // set drawer menu width in dp
        this.setDrawerDPWidth(300);

        // create HeadItem 1 with menu

        // create menu
        MaterialMenu menu1 = new MaterialMenu();
        // create sections for menu
        MaterialSection section1 = this.newSection("Favoriten", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentIndex(), false);
        MaterialSection section2 = this.newSection("Verfügbare Tools", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentIndex(), false);
        // no new Fragment or Activity. need own listener.
        MaterialSection section3 = this.newSection("Tools Erwerben", this.getResources().getDrawable(R.drawable.ic_extension_black_36dp), false).setSectionColor((Color.parseColor("#ff9800")));
        section3.setNotifications(33);
        // set on click listener for section 3
        section3.setOnClickListener(new MaterialSectionListener() {
            @Override
            public void onClick(MaterialSection section) {
                unSelectOldSection(section);

                // on click action for section 3
                Context context = getApplicationContext();
                CharSequence text = "Tools erwerben toast";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                drawer.closeDrawer();
            }
        });

        Intent intentSettings = new Intent(this, Settings.class);
        // create settings section. with bottom true. it will be shown on the bottom of the drawer
        MaterialSection settingsSection = this.newSection("Settings", this.getResources().getDrawable(R.drawable.ic_settings_black_24dp), intentSettings, true);

        // add sections to the menu
        menu1.getSections().add(section1);
        menu1.getSections().add(section2);
        menu1.getSections().add(section3);
        menu1.getSections().add(new MaterialDevisor());
        menu1.getSections().add(settingsSection);

        // create TextDrawable with lable F and color blue
        TextDrawable headPhoto = TextDrawable.builder()
                .buildRound("F", Color.BLUE);

        // create headItem1 and add menu1
        MaterialHeadItem headItem1 = new MaterialHeadItem("F HeadItem", "F Subtitle", headPhoto, this.getResources().getDrawable(R.drawable.mat5), menu1, 0);
        // add headItem1 to the drawer
        headItem1.setCloseDrawerOnChanged(true);
        this.addHeadItem(headItem1);
        
                // set this class as onchangedListener
        this.setOnChangedListener(this);
    }

    @Override
    public void onBeforeChangedHeadItem(MaterialHeadItem newHeadItem) {
        //Toast.makeText(getApplicationContext(), "on changed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAfterChangedHeadItem(MaterialHeadItem newHeadItem) {

    }
```
Do not override the <code>onCreate</code> method in the activity! Use <code>init</code> method instead, it will be called from the library.<br>

Your styles.xml
```xml
<resources>

    <!-- Base application theme. -->
    <style name="MyTheme" parent="MaterialNavigationDrawerTheme">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@android:color/holo_blue_light</item>
        <item name="colorAccent">#FFFFFF</item>
    </style>

</resources>
```

### Import to your Project
For now you musst download the library source code and add it as a module. But Maven Repo will come.
See the gradle files in the example for the settings.


### Screenshots
