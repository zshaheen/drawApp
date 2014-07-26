package com.example.drawapp;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;

public class ResizeBrushActivity extends Activity {

	private ImageView imageview;
	private float maxDialogWidth = 0;
	private float scale = 0;
	
	private Paint paint;
	private int sizeDP=5;
	
	private Bitmap bmp;
	private Button button;
	private Canvas c;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resize_brush);
		
		button = (Button) findViewById(R.id.button_ok);
		
		scale = getResources().getDisplayMetrics().density;
		
		//The diameters will range from 2 to 300
		maxDialogWidth = (320 * scale + 0.5f);
		getWindow().setLayout((int)maxDialogWidth/*LayoutParams.WRAP_CONTENT  width */ , LayoutParams.WRAP_CONTENT /* height */);
		
		imageview = (ImageView)findViewById(R.id.circleimageview);
		bmp = Bitmap.createBitmap((int)maxDialogWidth,(int)maxDialogWidth,Bitmap.Config.ARGB_8888);
	    c = new Canvas(bmp);
	    
	    paint = new Paint();
	    paint.setColor(DrawingView.getPaintColor());
	    paint.setAntiAlias(true);
	    
	    //float radius = (sizeDP * scale + 0.5f)/2;
	    float r = (300 * scale + 0.5f);
	    
	    c.drawCircle(maxDialogWidth/2, maxDialogWidth/2, r/2, paint);
	    imageview.setImageDrawable(new BitmapDrawable(getResources(), bmp));

	    //imageview.invalidate();
	    //onDraw(c);
	}

}
