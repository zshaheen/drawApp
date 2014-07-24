package com.example.drawapp;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.app.ActionBar;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {
	
	private DrawingView drawView;
	String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/SnapDrawShare/";
    
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        drawView = (DrawingView)findViewById(R.id.drawing);
	    
    }

    
    private void saveFile() throws FileNotFoundException {
    	
    	String filename = "thing.png";
    	OutputStream stream = new FileOutputStream(dir + filename);
    	drawView.canvasBitmap.compress(CompressFormat.PNG, 100, stream);
    	
    }
    
    public void onSaveButton()  {
    	/*File image;
    	try {
	    	image = createImageFile();
    	} catch (IOException ex) {
    		Toast.makeText(getApplicationContext(), "Error saving picture from camera", Toast.LENGTH_LONG).show();
    		
    	}*/
    	try {
			saveFile();
		} catch (FileNotFoundException e) {
			Toast.makeText(getApplicationContext(), "Error saving image", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
    	
    	
    	Toast.makeText(getApplicationContext(), "Image saved in Pictures/SnapDrawShare", Toast.LENGTH_LONG).show();
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id) {
        
        case R.id.action_settings:
        	return true;
        
        case R.id.action_save:
        	onSaveButton();
        	break;
        
        }
      //return super.onOptionsItemSelected(item);
    	return true;
    }

}
