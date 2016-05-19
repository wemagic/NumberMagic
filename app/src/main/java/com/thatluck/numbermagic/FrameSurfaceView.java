package com.thatluck.numbermagic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * 
 * @author Himi
 *
 */
public class FrameSurfaceView extends SurfaceView implements Callback, Runnable {
	//public static  boolean FLAG_BEGIN =false ;
	private SurfaceHolder sfh;
	private Paint paint;
	private Thread th;
	//public static boolean FLAG_PAUSE=false;
	//public static boolean FLAG_QUIT=false;
	private Canvas canvas;
	private int screenW, screenH;
	//public static boolean flag=false;

	private Bitmap fishBmp[] = new Bitmap[40];

	private int currentFrame=0;

	public FrameSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		setFocusable(true);
		for (int i = 1; i <= fishBmp.length ; i++) {

			fishBmp[i - 1] = BitmapFactory.decodeResource(this.getResources(), R.drawable.p00 + i);
		}
	}

	private void init() {
		PokerStateTool.init();
	}

	public FrameSurfaceView(Context context) {
		super(context);
		init();
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		setFocusable(true);
		for (int i = 1; i <= fishBmp.length ; i++) {

			fishBmp[i - 1] = BitmapFactory.decodeResource(this.getResources(), R.drawable.p00 + i);
		}

	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenW = this.getWidth();
		screenH = this.getHeight();
		th = new Thread(this);
		th.start();

	}


	public void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);
				int bmp_width=fishBmp[currentFrame].getWidth();
				int bmp_height=fishBmp[currentFrame].getHeight();
				canvas.drawBitmap(fishBmp[currentFrame], (screenW-bmp_width)/2, (screenH-bmp_height)/2, paint);
				invalidate();
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}


	private void logic() {
		currentFrame++;
		if (currentFrame >= fishBmp.length) {
			currentFrame = 0;
		}
	}

	@Override
	public void run() {
		while(!PokerStateTool.FLAG_QUIT) {
			if (PokerStateTool.FLAG_BEGIN&&!PokerStateTool.FLAG_PAUSE) {
				myDraw();
				logic();
				try {
					Thread.sleep(MainPreference.POKER_TIME_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}



	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}


	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		PokerStateTool.setStop();
	}
}
