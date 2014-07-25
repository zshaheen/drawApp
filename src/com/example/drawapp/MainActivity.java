package com.example.drawapp;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {
	
	private static final int COLOR_ICON = 0;
	private Menu mMenu;
	private DrawingView drawView;
	String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/SnapDrawShare/";
    String filename = "temp";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        drawView = (DrawingView)findViewById(R.id.drawing);
	    
    }

    
    private void saveFile() throws FileNotFoundException {
    	
    	final String savePath = dir + filename+".png";
    	File file = new File(savePath);
    	if(file.exists()) {
    		AlertDialog.Builder nameCollision = new AlertDialog.Builder(this);
    		nameCollision.setTitle("Uh oh");
    		TextView text = new TextView(this);
    		text.setText("A file with this name exists.\nReplace the existing file?");
    		text.setTextSize(18);
    		text.setGravity(Gravity.CENTER);
    		nameCollision.setView(text);
    		nameCollision.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    	        public void onClick(DialogInterface dialog, int whichButton) {
    	        	OutputStream stream = null;
					try {
						stream = new FileOutputStream(savePath);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						Toast.makeText(getApplicationContext(), "Error creating file stream", Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
    		    	drawView.canvasBitmap.compress(CompressFormat.PNG, 100, stream);
    		    	Toast.makeText(getApplicationContext(), "Image saved in Pictures/SnapDrawShare", Toast.LENGTH_LONG).show();
    	        }
    	        
    	    });
    		nameCollision.setNegativeButton("No", new DialogInterface.OnClickListener() {
    	        public void onClick(DialogInterface dialog, int whichButton) {
    	        	dialog.cancel();
    	        }});
    		
    		nameCollision.show();
    	}
    	
    	else {
    		OutputStream stream = null;
			try {
				stream = new FileOutputStream(savePath);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				Toast.makeText(getApplicationContext(), "Error creating file stream", Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
	    	drawView.canvasBitmap.compress(CompressFormat.PNG, 100, stream);
	    	Toast.makeText(getApplicationContext(), "Image saved in Pictures/SnapDrawShare", Toast.LENGTH_LONG).show();
    	}
    }
    
    public void onSaveButton()  {
    	//open a dialog that allows the user to choose a name
    	final EditText input = new EditText(this);
    	input.setInputType(InputType.TYPE_CLASS_TEXT);

    	
    	AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
		saveDialog.setTitle("Save Drawing");
		saveDialog.setView(input);
		saveDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	        	filename = input.getText().toString();
	        	
	        	
	        	try {
	    			saveFile();
	    		} catch (FileNotFoundException e) {
	    			Toast.makeText(getApplicationContext(), "Error saving image", Toast.LENGTH_LONG).show();
	    			e.printStackTrace();
	    		}
	        }
	        
   		});
		saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	        	dialog.cancel();
        }});
		saveDialog.show();
    	
    }
    
    public void onColorChange(MenuItem item)  {
    	//launch a dialog with color
    	//launch a separate activity 
    	Intent intent = new Intent(this, ColorActivity.class);
    	startActivityForResult(intent, COLOR_ICON);
    	//item.setIcon(R.drawable.ic_action_draw);
    
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {     
    	 // super.onActivityResult(requestCode, resultCode, data); 
    	  if(requestCode == COLOR_ICON && resultCode == RESULT_OK) {
    	      int colorIndex = data.getIntExtra("COLOR_INDEX", 0);
    	      // TODO Switch tabs using the index.
    	      //int shit = ColorActivity.colors[colorIndex];
    	      
    	      MenuItem colorButton = (MenuItem) mMenu.findItem(R.id.action_color_change);
    	      //Toast.makeText(getApplicationContext(), "Index:" + colorIndex, Toast.LENGTH_LONG).show();
    	      //ColorActivity.getColor(colorIndex);
    	      colorButton.setIcon(ColorActivity.getColor(colorIndex));
    	      
    	      //colorButton.setIcon(R.drawable.red);
    	  	} 
    	}

    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        mMenu = menu;
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
        
        case R.id.action_color_change:
        	onColorChange(item);
        	break;
        
        }
      //return super.onOptionsItemSelected(item);
    	return true;
    }

}
