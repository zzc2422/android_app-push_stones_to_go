package com.zzc2422.push_stones_to_go.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

import com.zzc2422.push_stones_to_go.data.Behavior;

public final class GameView extends NoTouchGameView {
	
	public GameView(Context context) {
		super(context);
	}
	
	// 对上下左右手势的处理
	@Override
	public void treatGesture(boolean vertiOrHori, boolean plusOrMinus) {
		
	}
	
	// 游戏开始时的初始化（必须实现）
	@Override
	public void initGame() {
		Paint paint=new Paint();
		paint.setColor(Color.YELLOW);
		drawRect(0,0,240,240,paint);
		showBehavior(Behavior.INSTANCE);
		showScore(0);
		refresh();
	}
}