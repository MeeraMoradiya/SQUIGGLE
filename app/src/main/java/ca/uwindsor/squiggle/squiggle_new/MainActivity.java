package ca.uwindsor.squiggle.squiggle_new;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.raed.drawingview.DrawingView;
import com.raed.drawingview.brushes.BrushSettings;
import com.raed.drawingview.brushes.Brushes;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {


    BrushSettings brushSettings;
    DrawingView mDrawingView;
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
                        brushSettings.setSelectedBrushSize(0.6f);
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
                    case R.id.blue:
                        brushSettings.setColor(Color.BLUE);
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

                    default:
                        return true;
                 }
            }

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


        // mTextMessage = (TextView) findViewById(R.id.message);
       /* BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);*/
    }



}