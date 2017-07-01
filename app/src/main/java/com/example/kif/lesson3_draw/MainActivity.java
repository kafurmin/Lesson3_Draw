package com.example.kif.lesson3_draw;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import static android.R.id.mask;

public class MainActivity extends AppCompatActivity {

    public Paint paint;
    public ColorMatrix colorMatrix;
    public ImageView image;
    public Bitmap bitmap;
    public Bitmap drawableBitmap;
    public Bitmap newBitmap;
    public Canvas canvas;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView)findViewById(R.id.imageView);

        bitmap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.img_78355);

    }

    public void onClick(View v){

        switch(v.getId()){
            case(R.id.buttonGreen):
                colorMatrix = new ColorMatrix(new float[]{
                        1, 0, 0, 0, 0,
                        0, -1, 0, 0, 0,
                        0, 0, 1, 0, 0,
                        0, 0, 0, 1, 0,
                });

                newBitmap = invertImage(colorMatrix);

                image.setImageBitmap(newBitmap);

                break;
            case(R.id.buttonBlue):
                colorMatrix = new ColorMatrix(new float[]{
                        1, 0, 0, 0, 0,
                        0, 1, 0, 0, 0,
                        0, 0, -1, 0, 0,
                        0, 0, 0, 1, 0,
                });
                newBitmap = invertImage(colorMatrix);

                image.setImageBitmap(newBitmap);

                break;

            case(R.id.buttonCut):

                drawableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

                Bitmap mMaskBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tree).copy(Bitmap.Config.ARGB_8888, true);



                canvas = new Canvas(drawableBitmap);
                canvas.drawBitmap(drawableBitmap, 0, 0, null);

                paint = new Paint();
                paint.setXfermode(
                        new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
                canvas.drawBitmap(mMaskBitmap, 0, 0, paint);

                image.setImageBitmap(drawableBitmap);
                //image.setScaleType(ImageView.ScaleType.FIT_CENTER);

                break;

            case(R.id.buttonGradient):

                drawableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

                //Bitmap mGradientBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gradient).copy(Bitmap.Config.ARGB_8888, true);
                Bitmap mGradientBitmap = getBitmap(R.drawable.gradient);


                canvas = new Canvas(drawableBitmap);
                canvas.drawBitmap(drawableBitmap, 0, 0, null);

                paint = new Paint();
                paint.setXfermode(
                        new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
                canvas.drawBitmap(mGradientBitmap, 0, 0, paint);

                image.setImageBitmap(drawableBitmap);


                break;
            case(R.id.buttonClear):
                image.setImageResource(R.drawable.img_78355);

                break;
        }
    }

    private Bitmap invertImage(ColorMatrix colorMatrix) {

        drawableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        paint = new Paint();
        canvas = new Canvas(drawableBitmap);

        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

        canvas.drawBitmap(drawableBitmap, 0, 0, paint);


        return drawableBitmap;
    }
    private Bitmap getBitmap(int drawableRes) {
        Drawable drawable = getResources().getDrawable(drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);//extractAlpha()
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
