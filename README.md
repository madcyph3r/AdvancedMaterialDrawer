AdvancedMaterialDrawer
======================
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AdvancedMaterialDrawer-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1617)

A Material Drawer implementation (Like gMail App)

Based on neokree MaterialDrawer (https://github.com/neokree/MaterialNavigationDrawer).
Big thanks to neokree, without him this library would not be possible.

### API
[![API](https://img.shields.io/badge/API-10%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=10)

### Different from neokree MaterialDrawer and some features
- Unlimit Head-Items (Accounts), (see first screenshot, it has 5 Head-Items)
- Every Head-Item can has his own menu
- Head-Item listener for avatar or background click
- Add and remove Head-Items at runtime
- Add and remove Menu-Items at runtime (since 1.0.7)
- Themeing
- Labels
- Normal Sections (with Fragment/Activty start or own onClick listener)
- Bottom-Sections (see second screenshot)
- and many other things, like set your own fragment on start and so on

### Example APK (Lib-Version 1.0.7 [pre-version]) 
https://github.com/madcyph3r/AdvancedMaterialDrawer/raw/master/example-release.apk

###  How to import (my own repo, Version: 1.0.6)
```java
repositories {
    maven {
            url "http://dl.bintray.com/madcyph3r/maven/"
        }
}

dependencies {
    compile(group: 'de.madcyph3r', name: 'materialDrawer', version: '1.0.6', ext: 'aar')
}
```

### Usage
See the example apk and the example code. It has 24 examples with explanations, how to use the library. It's really not difficult.
But here is a small example, with one Head-Item. It looks like screenshot three, only in black color.

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
- If you found an issues, then open a ticket.
- If you like the library, press the star ;)
- A Wiki with small intruductions will come

### Screenshots
<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_1.png" alt="screenshot" width="300px" height="auto" />
<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_2.png" alt="screenshot" width="300px" height="auto" />
<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_3.png" alt="screenshot" width="300px" height="auto" />
<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_4.png" alt="screenshot" width="300px" height="auto" />
<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_5.png" alt="screenshot" width="300px" height="auto" />
<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_6.png" alt="screenshot" width="300px" height="auto" />
