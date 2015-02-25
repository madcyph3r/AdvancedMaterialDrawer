package de.madcyph3r.materialnavigationdrawer.tools;

import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.BitmapShader;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;

public class RoundedCornersDrawable extends BitmapDrawable {

    private final BitmapShader bitmapShader;
    private final Paint p;

    public RoundedCornersDrawable(final Resources resources, final Bitmap bitmap) {
        super(resources, bitmap);
        bitmapShader = new BitmapShader(getBitmap(), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        final Bitmap b = getBitmap();
        p = getPaint();
        p.setAntiAlias(true);
        p.setShader(bitmapShader);
    }

    @Override
    public void draw(final Canvas canvas) {
        canvas.drawCircle(getBitmap().getWidth() / 2, getBitmap().getHeight() / 2, getBitmap().getWidth() / 2, p);
    }
}