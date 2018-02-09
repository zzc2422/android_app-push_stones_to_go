package com.zzc2422.push_stones_to_go.view;

import android.content.Context;
import android.view.MotionEvent;

import com.zzc2422.push_stones_to_go.TouchEvent;
import com.zzc2422.push_stones_to_go.data.Behavior;

public abstract class NoTouchGameView extends DrawView {
	
	private boolean isInGame;
	
	public NoTouchGameView(Context context) {
		super(context);
		isInGame = true;
	}
	
	/**
	 * 画格子
	 * row：格子所在行
	 * column：格子所在列
	 * type：格子类型（为MAP中的颜色常量）
	 */
	public void drawGrid(int row, int column, int type) {
		
	}
	
	/**
	 * 显示、隐藏动作说明
	 * vertiOrHori：上下或左右
	 * distance：距离（可正可负，为0则清除文字。）
	 */
	public void showBehavior(boolean vertiOrHori, int distance) {
		
	}
	
	// 处理触屏事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isInGame) {
			int x = (int) event.getX(), y = (int) event.getY();
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					performClick();
					TouchEvent.INSTANCE.down(x, y);
					break;
				case MotionEvent.ACTION_UP:
					int gesture = TouchEvent.INSTANCE.up(x, y);
					switch (gesture){
						case Behavior.UP:
							treatGesture(Behavior.VERTICAL,Behavior.MINUS);
							break;
						case Behavior.DOWN:
							treatGesture(Behavior.VERTICAL,Behavior.PLUS);
							break;
						case Behavior.LEFT:
							treatGesture(Behavior.HORISONTAL,Behavior.MINUS);
							break;
						case Behavior.RIGHT:
							treatGesture(Behavior.HORISONTAL,Behavior.PLUS);
					}
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			isInGame = true;
			initAll();
		}
		return true;
	}
	
	// 处理点击事件（无需更改）
	@Override
	public boolean performClick() {
		return super.performClick();
	}
	
	// 对上下左右手势的处理
	public abstract void treatGesture(boolean vertiOrHori, boolean plusOrMinus);
}