package com.zzc2422.push_stones_to_go.view;

import android.content.Context;
import android.graphics.Color;

import com.zzc2422.push_stones_to_go.GameOverException;
import com.zzc2422.push_stones_to_go.Position;
import com.zzc2422.push_stones_to_go.data.Behavior;
import com.zzc2422.push_stones_to_go.data.Map;

import java.util.HashSet;
import java.util.Set;

public final class GameView extends NoTouchGameView {
	
	public GameView(Context context) {
		super(context);
	}
	
	// 对上下左右手势的处理
	@Override
	public void treatMove(boolean vertiOrHori, boolean plusOrMinus) {
		Behavior.INSTANCE.changeBehaviour(vertiOrHori, plusOrMinus);
		showBehavior(Behavior.INSTANCE);
		refresh();
	}
	
	// 对点击手势的处理
	@Override
	public void treatClick() throws GameOverException {
		
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
		drawGrid(Map.INSTANCE.getCharacterRow(), Map.INSTANCE.getCharacterRow(),
				Map.CHARACTER_RED);
		drawGrid(Map.INSTANCE.getPrizeRow(), Map.INSTANCE.getPrizeColumn(),
				Map.PRIZE_YELLOW);
		showScore(0);
		refresh();
	}
	
	/**
	 * 判断某一行上要删除的石头
	 * row：行号
	 * movedSet：发生了移动的石头列号集合
	 * 返回值：需要删除的石头行列坐标集合
	 */
	private Set<Position> getRowSetToDelete(int row, Set<Integer> movedSet) {
		Set<Position> deleteSet = new HashSet<>();
		judgeRowDelete(row, deleteSet);
		for (int column : movedSet) {
			judgeColumnDelete(column, deleteSet);
		}
		return deleteSet;
	}
	
	/**
	 * 判断某一列上要删除的石头
	 * column：列号
	 * movedSet：发生了移动的石头行号集合
	 * 返回值：需要删除的石头行列坐标集合
	 */
	private Set<Position> getColumnSetToDelete(int column,
											   Set<Integer> movedSet) {
		Set<Position> deleteSet = new HashSet<>();
		judgeColumnDelete(column, deleteSet);
		for (int row : movedSet) {
			judgeRowDelete(row, deleteSet);
		}
		return deleteSet;
	}
	
	/**
	 * 判断给定行能否实现行消除，若能，将消除的石头坐标加入给定集合。
	 * row：石头所在行
	 * deleteSet：消除集合
	 */
	private void judgeRowDelete(int row, Set<Position> deleteSet) {
		Map map = Map.INSTANCE;
		boolean isPrizeOnRow = map.hasPrize() && map.getPrizeRow() == row;
		int column, amount;
		// 数据设定：每行最多可能进行一次行消除
		for (column = 0, amount = 0; column < Map.COLUMN_AMOUNT; column++) {
			if (map.isStone(row, column) ||
					(isPrizeOnRow && map.getPrizeColumn() == column)) {
				amount++;
			} else if (amount >= Map.MIN_DELETE) {
				break;
			} else {
				amount = 0;
			}
		}
		if (amount >= Map.MIN_DELETE) {
			for (int i = column - amount; i < column; i++) {
				deleteSet.add(new Position(row, i));
			}
		}
	}
	
	/**
	 * 判断给定行能否实现列消除，若能，将消除的石头坐标加入给定集合。
	 * column：石头所在列
	 * deleteSet：消除集合
	 */
	private void judgeColumnDelete(int column, Set<Position> deleteSet) {
		Map map = Map.INSTANCE;
		boolean isPrizeOnColumn = map.hasPrize() &&
				map.getPrizeColumn() == column;
		int row, amount;
		// 数据设定：每行最多可能进行一次行消除
		for (row = 0, amount = 0; row < Map.ROW_AMOUNT; row++) {
			if (map.isStone(row, column) ||
					(isPrizeOnColumn && map.getPrizeRow() == row)) {
				amount++;
			} else if (amount >= Map.MIN_DELETE) {
				break;
			} else {
				amount = 0;
			}
		}
		if (amount >= Map.MIN_DELETE) {
			for (int i = row - amount; i < row; i++) {
				deleteSet.add(new Position(i, column));
			}
		}
	}
}