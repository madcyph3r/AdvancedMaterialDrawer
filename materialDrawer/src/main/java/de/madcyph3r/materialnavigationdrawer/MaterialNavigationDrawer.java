package de.madcyph3r.materialnavigationdrawer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
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
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import de.madcyph3r.materialnavigationdrawer.listener.MaterialHeadItemChangeListener;
import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionChangeListener;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialDevisor;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialLabel;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionOnClickListener;
import de.madcyph3r.materialnavigationdrawer.tools.Utils;


/**
 * Activity that implements ActionBarActivity with a Navigation Drawer with Material Design style
 *
 * @author created by neokree
 */
@SuppressLint("InflateParams")
public abstract class MaterialNavigationDrawer<Fragment> extends ActionBarActivity implements MaterialSectionOnClickListener, MaterialSectionChangeListener, MaterialHeadItem.OnHeadItemDataLoaded {

    // static backpattern types
    public static final int BACKPATTERN_BACK_ANYWHERE = 0;
    public static final int BACKPATTERN_BACK_TO_START_INDEX = 1;
    public static final int BACKPATTERN_CUSTOM = 2;

    // static header types
    public static final int DRAWERHEADER_HEADITEMS = 0;
    public static final int DRAWERHEADER_IMAGE = 1;
    public static final int DRAWERHEADER_CUSTOM = 2;
    public static final int DRAWERHEADER_NO_HEADER = 3;

    // static restore state , maybe need later
    /*private static final String STATE_HEAD_ITEM = "headitem";
    private static final String STATE_SECTION = "section";
    private static final String STATE_HEAD_COUNTER = "headcounter";*/

    // globbar vars view
    private MaterialDrawerLayout layout;
    private ActionBarDrawerToggle actionBarToggle;
    private ImageView statusBar;
    private Toolbar toolbar;
    private RelativeLayout drawer;
    private RelativeLayout content;

    // global vars view headItem
    private ImageView headItemFirstPhoto;
    private ImageView headItemSecondPhoto;
    private ImageView headItemThirdPhoto;
    private ImageView headItemBackground;
    private TextView headItemTitle;
    private TextView headItemSubTitle;
    private ImageView headItemBackgroundSwitcher;
    private ImageButton headItemButtonSwitcher;
    private LinearLayout headItemBackgroundGradientLL;

    // global vars view custom
    private LinearLayout customDrawerHeader;

    // global vars view menu
    private LinearLayout items;
    private LinearLayout bottomSections;

    // global vars headItem
    private List<MaterialHeadItem> headItemManager;
    private MaterialHeadItemChangeListener headItemChangedListener;
    private MaterialSectionChangeListener sectionChangedListener;
    private MaterialMenu headItemSwitchExtraMenu;
    private boolean headItemSwitchShowForce;
    private boolean headItemSwitcherOpen = false;
    private Drawable headItemBackgroundGradient;

    // global vars menu
    private MaterialSection currentSection;
    private MaterialMenu currentMenu;
    private boolean loadFragmentOnStart;

    // global vars other
    private CharSequence title; // not really needed now, maybe later for restore
    private int animationTransition;
    private float displayDensity;
    private int primaryColor;
    private int primaryDarkColor;
    private boolean autoDarkStatusbar;
    private boolean multiPaneSupport;
    private boolean drawerTouchLocked;
    private boolean slidingDrawerEffect;
    private boolean kitkatTraslucentStatusbar;
    private Resources resources;
    private int backPattern;
    private int drawerDPWidth;
    private int drawerHeaderType;
    private boolean uniqueToolbarColor;
    private boolean finishActivityOnNewIntent;
    private DrawerLayout.DrawerListener drawerStateListener;
    private int drawerColor;
    private int titleColor;
    private int subTitleColor;
    private ActionBar actionBar;

