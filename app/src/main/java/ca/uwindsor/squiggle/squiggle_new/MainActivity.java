package ca.uwindsor.squiggle.squiggle_new;

import android.content.*;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.raed.drawingview.DrawingView;
import com.raed.drawingview.brushes.BrushSettings;
import com.raed.drawingview.brushes.Brushes;

public class MainActivity extends AppCompatActivity {

    DrawingView mDrawingView;
    BrushSettings brushSettings;

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
                //    mTextMessage.setText(R.string.settings);
                    Toast.makeText(getApplicationContext(),"Setting",Toast.LENGTH_LONG).show();

                    Intent i=new Intent(getApplicationContext(),api.class);
                    startActivity(i);
                    return true;
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
    }



}
