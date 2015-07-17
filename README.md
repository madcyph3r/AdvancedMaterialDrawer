AdvancedMaterialDrawer
======================

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AdvancedMaterialDrawer-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1617) [![API](https://img.shields.io/badge/API-10%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=10)

[![Donate](https://www.paypalobjects.com/en_GB/i/btn/btn_donate_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=FXXJENAV99CNQ)

A Gmail-like Material Drawer implementation

Based on neokree's [MaterialDrawer](https://github.com/neokree/MaterialNavigationDrawer) library, but they are not the same. I have made many improvments, changes and added a lot of new features.
Big thanks to neokree, without him this library would not exist.


### Main Features:
Different Drawer types
- HeadItem (f.e. screenshot 1)
- No header (f.e. screenshot 6)
- Only Image in header (see example tool)
- Create your own header style (screenshot 7)

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
- Only icon menu (screenshot 8)
- Available Menu-items:
  - Labels
  - Divider
  - Normal Sections
    - can call new fragment, activity or implements own onClick
    - with and without icon
    - with notification number
  - Only Icon
    - can call new fragment, activity or implements own onClick
  - Bottom-Sections
    - same as normal section
  - Custom Sections ! (add your own section implementation, like a checkbox etc.) see screenshot 5

Fully themeable (screenshot 11)

Other:
- Drawer can be over and below the toolbar
- Tablet support
- Toolbar overflow support, so a image can be your background. (screenshot 9)
- Many many more: Setting your own Fragment on Start, etc. Play with the example app ;).
- The screenshots doesn't show all functions, you can do many more with the lib.

Write your own header Class

Many Many more


### Live Demo (Lib-Version 2.0.0 (17.07.15))
You can test the example application in your web browser.
https://appetize.io/app/dqmyynvjanx9hydtq4a4vjbkzw

### Example APK (Lib-Version 2.0.0 (17.07.15))
https://github.com/madcyph3r/AdvancedMaterialDrawer/raw/master/example-release.apk

or on the play store (Lib-Version 2.0.0 (17.07.15))

<a href="https://play.google.com/store/apps/details?id=de.madcyph3r.MaterialDrawer">
  <img alt="Android app on Google Play" src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>

### Download
```java
repositories {
    maven { url 'http://dl.bintray.com/madcyph3r/maven/' }
}

dependencies {
    compile 'de.madcyph3r:materialDrawer:2.0.0@aar'
}
```

### Dependencies
```java
compile 'com.android.support:appcompat-v7:21.0.3'
compile 'com.nineoldandroids:library:2.4.0'
```

### Usage
There are a lot of examples with explanations, how to use the library, here is a small example with one Head-Item. You will get the result from screenshot 1.
You see, it's easy :)
```java
public class HeadItemOneActivity extends MaterialNavHeadItemActivity {

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
        bundle.putString("instruction", "This example shows the head item style.");

        Fragment fragmentInstruction = new FragmentInstruction();
        fragmentInstruction.setArguments(bundle);

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Instruction", fragmentInstruction, "Head Item Style (One Item)"));
        menu.add(new MaterialItemSectionFragment(this, "Section 1", new FragmentDummy(), "Section 1"));
        menu.add(new MaterialItemSectionFragment(this, "Section 2", new FragmentDummy(), "Section 2"));
        menu.add(new MaterialItemSectionFragment(this, "Section 3", new FragmentDummy(), "Section 3"));

        // create Head Item
        // use bitmap and make a circle photo
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.yourAvatar);
        final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);
        MaterialHeadItem headItem = new MaterialHeadItem(this, "My HeadItem", "My Subtitle", drawableAppIcon, R.drawable.yourBackground, menu);
        this.addHeadItem(headItem);

        // load menu
        this.loadMenu(getCurrentHeadItem().getMenu());

        // load the first MaterialItemSectionFragment from the menu
        this.loadStartFragmentFromMenu(getCurrentHeadItem().getMenu());
    }

    @Override
    public void afterInit(Bundle savedInstanceState) {

    }
}
```
### From <= 1.1.4 to 2.0.0
I have changed a lot of names and methods. Sorry for that. But now its a lot easier to use this library. The methods name and parameters make more sense. Better abstraction level for the menu items and drawer types. A really better code understanding.
Please look in the examples, for the code changes. Then edit your code to the new code base. 

### Others

- If you've found an issue, look up the open [issues](https://github.com/madcyph3r/AdvancedMaterialDrawer/issues) and submit a new one if it isn't yet reported.
- If you like the library press the star ;)
- A Wiki with some Introduction will come soon.

### Screenshots

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_1.png" alt="screenshot" width="150px" height="auto" />

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_2.png" alt="screenshot" width="150px" height="auto" />

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_3.png" alt="screenshot" width="150px" height="auto" />

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_4.png" alt="screenshot" width="150px" height="auto" />

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_5.png" alt="screenshot" width="150px" height="auto" />

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_6.png" alt="screenshot" width="150px" height="auto" />

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_7.png" alt="screenshot" width="150px" height="auto" />

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_8.png" alt="screenshot" width="150px" height="auto" />

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_9.png" alt="screenshot" width="150px" height="auto" />

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_10.png" alt="screenshot" width="150px" height="auto" />

<img src="https://github.com/madcyph3r/AdvancedMaterialDrawer/blob/master/Screenshot_11.png" alt="screenshot" width="150px" height="auto" />
