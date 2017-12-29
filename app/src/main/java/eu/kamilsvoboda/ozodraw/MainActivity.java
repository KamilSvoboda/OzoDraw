package eu.kamilsvoboda.ozodraw;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    private static int COLOR_RED = Color.parseColor("#910a0a");
    private static int COLOR_GREEN = Color.parseColor("#247c07");
    private static int COLOR_BLUE = Color.parseColor("#190a91");
    private static float STROKE_WIDTH = 45;
    MyView mv;
    private Paint mPaint;
    private int mSelectedColor = Color.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mv = new MyView(this);
        mv.setDrawingCacheEnabled(true);

        setContentView(mv);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(mSelectedColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(STROKE_WIDTH);
    }

    public class MyView extends View {

        private Bitmap mBitmap;
        private Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint;
        Context context;

        public MyView(Context c) {
            super(c);
            context = c;
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);

        }

        public void clear() {
            mCanvas.drawColor(Color.WHITE);
            invalidate();
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath(mPath, mPaint);
        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y) {
            //showDialog();
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;

        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;
            }
        }

        private void touch_up() {
            mPath.lineTo(mX, mY);
            // commit the path to our offscreen
            mCanvas.drawPath(mPath, mPaint);
            // kill this so we don't double draw
            mPath.reset();
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:

                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xFF);
        mPaint.setStrokeWidth(STROKE_WIDTH);
        switch (item.getItemId()) {
            case R.id.menu_color_red:
                mSelectedColor = COLOR_RED;
                if (mPaint != null)
                    mPaint.setColor(mSelectedColor);
                break;
            case R.id.menu_color_green:
                mSelectedColor = COLOR_GREEN;
                if (mPaint != null)
                    mPaint.setColor(mSelectedColor);
                break;
            case R.id.menu_color_blue:
                mSelectedColor = COLOR_BLUE;
                if (mPaint != null)
                    mPaint.setColor(mSelectedColor);
                break;
            case R.id.menu_color_black:
                mSelectedColor = Color.BLACK;
                if (mPaint != null)
                    mPaint.setColor(mSelectedColor);
                break;
            case R.id.menu_erase:
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                mPaint.setStrokeWidth(STROKE_WIDTH * 2);
                mPaint.setAlpha(0x80);
                break;
            case R.id.menu_new:
                mv.clear();
                break;
            case R.id.menu_save:
                AlertDialog.Builder editalert = new AlertDialog.Builder(MainActivity.this);
                editalert.setTitle("Please Enter the name with which you want to Save");
                final EditText input = new EditText(MainActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.FILL_PARENT);
                input.setLayoutParams(lp);
                editalert.setView(input);
                editalert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String name = input.getText().toString();
                        Bitmap bitmap = mv.getDrawingCache();

                        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                        File file = new File("/sdcard/" + name + ".png");
                        try {
                            if (!file.exists()) {
                                file.createNewFile();
                            }
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);
                            ostream.close();
                            mv.invalidate();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {

                            mv.setDrawingCacheEnabled(false);
                        }
                    }
                });

                editalert.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}