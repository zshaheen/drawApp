package com.example.drawapp;



import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class ColorActivity extends Activity {
	
	//private DrawingView drawView;
	private int mPaintColor;
	
	private static Integer[] colors = {R.drawable.red ,
			R.drawable.orange , R.drawable.yellow ,
			R.drawable.limegreen , R.drawable.skyblue ,
			R.drawable.blue , R.drawable.darkblue ,
			R.drawable.purple , R.drawable.maroon ,
			R.drawable.white , R.drawable.grey ,
			R.drawable.black, R.drawable.red ,
			R.drawable.orange , R.drawable.yellow ,
			R.drawable.limegreen , R.drawable.skyblue ,
			R.drawable.blue , R.drawable.darkblue ,
			R.drawable.purple , R.drawable.maroon ,
			R.drawable.white , R.drawable.grey ,
			R.drawable.black, R.drawable.red ,
			R.drawable.orange , R.drawable.yellow ,
			R.drawable.limegreen , R.drawable.skyblue ,
			R.drawable.blue , R.drawable.darkblue ,
			R.drawable.purple , R.drawable.maroon ,
			R.drawable.white , R.drawable.grey ,
			R.drawable.black, R.drawable.orange , R.drawable.yellow ,
			R.drawable.limegreen , R.drawable.skyblue ,
			R.drawable.blue , R.drawable.darkblue ,
			R.drawable.purple , R.drawable.maroon ,
			R.drawable.white , R.drawable.grey ,
			R.drawable.black, R.drawable.red ,
			R.drawable.orange , R.drawable.yellow ,
			R.drawable.limegreen , R.drawable.skyblue ,
			R.drawable.blue , R.drawable.darkblue ,
			R.drawable.purple , R.drawable.maroon ,
			R.drawable.white , R.drawable.grey ,
			R.drawable.black, R.drawable.red ,
			R.drawable.orange , R.drawable.yellow ,
			R.drawable.limegreen , R.drawable.skyblue ,
			R.drawable.blue , R.drawable.darkblue ,
			R.drawable.purple , R.drawable.maroon ,
			R.drawable.white , R.drawable.grey ,
			R.drawable.black 
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//drawView = (DrawingView)findViewById(R.id.drawing);
		//mPaintColor = DrawingView.getPaintColor();
		
		setContentView(R.layout.activity_color);
		GridView gridView = (GridView) findViewById(R.id.gridviewcolors);
        gridView.setAdapter(new ColorAdapter(this));
        
        gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				//default color is black
				int num = 0xFF000000;
				
            	switch(colors[position]) {
            	//Try using an array of hex values
	            	case R.drawable.red:
	            		num = 0xFFFF0000;
	            		break;
	            	case R.drawable.orange:
	            		num = 0xFFFF6A00;
	            		break;
	            	case R.drawable.yellow:
	            		num = 0xFFFFD800;
	            		break;
	            	case R.drawable.limegreen:
	            		num = 0xFF6AFF3D;
	            		break;
	            	case R.drawable.skyblue:
	            		num = 0xFF00FFFF;
	            		break;
	            	case R.drawable.blue:
	            		num = 0xFF0094FF;
	            		break;
	            	case R.drawable.darkblue:
	            		num = 0xFF0026FF;
	            		break;
	            	case R.drawable.purple:
	            		num = 0xFFB200FF;
	            		break;
	            	case R.drawable.maroon:
	            		num = 0xFF7F0000;
	            		break;
	            	case R.drawable.white:
	            		num = 0xFFFFFFFF;
	            		break;
	            	case R.drawable.grey:
	            		num = 0xFF404040;
	            		break;
	            	case R.drawable.black:
	            		num = 0xFF000000;
	            		break;
	            	default:
	            		num = 0xFF000000;
	            		break;
	            	
            	}
            	DrawingView.drawPaint.setColor(num);
            	Intent resultIntent = new Intent();
        		resultIntent.putExtra("COLOR_INDEX", position);
        		setResult(Activity.RESULT_OK, resultIntent);
        		finish();
				
			}
        });
        
	}
	
	public static int getColor(int index) {
		return colors[index];
	}
	
	public class ColorAdapter extends BaseAdapter {
    	
        private Context mContext;
        private int size = (int) getResources().getDimension(R.dimen.color_image_size);
       
        
        public ColorAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
        	return colors.length;
        }

        public Object getItem(int position) {
            return colors[position];
        }

        public long getItemId(int position) {
            return 0;
        }
        
        
        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
        	
            ImageView imageView;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(size, size));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(2, 2, 2, 2);
            } else {
                imageView = (ImageView) convertView;
            }
            
            imageView.setImageResource(colors[position]);
            return imageView;
        }
    }   
	

}
