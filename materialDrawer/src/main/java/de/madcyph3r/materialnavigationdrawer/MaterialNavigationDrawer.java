package de.madcyph3r.materialnavigationdrawer;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.madcyph3r.materialnavigationdrawer.custom.DefaultDrawerListener;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionChangeListener;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialMenuItem;
import de.madcyph3r.materialnavigationdrawer.menu.item.custom.MaterialItemCustom;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSection;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionActivity;
import de.madcyph3r.materialnavigationdrawer.menu.item.style.MaterialItemDevisor;
import de.madcyph3r.materialnavigationdrawer.menu.item.style.MaterialItemLabel;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionOnClickListener;
import de.madcyph3r.materialnavigationdrawer.tools.Utils;


@SuppressLint("InflateParams")
public abstract class MaterialNavigationDrawer<Fragment, CustomTextView extends TextView> extends ActionBarActivity implements MaterialSectionOnClickListener/*, MaterialSectionChangeListener*/ {

    // static backpattern types
    public static final int BACKPATTERN_BACK_ANYWHERE = 0;
    public static final int BACKPATTERN_BACK_TO_START_INDEX = 1;
    public static final int BACKPATTERN_CUSTOM = 2;
    public static final int BACKPATTERN_LAST_SECTION_FRAGMENT = 3;

    // default width and height
    public static final int DRAWER_DEFAULT_HEIGHT = 0;
    public static final int DRAWER_DEFAULT_WIDTH = 0;

    // global vars view
    protected MaterialDrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarToggle;
    private ImageView statusBar;
    private Toolbar toolbar;
    protected RelativeLayout drawerViewGroup;
    private ViewGroup contentViewGroup;
    //private FrameLayout frameContainer;
    //private RelativeLayout contentViewGroup;

    // global vars view menu
    protected LinearLayout itemSections;
    protected LinearLayout itemBottomSections;

    // global vars menu
    private MaterialItemSectionFragment currentSectionFragment;
    protected MaterialMenu currentMenu;
    // TODO implementieren
    protected MaterialMenu currentBottomMenu;
    private MaterialSectionChangeListener sectionChangeListener;
    private boolean loadFragmentOnStart;

    // global vars other
    private CharSequence title; // not really needed now, maybe later for restore

    protected float displayDensity;
    private int primaryColor;
    private int primaryDarkColor;
    private int dividerStrokeThickness;
    private boolean autoDarkStatusbar;
    private boolean multiPaneSupport;
    protected boolean drawerTouchLocked;
    private boolean slidingDrawerEffect;
    protected boolean kitkatTraslucentStatusbar;
    protected boolean belowToolbar;
    protected Resources resources;
    private int backPattern;
    protected int headerDPHeight;
    private int drawerDPWidth;
    private int drawerWidth;
    //private int drawerHeaderType;
    private boolean uniqueToolbarColor;
    private boolean finishActivityOnNewIntent;
    private DrawerLayout.DrawerListener drawerStateListener;
    private int drawerColor;
    private Drawable dividerColor;

    private int drawerActionBarStyle;
    protected ActionBar actionBar;
    private View overlayView;
    private boolean actionBarOverlay;
    private Float actionBarOverlayAlpha;
    protected List<MaterialItemSectionFragment> sectionFragmentLastBackPatternList;
    private Fragment currentFragment;
    private MaterialNavigationDrawer materialNavigationDrawer;

    protected abstract void afterSetContentView();

