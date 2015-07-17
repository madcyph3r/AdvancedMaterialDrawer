package de.madcyph3r.materialnavigationdrawer.head;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import de.madcyph3r.materialnavigationdrawer.listener.MaterialHeadItemAvatarOnClickListener;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialHeadItemBackgroundOnClickListener;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.tools.Utils;

public class MaterialHeadItem {
    private Drawable photo;
    private Drawable background;
    private String title;
    private String subTitle;
    private MaterialHeadItemAvatarOnClickListener avatarListener;
    private MaterialHeadItemBackgroundOnClickListener backgroundListener;
    private boolean closeDrawerAvatarOnClick;
    private boolean closeDrawerBackgroundOnClick;
    private boolean closeDrawerOnChanged;
    private MaterialMenu menu;
    private boolean loadFragmentOnChanged;
    private Resources resources;
    private OnHeadItemDataLoaded listenerLoaded;
    private MaterialNavigationDrawer drawer;
    private MaterialMenu menuBottom;

    //public static final int FIRST_HEADITEM = 0;
    public static final int SECOND_HEADITEM = 1;
    public static final int THIRD_HEADITEM = 2;

    // without background
    public MaterialHeadItem(MaterialNavigationDrawer drawer, String title, String subTitle, int photoRessourceID) {
        initVars();

        this.title = title;
        this.subTitle = subTitle;
        this.menu = null;
        this.photo = drawer.getResources().getDrawable(photoRessourceID);
        this.drawer = drawer;
        this.resources = drawer.getResources();

        background = null;
    }

    public MaterialHeadItem(MaterialNavigationDrawer drawer, String title, String subTitle, int photoRessourceID, MaterialMenu menu) {
        initVars();

        this.title = title;
        this.subTitle = subTitle;
        this.menu = menu;
        this.photo = drawer.getResources().getDrawable(photoRessourceID);
        this.drawer = drawer;
        this.resources = drawer.getResources();

        background = null;
    }

    public MaterialHeadItem(MaterialNavigationDrawer drawer, String title, String subTitle, Drawable photo) {
        initVars();

        this.title = title;
        this.subTitle = subTitle;
        this.menu = null;
        this.photo = photo;
        this.drawer = drawer;
        this.resources = drawer.getResources();

        background = null;
    }

    public MaterialHeadItem(MaterialNavigationDrawer drawer, String title, String subTitle, Drawable photo, MaterialMenu menu) {
        initVars();

        this.title = title;
        this.subTitle = subTitle;
        this.menu = menu;
        this.photo = photo;
        this.drawer = drawer;
        this.resources = drawer.getResources();

        background = null;
    }


    // with background
    public MaterialHeadItem(MaterialNavigationDrawer drawer, String title, String subTitle, int photoRessourceID, int backgroundRessourceID) {
        initVars();

        this.title = title;
        this.subTitle = subTitle;
        this.menu = null;
        this.photo = drawer.getResources().getDrawable(photoRessourceID);
        this.drawer = drawer;
        this.resources = drawer.getResources();

        resizeBackground.execute(backgroundRessourceID);
    }

    public MaterialHeadItem(MaterialNavigationDrawer drawer, String title, String subTitle, int photoRessourceID, int backgroundRessourceID, MaterialMenu menu) {
        initVars();

        this.title = title;
        this.subTitle = subTitle;
        this.menu = menu;
        this.photo = drawer.getResources().getDrawable(photoRessourceID);
        this.drawer = drawer;
        this.resources = drawer.getResources();

        resizeBackground.execute(backgroundRessourceID);
    }

    /*public MaterialHeadItem(MaterialNavigationDrawer drawer, String title, String subTitle, int photoRessourceID, int backgroundRessourceID, MaterialMenu menu, int startIndex) {
        initVars();

        this.title = title;
        this.subTitle = subTitle;
        menu.setStartIndex(startIndex);
        this.menu = menu;

        this.drawer = drawer;
        this.resources = drawer.getResources();

        resizeBackground.execute(backgroundRessourceID);
        resizePhoto.execute(photoRessourceID);
    }*/

    public MaterialHeadItem(MaterialNavigationDrawer drawer, String title, String subTitle, Drawable photo, int backgroundRessourceID) {
        initVars();

        this.title = title;
        this.subTitle = subTitle;
        this.menu = null;
        this.photo = photo;
        this.drawer = drawer;
        this.resources = drawer.getResources();

        resizeBackground.execute(backgroundRessourceID);
        //resizePhoto.execute(photo.getRes);
    }

    public MaterialHeadItem(MaterialNavigationDrawer drawer, String title, String subTitle, Drawable photo, int backgroundRessourceID, MaterialMenu menu) {
        initVars();

        this.title = title;
        this.subTitle = subTitle;
        this.menu = menu;
        this.photo = photo;
        this.drawer = drawer;
        this.resources = drawer.getResources();

        resizeBackground.execute(backgroundRessourceID);
    }


