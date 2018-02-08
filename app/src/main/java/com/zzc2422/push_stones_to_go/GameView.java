package com.zzc2422.push_stones_to_go;

import android.content.Context;
import android.view.MotionEvent;

import com.zzc2422.push_stones_to_go.frame.DrawView;


public final class GameView extends DrawView {
	
	public GameView(Context context) {
		super(context);
	}
	
	// 游戏开始时的初始化（必须实现）
	@Override
	public void initAll() {
		
		refresh();
	}
	
	// 处理触屏事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			performClick();
			
		}
		return true;
	}
	
	// 处理点击事件（无需更改）
	@Override
	public boolean performClick() {
		return super.performClick();
	}
}