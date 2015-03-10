AdvancedMaterialDrawer
======================

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AdvancedMaterialDrawer-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1617) [![API](https://img.shields.io/badge/API-10%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=10)

[![Donate](https://www.paypalobjects.com/en_GB/i/btn/btn_donate_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=FXXJENAV99CNQ)

A Gmail-like Material Drawer implementation 

Based on neokree's [MaterialDrawer](https://github.com/neokree/MaterialNavigationDrawer) library.
Big thanks to neokree, without him this library would not exist.


### Differences with neokree's lib & new Features:
- Unlimited Head-Items (Accounts)
- Every Head-Item can have its own Menu
- Head-Item listeners for Avatar or Background taps
- Adding and removing Head-Items and Menu-Items at runtime
- Theming
- Labels
- Normal Sections (with Fragment/Activty start or own onClick listener)
- Bottom-Sections
- More: Setting your own Fragment on Start, etc

### Example APK (Lib-Version 1.0.9) 
https://github.com/madcyph3r/AdvancedMaterialDrawer/raw/master/example-release.apk

### Download
```java
repositories {
    maven { url 'http://dl.bintray.com/madcyph3r/maven/' }
}

dependencies {
    compile 'de.madcyph3r:materialDrawer:1.0.9@aar'
}
```

### Dependencies
```java
compile 'com.android.support:appcompat-v7:21.0.3'
compile 'com.nineoldandroids:library:2.4.0'
```

### Usage
There are 29 examples with explanations how to use the library, here is a small example with one Head-Item. It looks like [screenshot three](https://raw.githubusercontent.com/madcyph3r/AdvancedMaterialDrawer/master/Screenshot_3.png), only in black color.

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
        MaterialHeadItem headItem = new MaterialHeadItem(this, "F HeadItem", "F Subtitle", drawableAppIcon, R.drawable.mat5, menu, 0);

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
<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_1.png" alt="screenshot" width="300px" height="auto" />
<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_2.png" alt="screenshot" width="300px" height="auto" />
<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_3.png" alt="screenshot" width="300px" height="auto" />
<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_4.png" alt="screenshot" width="300px" height="auto" />
<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_5.png" alt="screenshot" width="300px" height="auto" />
<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_6.png" alt="screenshot" width="300px" height="auto" />
