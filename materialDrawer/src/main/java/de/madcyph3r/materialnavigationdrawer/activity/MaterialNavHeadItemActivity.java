package de.madcyph3r.materialnavigationdrawer.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.R;
import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialHeadItemChangeListener;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionOnClickListener;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSection;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionOnClick;

@SuppressLint("InflateParams")
public abstract class MaterialNavHeadItemActivity<Fragment> extends MaterialNavigationDrawer implements MaterialHeadItem.OnHeadItemDataLoaded {

    private int animationTransition;

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


    // global vars headItem
    private List<MaterialHeadItem> headItemManager;
    private MaterialHeadItemChangeListener headItemChangedListener;
    private MaterialMenu switcherExtraMenu;
    private boolean beforeOtherHeadItems;
    private boolean headItemSwitchShowForce;
    private boolean headItemSwitcherOpen = false;
    private Drawable headItemBackgroundGradient;
    private boolean staticHeadItemBackground = false;
    private int headItemTitleColor;
    private int headItemSubTitleColor;

    private MaterialNavHeadItemActivity materialNavHeadItemActivity;

    @Override
    /**
     * Do not Override this method!!!
     * Use init() instead, see example app
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        materialNavHeadItemActivity = this;

        // init headItems
        initHeadItemVars();

        // init headItem listeners
        // set listener without sound. sound is called in the own listener
        headItemFirstPhoto.setSoundEffectsEnabled(false);
        headItemFirstPhoto.setOnClickListener(headItemFirstOnClickListener);
        headItemBackground.setSoundEffectsEnabled(false);
        headItemBackground.setOnClickListener(headItemBackgroundOnClickListener);
        headItemSecondPhoto.setSoundEffectsEnabled(false);
        headItemSecondPhoto.setOnClickListener(headItemSecondOnClickListener);
        headItemThirdPhoto.setSoundEffectsEnabled(false);
        headItemThirdPhoto.setOnClickListener(headItemThirdOnClickListener);


        // init kitkat dependencies
        Resources.Theme theme = this.getTheme();
        initKitKatDependencies(theme);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        // DEVELOPER CALL TO INIT
        init(savedInstanceState);
        // load here header and menu
        initHeaderAndMenu(savedInstanceState);
        //afterInit();
        afterInit(savedInstanceState);
    }


    protected void afterSetContentView() {
        Resources.Theme theme = this.getTheme();
        initThemeVars(theme);

        // init views
        initHeadViews();
    }

    protected void setContentView() {
        if (belowToolbar)
            setContentView(R.layout.activity_material_navigation_drawer_below_toolbar);
        else
            setContentView(R.layout.activity_material_navigation_drawer);
    }


    // init methods
    @SuppressLint("LongLogTag")
    private void initThemeVars(Resources.Theme theme) {
        // init theme params
        TypedValue typedValue = new TypedValue();
        theme.resolveAttribute(R.attr.headItemStyle, typedValue, true);

        // Values of the Account
        TypedArray values = theme.obtainStyledAttributes(typedValue.resourceId, R.styleable.MaterialHeadItem);
        headItemTitleColor = values.getColor(R.styleable.MaterialHeadItem_titleColor, 0x00FFFFFF);
        headItemSubTitleColor = values.getColor(R.styleable.MaterialHeadItem_subTitleColor, 0x00FFFFFF);
        headItemBackgroundGradient = values.getDrawable(R.styleable.MaterialHeadItem_backgroundGradient);
    }


    private void initHeadItemVars() {
        headItemSwitchShowForce = false;
        headItemManager = new LinkedList<>();
        switcherExtraMenu = new MaterialMenu();
        animationTransition = 500;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("deprecation")
    private void initHeadViews() {
        headItemTitle = (TextView) this.findViewById(R.id.current_head_item_title);
        headItemTitle.setTextColor(headItemTitleColor);

        headItemSubTitle = (TextView) this.findViewById(R.id.current_head_item_sub_title);
        headItemSubTitle.setTextColor(headItemSubTitleColor);

        headItemFirstPhoto = (ImageView) this.findViewById(R.id.current_head_item_photo);
        headItemSecondPhoto = (ImageView) this.findViewById(R.id.second_head_item_photo);
        headItemThirdPhoto = (ImageView) this.findViewById(R.id.third_head_item_photo);
        headItemBackground = (ImageView) this.findViewById(R.id.current_back_item_background);
        headItemBackgroundSwitcher = (ImageView) this.findViewById(R.id.background_switcher);
        headItemButtonSwitcher = (ImageButton) this.findViewById(R.id.user_switcher);
        headItemBackgroundGradientLL = (LinearLayout) this.findViewById(R.id.background_gradient);
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            headItemBackgroundGradientLL.setBackgroundDrawable(headItemBackgroundGradient);
        } else {
            headItemBackgroundGradientLL.setBackground(headItemBackgroundGradient);
        }
    }


    private void initKitKatDependencies(Resources.Theme theme) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT && kitkatTraslucentStatusbar) {

            RelativeLayout.LayoutParams photoParams = (RelativeLayout.LayoutParams) headItemFirstPhoto.getLayoutParams();
            photoParams.setMargins((int) (16 * displayDensity), resources.getDimensionPixelSize(R.dimen.traslucentPhotoMarginTop), 0, 0);
            headItemFirstPhoto.setLayoutParams(photoParams);
        }
    }


    protected void initDrawerPrivateType(int width) {

        int heightHeader = 0;

        if (headerDPHeight == 0)
            heightHeader = (9 * width) / 16;
        else {
            Resources r = getResources();
            heightHeader = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, headerDPHeight, r.getDisplayMetrics());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            if (!belowToolbar)
                heightHeader += (int) (24 * displayDensity);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT && kitkatTraslucentStatusbar)
            if (!belowToolbar)
                heightHeader += (int) (25 * displayDensity);

        headItemBackground.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, heightHeader));
        headItemBackgroundSwitcher.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, heightHeader));

    }


    private void initHeaderAndMenu(Bundle savedInstanceState) {
        // init headitem views
        if (headItemManager.size() > 0) {
            //currentHeadItem = headItemManager.get(0);
            notifyHeadItemsDataChanged();
        } else {
            throw new RuntimeException("No head item");
        }

        if (headItemManager.get(0).getMenu() != null) {
            MaterialMenu menu = headItemManager.get(0).getMenu();
            loadMenu(menu);

            // load fragment on start from menu
            if (isLoadFragmentOnStartFromMenu()) {
                loadStartFragmentFromMenu(menu, "isLoadFragmentOnStartFromMenu is true, but the menu has no MaterialItemSectionFragment. Add one MaterialItemSectionFragment or set isLoadFragmentOnStartFromMenu to false and set your own fragment");
            }

        }

    }

    private void loadHeadItemSwitcherMenu(MaterialMenu menu) {
        loadMenu(menu);
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


    // change the headItem1 with headItem 2 in the headItemManager list
    private void changeHeadItems(MaterialHeadItem headItem1, MaterialHeadItem headItem2) {
        int index1 = headItemManager.indexOf(headItem1);
        int index2 = headItemManager.indexOf(headItem2);

        MaterialHeadItem headItemTmp = headItemManager.get(index1);
        headItemManager.set(index1, headItem2);
        headItemManager.set(index2, headItemTmp);
    }

    private void switchHeadItemsList(MaterialHeadItem oldHeadItem, MaterialHeadItem newFirstHeadItem) {

        // change button back
        headItemButtonSwitcher.setImageResource(R.drawable.ic_arrow_drop_down_white_24dp);
        headItemSwitcherOpen = false;

        changeHeadItems(oldHeadItem, newFirstHeadItem);

        // load new first head item information
        notifyHeadItemDataChangedSwitch();

        if (newFirstHeadItem.getMenu() != null && newFirstHeadItem.getMenu().getItems() != null && newFirstHeadItem.getMenu().getItems().size() > 0) {
            MaterialMenu menu = newFirstHeadItem.getMenu();

            //MaterialMenu tmpMenu = currentMenu;

            //loadMenu(menu);

            // load fragment on headitem change
            if (menu != currentMenu) {
                loadMenu(menu);
                sectionFragmentLastBackPatternList.clear();
                if (newFirstHeadItem.isLoadFragmentOnChanged()) {
                    loadStartFragmentFromMenu(menu, "isLoadFragmentOnChanged is true, but the menu has no" +
                            " MaterialItemSectionFragment. Add one MaterialItemSectionFragment or set" +
                            " isLoadFragmentOnStartFromMenu to false and set your own fragment");
                } else if (currentMenu.getItems().contains(getCurrentSectionFragment())) {
                    getCurrentSectionFragment().select();
                }
            } else if (currentMenu.getItems().contains(getCurrentSectionFragment())) {
                getCurrentSectionFragment().select();
            }

            if (headItemManager.get(0).isCloseDrawerOnChanged() && !deviceSupportMultiPane()) {
                drawerLayout.closeDrawer(drawerViewGroup);
            }
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

            if (newFirstHeadItem.getMenu() != null && newFirstHeadItem.getMenu().getItems() != null && newFirstHeadItem.getMenu().getItems().size() > 0) {

                MaterialMenu menu = newFirstHeadItem.getMenu();

                //MaterialMenu tmpMenu = currentMenu;

                // load fragment on headitem change
                if (menu != currentMenu) {
                    loadMenu(menu);
                    sectionFragmentLastBackPatternList.clear();
                    if (newFirstHeadItem.isLoadFragmentOnChanged()) {
                        loadStartFragmentFromMenu(menu, "isLoadFragmentOnChanged is true, but the menu has no" +
                                " MaterialItemSectionFragment. Add one MaterialItemSectionFragment or set" +
                                " isLoadFragmentOnStartFromMenu to false and set your own fragment");
                    } else if (currentMenu.getItems().contains(getCurrentSectionFragment())) {
                        getCurrentSectionFragment().select();
                    }
                } else if (currentMenu.getItems().contains(getCurrentSectionFragment())) {
                    getCurrentSectionFragment().select();
                }

                if (headItemManager.get(0).isCloseDrawerOnChanged() && !deviceSupportMultiPane()) {
                    drawerLayout.closeDrawer(drawerViewGroup);
                }
            }

            // set switcher button to default
            headItemButtonSwitcher.setImageResource(R.drawable.ic_arrow_drop_down_white_24dp);
            headItemSwitcherOpen = false;
        }
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

        // add floating image, this image will get moved and animated
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(headItemPhotoClicked.getWidth(), headItemPhotoClicked.getHeight());
        params.setMargins(offsetHover.x, offsetHover.y - statusBarHeight, 0, 0);
        drawerViewGroup.addView(floatingPhotoImage, params);

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
                .with(ObjectAnimator.ofFloat(floatingPhotoImage, View.Y, headItemPhotoClicked.getTop(), headItemFirstPhoto.getTop() + offset))
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
                drawerViewGroup.removeView(floatingPhotoImage);

                // set title and subtitle
                setHeadItemTitle(newFirstHeadItem.getTitle());
                setHeadItemSubTitle(newFirstHeadItem.getSubTitle());

                System.out.println();

                // show new background
                if (!staticHeadItemBackground && newFirstHeadItem.getBackground() != null)
                    setHeadItemBackground(newFirstHeadItem.getBackground());
                ((View) headItemBackground).setAlpha(1);

                // switch old and new headitem in the manager list
                changeHeadItems(headItemManager.get(0), newFirstHeadItem);

                // load head item menu
                if (newFirstHeadItem.getMenu() != null && newFirstHeadItem.getMenu().getItems() != null && newFirstHeadItem.getMenu().getItems().size() > 0) {

                    MaterialMenu menu = newFirstHeadItem.getMenu();

                    //MaterialMenu tmpMenu = currentMenu;

                    //loadMenu(menu);

                    // load fragment on headitem change
                    if (menu != currentMenu) {
                        loadMenu(menu);
                        sectionFragmentLastBackPatternList.clear();
                        if (newFirstHeadItem.isLoadFragmentOnChanged()) {
                            loadStartFragmentFromMenu(menu, "isLoadFragmentOnChanged is true, but the menu has no" +
                                    " MaterialItemSectionFragment. Add one MaterialItemSectionFragment or set" +
                                    " isLoadFragmentOnStartFromMenu to false and set your own fragment");
                        } else if (currentMenu.getItems().contains(getCurrentSectionFragment())) {
                            getCurrentSectionFragment().select();
                        }
                    } else if (currentMenu.getItems().contains(getCurrentSectionFragment())) {
                        getCurrentSectionFragment().select();
                    }

                    headItemButtonSwitcher.setImageResource(R.drawable.ic_arrow_drop_down_white_24dp);
                    headItemSwitcherOpen = false;

                    if (headItemManager.get(0).isCloseDrawerOnChanged() && !deviceSupportMultiPane()) {
                        drawerLayout.closeDrawer(drawerViewGroup);
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                onAnimationEnd(animation);
            }
        });

        set.start();
    }

    private void recycleHeadItem() {
        for (MaterialHeadItem headItem : headItemManager) {
            headItem.recycleDrawable();
        }
    }

    public void removeHeadItem(MaterialHeadItem headItem) {
        removeHeadItem(headItemManager.indexOf(headItem));
    }

    public void removeHeadItem(int location) {

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
        if (!staticHeadItemBackground && headItemManager.get(0).getBackground() != null)
            this.setHeadItemBackground(headItemManager.get(0).getBackground());

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
            if (!staticHeadItemBackground && headItemManager.get(0).getBackground() != null)
                this.setHeadItemBackground(headItemManager.get(0).getBackground());
            this.setHeadItemTitle(headItemManager.get(0).getTitle());
            this.setHeadItemSubTitle(headItemManager.get(0).getSubTitle());
        } else if (headItemManager.size() == 2) {
            this.setThirdHeadItemPhoto(null);
            this.setSecondHeadItemPhoto(findHeadItemNumber(MaterialHeadItem.SECOND_HEADITEM).getPhoto());
            this.setFirstHeadItemPhoto(headItemManager.get(0).getPhoto());
            if (!staticHeadItemBackground && headItemManager.get(0).getBackground() != null)
                this.setHeadItemBackground(headItemManager.get(0).getBackground());
            this.setHeadItemTitle(headItemManager.get(0).getTitle());
            this.setHeadItemSubTitle(headItemManager.get(0).getSubTitle());
        } else if (headItemManager.size() == 1) {
            this.setThirdHeadItemPhoto(null);
            this.setSecondHeadItemPhoto(null);
            this.setFirstHeadItemPhoto(headItemManager.get(0).getPhoto());
            if (!staticHeadItemBackground && headItemManager.get(0).getBackground() != null)
                this.setHeadItemBackground(headItemManager.get(0).getBackground());
            this.setHeadItemTitle(headItemManager.get(0).getTitle());
            this.setHeadItemSubTitle(headItemManager.get(0).getSubTitle());
        }
    }

    @Override
    public abstract void init(Bundle savedInstanceState);

    /**
     * listener Head Item Listener
     */
    private View.OnClickListener headItemBackgroundOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!drawerTouchLocked) {
                if (headItemManager.get(0).getBackgroundOnClickListener() != null) {

                    headItemBackground.setSoundEffectsEnabled(true);
                    v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
                    headItemFirstPhoto.setSoundEffectsEnabled(false);

                    headItemManager.get(0).getBackgroundOnClickListener().onClick(headItemManager.get(0));

                    if (headItemManager.get(0).isCloseDrawerBackgroundOnClick()) {
                        drawerLayout.closeDrawer(drawerViewGroup);
                    }
                }
            }
        }
    };

    private View.OnClickListener headItemFirstOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!drawerTouchLocked) {
                if (headItemManager.get(0).getAvatarOnClickListener() != null) {

                    headItemFirstPhoto.setSoundEffectsEnabled(true);
                    v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
                    headItemFirstPhoto.setSoundEffectsEnabled(false);

                    headItemManager.get(0).getAvatarOnClickListener().onClick(headItemManager.get(0));

                    if (headItemManager.get(0).isCloseDrawerAvatarOnClick() && !deviceSupportMultiPane()) {
                        drawerLayout.closeDrawer(drawerViewGroup);
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
                        headItemChangedListener.onBeforeChangeHeadItem(headItem);

                    switchHeadItemsIcon(headItem, true);

                    if (headItemChangedListener != null)
                        headItemChangedListener.onAfterChangeHeadItem(headItem);
                } else {
                    if (headItemManager.get(0).getBackgroundOnClickListener() != null) {

                        headItemSecondPhoto.setSoundEffectsEnabled(true);
                        v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
                        headItemSecondPhoto.setSoundEffectsEnabled(false);

                        headItemManager.get(0).getBackgroundOnClickListener().onClick(headItemManager.get(0));

                        if (headItemManager.get(0).isCloseDrawerBackgroundOnClick() && !deviceSupportMultiPane()) {
                            drawerLayout.closeDrawer(drawerViewGroup);
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
                        headItemChangedListener.onBeforeChangeHeadItem(headItem);

                    switchHeadItemsIcon(headItem, true);

                    if (headItemChangedListener != null)
                        headItemChangedListener.onAfterChangeHeadItem(headItem);
                } else {// if there is no second account user clicked for open it
                    //accountListener.onAccountOpening(currentAccount);
                    if (headItemManager.get(0).getBackgroundOnClickListener() != null) {

                        headItemThirdPhoto.setSoundEffectsEnabled(true);
                        v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
                        headItemThirdPhoto.setSoundEffectsEnabled(false);

                        headItemManager.get(0).getBackgroundOnClickListener().onClick(headItemManager.get(0));

                        if (headItemManager.get(0).isCloseDrawerBackgroundOnClick() && !deviceSupportMultiPane()) {
                            drawerLayout.closeDrawer(drawerViewGroup);
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
                    final MaterialHeadItem headItem = headItemManager.get(i);

                    MaterialItemSectionOnClick section = new MaterialItemSectionOnClick(materialNavHeadItemActivity, headItem.getTitle(), headItem.getPhoto());
                    section.setOnSectionClickListener(new MaterialSectionOnClickListener() {
                        @Override
                        public void onClick(MaterialItemSection section, View view) {
                            switchHeadItemsList(headItemManager.get(0), headItem);
                        }
                    });

                    menu.add(section);
                }

                // add extra menu
                if (beforeOtherHeadItems)
                    menu.getItems().addAll(0, switcherExtraMenu.getItems());
                else
                    menu.getItems().addAll(switcherExtraMenu.getItems());

                //currentMenu = menu;
                loadHeadItemSwitcherMenu(menu);
            } else {
                // load old menu
                headItemButtonSwitcher.setImageResource(R.drawable.ic_arrow_drop_down_white_24dp);
                headItemSwitcherOpen = false;

                //currentMenu = getCurrentHeadItem().getMenu();
                loadMenu(getCurrentHeadItem().getMenu());

            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // recycle bitmaps
        recycleHeadItem();
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
    public MaterialHeadItem getCurrentHeadItem() {
        return headItemManager.get(0);
    }

    public MaterialMenu getCurrentMenu() {
        return currentMenu;
    }

    public void setHeadItemOnChangeListener(MaterialHeadItemChangeListener listener) {
        this.headItemChangedListener = listener;
    }

    public List<MaterialHeadItem> getHeadItemManager() {
        return headItemManager;
    }

    public void setSwitcherExtraMenu(MaterialMenu switcherExtraMenu, boolean beforeOtherHeadItems) {
        this.switcherExtraMenu = switcherExtraMenu;
        this.beforeOtherHeadItems = beforeOtherHeadItems;
    }

    public boolean isSwitchShowForce() {
        return headItemSwitchShowForce;
    }

    public void setSwitchShowForce(boolean headItemSwitchShowForce) {
        this.headItemSwitchShowForce = headItemSwitchShowForce;

        if (headItemSwitchShowForce) {
            headItemButtonSwitcher.setImageResource(R.drawable.ic_arrow_drop_down_white_24dp);
            headItemButtonSwitcher.setVisibility(View.VISIBLE);
            headItemButtonSwitcher.setOnClickListener(headItemSwitcher);
        } else {
            headItemButtonSwitcher.setVisibility(View.GONE);
        }
    }


    public void setHeadItemBackground(Drawable background) {
        setHeadItemBackground(background, false);
    }

    public void setHeadItemBackground(Drawable background, boolean staticBackground) {
        staticHeadItemBackground = staticBackground;
        this.headItemBackground.setImageDrawable(background);
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

    public int getAnimationTransition() {
        return animationTransition;
    }

    public void setAnimationTransition(int animationTransition) {
        this.animationTransition = animationTransition;
    }
}