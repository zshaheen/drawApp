package com.example.drawapp;

import android.content.Context;
import android.view.View;
import android.util.AttributeSet;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.MotionEvent;


public class DrawingView extends View {
	
	//drawing path that is what the user touches
	private static Path drawPath;
	//drawing and canvas paint
	//the user paths are drawn with drawPaint
	//this path is then drawn on the canvas by canvasPaint
	public static Paint drawPaint;
	private Paint canvasPaint;
	//initial color
	private static int paintColor = 0xFF000000;
	private static int paintBrushSize = 5;
	//canvas
	private Canvas drawCanvas;
	//canvas bitmap
	public Bitmap canvasBitmap, tempBmp;
	
	private static float scale = 0; 
	
	public static boolean erase = false;
	
	
	public DrawingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupDrawing();
	}
	
	private void setupDrawing() {
		
		
		drawPath = new Path();
		drawPaint = new Paint();
		drawPaint.setColor(paintColor);
		
		scale = getResources().getDisplayMetrics().density;
		
		//paintBrushSize varies from 1 to 300
		//paintBrushSize = 20;
		
		drawPaint.setAntiAlias(true);
		drawPaint.setStrokeWidth((float) (paintBrushSize * scale + 0.5f));
		//TODO look into setStyle and setStrokeJoin
		drawPaint.setStyle(Paint.Style.STROKE);
		drawPaint.setStrokeJoin(Paint.Join.ROUND);
		drawPaint.setStrokeCap(Paint.Cap.ROUND);
		//drawPaint.setAlpha(0xFF);
		//setLayerType(View.LAYER_TYPE_SOFTWARE, drawPaint);
		canvasPaint = new Paint(Paint.DITHER_FLAG);
		//canvasBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		tempBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		//canvasBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
		//drawCanvas = new Canvas(canvasBitmap.copy(Bitmap.Config.ARGB_8888, true));//(canvasBitmap.copy(Bitmap.Config.ARGB_8888, true));
		
		
	}
	
	
	public void onErase(boolean val) {
		erase = val;
		if(erase)
			drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		else 
			drawPaint.setXfermode(null);
					
	}
	
	public static void setPaintColor(int color) {
		paintColor = color;
		drawPaint.setColor(paintColor);
	}
	
	public static int getPaintColor() {
		return paintColor;
		
	}
	
	
	public static void setBrushSize(int size) {
		paintBrushSize = size;
		drawPaint.setStrokeWidth((float) (paintBrushSize * scale + 0.5f));
	}
	
	public static int getBrushSize() {
		return paintBrushSize;
	}
	
	public Bitmap get(){
		setDrawingCacheEnabled(true);
		return getDrawingCache();
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		//canvasBitmap = Bitmap.createBitmap(tempBmp,12,12, w, h/*, Bitmap.Config.ARGB_8888*/);
		canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		drawCanvas = new Canvas(canvasBitmap);
		//canvasBitmap = Bitmap.createBitmap(tempBmp);
		//drawCanvas = new Canvas(canvasBitmap);
		//drawCanvas.drawColor(Color.WHITE);
		//drawCanvas.drawColor(Color.TRANSPARENT);
		
		
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(tempBmp, 0, 0, canvasPaint);
		canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
		
		
		canvas.drawPath(drawPath, drawPaint);
		setDrawingCacheEnabled(true);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float touchX = event.getX();
		float touchY = event.getY();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		    drawPath.moveTo(touchX, touchY);
		    drawPath.lineTo(touchX+0.001f, touchY+0.001f);
		    //if(erase) {
		    	drawCanvas.drawPath(drawPath, drawPaint);
		    	drawPath.reset();
		    	drawPath.moveTo(touchX, touchY);
		    //}
		    break;
		case MotionEvent.ACTION_MOVE:
		    drawPath.lineTo(touchX, touchY);
		    //if(erase) {
		    	drawCanvas.drawPath(drawPath, drawPaint);
		    	drawPath.reset();
		    	drawPath.moveTo(touchX, touchY);
		   // }
		    break;
		case MotionEvent.ACTION_UP:
		    drawCanvas.drawPath(drawPath, drawPaint);
		    drawPath.reset();
		    break;
		default:
		    return false;
		}
		
		//View must be redrawn now
		invalidate();
		return true;
	}
	
}
