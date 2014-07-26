package com.example.drawapp;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
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
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {
	
	private static final int COLOR_ICON = 0;
	private static final int CHANGE_BRUSH_SIZE = 1;
	private Menu mMenu;
	private DrawingView drawView;
	private String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/SnapDrawShare/";
	private InputMethodManager imm;// = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	private Boolean firstSave = true, currFileSaved = false;
	//Change this to correspond to the picture name that is loaded
	private String filename = "temp";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        /*WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);*/
        getWindow().setFlags(
        	    WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        	    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getWindow().getDecorView()
        //.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


        setContentView(R.layout.activity_main);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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

	            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	        	dialog.cancel();
        }});
		input.requestFocus();
		
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		saveDialog.show();
    	
    }
    
    public void onColorButton()  {
    	//launch a dialog with color
    	Intent intent = new Intent(this, ColorActivity.class);
    	startActivityForResult(intent, COLOR_ICON);
    
    }
    
    
    public void onShareButton()  {
    	//launch a dialog with color\
    	
    	//First check with a bool if the currentpic is the most recent saved
    	
    	
    }
    
    public void onResizeBrushButton() {
    	//launch a new activity
    	Intent intent = new Intent(this, ResizeBrushActivity.class);
    	startActivityForResult(intent, CHANGE_BRUSH_SIZE);
    	//DrawingView.changeBrushSize(20);
    	
    }
    
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {     
    	 // super.onActivityResult(requestCode, resultCode, data); 
    	  if(requestCode == COLOR_ICON && resultCode == RESULT_OK) {
    	      int colorIndex = data.getIntExtra("COLOR_INDEX", 0);
    	      
    	      MenuItem colorButton = (MenuItem) mMenu.findItem(R.id.action_color_change);
    	      colorButton.setIcon(ColorActivity.getColor(colorIndex));
    	  	} 
    	  
    	  /*if(requestCode == CHANGE_BRUSH_SIZE && resultCode == RESULT_OK) {
    		  int brushSize = data.getIntExtra("BRUSH_SIZE", 0);
    		  //drawView.
    	  }*/
    	  
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
        	onColorButton();
        	break;
        	
        case R.id.action_share:
        	onShareButton();
        	break;
        
        case R.id.action_resize_brush:
        	onResizeBrushButton();
        	break;
        
        }
      //return super.onOptionsItemSelected(item);
    	return true;
    }

}
