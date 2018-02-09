package com.zzc2422.push_stones_to_go.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class DrawView extends SurfaceView
		implements SurfaceHolder.Callback, Runnable {
	
	private final SurfaceHolder HOLDER;
	private final Canvas CANVAS;
	private Bitmap screenBitmap;
	
	public DrawView(Context context) {
		super(context);
		new Thread(this).start();
		HOLDER = getHolder();
		HOLDER.addCallback(this);
		CANVAS = new Canvas();
		screenBitmap = null;
	}
	
	/**
	 * 用某一颜色清空屏幕
	 * color：所用颜色，其数值可以用Color类获得。
	 */
	public synchronized void clearWithColor(int color) {
		CANVAS.drawColor(color);
	}
	
	/**
	 * 画矩形
	 * x1、y1：左上角x、y坐标
	 * x2、y2：右下角x、y坐标
	 * paint：使用的画笔
	 */
	public synchronized void drawRect(int x1, int y1, int x2, int y2,
									  Paint paint) {
		CANVAS.drawRect(x1, y1, x2, y2, paint);
	}
	
	/**
	 * 写文字
	 * text：文字内容
	 * x、y：文字坐标（参照点取决于paint）
	 * paint：使用的画笔
	 */
	public synchronized void drawText(String text, int x, int y, Paint paint) {
		CANVAS.drawText(text, x, y, paint);
	}
	
	// 刷新屏幕
	public synchronized void refresh() {
		notify();
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format,
							   int width, int height) {
		if (screenBitmap == null || screenBitmap.isRecycled() ||
				screenBitmap.getWidth() != width ||
				screenBitmap.getHeight() != height) {
			if (screenBitmap != null && !screenBitmap.isRecycled()) {
				screenBitmap.recycle();
			}
			screenBitmap = Bitmap.createBitmap
					(width, height, Bitmap.Config.RGB_565);
			CANVAS.setBitmap(screenBitmap);
			initAll(width, height);
		}
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}
	
	@Override
	public synchronized void run() {
		while (true) {
			try {
				wait();
			} catch (InterruptedException e) {
				continue;
			}
			synchronized (HOLDER) {
				Canvas screenCanvas = HOLDER.lockCanvas();
				screenCanvas.drawBitmap
						(screenBitmap, 0, 0, null);
				HOLDER.unlockCanvasAndPost(screenCanvas);
			}
		}
	}
	
	// 初始化
	public abstract void initAll(int width, int height);
}