    @Override
    /**
     * Do not Override this method!!!
     * Use init() instead, see example app
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        materialNavigationDrawer = this;

        Resources.Theme theme = this.getTheme();
        initThemeVars(theme);
        // init global vars
        initGlobalVars();

        finishActivityOnNewIntent = finishActivityOnNewIntent();

        // must be implemented from the child class
        setContentView();
        afterSetContentView();

        // init views
        initViews();

        // init kitkat dependencies
        initKitKatDependencies(theme);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        // INIT ACTION BAR
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        overlayView = findViewById(R.id.overlayView);
        setActionBarOverlay(actionBarOverlay);

        // drawerViewGroup init
        initDrawerPrivate();
        // load here header and menu
    }

    protected abstract void setContentView();

    protected void settingToolbar(Toolbar tb) {

    }

    // init methods
    @SuppressLint("LongLogTag")
    private void initThemeVars(Resources.Theme theme) {
        DisplayMetrics dm = getResources().getDisplayMetrics();

        // init theme params
        TypedValue typedValue = new TypedValue();

        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        primaryColor = typedValue.data;

        theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        primaryDarkColor = typedValue.data;

        theme.resolveAttribute(R.attr.autoDarkStatusBar, typedValue, true);
        autoDarkStatusbar = typedValue.data != 0;

        theme.resolveAttribute(R.attr.uniqueToolbarColor, typedValue, false);
        uniqueToolbarColor = typedValue.data != 0;

        theme.resolveAttribute(R.attr.drawerColor, typedValue, true);
        drawerColor = typedValue.data;

       /* theme.resolveAttribute(R.attr.dividerColor, typedValue, true);
        dividerColor = typedValue.data;
*/
        theme.resolveAttribute(R.attr.drawerWidth, typedValue, true);
        drawerWidth = (int) typedValue.getDimension(dm);
        //TypedValue.complexToDimensionPixelSize(typedValue.data, getResources().getDisplayMetrics());
        if (drawerWidth < 0) {
            drawerWidth = 1;
        } else {
            setDrawerStateListener(new DefaultDrawerListener(drawerWidth));
        }

        theme.resolveAttribute(R.attr.dividerStrokeWidth, typedValue, true);
        dividerStrokeThickness = (int) typedValue.getDimension(dm);
        //TypedValue.complexToDimensionPixelSize(typedValue.data, getResources().getDisplayMetrics());
        if (dividerStrokeThickness < 0) {
            dividerStrokeThickness = 1;
        }

        theme.resolveAttribute(R.attr.belowToolbar, typedValue, true);
        belowToolbar = typedValue.data != 0;

        theme.resolveAttribute(R.attr.multipaneSupport, typedValue, false);
        multiPaneSupport = typedValue.data != 0;

        theme.resolveAttribute(R.attr.titleColor, typedValue, true);
        theme.resolveAttribute(R.attr.headItemStyle, typedValue, true);

        // Values of the NavigationDrawer
        TypedArray valuesNavigationDrawer = theme.obtainStyledAttributes(typedValue.resourceId, R.styleable.MaterialNavigationDrawer);
        dividerColor = valuesNavigationDrawer.getDrawable(R.styleable.MaterialNavigationDrawer_dividerColor);

        // Values of the Account
        TypedArray values = theme.obtainStyledAttributes(typedValue.resourceId, R.styleable.MaterialHeadItem);

        theme.resolveAttribute(R.attr.actionBarOverlay, typedValue, false);
        actionBarOverlay = typedValue.data != 0;

        theme.resolveAttribute(R.attr.actionBarOverlayAlpha, typedValue, false);
        actionBarOverlayAlpha = typedValue.getFloat();

