package com.zzc2422.push_stones_to_go.view;

import android.content.Context;
import android.graphics.Color;

import com.zzc2422.push_stones_to_go.GameOverException;
import com.zzc2422.push_stones_to_go.data.Behavior;
import com.zzc2422.push_stones_to_go.data.Map;

public final class GameView extends NoTouchGameView {
	
	public GameView(Context context) {
		super(context);
	}
	
	// 对上下左右手势的处理
	@Override
	public void treatMove(boolean vertiOrHori, boolean plusOrMinus) {
		
	}
	
	@Override
	public void treatClick() throws GameOverException {
		throw new GameOverException(0);
	}
	
	// 游戏开始时的初始化（必须实现）
	@Override
	public void initGame() {
		Behavior.INSTANCE.init();
		Map.INSTANCE.init();
		clearWithColor(Color.BLACK);
		for (int row = 0; row < Map.ROW_AMOUNT; row++) {
			for (int column = 0; column < Map.COLUMN_AMOUNT; column++) {
				drawGrid(row, column, Map.EMPTY_GREEN);
			}
		}
		drawGrid(Map.INSTANCE.getCharacterRow(),
				Map.INSTANCE.getCharacterRow(),
				Map.CHARACTER_RED);
		drawGrid(Map.INSTANCE.getPrizeRow(), Map.INSTANCE.getPrizeColumn(),
				Map.PRIZE_YELLOW);
		showScore(0);
		refresh();
	}
}