    /*public MaterialHeadItem(MaterialNavigationDrawer drawer, String title, String subTitle, Drawable photo, int backgroundRessourceID, MaterialMenu menu, int startIndex) {
        initVars();

        this.title = title;
        this.subTitle = subTitle;
        this.menu = menu;

        menu.setStartIndex(startIndex);
        //this.startIndex = startIndex;
        //this.drawerDPWidth = drawer.getDrawerDPWidth();
        this.photo = photo;
        //this.background = drawer.getResources().getDrawable(backgroundRessourceID);
        this.drawer = drawer;
        this.resources = drawer.getResources();

        resizeBackground.execute(backgroundRessourceID);
    }*/

    private void initVars() {
        closeDrawerAvatarOnClick = false;
        closeDrawerBackgroundOnClick = false;
        closeDrawerOnChanged = true;
        //startIndex = 0;
        loadFragmentOnChanged = true;
    }


    // setter
    public void setPhoto(Drawable photo) {
        this.photo = photo;
    }

    public void setBackground(Drawable background) {
        this.background = background;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setCloseDrawerAvatarOnClick(boolean closeDrawerAvatarOnClick) {
        this.closeDrawerAvatarOnClick = closeDrawerAvatarOnClick;
    }

    public void setCloseDrawerOnChanged(boolean closeDrawerOnChanged) {
        this.closeDrawerOnChanged = closeDrawerOnChanged;
    }

    public void setCloseDrawerBackgroundOnClick(boolean closeDrawerBackgroundOnClick) {
        this.closeDrawerBackgroundOnClick = closeDrawerBackgroundOnClick;
    }

    public void setMenu(MaterialMenu menu) {
        this.menu = menu;
    }

    /*  public void setStartIndex(int startIndex) {
          this.startIndex = startIndex;
      }
  */
    public void setLoadFragmentOnChanged(boolean loadFragmentOnChanged) {
        this.loadFragmentOnChanged = loadFragmentOnChanged;
    }

    public void setHeadItemDataLoadedListener(OnHeadItemDataLoaded listener) {
        this.listenerLoaded = listener;
    }

    // getter
    public Drawable getPhoto() {
        return photo;
    }

    public Drawable getBackground() {
        return background;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public MaterialHeadItemAvatarOnClickListener getAvatarOnClickListener() {
        return avatarListener;
    }

    public MaterialHeadItemBackgroundOnClickListener getBackgroundOnClickListener() {
        return backgroundListener;
    }

    public MaterialMenu getMenu() {
        return menu;
    }

    public boolean isCloseDrawerAvatarOnClick() {
        return closeDrawerAvatarOnClick;
    }

    public boolean isCloseDrawerOnChanged() {
        return closeDrawerOnChanged;
    }

    public boolean isCloseDrawerBackgroundOnClick() {
        return closeDrawerBackgroundOnClick;
    }

    /*   public int getStartIndex() {
           return startIndex;
       }
   */
    public boolean isLoadFragmentOnChanged() {
        return loadFragmentOnChanged;
    }

    // avatarListener
    public void setAvatarOnClickListener(MaterialHeadItemAvatarOnClickListener listener) {
        this.avatarListener = listener;
    }

    // backgroundListener
    public void setBackgroundOnClickListener(MaterialHeadItemBackgroundOnClickListener listener) {
        this.backgroundListener = listener;
    }

    // own method
    public void recycleDrawable() {
        //Utils.recycleDrawable(photo);
        Utils.recycleDrawable(background);
    }

    // interface
    public interface OnHeadItemDataLoaded {
        public void onUserPhotoLoaded(MaterialHeadItem headItem);

        public void onBackgroundLoaded(MaterialHeadItem headItem);
    }

    // asynctasks
    private AsyncTask<Integer, Void, Drawable> resizePhoto = new AsyncTask<Integer, Void, Drawable>() {
        @Override
        protected Drawable doInBackground(Integer... params) {
            Point photoSize = Utils.getUserPhotoSize(resources);
            Bitmap photo = Utils.resizeBitmapFromResource(resources, params[0], photoSize.x, photoSize.y);
            BitmapDrawable photoD = new BitmapDrawable(resources, photo);
            photo.recycle();
            return photoD;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            photo = drawable;
            if (listenerLoaded != null)
                listenerLoaded.onUserPhotoLoaded(MaterialHeadItem.this);
        }
    };

    private AsyncTask<Integer, Void, BitmapDrawable> resizeBackground = new AsyncTask<Integer, Void, BitmapDrawable>() {
        @Override
        protected BitmapDrawable doInBackground(Integer... params) {
            Point backSize = Utils.getBackgroundSize(resources, drawer.getDrawerDPWidth());
            Bitmap back = Utils.resizeBitmapFromResource(resources, params[0], backSize.x, backSize.y);
            return new BitmapDrawable(resources, back);
        }

        @Override
        protected void onPostExecute(BitmapDrawable drawable) {
            background = drawable;
            if (listenerLoaded != null)
                listenerLoaded.onBackgroundLoaded(MaterialHeadItem.this);
        }
    };
}