        //actionbar style customization
        theme.resolveAttribute(R.attr.drawerActionBarStyle, typedValue, true);
        drawerActionBarStyle = typedValue.data;
    }


    private void initGlobalVars() {
        loadFragmentOnStart = true;
        drawerTouchLocked = false;
        slidingDrawerEffect = true;
        kitkatTraslucentStatusbar = false;
        drawerDPWidth = drawerWidthInit(); // 0 not set, will get calculated
        headerDPHeight = 0;
        backPattern = BACKPATTERN_BACK_ANYWHERE; // default back button option
        sectionFragmentLastBackPatternList = new ArrayList<>();
        //get resources and displayDensity
        resources = this.getResources();
        displayDensity = resources.getDisplayMetrics().density;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("deprecation")
    private void initViews() {
        // init toolbar & status bar
        statusBar = (ImageView) findViewById(R.id.statusBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // init drawerViewGroup components
        drawerLayout = (MaterialDrawerLayout) this.findViewById(R.id.drawer_layout);
        // init contentViewGroup
        contentViewGroup = (ViewGroup) this.findViewById(R.id.content);
        drawerViewGroup = (RelativeLayout) this.findViewById(R.id.drawer);

        drawerViewGroup.setBackgroundColor(drawerColor);
        // set items
        itemSections = (LinearLayout) this.findViewById(R.id.sections);
        itemBottomSections = (LinearLayout) this.findViewById(R.id.bottom_sections);
    }


    private void initKitKatDependencies(Resources.Theme theme) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            TypedArray windowTraslucentAttribute = theme.obtainStyledAttributes(new int[]{android.R.attr.windowTranslucentStatus});
            kitkatTraslucentStatusbar = windowTraslucentAttribute.getBoolean(0, false);
            if (kitkatTraslucentStatusbar) {
                Window window = this.getWindow();
                window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                RelativeLayout.LayoutParams statusParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, resources.getDimensionPixelSize(R.dimen.traslucentStatusMargin));
                statusBar.setLayoutParams(statusParams);
                statusBar.setImageDrawable(new ColorDrawable(darkenColor(primaryColor)));
            }
        }
    }

    private void initDrawerPrivate() {

        DrawerLayout.LayoutParams drawerParams = (android.support.v4.widget.DrawerLayout.LayoutParams) drawerViewGroup.getLayoutParams();
        Resources r = getResources();

        if (drawerDPWidth > 0) {
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, drawerDPWidth, r.getDisplayMetrics());
            drawerParams.width = px;
            drawerViewGroup.setLayoutParams(drawerParams);
        } else {
            drawerParams.width = Utils.getDrawerWidth(resources, drawerDPWidth);
        }

        drawerViewGroup.setLayoutParams(drawerParams);

        initDrawer();

        ViewTreeObserver vto = drawerViewGroup.getViewTreeObserver();

        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                int width = drawerViewGroup.getWidth();

                initDrawerPrivateType(width);

                ViewTreeObserver obs = drawerViewGroup.getViewTreeObserver();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    obs.removeOnGlobalLayoutListener(this);
                } else {
                    obs.removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

    protected abstract void initDrawerPrivateType(int width);

    public void initDrawer() {
        if (deviceSupportMultiPane()) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, drawerViewGroup);
            DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins((int) (320 * displayDensity), 0, 0, 0);
            contentViewGroup.setLayoutParams(params);
            drawerLayout.setScrimColor(Color.TRANSPARENT);
            drawerLayout.openDrawer(drawerViewGroup);
            drawerLayout.setMultipaneSupport(true);
            //drawerLayout.requestDisallowInterceptTouchEvent(true);
        } else {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);

            // don't use the constructor with toolbar, or onOptionsItemSelected() method will not be called
            actionBarToggle = new ActionBarDrawerToggle(this, drawerLayout/*, toolbar*/, R.string.nothing, R.string.nothing) {

                public void onDrawerClosed(View view) {
                    invalidateOptionsMenu();

                    drawerTouchLocked = false;

                    if (drawerStateListener != null)
                        drawerStateListener.onDrawerClosed(view);
                }

                public void onDrawerOpened(View drawerView) {
                    invalidateOptionsMenu();

                    if (drawerStateListener != null)
                        drawerStateListener.onDrawerOpened(drawerView);
                }

                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    // if user wants the sliding arrow it compare
                    if (slidingDrawerEffect)
                        super.onDrawerSlide(drawerView, slideOffset);
                    else
                        super.onDrawerSlide(drawerView, 0);

                    if (drawerStateListener != null)
                        drawerStateListener.onDrawerSlide(drawerView, slideOffset);
                }

                @Override
                public void onDrawerStateChanged(int newState) {
                    super.onDrawerStateChanged(newState);

                    if (drawerStateListener != null)
                        drawerStateListener.onDrawerStateChanged(newState);
                }
            };

            drawerLayout.setDrawerListener(actionBarToggle);
            drawerLayout.setMultipaneSupport(false);
        }
    }


    // own methods
    public void reloadMenu() {
        loadMenu(currentMenu, true);
        //loadMenuAndFragment(false, false);
        if (null != currentSectionFragment)
            currentSectionFragment.select();
    }

    protected void loadMenu(MaterialMenu menu) {
        loadMenu(menu, false);
    }
    // protected abstract MaterialMenu loadMenuType();

    protected void loadMenu(MaterialMenu menu, boolean forceReload) {

        if (menu != null && (menu != currentMenu || forceReload)) {

            // set the new current menu
            currentMenu = menu;

            itemSections.removeAllViews();
            itemBottomSections.removeAllViews();
            // create Menu
            List<MaterialMenuItem> itemList = menu.getItems();
            for (int i = 0; i < itemList.size(); i++) {
                if (itemList.get(i) instanceof MaterialItemSection) {
                    MaterialItemSection section = (MaterialItemSection) itemList.get(i);
                    if (section.isBottom())
                        addBottomSection((MaterialItemSection) itemList.get(i));
                    else
                        addSection((MaterialItemSection) itemList.get(i));
                } else if (itemList.get(i) instanceof MaterialItemCustom) {
                    MaterialItemCustom custom = (MaterialItemCustom) itemList.get(i);
                    if (custom.isBottom())
                        addBottomCustom((MaterialItemCustom) itemList.get(i));
                    else
                        addCustom((MaterialItemCustom) itemList.get(i));
                } else if (itemList.get(i) instanceof MaterialItemDevisor) {
                    MaterialItemDevisor devisor = (MaterialItemDevisor) itemList.get(i);
                    if (devisor.isBottom())
                        addDevisorBottom();
                    else
                        addDevisor();
                } else if (itemList.get(i) instanceof MaterialItemLabel) {
                    MaterialItemLabel label = (MaterialItemLabel) itemList.get(i);
                    if (label.isBottom())
                        addBottomLabel((MaterialItemLabel) itemList.get(i));
                    else
                        addLabel((MaterialItemLabel) itemList.get(i));
                }
            }

            // unselect all items
            for (int i = 0; i < itemList.size(); i++) {
                try {
                    ((MaterialItemSection) itemList.get(i)).unSelect();
                } catch (ClassCastException e) {
                    // nothing to do here
                }
            }
        }
    }

    /**
     * Method used with BACKPATTERN_CUSTOM to retrieve the section which is restored
     *
     * @param currentSection
     * @return the Section to restore that has Fragment as target (or currentSectionFragment for exit from activity)
     */
    protected MaterialItemSection backToSection(MaterialItemSection currentSection) {
        return currentSection;
    }

    /**
     * validate if the current fragment check against with the stored fragment class name
     *
     * @param fragmentClass the fragment class name
     * @return tell the truth
     */
    public boolean checkCurrentFragment(Class fragmentClass) {
        return fragmentClass == currentFragment.getClass();
    }

    public Fragment getStoredFragment() {
        return currentFragment;
    }

    public void loadStartFragmentFromMenu(MaterialMenu menu) {
        loadStartFragmentFromMenu(menu, "Can't find any fragment section, to set an start fragment. Set your own fragment.");
    }

    protected void loadStartFragmentFromMenu(MaterialMenu menu, String runtimeExceptionMessage) {
        MaterialItemSectionFragment section = null;
        if (menu.getStartIndex() > -1) {
            if (menu.getItem(menu.getStartIndex()) instanceof MaterialItemSectionFragment) {
                section = (MaterialItemSectionFragment) menu.getItem(menu.getStartIndex());
            } else {
                throw new RuntimeException("The index doesn't point on a MaterialFragmentSection.");
            }
        } else {
            section = menu.getFirstFragmentItem();
        }

        if (section != null) {
            changeToolbarColor(section);
            changeFragment((Fragment) section.getFragment(), section.getFragmentTitle());
            currentSectionFragment = section;

            // try to select. then it doesn't work, it's not the current menu
            if(currentMenu.getItems().contains(currentSectionFragment)) {
                section.select();
                sectionFragmentLastBackPatternList.add(section);
            }

        } else {
            throw new RuntimeException(runtimeExceptionMessage);
        }
    }

    /**
     * to allow fragment to be handled easy and fast
     *
     * @param fragment the new fragment item
     * @param title    the display title on the actionbar
     */
    public void changeFragment(Fragment fragment, String title) {
        changeFragment(fragment, title, null, false);
    }

    public void changeFragment(Fragment fragment, String title, boolean closeDrawerOnChange) {
        changeFragment(fragment, title, null, closeDrawerOnChange);
    }


    public void changeFragment(Fragment fragment, String title, Fragment oldFragment, boolean closeDrawerOnChange) {
        currentFragment = fragment;
        setTitle(title);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            // before honeycomb there is not android.app.Fragment
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (oldFragment != null && oldFragment != fragment)
                ft.remove((android.support.v4.app.Fragment) oldFragment);

            ft.replace(R.id.frame_container, (android.support.v4.app.Fragment) fragment).commit();
        } else if (fragment instanceof android.app.Fragment) {
            if (oldFragment instanceof android.support.v4.app.Fragment)
                throw new RuntimeException("You should use only one type of Fragment");

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (oldFragment != null && fragment != oldFragment)
                ft.remove((android.app.Fragment) oldFragment);

            ft.replace(R.id.frame_container, (android.app.Fragment) fragment).commit();
        } else if (fragment instanceof android.support.v4.app.Fragment) {
            if (oldFragment instanceof android.app.Fragment)
                throw new RuntimeException("You should use only one type of Fragment");

            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (oldFragment != null && oldFragment != fragment)
                ft.remove((android.support.v4.app.Fragment) oldFragment);

            ft.replace(R.id.frame_container, (android.support.v4.app.Fragment) fragment).commit();
        } else
            throw new RuntimeException("Fragment must be android.app.Fragment or android.support.v4.app.Fragment");

        if (!deviceSupportMultiPane() && closeDrawerOnChange)
            drawerLayout.closeDrawer(drawerViewGroup);
    }

    public void closeDrawer() {
        drawerLayout.closeDrawer(drawerViewGroup);
    }

    public void openDrawer() {
        drawerLayout.openDrawer(drawerViewGroup);
    }

    public enum ActionBarMenuItem {
        MENU, BACK, NONE
    }

    public void showActionBarMenuIcon(ActionBarMenuItem icon) {
        switch (icon) {
            case BACK:
                getActionBarToggle().setDrawerIndicatorEnabled(false);
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().invalidateOptionsMenu();
                break;
            case NONE:
                getActionBarToggle().setDrawerIndicatorEnabled(false);
                getSupportActionBar().setHomeButtonEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().invalidateOptionsMenu();
                break;
            case MENU:
            default:
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getActionBarToggle().setDrawerIndicatorEnabled(true);
                getSupportActionBar().invalidateOptionsMenu();
                break;
        }
    }

    public boolean deviceSupportMultiPane() {
        if (multiPaneSupport && resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && Utils.isTablet(getResources()))
            return true;
        else
            return false;
    }


    protected int darkenColor(int color) {
        /*if (color == primaryColor)
            return primaryDarkColor;*/

        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f; // value component
        return Color.HSVToColor(hsv);
    }

    /*private void unSelectOldSection(MaterialItemSectionOnClick section) {
        if (currentSectionFragment != null && section != currentSectionFragment) {
            currentSectionFragment.unSelect();
        }
    }*/

    public void addMultiPaneSupport() {
        this.multiPaneSupport = true;
    }

    /*   public void allowArrowAnimation() {
           slidingDrawerEffect = true;
       }
   */
    public void changeToolbarColor() {
        changeToolbarColor(null);
    }

    public void changeToolbarColor(MaterialItemSection section) {

        int sectionPrimaryColor;
        int sectionPrimaryColorDark;

        if (section != null && section.hasSectionColor() && !uniqueToolbarColor) {
            if (!section.hasSectionColorDark())
                sectionPrimaryColorDark = darkenColor(section.getSectionColor());
            else
                sectionPrimaryColorDark = section.getSectionColorDark();

            sectionPrimaryColor = section.getSectionColor();
        } else {
            if (autoDarkStatusbar)
                sectionPrimaryColorDark = darkenColor(primaryColor);
            else
                sectionPrimaryColorDark = primaryDarkColor;

            sectionPrimaryColor = primaryColor;
        }

        /*this.getToolbar().setBackgroundColor(sectionPrimaryColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            this.statusBar.setImageDrawable(new ColorDrawable(sectionPrimaryColorDark));*/

        changeToolbarColor(sectionPrimaryColor, sectionPrimaryColorDark);
    }

    public void changeToolbarColor(int primaryColor, int primaryDarkColor) {
        if (statusBar != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            this.statusBar.setImageDrawable(new ColorDrawable(primaryDarkColor));
        if (getToolbar() != null) {
            this.getToolbar().setBackgroundColor(primaryColor);
            setActionBarAlpha(actionBarOverlayAlpha);
        }
    }

    protected void addLabel(MaterialItemLabel label) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (48 * displayDensity));
        itemSections.addView(label.getView(), params);
    }

    protected void addBottomLabel(MaterialItemLabel label) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (48 * displayDensity));
        itemBottomSections.addView(label.getView(), params);
    }

    protected void addSection(MaterialItemSection section) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (48 * displayDensity));
        itemSections.addView(section.getView(), params);
    }

    protected void addBottomSection(MaterialItemSection section) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (48 * displayDensity));
        itemBottomSections.addView(section.getView(), params);
    }

    protected void addCustom(MaterialItemCustom custom) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (48 * displayDensity));
        itemSections.addView(custom.getView(), params);
    }

    protected void addBottomCustom(MaterialItemCustom custom) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (48 * displayDensity));
        itemBottomSections.addView(custom.getView(), params);
    }


    /**
     * the actual thing to add the devisor
     */
    protected void addDevisor() {
        LinearLayout.LayoutParams separator = new LinearLayout.LayoutParams(HorizontalScrollView.LayoutParams.MATCH_PARENT, dividerStrokeThickness);
        //border set
        View bar = new View(this);

        if (android.os.Build.VERSION.SDK_INT >= 16) {
            bar.setBackground(dividerColor);
        } else {
            bar.setBackgroundDrawable(dividerColor);
        }

        //bar.setBackgroundResource(dividerColor);
        bar.setLayoutParams(separator);
        itemSections.addView(bar);
    }

    protected void addDevisorBottom() {
        LinearLayout.LayoutParams separator = new LinearLayout.LayoutParams(HorizontalScrollView.LayoutParams.MATCH_PARENT, dividerStrokeThickness);
        //border set
        View bar = new View(this);

        if (android.os.Build.VERSION.SDK_INT >= 16) {
            bar.setBackground(dividerColor);
        } else {
            bar.setBackgroundDrawable(dividerColor);
        }

        //bar.setBackgroundResource(dividerColor);
        bar.setLayoutParams(separator);
        itemBottomSections.addView(bar);
    }


    // abstract methods
    public abstract void init(Bundle savedInstanceState);

    /**
     * you need to extend this function to get what on it.
     *
     * @return false as default;
     */
    protected abstract boolean finishActivityOnNewIntent();

    public int drawerWidthInit() {
        return 0;
    };

    public abstract void afterInit(Bundle savedInstanceState);

    /**
     * it will take up the request code to open a new intent for the activity to start
     *
     * @param clazz the class name
     * @return the request code in return
     */
    protected abstract int getNewIntentRequestCode(Class<?> clazz);


    // override
    @Override
    protected void onResume() {
        super.onResume();
        if (!deviceSupportMultiPane())
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, drawerViewGroup);
        else
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, drawerViewGroup);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarToggle != null)
            if (actionBarToggle.onOptionsItemSelected(item)) {
                return true;
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if (actionBarToggle != null)
            actionBarToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {// al cambio di orientamento dello schermo
        super.onConfigurationChanged(newConfig);

        // Passa tutte le configurazioni al drawerViewGroup
        if (actionBarToggle != null) {
            actionBarToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
        this.getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        switch (backPattern) {
            default:
            case BACKPATTERN_BACK_ANYWHERE:
                super.onBackPressed();
                break;
            case BACKPATTERN_BACK_TO_START_INDEX:
                if (currentMenu.getStartIndex() > -1) {
                    MaterialMenuItem menuItem = currentMenu.getItems().get(currentMenu.getStartIndex());
                    if (menuItem instanceof MaterialItemSectionFragment) {
                        MaterialItemSectionFragment section = (MaterialItemSectionFragment) menuItem;
                        if (currentSectionFragment == section)
                            super.onBackPressed();
                        else {
                            //section.select();
                            onClick(section, section.getView());
                        }
                    } else {
                        throw new RuntimeException("The index doesn't point on a MaterialFragmentSection.");
                    }
                } else {
                    throw new RuntimeException("There is no start index for this menu.");
                }
                break;
            case BACKPATTERN_CUSTOM:
                MaterialItemSection backedSection = backToSection(getCurrentSectionFragment());

                if (currentSectionFragment == backedSection)
                    super.onBackPressed();
                else {
                    if (!(backedSection instanceof MaterialItemSectionFragment)) {
                        throw new RuntimeException("The restored section must be a MaterialFragmentSection");
                    }
                    onClick(backedSection, backedSection.getView());
                }
                break;
            case BACKPATTERN_LAST_SECTION_FRAGMENT:

                int lastPos = sectionFragmentLastBackPatternList.size()-1;

                // do not load the current fragment again
                if (sectionFragmentLastBackPatternList.size() > 0 && currentSectionFragment == sectionFragmentLastBackPatternList.get(lastPos)) {
                    sectionFragmentLastBackPatternList.remove(lastPos);
                    lastPos = sectionFragmentLastBackPatternList.size()-1;
                }

                if (sectionFragmentLastBackPatternList.size() == 0) {
                    super.onBackPressed();
                } else {
                    MaterialItemSectionFragment sectionFragment = sectionFragmentLastBackPatternList.get(lastPos);
                    onClick(sectionFragment, sectionFragment.getView());
                    sectionFragmentLastBackPatternList.remove(lastPos);
                }
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();  // Always call the superclass method first
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /*   @Override
       protected void onSaveInstanceState(Bundle outState) {

       }
   */

    /**
     * states in minsecond that the drawer will need to wait before jumping into another view or intent to start
     *
     * @return in minseconds
     */
    protected int getSmoothPauseFromSelectingItem() {
        return 200;
    }

    @Override
    public void onClick(final MaterialItemSection section, View view) {

        if (!drawerTouchLocked) {
            if (section != currentSectionFragment) {
                if (section instanceof MaterialItemSectionFragment) {

                    sectionFragmentLastBackPatternList.add((MaterialItemSectionFragment)section);

                    //unSelectOldSection(section);
                    MaterialItemSectionFragment sectionFragment = (MaterialItemSectionFragment) section;
                    if (currentSectionFragment != null) {
                        currentSectionFragment.unSelect();
                        changeFragment((Fragment) sectionFragment.getFragment(), sectionFragment.getFragmentTitle(), (Fragment) ((MaterialItemSectionFragment) currentSectionFragment).getFragment(), true);
                        //sectionFragmentLastBackPatternList.add(currentSectionFragment);
                    } else
                        changeFragment((Fragment) sectionFragment.getFragment(), sectionFragment.getFragmentTitle(), null, true);

                    section.select();
                    changeToolbarColor(section);
                    currentSectionFragment = sectionFragment;
                } else if (section instanceof MaterialItemSectionActivity) {
                    section.unSelect();

                    // smooth close drawerViewGroup before activity start
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Class<?> clazzSign = null;
                            try {
                                MaterialItemSectionActivity sectionActivity = (MaterialItemSectionActivity) section;

                                clazzSign = Class.forName(sectionActivity.getIntent().getComponent().getClassName());
                                final int RequestCode = getNewIntentRequestCode(clazzSign);
                                if (RequestCode > 100) {
                                    startActivityForResult(sectionActivity.getIntent(), RequestCode);
                                } else {
                                    startActivity(sectionActivity.getIntent());
                                }
                                if (finishActivityOnNewIntent)
                                    finish();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }

                        }
                    }, getSmoothPauseFromSelectingItem());

                    closeDrawer();
                }
            } else {
                currentSectionFragment.select();
            }
        }
    }

    // getter and setter
    public Toolbar getToolbar() {
        return toolbar;
    }

    public MaterialItemSectionFragment getCurrentSectionFragment() {
        return currentSectionFragment;
    }

    /*public void setCurrentSectionFragment(MaterialItemSectionFragment section) {
        currentSectionFragment = section;
    }*/


    public int getDrawerDPWidth() {
        return drawerDPWidth;
    }

    public void setDrawerDPWidth(int drawerDPWidth) {
        this.drawerDPWidth = drawerDPWidth;
    }

    public MaterialMenu getCurrentMenu() {
        return currentMenu;
    }

    public MaterialMenu getCurrentBottomMenu() {
        return currentBottomMenu;
    }

    public boolean isLoadFragmentOnStartFromMenu() {
        return loadFragmentOnStart;
    }

    public void setLoadFragmentOnStartFromMenu(boolean loadFragmentOnStart) {
        this.loadFragmentOnStart = loadFragmentOnStart;
    }

    public void setDrawerStateListener(DrawerLayout.DrawerListener listener) {
        this.drawerStateListener = listener;
    }

    public void setBackPattern(int backPattern) {
        this.backPattern = backPattern;
    }

    /*public void setDrawerHeaderCustom(View view) {
        if (drawerHeaderType != DRAWERHEADER_CUSTOM)
            throw new RuntimeException("Your header is not set to Custom.");

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        customDrawerHeader.addView(view, params);
    }*/

   /* public void setDrawerHeaderCustom(View view, int headerDPHeight) {
        setDrawerHeaderCustom(view);

        this.headerDPHeight = headerDPHeight;
    }

    public void setDrawerHeaderImage(Bitmap background) {
        if (drawerHeaderType != DRAWERHEADER_IMAGE)
            throw new RuntimeException("Your header is not set to Image.");

        ImageView image = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        image.setImageBitmap(background);

        customDrawerHeader.addView(image, params);
    }*/

  /*  public void setDrawerHeaderImage(Bitmap background, int headerDPHeight) {
        setDrawerHeaderImage(background);

        this.headerDPHeight = headerDPHeight;
    }

    public void setDrawerHeaderImage(Drawable background) {
        if (drawerHeaderType != DRAWERHEADER_IMAGE)
            throw new RuntimeException("Your header is not set to Image.");

        ImageView image = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        image.setImageDrawable(background);

        customDrawerHeader.addView(image, params);
    }

    public void setDrawerHeaderImage(Drawable background, int headerDPHeight) {
        setDrawerHeaderImage(background);

        this.headerDPHeight = headerDPHeight;
    }*/

    public ActionBarDrawerToggle getActionBarToggle() {
        return actionBarToggle;
    }

    public boolean isDrawerTouchLocked() {
        return drawerTouchLocked;
    }

    public void setDrawerTouchLocked(boolean drawerTouchLocked) {
        this.drawerTouchLocked = drawerTouchLocked;
    }

    public void setDrawerLockMode(int mode) {
        drawerLayout.setDrawerLockMode(mode, drawerViewGroup);
    }

    public MaterialDrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public void setToolbarOverlay(boolean overlay) {
        setActionBarOverlay(overlay);
    }

    public void setActionBarOverlay(boolean overlay) {
        actionBarOverlay = overlay;
        if (actionBarOverlay) overlayView.setVisibility(View.GONE);
        else overlayView.setVisibility(View.VISIBLE);
    }

    public void setToolbarAlpha(Float alpha) {
        setActionBarAlpha(alpha);
    }

    public void setActionBarAlpha(Float alpha) {
        Drawable mActionBarBackgroundDrawable = toolbar.getBackground();
        int newAlpha = (int) (alpha * 255);
        mActionBarBackgroundDrawable.setAlpha(newAlpha);
        toolbar.getBackground().setAlpha(newAlpha);
    }

    public boolean isActionBarOverlay() {
        return actionBarOverlay;
    }

    public MaterialSectionChangeListener getSectionChangeListener() {
        return sectionChangeListener;
    }

    public void setSectionChangeListener(MaterialSectionChangeListener sectionChangeListener) {
        this.sectionChangeListener = sectionChangeListener;
    }

    public boolean isBelowToolbar() {
        return belowToolbar;
    }

    /*public List<MaterialItemSection> getSectionLastBackPatternList() {
        return sectionFragmentLastBackPatternList;
    }*/

}