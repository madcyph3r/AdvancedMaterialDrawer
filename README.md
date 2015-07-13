AdvancedMaterialDrawer
======================

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AdvancedMaterialDrawer-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1617) [![API](https://img.shields.io/badge/API-10%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=10)

[![Donate](https://www.paypalobjects.com/en_GB/i/btn/btn_donate_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=FXXJENAV99CNQ)

A Gmail-like Material Drawer implementation 

Based on neokree's [MaterialDrawer](https://github.com/neokree/MaterialNavigationDrawer) library, but the are not the same. I have made many improvments, changes and added a lot of new feature.
Big thanks to neokree, without him this library would not exist.


### Main Features:
Head Item (Account):
- Unlimited Head-Items (Accounts), tested with 100 Head-Items
- Head-Item listeners for Avatar or Background taps
- Adding and removing Head-Items at runtime
- Every Head-Item can have its own menu
- Create your own Head-Item style (Custom Head-Item)
- Or no Head-Item, if you need only the menu

Menu:
- Menu with unlimit Menu-Items
- Adding and removing Menu-Items at runtime
- Available Menu-items:
  - Labels
  - Normal Sections (with Fragment/Activty start or own onClick listener)
  - Bottom-Sections
  - Custom Sections !!! (add your own section, like a checkbox etc.) see screenshot 2  

Other:
- Drawer can be over and below the toolbar
- Create your own theme
- Tablet support
- Toolbar overflow support, so a image can be your background. see screenshot 5
- Many many more: Setting your own Fragment on Start, etc. Play with the example app ;).
- 

### Live Demo
You can test the example application in your web browser.
https://appetize.io/app/dqmyynvjanx9hydtq4a4vjbkzw

### Example APK (Lib-Version 1.1.4 (13.07.15)) 
https://github.com/madcyph3r/AdvancedMaterialDrawer/raw/master/example-release.apk

or on the play store (Lib-Version 1.1.4 (13.07.15)) 

<a href="https://play.google.com/store/apps/details?id=de.madcyph3r.MaterialDrawer">
  <img alt="Android app on Google Play" src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>

### Download
```java
repositories {
    maven { url 'http://dl.bintray.com/madcyph3r/maven/' }
}

dependencies {
    compile 'de.madcyph3r:materialDrawer:1.1.4@aar'
}
```

### Dependencies
```java
compile 'com.android.support:appcompat-v7:21.0.3'
compile 'com.nineoldandroids:library:2.4.0'
```

### Usage
There are a lot of examples with explanations, how to use the library, here is a small example with one Head-Item. It looks like [screenshot three](https://raw.githubusercontent.com/madcyph3r/AdvancedMaterialDrawer/master/Screenshot_4.png), only in black color.

```java
public class OneHeadItem extends MaterialNavigationDrawer {

    MaterialNavigationDrawer drawer = null;

    @Override
    public int headerType() {
        // set type. you get the available constant from MaterialNavigationDrawer class
        return MaterialNavigationDrawer.DRAWERHEADER_HEADITEMS;
    }

    // called from onCreate(), make your view init here or in your fragment.
    @Override
    public void init(Bundle savedInstanceState) {

        drawer = this;

        MaterialMenu menu = new MaterialMenu();

        // first section is loaded
        MaterialSection section1 = this.newSection("Section 1", this.getResources().getDrawable(R.drawable.ic_favorite_black_36dp), new FragmentIndex(), false, menu);
        MaterialSection section2 = this.newSection("Section 2", this.getResources().getDrawable(R.drawable.ic_list_black_36dp), new FragmentIndex(), false, menu);

        // use bitmap and make a circle photo
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_drawer_icon);
        final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);

        // create Head Item
        MaterialHeadItem headItem = new MaterialHeadItem(this, "F HeadItem", "F Subtitle", drawableAppIcon, R.drawable.mat5, menu);
        
        // add head Item (menu will be loaded automatically)
        this.addHeadItem(headItem);
    }
}
```

### Others
- If you've found an issue, look up the open [issues](https://github.com/madcyph3r/AdvancedMaterialDrawer/issues) and submit a new one if it isn't yet reported.
- If you like the library press the star ;)
- A Wiki with some Introduction will come soon.

### Screenshots 
**Info: The statusbar is now semi-transparent on lollipop with the latest snapshot. New screenshot will come, with the new release.**

5 head-items. Every one has his own menu. (Dark-Style):

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_1.png" alt="screenshot" width="200px" height="auto" />

Section kinds (section with and without icon, bottom Section, label, devisor, icon color):

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_2.png" alt="screenshot" width="200px" height="auto" />

drawer below the toolbar:

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_7.png" alt="screenshot" width="200px" height="auto" />

Light-Style:

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_3.png" alt="screenshot" width="200px" height="auto" />

Toolbar (Actionbar) Overflow:

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_11.png" alt="screenshot" width="200px" height="auto" />

Own Theme:

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_4.png" alt="screenshot" width="200px" height="auto" />

Custom-Header:

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_5.png" alt="screenshot" width="200px" height="auto" />

No-Header:

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_12.png" alt="screenshot" width="200px" height="auto" />

No-Header Below Toolbar:

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_13.png" alt="screenshot" width="200px" height="auto" />

Selected section sets the toolbar color:

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_6.png" alt="screenshot" width="200px" height="auto" />

Tablet, drawer is always present (multipaneSupport = true), below toolbar:

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_8.png" alt="screenshot" width="300px" height="auto" />

Tablet, drawer with hiding, not always open:

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_9.png" alt="screenshot" width="300px" height="auto" />

Tablet, drawer is always present (multipaneSupport = true):

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_10.png" alt="screenshot" width="300px" height="auto" />
