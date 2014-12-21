package de.madcyph3r.materialnavigationdrawer.item;

import android.graphics.drawable.Drawable;

import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;

/**
 * Created by neokree on 11/12/14.
 */
public class MaterialHeadItem {
    private Drawable photo;
    private Drawable background;
    private String title;
    private String subTitle;
    private int headItemNumber;
    private MaterialHeadItemListener listener = null;
    private boolean closeDrawerOnClick = false;
    private boolean closeDrawerOnBackgroundClick = false;
    private boolean closeDrawerOnChanged = true;
    private MaterialMenu menu;
    private int startIndex = 0;
    private boolean loadFragmentOnChanged = true;

    public static final int FIRST_HEADITEM = 0;
    public static final int SECOND_HEADITEM = 1;
    public static final int THIRD_HEADITEM = 2;

//    public MaterialAccount(String title, String subTitle, Drawable photo,Bitmap background) {
//        this.photo = convertToBitmap(photo);
//        this.title = title;
//        this.subTitle = subTitle;
//        this.background = background;
//    }

    public MaterialHeadItem(String title, String subTitle, Drawable photo, Drawable background) {
        this.photo = photo;
        this.title = title;
        this.subTitle = subTitle;
        this.background = background;
        this.menu = null;
        this.startIndex = -1;
    }

    public MaterialHeadItem(String title, String subTitle, Drawable photo, Drawable background, MaterialMenu menu, int startIndex) {
        this.photo = photo;
        this.title = title;
        this.subTitle = subTitle;
        this.background = background;
        this.menu = menu;
        this.startIndex = startIndex;
    }

//    public MaterialAccount(String title, String subTitle, Bitmap photo, Drawable background) {
//        this.photo = photo;
//        this.title = title;
//        this.subTitle = subTitle;
//        this.background = convertToBitmap(background);
//    }
//
//    public MaterialAccount(String title, String subTitle, Bitmap photo, Bitmap background) {
//        this.photo = photo;
//        this.title = title;
//        this.subTitle = subTitle;
//        this.background = background;
//    }

    // setter

//    public void setPhoto(Drawable photo){
//        this.photo = convertToBitmap(photo);
//    }

    public void setPhoto(Drawable photo) {
        this.photo = photo;
    }

    public void setBackground(Drawable background) {
        this.background = background;
    }

//    public void setBackground(Drawable background) {
//        this.background = convertToBitmap(background);
//    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setHeadItemNumber(int number) {
        this.headItemNumber = number;
    }

    // getter

    public Drawable getPhoto() {
        return photo;
    }

    public Drawable getBackground() {
        return background;
    }

//    public Bitmap getCircularPhoto() {
//        return getCroppedBitmap(photo);
//    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public int getHeadItemNumber() {
        return headItemNumber;
    }

//    private Bitmap convertToBitmap(Drawable drawable) {
//        Bitmap mutableBitmap;
//        if(drawable.getMinimumHeight() == 0 || drawable.getMinimumWidth() == 0)
//            mutableBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
//        else
//            mutableBitmap = Bitmap.createBitmap(drawable.getMinimumWidth(), drawable.getMinimumHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(mutableBitmap);
//        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//        drawable.draw(canvas);
//
//        return mutableBitmap;
//    }
//
//    private Bitmap getCroppedBitmap(Bitmap bitmap) {
//        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
//                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(output);
//
//        final int color = 0xff424242;
//        final Paint paint = new Paint();
//        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//
//        paint.setAntiAlias(true);
//        canvas.drawARGB(0, 0, 0, 0);
//        paint.setColor(color);
//        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
//        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
//                bitmap.getWidth() / 2, paint);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(bitmap, rect, rect, paint);
//        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
//        //return _bmp;
//        return output;
//    }

    public MaterialHeadItemListener getOnClickListener() {
        return listener;
    }

    public void setOnClickListener(MaterialHeadItemListener listener) {
        this.listener = listener;
    }

    public boolean isCloseDrawerOnClick() {
        return closeDrawerOnClick;
    }

    public void setCloseDrawerOnClick(boolean closeDrawerOnClick) {
        this.closeDrawerOnClick = closeDrawerOnClick;
    }

    public boolean isCloseDrawerOnChanged() {
        return closeDrawerOnChanged;
    }

    public void setCloseDrawerOnChanged(boolean closeDrawerOnChanged) {
        this.closeDrawerOnChanged = closeDrawerOnChanged;
    }

    public boolean isCloseDrawerOnBackgroundClick() {
        return closeDrawerOnBackgroundClick;
    }

    public void setCloseDrawerOnBackgroundClick(boolean closeDrawerOnBackgroundClick) {
        this.closeDrawerOnBackgroundClick = closeDrawerOnBackgroundClick;
    }

    public MaterialMenu getMenu() {
        return menu;
    }

    public void setMenu(MaterialMenu menu) {
        this.menu = menu;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public boolean isLoadFragmentOnChanged() {
        return loadFragmentOnChanged;
    }

    public void setLoadFragmentOnChanged(boolean loadFragmentOnChanged) {
        this.loadFragmentOnChanged = loadFragmentOnChanged;
    }
}
