package com.zzc2422.push_stones_to_go.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.zzc2422.push_stones_to_go.GameOverException;
import com.zzc2422.push_stones_to_go.TouchEvent;
import com.zzc2422.push_stones_to_go.data.Behavior;
import com.zzc2422.push_stones_to_go.data.Map;

public abstract class NoTouchGameView extends DrawView {
	
	private final Paint GAME_PAINT, OVER_PAINT;
	private boolean isInGame;
	
	private int
			SCREEN_WIDTH, GRID_WIDTH,
			WORD_UP, WORD_MIDDLE, WORD_DOWN,
			BEHAVIOR_BASE, SCORE_BASE,
			OVER1, OVER2, OVER3, OVER_X;
	
	public NoTouchGameView(Context context) {
		super(context);
		GAME_PAINT = new Paint();
		GAME_PAINT.setTextAlign(Paint.Align.LEFT);
		OVER_PAINT = new Paint();
		OVER_PAINT.setTextAlign(Paint.Align.CENTER);
		isInGame = true;
	}
	
	/**
	 * 画格子
	 * row：格子所在行
	 * column：格子所在列
	 * type：格子类型（为MAP中的颜色常量）
	 */
	public void drawGrid(int row, int column, int typeColor) {
		int x1 = row * GRID_WIDTH, y1 = column * GRID_WIDTH;
		GAME_PAINT.setColor(typeColor);
		drawRect(x1, y1, x1 + GRID_WIDTH - 1, y1 + GRID_WIDTH - 1,
				GAME_PAINT);
	}
	
	/**
	 * 显示、隐藏动作说明
	 * vertiOrHori：上下或左右
	 * distance：距离（可正可负，为0则清除文字。）
	 */
	public void showBehavior(boolean vertiOrHori, int distance) {
		GAME_PAINT.setColor(Color.BLACK);
		drawRect(0, WORD_UP, SCREEN_WIDTH, WORD_MIDDLE, GAME_PAINT);
		drawText(Behavior.INSTANCE.toString(), 0, BEHAVIOR_BASE, GAME_PAINT);
	}
	
	// 初始化所有
	@Override
	public void initAll(int width, int height) {
		initSize(width, height);
		initGame();
		refresh();
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
					gameGesture(gesture);
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			isInGame = true;
			initGame();
		}
		return true;
	}
	
	// 处理点击事件（无需更改）
	@Override
	public boolean performClick() {
		return super.performClick();
	}
	
	// 初始化尺寸参数
	private void initSize(int width, int height) {
		SCREEN_WIDTH = width;
		GRID_WIDTH = width / Map.COLUMN_AMOUNT;
		WORD_UP = GRID_WIDTH * Map.ROW_AMOUNT;
		WORD_DOWN = height;
		WORD_MIDDLE = (WORD_UP + WORD_DOWN) >> 1;
		OVER_PAINT.setTextSize(width >> 4);
		OVER1 = height >> 2;
		OVER2 = height >> 1;
		OVER3 = height - OVER1;
	}
	
	private void gameGesture(int gesture) {
		try {
			switch (gesture) {
				case Behavior.UP:
					treatGesture(Behavior.VERTICAL, Behavior.MINUS);
					break;
				case Behavior.DOWN:
					treatGesture(Behavior.VERTICAL, Behavior.PLUS);
					break;
				case Behavior.LEFT:
					treatGesture(Behavior.HORISONTAL, Behavior.MINUS);
					break;
				case Behavior.RIGHT:
					treatGesture(Behavior.HORISONTAL, Behavior.PLUS);
			}
		} catch (GameOverException e) {
			drawGameOver(e.SCORE);
		}
	}
	
	private void drawGameOver(int score) {
		clearWithColor(Color.BLACK);
		OVER_PAINT.setColor(Color.YELLOW);
		drawText("游戏结束", OVER_X, OVER1, OVER_PAINT);
		OVER_PAINT.setColor(Color.RED);
		drawText("你的得分是：" + score, OVER_X, OVER2, OVER_PAINT);
		OVER_PAINT.setColor(Color.WHITE);
		drawText("触摸屏幕再玩一局", OVER_X, OVER3, OVER_PAINT);
	}
	
	// 对上下左右手势的处理
	public abstract void treatGesture(boolean vertiOrHori, boolean plusOrMinus)
			throws GameOverException;
	
	// 游戏初始化
	public abstract void initGame();
}