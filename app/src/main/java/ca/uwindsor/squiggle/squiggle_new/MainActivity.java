package ca.uwindsor.squiggle.squiggle_new;

import android.Manifest;
import android.content.*;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.raed.drawingview.DrawingView;
import com.raed.drawingview.brushes.BrushSettings;
import com.raed.drawingview.brushes.Brushes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    DrawingView mDrawingView;
    BrushSettings brushSettings;
    private final String LOG_TAG = "MainActivity";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.undo:
                 //   mTextMessage.setText(R.string.undo);
                    mDrawingView.undo();
                   // mUndoButton.setEnabled(!mDrawingView.isUndoStackEmpty());
                    return true;
                case R.id.pen:
                //    mTextMessage.setText(R.string.pen);
                    brushSettings.setSelectedBrush(Brushes.PENCIL);
                    brushSettings.setSelectedBrushSize(0.3f);
                    brushSettings.setColor(0xFF000000);
                    return true;
                case R.id.eraser:
                //    mTextMessage.setText(R.string.eraser);
                    brushSettings.setSelectedBrush(Brushes.ERASER);
                    return true;
                case R.id.settings:
              saveTempBitmap(mDrawingView.exportDrawing());




                    //Code for API
                    /*Toast.makeText(getApplicationContext(),"Setting",Toast.LENGTH_LONG).show();

                    Intent i=new Intent(getApplicationContext(),api.class);
                    startActivity(i);
                    return true;*/
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawingView=(DrawingView)findViewById(R.id.drawing_view);
        mDrawingView.setUndoAndRedoEnable(true);
        brushSettings = mDrawingView.getBrushSettings();



       // mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getPermission();
        }

    }


    public void saveTempBitmap(Bitmap bitmap) {
        Log.d(LOG_TAG,"saveImageBitmap");
        if (isExternalStorageWritable()) {
            Log.d(LOG_TAG,"saveImageBitmap1");
            saveImage(bitmap);
        }else{
            //prompt the user or do something
        }
    }

    private void saveImage(Bitmap finalBitmap) {
        Log.d(LOG_TAG,"saveImage");

    //    String root = Environment.getExternalStorageDirectory().toString();

        File myDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = timeStamp +".jpg";

        File file = new File(myDir, fname);
        if (file.exists()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
           // sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));



            Log.d(LOG_TAG,"File Saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getPermission() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                requestPermissions(new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                }, 0);
            }
        }
    }



}