    @Override
    /**
     * Do not Override this method!!!
     * Use init() instead, see example app
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources.Theme theme = this.getTheme();
        initThemeVars(theme);

        // init global vars
        initGlobalVars();

        // init headItems
        initHeadItemVars();

        // get and set header type
        drawerHeaderType = headerType();
        finishActivityOnNewIntent = finishActivityOnNewIntent();

        // set contentView
        if (drawerHeaderType == DRAWERHEADER_HEADITEMS)
            setContentView(R.layout.activity_material_navigation_drawer);
        else
            setContentView(R.layout.activity_material_navigation_drawer_customheader);

        // init views
        initViews();

        // init headItem listeners
        if (drawerHeaderType == DRAWERHEADER_HEADITEMS) {
            // set listener without sound. sound is called in the own listener
            headItemFirstPhoto.setSoundEffectsEnabled(false);
            headItemFirstPhoto.setOnClickListener(headItemFirstOnClickListener);
            headItemBackground.setSoundEffectsEnabled(false);
            headItemBackground.setOnClickListener(headItemBackgroundOnClickListener);
            headItemSecondPhoto.setSoundEffectsEnabled(false);
            headItemSecondPhoto.setOnClickListener(headItemSecondOnClickListener);
            headItemThirdPhoto.setSoundEffectsEnabled(false);
            headItemThirdPhoto.setOnClickListener(headItemThirdOnClickListener);
        }

        // init kitkat dependencies
        initKitKatDependencies(theme);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        // INIT ACTION BAR
        this.setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        // DEVELOPER CALL TO INIT
        init(savedInstanceState);

        // drawer init
        initDrawer();

        // load here header and menu
        initHeaderAndMenu(savedInstanceState);
    }

    // init methods
    private void initThemeVars(Resources.Theme theme) {
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
        theme.resolveAttribute(R.attr.multipaneSupport, typedValue, false);
        multiPaneSupport = typedValue.data != 0;
        theme.resolveAttribute(R.attr.titleColor, typedValue, true);
        theme.resolveAttribute(R.attr.accountStyle, typedValue, true);
        TypedArray values = theme.obtainStyledAttributes(typedValue.resourceId, R.styleable.MaterialAccount);
        titleColor = values.getColor(R.styleable.MaterialAccount_titleColor, 0x00FFFFFF);
        subTitleColor = values.getColor(R.styleable.MaterialAccount_subTitleColor, 0x00FFFFFF);
        headItemBackgroundGradient = values.getDrawable(R.styleable.MaterialAccount_backgroundGradient);
    }

    private void initGlobalVars() {
        animationTransition = 500;
        loadFragmentOnStart = true;
        drawerTouchLocked = false;
        slidingDrawerEffect = true;
        kitkatTraslucentStatusbar = false;
        drawerDPWidth = 0; // 0 not set, will get calculated
        drawerHeaderType = 0; // default type is headItem, but will get overridden
        backPattern = BACKPATTERN_BACK_ANYWHERE; // default back button option

        //get resources and displayDensity
        resources = this.getResources();
        displayDensity = resources.getDisplayMetrics().density;
    }

    private void initHeadItemVars() {
        headItemSwitchShowForce = false;
        headItemManager = new LinkedList<>();
        headItemSwitchExtraMenu = new MaterialMenu();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("deprecation")
    private void initViews() {
        // init toolbar & status bar
        statusBar = (ImageView) findViewById(R.id.statusBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        // init drawer components
        layout = (MaterialDrawerLayout) this.findViewById(R.id.drawer_layout);

        // init content
        content = (RelativeLayout) this.findViewById(R.id.content);
        drawer = (RelativeLayout) this.findViewById(R.id.drawer);

        // init header, depends on setContentView
        if (drawerHeaderType == DRAWERHEADER_HEADITEMS) {
            headItemTitle = (TextView) this.findViewById(R.id.current_head_item_title);
            headItemTitle.setTextColor(titleColor);
            headItemSubTitle = (TextView) this.findViewById(R.id.current_head_item_sub_title);
            headItemSubTitle.setTextColor(subTitleColor);
            headItemFirstPhoto = (ImageView) this.findViewById(R.id.current_head_item_photo);
            headItemSecondPhoto = (ImageView) this.findViewById(R.id.second_head_item_photo);
            headItemThirdPhoto = (ImageView) this.findViewById(R.id.third_head_item_photo);
            headItemBackground = (ImageView) this.findViewById(R.id.current_back_item_background);
            headItemBackgroundSwitcher = (ImageView) this.findViewById(R.id.background_switcher);
            headItemButtonSwitcher = (ImageButton) this.findViewById(R.id.user_switcher);
            headItemBackgroundGradientLL = (LinearLayout) this.findViewById(R.id.background_gradient);

            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                headItemBackgroundGradientLL.setBackgroundDrawable(headItemBackgroundGradient);
            } else {
                headItemBackgroundGradientLL.setBackground(headItemBackgroundGradient);
            }
        } else
            customDrawerHeader = (LinearLayout) this.findViewById(R.id.drawer_header);

        drawer.setBackgroundColor(drawerColor);


        // set items
        items = (LinearLayout) this.findViewById(R.id.items);
        bottomSections = (LinearLayout) this.findViewById(R.id.bottom_sections);
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

                if (drawerHeaderType == DRAWERHEADER_HEADITEMS) {
                    RelativeLayout.LayoutParams photoParams = (RelativeLayout.LayoutParams) headItemFirstPhoto.getLayoutParams();
                    photoParams.setMargins((int) (16 * displayDensity), resources.getDimensionPixelSize(R.dimen.traslucentPhotoMarginTop), 0, 0);
                    headItemFirstPhoto.setLayoutParams(photoParams);
                }
            }
        }
    }

    private void initDrawer() {
        if (drawerDPWidth > 0) {
            ViewGroup.LayoutParams params = drawer.getLayoutParams();
            Resources r = getResources();
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, drawerDPWidth, r.getDisplayMetrics());
            params.width = px;
            drawer.setLayoutParams(params);
        } else {
            DrawerLayout.LayoutParams drawerParams = (android.support.v4.widget.DrawerLayout.LayoutParams) drawer.getLayoutParams();
            drawerParams.width = Utils.getDrawerWidth(resources, drawerDPWidth);
            drawer.setLayoutParams(drawerParams);
        }

        if (deviceSupportMultiPane()) {
            layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, drawer);
            DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins((int) (320 * displayDensity), 0, 0, 0);
            content.setLayoutParams(params);
            layout.setScrimColor(Color.TRANSPARENT);
            layout.openDrawer(drawer);
            layout.setMultipaneSupport(true);
            //layout.requestDisallowInterceptTouchEvent(true);
        } else {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);

            actionBarToggle = new ActionBarDrawerToggle(this, layout, toolbar, R.string.nothing, R.string.nothing) {

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
            layout.setDrawerListener(actionBarToggle);
            layout.setMultipaneSupport(false);
        }

        ViewTreeObserver vto = drawer.getViewTreeObserver();

        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                int width = drawer.getWidth();

                int heightHeader = 0;

                switch (drawerHeaderType) {
                    default:
                    case DRAWERHEADER_HEADITEMS:
                    case DRAWERHEADER_IMAGE:
                    case DRAWERHEADER_CUSTOM:
                        heightHeader = (9 * width) / 16;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                            heightHeader += (int) (24 * displayDensity);

                        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT && kitkatTraslucentStatusbar)
                            heightHeader += (int) (25 * displayDensity);

                        break;
                    case DRAWERHEADER_NO_HEADER:
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT || (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT && !kitkatTraslucentStatusbar)) {
                            heightHeader = 0;
                        } else {
                            // kitkat traslucentstatusbar and lollipop has 25 dp
                            heightHeader = (int) (25 * displayDensity);
                        }
                        break;
                }

                // set header space
                if (drawerHeaderType == DRAWERHEADER_HEADITEMS) {
                    headItemBackground.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, heightHeader));
                    headItemBackgroundSwitcher.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, heightHeader));
                } else {
                    customDrawerHeader.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, heightHeader));
                }

                ViewTreeObserver obs = drawer.getViewTreeObserver();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    obs.removeOnGlobalLayoutListener(this);
                } else {
                    obs.removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

    private void initHeaderAndMenu(Bundle savedInstanceState) {
        // implements maybe later savedInstanceState
        //if (savedInstanceState == null) {

        if (drawerHeaderType == DRAWERHEADER_HEADITEMS) {
            // init headitem views
            if (headItemManager.size() > 0) {
                //currentHeadItem = headItemManager.get(0);
                notifyHeadItemsDataChanged();
            } else {
                throw new RuntimeException("No head item");
            }
        }

        // loadMenu
        loadMenu(loadFragmentOnStart, true);

//        changeToolbarColor(currentSection);

        /*} else {

            // load here the saved data
            ArrayList<Integer> headItemsNumbers = savedInstanceState.getIntegerArrayList(STATE_HEAD_ITEM);
            ....
            changeToolbarColor(section);*/
        //}
    }


    // own methods
    public void reloadMenu() {
        loadMenu(false, false);
        currentSection.select();
    }

    // if you send -1, it will load the first fragment section
    public void reloadMenu(int loadSectionPosition) {
        if (loadSectionPosition == -1)
            loadMenu(true, true);
        else {
            loadMenu(false, false);
            if (currentMenu.getSection(loadSectionPosition) instanceof MaterialSection) {
                currentSection = currentMenu.getSection(loadSectionPosition);
                if ((currentSection.getTarget() == MaterialSection.TARGET_FRAGMENT)) {
                    currentSection.select();
                    setFragment((Fragment) currentSection.getTargetFragment(), currentSection.getTitle(), null, false);
                    changeToolbarColor(currentSection);
                }
            } else {
                throw new RuntimeException("Given Position has not a MaterialSection");
            }
        }
    }

    private void loadMenu(boolean loadFragment) {
        loadMenu(loadFragment, false);
    }

    private void loadMenu(boolean loadFragment, boolean fromStart) {
        //change menu only if a menu is defined
        // clear selected after change
        boolean loadMenu = true;
        if (drawerHeaderType == DRAWERHEADER_HEADITEMS) {
            if ((headItemManager.get(0).getMenu() != null && currentMenu != headItemManager.get(0).getMenu()) || currentMenu == null) {
                currentMenu = headItemManager.get(0).getMenu();
            } else {
                if (currentMenu.getItems().size() == 0)
                    loadMenu = false;
            }
        }

        if (currentMenu == null && loadMenu) {
            throw new RuntimeException("You must set a menu");
        }

        if (loadMenu) {
            items.removeAllViews();
            bottomSections.removeAllViews();
            // create Menu
            List<Object> sectionList = currentMenu.getItems();
            for (int i = 0; i < sectionList.size(); i++) {
                if (sectionList.get(i) instanceof MaterialSection) {
                    MaterialSection section = (MaterialSection) sectionList.get(i);
                    if (section.isBottom())
                        addBottomSection((MaterialSection) sectionList.get(i));
                    else
                        addSection((MaterialSection) sectionList.get(i));
                } else if (sectionList.get(i) instanceof MaterialDevisor) {
                    MaterialDevisor devisor = (MaterialDevisor) sectionList.get(i);
                    if (devisor.isBottom())
                        addDevisorBottom();
                    else
                        addDevisor();

                } else if (sectionList.get(i) instanceof MaterialLabel) {
                    MaterialLabel label = (MaterialLabel) sectionList.get(i);
                    if (label.isBottom())
                        addBottomLabel((MaterialLabel) sectionList.get(i));
                    else
                        addLabel((MaterialLabel) sectionList.get(i));
                }
            }

            // unselect all items
            for (int i = 0; i < sectionList.size(); i++) {
                try {
                    ((MaterialSection) sectionList.get(i)).unSelect();
                } catch (ClassCastException e) {
                    // nothing to do here
                }
            }

            try {
                if (loadFragment) {
                    if (drawerHeaderType == DRAWERHEADER_HEADITEMS && headItemManager.get(0) != null &&
                            headItemManager.get(0).isLoadFragmentOnChanged() &&
                            sectionList.get(headItemManager.get(0).getStartIndex()) instanceof MaterialSection) {

                        currentSection = (MaterialSection) sectionList.get(headItemManager.get(0).getStartIndex());
                        if ((currentSection.getTarget() == MaterialSection.TARGET_FRAGMENT)) {
                            currentSection.select();
                            setFragment((Fragment) currentSection.getTargetFragment(), currentSection.getTitle(), null, false);
                            changeToolbarColor(currentSection);
                        }

                    } else if (fromStart || headItemManager == null || headItemManager.get(0) == null || (headItemManager.get(0) != null && headItemManager.get(0).isLoadFragmentOnChanged())) {
                        // load first found fragment
                        for (int i = 0; i < sectionList.size(); i++) {
                            if (sectionList.get(i) instanceof MaterialSection) {
                                currentSection = (MaterialSection) sectionList.get(i);
                                if ((/*currentSection.getTarget() == MaterialSection.TARGET_ACTIVITY ||*/ currentSection.getTarget() == MaterialSection.TARGET_FRAGMENT)) {
                                    //fragmentFound = true;
                                    currentSection.select();
                                    setFragment((Fragment) currentSection.getTargetFragment(), currentSection.getTitle(), null, false);
                                    changeToolbarColor(currentSection);
                                    break;
                                } /*else {
                                currentSection.unSelect();
                            }*/
                            }
                        }
                    }
                }
            } catch (RuntimeException e) {
                // do nothing
            }
        }
    }

    private void loadHeadItemMenu() {
        // remove all section views
        items.removeAllViews();
        bottomSections.removeAllViews();

        // show current menu
        List<Object> sectionList = currentMenu.getItems();
        for (int i = 0; i < sectionList.size(); i++) {
            if (sectionList.get(i) instanceof MaterialSection) {
                MaterialSection section = (MaterialSection) sectionList.get(i);
                if (section.isBottom())
                    addBottomSection((MaterialSection) sectionList.get(i));
                else
                    addSection((MaterialSection) sectionList.get(i));
            } else if (sectionList.get(i) instanceof MaterialDevisor) {
                MaterialDevisor devisor = (MaterialDevisor) sectionList.get(i);
                if (devisor.isBottom())
                    addDevisorBottom();
                else
                    addDevisor();

            } else if (sectionList.get(i) instanceof MaterialLabel) {
                MaterialLabel label = (MaterialLabel) sectionList.get(i);
                if (label.isBottom())
                    addBottomLabel((MaterialLabel) sectionList.get(i));
                else
                    addLabel((MaterialLabel) sectionList.get(i));
            }
        }
    }

    /**
     * Method used with BACKPATTERN_CUSTOM to retrieve the section which is restored
     *
     * @param currentSection
     * @return the Section to restore that has Fragment as target (or currentSection for exit from activity)
     */
    protected MaterialSection backToSection(MaterialSection currentSection) {
        return currentSection;
    }

    public void setCustomFragment(Fragment fragment, String title) {
        setCustomFragment(fragment, title, true);
    }

    public void setCustomFragment(Fragment fragment, String title, boolean closeDrawer) {
        setFragment(fragment, title, null, closeDrawer);
        changeToolbarColor(null);
    }

    public void setFragment(Fragment fragment, String title, Fragment oldFragment, boolean closeDrawer) {
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

        if (!deviceSupportMultiPane() && closeDrawer)
            layout.closeDrawer(drawer);
    }

    private int getHeadItemNumber(MaterialHeadItem headItem) {
        return headItemManager.indexOf(headItem);
    }

    private MaterialHeadItem findHeadItemNumber(int number) {
        for (MaterialHeadItem headItem : headItemManager)
            if (getHeadItemNumber(headItem) == number)
                return headItem;

        return null;
    }

    public void closeDrawer() {
        layout.closeDrawer(drawer);
    }

    public void openDrawer() {
        layout.openDrawer(drawer);
    }

    // change the headItem1 with headItem 2 in the headItemManager list
    private void changeHeadItems(MaterialHeadItem headItem1, MaterialHeadItem headItem2) {
        int index1 = headItemManager.indexOf(headItem1);
        int index2 = headItemManager.indexOf(headItem2);

        MaterialHeadItem headItemTmp = headItemManager.get(index1);
        headItemManager.set(index1, headItem2);
        headItemManager.set(index2, headItemTmp);
    }

    private void switchHeadItemsList(MaterialHeadItem oldHeaditem, MaterialHeadItem newFirstHeadItem) {

        // change button back
        headItemButtonSwitcher.setImageResource(R.drawable.ic_arrow_drop_down_white_24dp);
        headItemSwitcherOpen = false;

        changeHeadItems(oldHeaditem, newFirstHeadItem);

        // load new first head item information
        notifyHeadItemDataChangedSwitch();
        loadMenu(true);

        if (headItemManager.get(0).isCloseDrawerOnChanged() && !deviceSupportMultiPane()) {
            layout.closeDrawer(drawer);
        }
    }

    private void switchHeadItemsIcon(final MaterialHeadItem newFirstHeadItem, boolean animation) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && animation) {
            // change with animation
            switchHeadItemsIconApi14(newFirstHeadItem);
        } else {
            //change no animation
            changeHeadItems(headItemManager.get(0), newFirstHeadItem);

            notifyHeadItemsDataChanged();
            loadMenu(true);

            if (headItemManager.get(0).isCloseDrawerOnChanged() && !deviceSupportMultiPane()) {
                layout.closeDrawer(drawer);
            }
        }

        // set switcher button to default
        headItemButtonSwitcher.setImageResource(R.drawable.ic_arrow_drop_down_white_24dp);
        headItemSwitcherOpen = false;
    }

    @TargetApi(14)
    private void switchHeadItemsIconApi14(final MaterialHeadItem newFirstHeadItem) {
        final ImageView floatingPhotoImage = new ImageView(this);

        Rect startingRect = new Rect();
        Rect finalRect = new Rect();
        Point offsetHover = new Point();

        // 64dp primary user image / 40dp other user image = 1.6 scale
        float finalScale = 1.6f;

        // get right statusbar height
        final int statusBarHeight;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT || (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT && !kitkatTraslucentStatusbar)) {
            statusBarHeight = (int) (25 * displayDensity);
        } else {
            statusBarHeight = 0;
        }

        // get clicked headItem
        ImageView headItemPhotoClicked;
        if (getHeadItemNumber(newFirstHeadItem) == MaterialHeadItem.SECOND_HEADITEM) {
            headItemPhotoClicked = headItemSecondPhoto;
        } else { // must be head item 3
            headItemPhotoClicked = headItemThirdPhoto;
        }

        headItemPhotoClicked.getGlobalVisibleRect(startingRect, offsetHover);
        floatingPhotoImage.setImageDrawable(headItemPhotoClicked.getDrawable());

        // add floating image, this image will egt moved and animated
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(headItemPhotoClicked.getWidth(), headItemPhotoClicked.getHeight());
        params.setMargins(offsetHover.x, offsetHover.y - statusBarHeight, 0, 0);
        drawer.addView(floatingPhotoImage, params);

        // get the drawable photo from the first headItem
        headItemPhotoClicked.setImageDrawable(headItemManager.get(0).getPhoto());

        // need for headItemBackground switch
        headItemBackgroundSwitcher.setImageDrawable(newFirstHeadItem.getBackground());

        headItemFirstPhoto.getGlobalVisibleRect(finalRect);

        //calculate offset for the move
        int offset = (((finalRect.bottom - finalRect.top) - (startingRect.bottom - finalRect.top)) / 2);
        finalRect.offset(offset, offset - statusBarHeight);
        startingRect.offset(0, -statusBarHeight);


        // generate animator set
        AnimatorSet set = new AnimatorSet();
        set
                // si ingrandisce l'immagine e la si sposta a sinistra.
                .play(ObjectAnimator.ofFloat(floatingPhotoImage, View.X, startingRect.left, finalRect.left))
                .with(ObjectAnimator.ofFloat(floatingPhotoImage, View.Y, startingRect.top, finalRect.top))
                .with(ObjectAnimator.ofFloat(floatingPhotoImage, View.SCALE_X, 1f, finalScale))
                .with(ObjectAnimator.ofFloat(floatingPhotoImage, View.SCALE_Y, 1f, finalScale))
                .with(ObjectAnimator.ofFloat(headItemFirstPhoto, View.ALPHA, 1f, 0f))
                .with(ObjectAnimator.ofFloat(headItemBackground, View.ALPHA, 1f, 0f))
                .with(ObjectAnimator.ofFloat(headItemPhotoClicked, View.SCALE_X, 0f, 1f))
                .with(ObjectAnimator.ofFloat(headItemPhotoClicked, View.SCALE_Y, 0f, 1f));
        set.setDuration(animationTransition);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {

            @SuppressLint("NewApi")
            @Override
            public void onAnimationEnd(Animator animation) {


                ((View) headItemFirstPhoto).setAlpha(1);
                setFirstHeadItemPhoto(newFirstHeadItem.getPhoto());

                // remove floating image after animation
                drawer.removeView(floatingPhotoImage);

                // set title and subtitle
                setHeadItemTitle(newFirstHeadItem.getTitle());
                setHeadItemSubTitle(newFirstHeadItem.getSubTitle());

                // show new background
                setDrawerBackground(newFirstHeadItem.getBackground());
                ((View) headItemBackground).setAlpha(1);

                // switch old and new headitem in the manager list
                changeHeadItems(headItemManager.get(0), newFirstHeadItem);

                // load head item menu
                loadMenu(true);

                if (headItemManager.get(0).isCloseDrawerOnChanged() && !deviceSupportMultiPane()) {
                    layout.closeDrawer(drawer);
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                onAnimationEnd(animation);
            }
        });

        set.start();
    }

    private boolean deviceSupportMultiPane() {
        if (multiPaneSupport && resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && Utils.isTablet(getResources()))
            return true;
        else
            return false;
    }

    private void recycleHeadItem() {
        for (MaterialHeadItem headItem : headItemManager) {
            headItem.recycleDrawable();
        }
    }

    protected int darkenColor(int color) {
        /*if (color == primaryColor)
            return primaryDarkColor;*/

        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f; // value component
        return Color.HSVToColor(hsv);
    }

    /*private void unSelectOldSection(MaterialSection section) {
        if (currentSection != null && section != currentSection) {
            currentSection.unSelect();
        }
    }*/

    public void addMultiPaneSupport() {
        this.multiPaneSupport = true;
    }

    public void allowArrowAnimation() {
        slidingDrawerEffect = true;
    }

    public void changeToolbarColor() {
        changeToolbarColor(null);
    }

    public void changeToolbarColor(MaterialSection section) {

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
        if (getToolbar() != null)
            this.getToolbar().setBackgroundColor(primaryColor);
    }

    private void addLabel(MaterialLabel label) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (48 * displayDensity));
        items.addView(label.getView(), params);
    }

    private void addBottomLabel(MaterialLabel label) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (48 * displayDensity));
        bottomSections.addView(label.getView(), params);
    }

    private void addSection(MaterialSection section) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (48 * displayDensity));
        items.addView(section.getView(), params);
    }

    private void addBottomSection(MaterialSection section) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (48 * displayDensity));
        bottomSections.addView(section.getView(), params);
    }

    private void addDevisor() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.layout_divisor, null, false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        items.addView(view, params);
    }

    private void addDevisorBottom() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.layout_divisor, null, false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        items.addView(view, params);
    }

    public void removeHeadItem(MaterialHeadItem headItem) {
        removeHeadItem(headItemManager.indexOf(headItem));
    }

    public void removeHeadItem(int location) {
        if (DRAWERHEADER_HEADITEMS != drawerHeaderType) {
            throw new RuntimeException("Your header is not set to HeadItems.");
        }

        if (headItemManager.size() == 1)
            throw new RuntimeException("You can't remove all head items.");

        headItemManager.remove(location);

        if (headItemManager.size() > 3 || headItemSwitchShowForce) {
            headItemButtonSwitcher.setImageResource(R.drawable.ic_arrow_drop_down_white_24dp);
            headItemButtonSwitcher.setVisibility(View.VISIBLE);
        } else {
            headItemButtonSwitcher.setVisibility(View.GONE);
        }

        notifyHeadItemsDataChanged();
    }

    public void addHeadItem(MaterialHeadItem headItem) {
        addHeadItem(headItem, false);
    }

    public void addHeadItem(MaterialHeadItem headItem, boolean notify) {
        if (DRAWERHEADER_HEADITEMS != drawerHeaderType) {
            throw new RuntimeException("Your header is not set to HeadItems.");
        }
        headItem.setHeadItemDataLoadedListener(this);

        headItemManager.add(headItem);

        if (headItemManager.size() == 4) {
            headItemButtonSwitcher.setOnClickListener(headItemSwitcher);
        }

        if (headItemManager.size() > 3 || headItemSwitchShowForce) {
            headItemButtonSwitcher.setImageResource(R.drawable.ic_arrow_drop_down_white_24dp);
            headItemButtonSwitcher.setVisibility(View.VISIBLE);
        } else {
            headItemButtonSwitcher.setVisibility(View.GONE);
        }

        if (notify)
            notifyHeadItemsDataChanged();
    }

    private void notifyHeadItemDataChangedSwitch() {
        this.setFirstHeadItemPhoto(headItemManager.get(0).getPhoto());
        this.setDrawerBackground(headItemManager.get(0).getBackground());
        this.setHeadItemTitle(headItemManager.get(0).getTitle());
        this.setHeadItemSubTitle(headItemManager.get(0).getSubTitle());
    }

    /**
     * Reload Application data from HeadItem Information
     */
    public void notifyHeadItemsDataChanged() {
        if (headItemManager.size() >= 3) {
            this.setThirdHeadItemPhoto(findHeadItemNumber(MaterialHeadItem.THIRD_HEADITEM).getPhoto());
            this.setSecondHeadItemPhoto(findHeadItemNumber(MaterialHeadItem.SECOND_HEADITEM).getPhoto());
            this.setFirstHeadItemPhoto(headItemManager.get(0).getPhoto());
            this.setDrawerBackground(headItemManager.get(0).getBackground());
            this.setHeadItemTitle(headItemManager.get(0).getTitle());
            this.setHeadItemSubTitle(headItemManager.get(0).getSubTitle());
        } else if (headItemManager.size() == 2) {
            this.setThirdHeadItemPhoto(null);
            this.setSecondHeadItemPhoto(findHeadItemNumber(MaterialHeadItem.SECOND_HEADITEM).getPhoto());
            this.setFirstHeadItemPhoto(headItemManager.get(0).getPhoto());
            this.setDrawerBackground(headItemManager.get(0).getBackground());
            this.setHeadItemTitle(headItemManager.get(0).getTitle());
            this.setHeadItemSubTitle(headItemManager.get(0).getSubTitle());
        } else if (headItemManager.size() == 1) {
            this.setThirdHeadItemPhoto(null);
            this.setSecondHeadItemPhoto(null);
            this.setFirstHeadItemPhoto(headItemManager.get(0).getPhoto());
            this.setDrawerBackground(headItemManager.get(0).getBackground());
            this.setHeadItemTitle(headItemManager.get(0).getTitle());
            this.setHeadItemSubTitle(headItemManager.get(0).getSubTitle());
        }
    }

    // create section for the headItem changer menu
    private MaterialSection newHeadSection(String title, Drawable icon, MaterialMenu menu, int position, boolean refreshMenu) {
        MaterialSection section = new MaterialSection<Fragment>(this, true, MaterialSection.TARGET_CLICK, false, this);
        section.setFillIconColor(false);
        section.setIcon(icon);
        section.setTitle(title);
        //section.setPosition(position);
        menu.addItem(section, position);

        if (refreshMenu)
            reloadMenu();

        return section;
    }

    private MaterialSection newHeadSection(String title, Drawable icon, MaterialMenu menu, int position) {
        return newHeadSection(title, icon, menu, position, false);
    }

    private MaterialSection newHeadSection(String title, Drawable icon, MaterialMenu menu, boolean refreshMenu) {
        return newHeadSection(title, icon, menu, menu.getItems().size(), refreshMenu);
    }

    private MaterialSection newHeadSection(String title, Drawable icon, MaterialMenu menu) {
        return newHeadSection(title, icon, menu, menu.getItems().size());
    }


    public MaterialLabel newLabel(String label, boolean bottom, MaterialMenu menu, int position, boolean refreshMenu) {
        MaterialLabel labelM = new MaterialLabel(this, label, bottom);
        menu.addItem(labelM, position);

        if (refreshMenu)
            reloadMenu();

        return labelM;
    }

    public MaterialLabel newLabel(String label, boolean bottom, MaterialMenu menu, int position) {
        return newLabel(label, bottom, menu, position, false);
    }

    public MaterialLabel newLabel(String label, boolean bottom, MaterialMenu menu, boolean refreshMenu) {
        return newLabel(label, bottom, menu, menu.getItems().size(), refreshMenu);
    }

    public MaterialLabel newLabel(String label, boolean bottom, MaterialMenu menu) {
        return newLabel(label, bottom, menu, menu.getItems().size());
    }


    public MaterialDevisor newDevisor(MaterialMenu menu, int position, boolean refreshMenu) {
        MaterialDevisor devisor = new MaterialDevisor();
        menu.addItem(devisor, position);

        if (refreshMenu)
            reloadMenu();

        return devisor;
    }

    public MaterialDevisor newDevisor(MaterialMenu menu, int position) {
        return newDevisor(menu, position, false);
    }

    public MaterialDevisor newDevisor(MaterialMenu menu, boolean refreshMenu) {
        return newDevisor(menu, menu.getItems().size(), refreshMenu);
    }

    public MaterialDevisor newDevisor(MaterialMenu menu) {
        return newDevisor(menu, menu.getItems().size());
    }


    // create items for a headItem
    public MaterialSection newSection(String title, Drawable icon, boolean bottom, MaterialMenu menu, int position, boolean refreshMenu) {
        MaterialSection section = new MaterialSection<Fragment>(this, true, MaterialSection.TARGET_CLICK, bottom, this);
        section.setIcon(icon);
        section.setTitle(title);
        //section.setPosition(position);
        menu.addItem(section, position);

        if (refreshMenu)
            reloadMenu();

        return section;
    }

    public MaterialSection newSection(String title, Drawable icon, boolean bottom, MaterialMenu menu, int position) {
        return newSection(title, icon, bottom, menu, position, false);
    }

    public MaterialSection newSection(String title, Drawable icon, boolean bottom, MaterialMenu menu, boolean refreshMenu) {
        return newSection(title, icon, bottom, menu, menu.getItems().size(), refreshMenu);
    }

    public MaterialSection newSection(String title, Drawable icon, boolean bottom, MaterialMenu menu) {
        return newSection(title, icon, bottom, menu, menu.getItems().size());
    }


    public MaterialSection newSection(String title, Drawable icon, Fragment target, boolean bottom, MaterialMenu menu, int position, boolean refreshMenu) {
        MaterialSection section = new MaterialSection<Fragment>(this, true, MaterialSection.TARGET_FRAGMENT, bottom, this);
        section.setOnClickListener(this);
        section.setIcon(icon);
        section.setTitle(title);
        section.setTarget(target);
        //section.setPosition(position);
        menu.addItem(section, position);

        if (refreshMenu)
            reloadMenu();

        return section;
    }

    public MaterialSection newSection(String title, Drawable icon, Fragment target, boolean bottom, MaterialMenu menu, int position) {
        return newSection(title, icon, target, bottom, menu, position, false);
    }

    public MaterialSection newSection(String title, Drawable icon, Fragment target, boolean bottom, MaterialMenu menu, boolean refreshMenu) {
        return newSection(title, icon, target, bottom, menu, menu.getItems().size(), refreshMenu);
    }

    public MaterialSection newSection(String title, Drawable icon, Fragment target, boolean bottom, MaterialMenu menu) {
        return newSection(title, icon, target, bottom, menu, menu.getItems().size());
    }


    public MaterialSection newSection(String title, Drawable icon, Intent target, boolean bottom, MaterialMenu menu, int position, boolean refreshMenu) {
        MaterialSection section = new MaterialSection<Fragment>(this, true, MaterialSection.TARGET_ACTIVITY, bottom, this);
        section.setOnClickListener(this);
        section.setIcon(icon);
        section.setTitle(title);
        section.setTarget(target);
        //section.setPosition(position);
        menu.addItem(section, position);

        if (refreshMenu)
            reloadMenu();

        return section;
    }

    public MaterialSection newSection(String title, Drawable icon, Intent target, boolean bottom, MaterialMenu menu, int position) {
        return newSection(title, icon, target, bottom, menu, position, false);
    }

    public MaterialSection newSection(String title, Drawable icon, Intent target, boolean bottom, MaterialMenu menu, boolean refreshMenu) {
        return newSection(title, icon, target, bottom, menu, menu.getItems().size(), refreshMenu);
    }

    public MaterialSection newSection(String title, Drawable icon, Intent target, boolean bottom, MaterialMenu menu) {
        return newSection(title, icon, target, bottom, menu, menu.getItems().size());
    }


    public MaterialSection newSection(String title, Bitmap icon, boolean bottom, MaterialMenu menu, int position, boolean refreshMenu) {
        MaterialSection section = new MaterialSection<Fragment>(this, true, MaterialSection.TARGET_CLICK, bottom, this);
        section.setIcon(icon);
        section.setTitle(title);
        menu.addItem(section, position);

        if (refreshMenu)
            reloadMenu();

        return section;
    }

    public MaterialSection newSection(String title, Bitmap icon, boolean bottom, MaterialMenu menu, int position) {
        return newSection(title, icon, bottom, menu, position, false);
    }

    public MaterialSection newSection(String title, Bitmap icon, boolean bottom, MaterialMenu menu, boolean refreshMenu) {
        return newSection(title, icon, bottom, menu, menu.getItems().size(), refreshMenu);
    }

    public MaterialSection newSection(String title, Bitmap icon, boolean bottom, MaterialMenu menu) {
        return newSection(title, icon, bottom, menu, menu.getItems().size());
    }


    public MaterialSection newSection(String title, Bitmap icon, Fragment target, boolean bottom, MaterialMenu menu, int position, boolean refreshMenu) {
        MaterialSection section = new MaterialSection<Fragment>(this, true, MaterialSection.TARGET_FRAGMENT, bottom, this);
        section.setOnClickListener(this);
        section.setIcon(icon);
        section.setTitle(title);
        section.setTarget(target);
        //section.setPosition(menu.getItems().size());
        menu.addItem(section, position);

        if (refreshMenu)
            reloadMenu();

        return section;
    }

    public MaterialSection newSection(String title, Bitmap icon, Fragment target, boolean bottom, MaterialMenu menu, int position) {
        return newSection(title, icon, target, bottom, menu, position, false);
    }

    public MaterialSection newSection(String title, Bitmap icon, Fragment target, boolean bottom, MaterialMenu menu, boolean refreshMenu) {
        return newSection(title, icon, target, bottom, menu, menu.getItems().size(), refreshMenu);
    }

    public MaterialSection newSection(String title, Bitmap icon, Fragment target, boolean bottom, MaterialMenu menu) {
        return newSection(title, icon, target, bottom, menu, menu.getItems().size());
    }


    public MaterialSection newSection(String title, Bitmap icon, Intent target, boolean bottom, MaterialMenu menu, int position, boolean refreshMenu) {
        MaterialSection section = new MaterialSection<Fragment>(this, true, MaterialSection.TARGET_ACTIVITY, bottom, this);
        section.setOnClickListener(this);
        section.setIcon(icon);
        section.setTitle(title);
        section.setTarget(target);
        //section.setPosition(menu.getItems().size());
        menu.addItem(section, position);

        if (refreshMenu)
            reloadMenu();

        return section;
    }

    public MaterialSection newSection(String title, Bitmap icon, Intent target, boolean bottom, MaterialMenu menu, int position) {
        return newSection(title, icon, target, bottom, menu, position, false);
    }

    public MaterialSection newSection(String title, Bitmap icon, Intent target, boolean bottom, MaterialMenu menu, boolean refreshMenu) {
        return newSection(title, icon, target, bottom, menu, menu.getItems().size(), refreshMenu);
    }

    public MaterialSection newSection(String title, Bitmap icon, Intent target, boolean bottom, MaterialMenu menu) {
        return newSection(title, icon, target, bottom, menu, menu.getItems().size());
    }


    public MaterialSection newSection(String title, boolean bottom, MaterialMenu menu, int position, boolean refreshMenu) {
        MaterialSection section = new MaterialSection<Fragment>(this, false, MaterialSection.TARGET_CLICK, bottom, this);
        section.setTitle(title);
        //section.setPosition(menu.getItems().size());
        menu.addItem(section, position);

        if (refreshMenu)
            reloadMenu();

        return section;
    }

    public MaterialSection newSection(String title, boolean bottom, MaterialMenu menu, int position) {
        return newSection(title, bottom, menu, position, false);
    }

    public MaterialSection newSection(String title, boolean bottom, MaterialMenu menu, boolean refreshMenu) {
        return newSection(title, bottom, menu, menu.getItems().size(), refreshMenu);
    }

    public MaterialSection newSection(String title, boolean bottom, MaterialMenu menu) {
        return newSection(title, bottom, menu, menu.getItems().size());
    }


    public MaterialSection newSection(String title, Fragment target, boolean bottom, MaterialMenu menu, int position, boolean refreshMenu) {
        MaterialSection section = new MaterialSection<Fragment>(this, false, MaterialSection.TARGET_FRAGMENT, bottom, this);
        section.setOnClickListener(this);
        section.setTitle(title);
        section.setTarget(target);
        //section.setPosition(menu.getItems().size());
        menu.addItem(section, position);

        if (refreshMenu)
            reloadMenu();

        return section;
    }

    public MaterialSection newSection(String title, Fragment target, boolean bottom, MaterialMenu menu, int position) {
        return newSection(title, target, bottom, menu, position, false);
    }

    public MaterialSection newSection(String title, Fragment target, boolean bottom, MaterialMenu menu, boolean refreshMenu) {
        return newSection(title, target, bottom, menu, menu.getItems().size(), refreshMenu);
    }

    public MaterialSection newSection(String title, Fragment target, boolean bottom, MaterialMenu menu) {
        return newSection(title, target, bottom, menu, menu.getItems().size());
    }


    public MaterialSection newSection(String title, Intent target, boolean bottom, MaterialMenu menu, int position, boolean refreshMenu) {
        MaterialSection section = new MaterialSection<Fragment>(this, false, MaterialSection.TARGET_ACTIVITY, bottom, this);
        section.setOnClickListener(this);
        section.setTitle(title);
        section.setTarget(target);
        //section.setPosition(menu.getItems().size());
        menu.addItem(section, position);

        if (refreshMenu)
            reloadMenu();

        return section;
    }

    public MaterialSection newSection(String title, Intent target, boolean bottom, MaterialMenu menu, int position) {
        return newSection(title, target, bottom, menu, position, false);
    }

    public MaterialSection newSection(String title, Intent target, boolean bottom, MaterialMenu menu, boolean refreshMenu) {
        return newSection(title, target, bottom, menu, menu.getItems().size(), refreshMenu);
    }

    public MaterialSection newSection(String title, Intent target, boolean bottom, MaterialMenu menu) {
        return newSection(title, target, bottom, menu, menu.getItems().size());
    }

    // abstract methods
    public abstract void init(Bundle savedInstanceState);

    public abstract int headerType();

    public boolean finishActivityOnNewIntent() {
        return true;
    }


    // listener
    // Head Item Listener
    private View.OnClickListener headItemBackgroundOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!drawerTouchLocked) {
                if (headItemManager.get(0).getOnClickListener() != null) {

                    headItemBackground.setSoundEffectsEnabled(true);
                    v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
                    headItemFirstPhoto.setSoundEffectsEnabled(false);

                    headItemManager.get(0).getOnClickListener().onBackgroundClick(headItemManager.get(0));

                    if (headItemManager.get(0).isCloseDrawerOnBackgroundClick()) {
                        layout.closeDrawer(drawer);
                    }
                }
            }
        }
    };

    private View.OnClickListener headItemFirstOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!drawerTouchLocked) {
                if (headItemManager.get(0).getOnClickListener() != null) {

                    headItemFirstPhoto.setSoundEffectsEnabled(true);
                    v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
                    headItemFirstPhoto.setSoundEffectsEnabled(false);

                    headItemManager.get(0).getOnClickListener().onClick(headItemManager.get(0));

                    if (headItemManager.get(0).isCloseDrawerOnClick() && !deviceSupportMultiPane()) {
                        layout.closeDrawer(drawer);
                    }
                }
            }
        }
    };

    private View.OnClickListener headItemSecondOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!drawerTouchLocked) {
                MaterialHeadItem headItem = findHeadItemNumber(MaterialHeadItem.SECOND_HEADITEM);
                if (headItem != null) {

                    headItemSecondPhoto.setSoundEffectsEnabled(true);
                    v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
                    headItemSecondPhoto.setSoundEffectsEnabled(false);

                    if (headItemChangedListener != null)
                        headItemChangedListener.onBeforeChangedHeadItem(headItem);

                    switchHeadItemsIcon(headItem, true);

                    if (headItemChangedListener != null)
                        headItemChangedListener.onAfterChangedHeadItem(headItem);
                } else {
                    if (headItemManager.get(0).getOnClickListener() != null) {

                        headItemSecondPhoto.setSoundEffectsEnabled(true);
                        v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
                        headItemSecondPhoto.setSoundEffectsEnabled(false);

                        headItemManager.get(0).getOnClickListener().onBackgroundClick(headItemManager.get(0));

                        if (headItemManager.get(0).isCloseDrawerOnBackgroundClick() && !deviceSupportMultiPane()) {
                            layout.closeDrawer(drawer);
                        }
                    }
                }
            }

        }
    };

    private View.OnClickListener headItemThirdOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!drawerTouchLocked) {
                MaterialHeadItem headItem = findHeadItemNumber(MaterialHeadItem.THIRD_HEADITEM);
                if (headItem != null) {

                    headItemThirdPhoto.setSoundEffectsEnabled(true);
                    v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
                    headItemThirdPhoto.setSoundEffectsEnabled(false);

                    if (headItemChangedListener != null)
                        headItemChangedListener.onBeforeChangedHeadItem(headItem);

                    switchHeadItemsIcon(headItem, true);

                    if (headItemChangedListener != null)
                        headItemChangedListener.onAfterChangedHeadItem(headItem);
                } else {// if there is no second account user clicked for open it
                    //accountListener.onAccountOpening(currentAccount);
                    if (headItemManager.get(0).getOnClickListener() != null) {

                        headItemThirdPhoto.setSoundEffectsEnabled(true);
                        v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
                        headItemThirdPhoto.setSoundEffectsEnabled(false);

                        headItemManager.get(0).getOnClickListener().onBackgroundClick(headItemManager.get(0));

                        if (headItemManager.get(0).isCloseDrawerOnBackgroundClick() && !deviceSupportMultiPane()) {
                            layout.closeDrawer(drawer);
                        }
                    }
                }
            }
        }
    };

    private View.OnClickListener headItemSwitcher = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!headItemSwitcherOpen) {
                // create menu with accounts in the headManagerList >= 3 (starting 3)
                headItemSwitcherOpen = true;
                headItemButtonSwitcher.setImageResource(R.drawable.ic_arrow_drop_up_white_24dp);

                MaterialMenu menu = new MaterialMenu();
                for (int i = 3; i < headItemManager.size(); i++) {
                    final MaterialHeadItem newHeadItem = headItemManager.get(i);

                    MaterialSection section = newHeadSection(newHeadItem.getTitle(), newHeadItem.getPhoto(), menu);
                    section.setOnClickListener(new MaterialSectionOnClickListener() {
                        @Override
                        public void onClick(MaterialSection section, View view) {
                            switchHeadItemsList(headItemManager.get(0), newHeadItem);
                        }
                    });
                }

                // add extra menu
                menu.getItems().addAll(headItemSwitchExtraMenu.getItems());

                currentMenu = menu;
                loadHeadItemMenu();
            } else {
                // load old menu
                headItemButtonSwitcher.setImageResource(R.drawable.ic_arrow_drop_down_white_24dp);
                headItemSwitcherOpen = false;
                loadMenu(true); // currentMenu will set in the method
            }
        }
    };


    // override
    @Override
    protected void onResume() {
        super.onResume();
        if (!deviceSupportMultiPane())
            layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, drawer);
        else
            layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, drawer);
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
        // Se dal drawer si seleziona un oggetto
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

        // Passa tutte le configurazioni al drawer
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
                MaterialSection section = (MaterialSection) headItemManager.get(0).getMenu().getItems().get(headItemManager.get(0).getStartIndex());
                if (currentSection == section)
                    super.onBackPressed();
                else {
                    section.select();
                    onClick(section, section.getView());
                }
                break;
            case BACKPATTERN_CUSTOM:
                MaterialSection backedSection = backToSection(getCurrentSection());

                if (currentSection == backedSection)
                    super.onBackPressed();
                else {
                    if (backedSection.getTarget() != MaterialSection.TARGET_FRAGMENT) {
                        throw new RuntimeException("The restored section must have a fragment as target");
                    }
                    onClick(backedSection, backedSection.getView());
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
        // recycle bitmaps
        recycleHeadItem();
    }


    @Override
    public void onClick(final MaterialSection section, View view) {
        if (section != currentSection) {

            if (section.getTarget() == MaterialSection.TARGET_FRAGMENT) {
                //unSelectOldSection(section);
                if (currentSection != null) {
                    currentSection.unSelect();
                    setFragment((Fragment) section.getTargetFragment(), section.getTitle(), (Fragment) currentSection.getTargetFragment(), true);
                } else
                    setFragment((Fragment) section.getTargetFragment(), section.getTitle(), null, true);

                section.select();
                changeToolbarColor(section);
                currentSection = section;
            } else if (section.getTarget() == MaterialSection.TARGET_ACTIVITY) {
                section.unSelect();

                //finish();
               /* if (finishActivityOnNewIntent) {
                    this.startActivity(section.getTargetIntent());
                    finish();
                } else {*/

                // smooth close drawer before activity start
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(section.getTargetIntent());
                        if (finishActivityOnNewIntent)
                            finish();
                    }
                }, 200);

                closeDrawer();
            }
        }
    }

    @Override
    public void onUserPhotoLoaded(MaterialHeadItem headItem) {
        if (getHeadItemNumber(headItem) <= MaterialHeadItem.THIRD_HEADITEM)
            notifyHeadItemsDataChanged();
    }

    @Override
    public void onBackgroundLoaded(MaterialHeadItem headItem) {
        if (getHeadItemNumber(headItem) <= MaterialHeadItem.THIRD_HEADITEM)
            notifyHeadItemsDataChanged();
    }


    // getter and setter
    public Toolbar getToolbar() {
        return toolbar;
    }

    public MaterialSection getCurrentSection() {
        return currentSection;
    }

    public MaterialHeadItem getCurrentHeadItem() {
        return headItemManager.get(0);
    }

    public int getDrawerDPWidth() {
        return drawerDPWidth;
    }

    public void setDrawerDPWidth(int drawerDPWidth) {
        this.drawerDPWidth = drawerDPWidth;
    }

    public int getDrawerHeaderType() {
        return drawerHeaderType;
    }

    public MaterialMenu getCurrentMenu() {
        return currentMenu;
    }

    public void setCustomMenu(MaterialMenu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public boolean isLoadFragmentOnStart() {
        return loadFragmentOnStart;
    }

    public void setLoadFragmentOnStart(boolean loadFragmentOnStart) {
        this.loadFragmentOnStart = loadFragmentOnStart;
    }

    public void setOnChangedListener(MaterialHeadItemChangeListener listener) {
        this.headItemChangedListener = listener;
    }

    public void setDrawerStateListener(DrawerLayout.DrawerListener listener) {
        this.drawerStateListener = listener;
    }

    public List<MaterialHeadItem> getHeadItemManager() {
        return headItemManager;
    }

    public void setHeadItemSwitchExtraMenu(MaterialMenu headItemSwitchExtraMenu) {
        this.headItemSwitchExtraMenu = headItemSwitchExtraMenu;
    }

    public boolean isHeadItemSwitchShowForce() {
        return headItemSwitchShowForce;
    }

    public void setHeadItemSwitchShowForce(boolean headItemSwitchShowForce) {
        this.headItemSwitchShowForce = headItemSwitchShowForce;

        if (headItemSwitchShowForce) {
            headItemButtonSwitcher.setImageResource(R.drawable.ic_arrow_drop_down_white_24dp);
            headItemButtonSwitcher.setVisibility(View.VISIBLE);
            headItemButtonSwitcher.setOnClickListener(headItemSwitcher);
        } else {
            headItemButtonSwitcher.setVisibility(View.GONE);
        }
    }

    public int getAnimationTransition() {
        return animationTransition;
    }

    public void setAnimationTransition(int animationTransition) {
        this.animationTransition = animationTransition;
    }

    public void setDrawerBackground(Drawable background) {
        this.headItemBackground.setImageDrawable(background);
    }

    public void setBackPattern(int backPattern) {
        this.backPattern = backPattern;
    }

    public void setDrawerHeaderCustom(View view) {
        if (drawerHeaderType != DRAWERHEADER_CUSTOM)
            throw new RuntimeException("Your header is not set to Custom.");

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        customDrawerHeader.addView(view, params);
    }

    public void setDrawerHeaderImage(Bitmap background) {
        if (drawerHeaderType != DRAWERHEADER_IMAGE)
            throw new RuntimeException("Your header is not set to Image.");

        ImageView image = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        image.setImageBitmap(background);

        customDrawerHeader.addView(image, params);
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

    public void setHeadItemSubTitle(String email) {
        headItemSubTitle.setText(email);
    }

    public void setHeadItemTitle(String headItemTitle) {
        this.headItemTitle.setText(headItemTitle);
    }

    public void setFirstHeadItemPhoto(Drawable photo) {
        headItemFirstPhoto.setImageDrawable(photo);
    }

    public void setSecondHeadItemPhoto(Drawable photo) {
        headItemSecondPhoto.setImageDrawable(photo);
    }

    public void setThirdHeadItemPhoto(Drawable photo) {
        headItemThirdPhoto.setImageDrawable(photo);
    }

    @Override
    public void onBeforeChangedSection(MaterialSection newSection) {

    }

    @Override
    public void onAfterChangedSection(MaterialSection newSection) {

    }
}