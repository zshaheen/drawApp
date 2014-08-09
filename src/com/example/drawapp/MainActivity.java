package com.example.drawapp;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static final int COLOR_ICON = 0;
	private static final int CHANGE_BRUSH_SIZE = 1;
	private Menu mMenu;
	private DrawingView drawView;
	private String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/SnapDrawShare/";
	//private Boolean firstSave = true, currFileSaved = false;
	//Change this to correspond to the picture name that is loaded
	private String filename = "temp";
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
        	    WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        	    WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
					drawView.getDrawCache().compress(CompressFormat.PNG, 100, stream);
					drawView.setDrawingCacheEnabled(false);
    		    	//drawView.canvasBitmap.compress(CompressFormat.PNG, 100, stream);
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
			drawView.getDrawCache().compress(CompressFormat.PNG, 100, stream);
			drawView.setDrawingCacheEnabled(false);
	    	//drawView.canvasBitmap.compress(CompressFormat.PNG, 100, stream);
	    	Toast.makeText(getApplicationContext(), "Image saved in Pictures/SnapDrawShare", Toast.LENGTH_LONG).show();
    	}
    }
    
    public void onSaveButton()  {
    	//open a dialog that allows the user to choose a name
    	final EditText input = new EditText(this);
    	
    	input.setInputType(InputType.TYPE_CLASS_TEXT);
    	
    	final AlertDialog.Builder saveDialogBuilder = new AlertDialog.Builder(this);
    	saveDialogBuilder.setTitle("Save Drawing");
    	saveDialogBuilder.setView(input);
		
    	saveDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
    	
    	saveDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	        	dialog.cancel();
        }});
    	
    	
		final AlertDialog saveDialog = saveDialogBuilder.create();
		saveDialog.show();
	
		input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
    	    @Override
    	    public void onFocusChange(View v, boolean hasFocus) {
    	        if (hasFocus) {//(input.requestFocus()) {
    	        	saveDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    	        }
    	    }
    	});

		

    }
    
    public void onColorButton()  {
    	//launch a dialog with color
    	drawView.onErase(false);
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
        
        case R.id.action_erase:
        	drawView.onErase(true);
        	break;
        
        case R.id.action_draw:
        	drawView.onErase(false);
        	break;
        
        case R.id.action_undo:
        	drawView.onUndo();
        	break;
        
        case R.id.action_redo:
        	drawView.onRedo();
        	break;
        }
      //return super.onOptionsItemSelected(item);
    	return true;
    }

}
