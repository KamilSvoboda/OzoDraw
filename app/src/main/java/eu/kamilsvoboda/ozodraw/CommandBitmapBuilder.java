package eu.kamilsvoboda.ozodraw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Třída generující bitmapy jednotlivých pokynů
 * Created by Kamil Svoboda on 5.1.2018.
 */
class CommandBitmapBuilder {
    static Bitmap getCommandBitmap(ArrayList<Integer> colors) {
        //https://android--code.blogspot.cz/2015/11/android-how-to-draw-rectangle-on-canvas.html
        Integer HEIGHT = MainActivity.STROKE_WIDTH;
        Integer WIDTH = HEIGHT * 6;

        Bitmap bitmap = Bitmap.createBitmap(
                WIDTH, // Width
                HEIGHT, // Height
                Bitmap.Config.ARGB_8888 // Config
        );

        // Initialize a new Canvas instance
        Canvas canvas = new Canvas(bitmap);

        // Draw a solid color to the canvas background
        canvas.drawColor(Color.BLACK);

        if (colors != null) {
            // Initialize a new Paint instance to draw the Rectangle
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);

            int left = colors.size() == 4 ? HEIGHT : HEIGHT + HEIGHT / 2;
            for (int i = 0; i < colors.size(); i++) {
                paint.setColor(colors.get(i));

                // Initialize a new Rect object
                Rect rectangle = new Rect(
                        left, // Left
                        0, // Top
                        left + HEIGHT, // Right
                        HEIGHT // Bottom
                );

                // Finally, draw the rectangle on the canvas
                canvas.drawRect(rectangle, paint);
                left = left + HEIGHT;
            }
        }
        return bitmap;
    }
}
