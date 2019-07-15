package ca.uwindsor.squiggle.squiggle_new;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.raed.drawingview.DrawingView;
import com.raed.drawingview.brushes.BrushSettings;
import com.raed.drawingview.brushes.Brushes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    BrushSettings brushSettings;
    DrawingView mDrawingView;
    private static int MY_REQUEST_CODE = 123;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Resources res = getResources();
        switch (item.getItemId()) {
            case R.id.smallPencil:
                brushSettings.setSelectedBrush(Brushes.PENCIL);
                brushSettings.setSelectedBrushSize(0.3f);

                return true;
            case R.id.mediumPencil:
                brushSettings.setSelectedBrush(Brushes.PENCIL);
                brushSettings.setSelectedBrushSize(0.6f);
                return true;

            case R.id.largePencil:
                brushSettings.setSelectedBrush(Brushes.PENCIL);
                brushSettings.setSelectedBrushSize(0.8f);
                return true;

            case R.id.eraser:
                brushSettings.setSelectedBrush(Brushes.ERASER);
                return true;

            case R.id.undo:
                mDrawingView.undo();

            case R.id.black:
                brushSettings.setColor(Color.BLACK);
                return true;
            case R.id.red:
                brushSettings.setColor(Color.RED);
                return true;
            case R.id.green:
                brushSettings.setColor(Color.GREEN);
                return true;
            case R.id.yellow:
                brushSettings.setColor(Color.YELLOW);
                return true;
            case R.id.blue:
                brushSettings.setColor(Color.BLUE);
                return true;

            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                Bitmap bitmap = mDrawingView.exportDrawing();
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bitmap, "Title", null);
                sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
                sendIntent.setType("image/jpg");
                Intent.createChooser(sendIntent, "Share via");
                startActivity(sendIntent);
                return true;

            case R.id.maths:
                mDrawingView.setBackgroundImage(BitmapFactory.decodeResource(getResources(), R.drawable.maths));
                return true;
            case R.id.english:
                mDrawingView.setBackgroundImage(BitmapFactory.decodeResource(getResources(), R.drawable.english));
                return true;
            case R.id.letter1:
                mDrawingView.setBackgroundImage(BitmapFactory.decodeResource(getResources(), R.drawable.letter1));
                return true;
            case R.id.letter2:
                mDrawingView.setBackgroundImage(BitmapFactory.decodeResource(getResources(), R.drawable.letter2));
                return true;
            case R.id.letter3:
                mDrawingView.setBackgroundImage(BitmapFactory.decodeResource(getResources(), R.drawable.letter3));
                return true;
            case R.id.letter4:
                mDrawingView.setBackgroundImage(BitmapFactory.decodeResource(getResources(), R.drawable.letter4));
                return true;
            case R.id.letter5:
                mDrawingView.setBackgroundImage(BitmapFactory.decodeResource(getResources(), R.drawable.letter5));
                return true;

            case R.id.saveAsImage:
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Bitmap images = mDrawingView.exportDrawing();
                    saveImage(images);

                } else {

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_REQUEST_CODE);
                }

                return true;

            case R.id.saveAsText:
                onClickWhatsApp();
                return true;

            default:
                return true;
        }
    }

    public void shareText(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBodyText = "Your shearing message goes here";
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(intent, "Choose sharing method"));
    }


    public void onClickWhatsApp() {

        PackageManager pm = getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "YOUR TEXT HERE";

            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }
    }


    private void saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/Squiggle");
        myDir.mkdirs();
        Random generator = new Random();

        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
// Tell the media scanner about the new file so that it is
// immediately available to the user.
        MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
        Toast.makeText(this, "Saved Image in Gallery", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawingView = (DrawingView) findViewById(R.id.drawing_view);
        brushSettings = mDrawingView.getBrushSettings();
        mDrawingView.setUndoAndRedoEnable(true);

        FloatingActionButton fab = findViewById(R.id.viewButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap bitmap = mDrawingView.exportDrawing();
                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
                byte[] byteArray = bStream.toByteArray();

                Intent i = new Intent(getApplicationContext(), api_new.class);
                i.putExtra("image", byteArray);
                startActivity(i);
            }
        });
        
    }


}