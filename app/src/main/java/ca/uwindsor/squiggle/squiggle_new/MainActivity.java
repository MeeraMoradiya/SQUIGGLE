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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.plus.PlusShare;
import com.raed.drawingview.DrawingView;
import com.raed.drawingview.brushes.BrushSettings;
import com.raed.drawingview.brushes.Brushes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    BrushSettings brushSettings;
    DrawingView mDrawingView;
    private static int MY_REQUEST_CODE = 123;
    /*private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {*/

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
                        Bitmap bitmap=mDrawingView.exportDrawing();
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                        String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bitmap, "Title", null);
                        sendIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse(path));
                        sendIntent.setType("image/jpg");
                        Intent.createChooser(sendIntent,"Share via");
                        startActivity(sendIntent);
                        return true;
                    case R.id.maths:

                        mDrawingView.setBackgroundImage(BitmapFactory.decodeResource(getResources(),R.drawable.maths));
                        return true;
                    case R.id.english:

                        mDrawingView.setBackgroundImage(BitmapFactory.decodeResource(getResources(),R.drawable.english));
                        return true;
                    case R.id.letter1:
                        mDrawingView.setBackgroundImage(BitmapFactory.decodeResource(getResources(),R.drawable.letter1));
                        return true;
                    case R.id.letter2:
                        mDrawingView.setBackgroundImage(BitmapFactory.decodeResource(getResources(),R.drawable.letter2));
                        return true;
                    case R.id.letter3:
                        mDrawingView.setBackgroundImage(BitmapFactory.decodeResource(getResources(),R.drawable.letter3));
                        return true;
                    case R.id.letter4:
                        mDrawingView.setBackgroundImage(BitmapFactory.decodeResource(getResources(),R.drawable.letter4));
                        return true;
                    case R.id.letter5:
                        mDrawingView.setBackgroundImage(BitmapFactory.decodeResource(getResources(),R.drawable.letter5));
                        return true;
                    case R.id.saveAsImage:
                        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            Bitmap images=mDrawingView.exportDrawing();
                            saveImage(images);

                        } else {

                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_REQUEST_CODE);
                        }

                      return true;

                    case R.id.saveAsText:
                      /*  Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto",null, null));
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                        startActivity(Intent.createChooser(emailIntent, "Send email..."));*/
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
            //Check if package exists or not. If not then code
            //in catch block will be called
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
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
             sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                 Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
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
   /* public void savePhoto(final Bitmap bitmap) {

        Log.d("save","save image called");
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            Log.d("SaveFile", "No External Storage!");
            return;
        }

        // if (!TextUtils.isEmpty(imageUrl)) {

        new AsyncTask<String, String, Bitmap>() {

            @Override
            protected Bitmap doInBackground(String... params) {
Log.d("save","save image Async task");
                //  Bitmap bitmap = Picasso.with(MainActivity.this).load(imageUrl).get();
                if (bitmap != null) {
                    try {
                        //create a file to write bitmap data
                        final String mFileName = getCacheDir().getAbsolutePath();
                        File mediafile = new File(mFileName);

                        //Convert bitmap to byte array
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 0 *//*ignored for PNG*//*, bos);
                        byte[] bitmapdata = bos.toByteArray();

                        //write the bytes in file
                        FileOutputStream fos = new FileOutputStream(mFileName);
                        fos.write(bitmapdata);
                        fos.flush();
                        fos.close();
                        Log.d("SaveFile Meera", "dirMeera " );
                        //save the file
                        String targetPath = Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES) + "/" + getResources().getString(R.string.app_name);
                        File dir = new File(targetPath);
                        if (!dir.mkdir() && !dir.isDirectory()) {
                            Log.e("SaveFile", "Directory not created");
                        }
                        targetPath += "/";
                        dir = new File(targetPath);
                        Log.d("SaveFile", "dir " + dir);
                        try {
                            FileUtils.copyFile(mediafile, dir);
                        } catch (IOException ex) {
                            Log.e("Save File", "IOException" + ex.getMessage());
                        }

                        //rescan gallery
                        Uri contentUri = Uri.fromFile(dir);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            mediaScanIntent.setData(contentUri);
                            sendBroadcast(mediaScanIntent);
                        } else {
                            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, contentUri));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return bitmap;

            }
        };
    }*/
      /*  @Override
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
                    //    mTextMessage.setText(R.string.settings);
                    Toast.makeText(getApplicationContext(),"Setting",Toast.LENGTH_LONG).show();

                    Intent i=new Intent(getApplicationContext(),api.class);
                    startActivity(i);
                    return true;
            }
            return false;
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawingView=(DrawingView)findViewById(R.id.drawing_view);
        brushSettings= mDrawingView.getBrushSettings();
        mDrawingView.setUndoAndRedoEnable(true);

        FloatingActionButton fab = findViewById(R.id.viewButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap bitmap=mDrawingView.exportDrawing();
                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100,bStream );
                byte[] byteArray = bStream.toByteArray();

               Intent i=new Intent(getApplicationContext(),api_new.class);
                i.putExtra("image", byteArray);
               startActivity(i);
            }
        });

      /*  FloatingActionButton share=findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                Bitmap bitmap=mDrawingView.exportDrawing();
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bitmap, "Title", null);
                sendIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse(path));
                sendIntent.setType("image/jpg");
                Intent.createChooser(sendIntent,"Share via");
                startActivity(sendIntent);
            }

        });*/

        // mTextMessage = (TextView) findViewById(R.id.message);
       /* BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);*/
    }



}