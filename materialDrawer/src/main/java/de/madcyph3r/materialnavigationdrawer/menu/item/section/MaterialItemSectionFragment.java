package de.madcyph3r.materialnavigationdrawer.menu.item.section;

import android.graphics.drawable.Drawable;
import android.view.View;

import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionOnClickListener;

public class MaterialItemSectionFragment<Fragment> extends MaterialItemSection implements View.OnTouchListener, View.OnClickListener {

    private String fragmentTitle;
    private Fragment fragment;


    public MaterialItemSectionFragment(MaterialNavigationDrawer drawer, String title, Fragment fragment, String fragmentTitle) {
        super.init(drawer, null, false);

        setTitle(title);
        sectionListener = drawer;

        //super.title = title;
        this.fragmentTitle = fragmentTitle;
        this.fragment = fragment;


    }

    public MaterialItemSectionFragment(MaterialNavigationDrawer drawer, String title, Drawable icon, Fragment fragment, String fragmentTitle) {
        super.init(drawer, icon, false);

        //super.title = title;
        setTitle(title);
        sectionListener = drawer;

        this.fragmentTitle = fragmentTitle;
        this.fragment = fragment;
    }

    public MaterialItemSectionFragment(MaterialNavigationDrawer drawer, Drawable icon, boolean iconBanner, Fragment fragment, String fragmentTitle) {
        super.init(drawer, icon, iconBanner);

        //super.title = title;
        setTitle("");
        sectionListener = drawer;

        this.fragmentTitle = fragmentTitle;
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getFragmentTitle() {
            return fragmentTitle;
    }

    public void setFragmentTitle(String fragmentTitle) {
        this.fragmentTitle = fragmentTitle;
    }

    public void setOnSectionClickListener (MaterialSectionOnClickListener sectionListener){
        super.sectionListener = sectionListener;
    }
}
