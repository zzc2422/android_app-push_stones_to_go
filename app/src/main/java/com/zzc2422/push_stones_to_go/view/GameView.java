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
		int distance = Behavior.INSTANCE.getDistance();
		if (distance != 0) {
			boolean vertiOrHori = Behavior.INSTANCE.getVertiOrHori();
			Set<Position> deleteSet;
			Behavior.INSTANCE.init();
			showBehavior(Behavior.INSTANCE);
			if (vertiOrHori == Behavior.VERTICAL) {
				deleteSet = getColumnSetToDelete
						(Map.INSTANCE.getCharacterColumn(),
								verticalMove(distance));
			} else {
				deleteSet = getRowSetToDelete(Map.INSTANCE.getCharacterRow(),
						horizontalMove(distance));
			}
			for (Position position : deleteSet) {
				int row = position.ROW, column = position.COLUMN;
				if (Map.INSTANCE.getPrizeRow() == row &&
						Map.INSTANCE.getPrizeColumn() == column) {
					Map.INSTANCE.deletePrize();
				} else {
					Map.INSTANCE.setEmpty(row, column);
				}
				drawGrid(row, column, Map.EMPTY_GREEN);
			}
			if (!Map.INSTANCE.hasPrize()) {
				Map.INSTANCE.makePrize();
				drawGrid(Map.INSTANCE.getPrizeRow(),
						Map.INSTANCE.getPrizeColumn(), Map.PRIZE_YELLOW);
			}
			refresh();
		}
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
	
	// 处理左右移动过程，返回移动的石头列集合。
	private Set<Integer> horizontalMove(int distance) throws GameOverException {
		int characterStep = (distance > 0 ? 1 : -1), stoneStep = -characterStep,
				row = Map.INSTANCE.getCharacterRow(),
				characterColumn = Map.INSTANCE.getCharacterColumn(),
				stoneColumn = characterColumn;
		Set<Integer> movedSet = new HashSet<>();
		Map.INSTANCE.setStone(row, stoneColumn);
		while (distance != 0) {
			int characterNext = characterColumn + characterStep,
					stoneNext = stoneColumn + stoneStep;
			if (characterNext < 0 || characterNext >= Map.COLUMN_AMOUNT ||
					Map.INSTANCE.isStone(row, characterNext) ||
					characterNext == stoneNext) {
				throw new GameOverException(Map.INSTANCE.getScore());
			} else if (stoneNext < 0 || stoneNext >= Map.COLUMN_AMOUNT) {
				stoneStep = -stoneStep;
			} else if (Map.INSTANCE.isStone(row, stoneNext)) {
				movedSet.add(stoneColumn);
				stoneColumn = stoneNext;
			} else {
				drawGrid(row, characterColumn,
						(Map.INSTANCE.isStone(row, characterColumn) ?
								Map.STONE_WHITE : Map.EMPTY_GREEN));
				characterColumn = characterNext;
				drawGrid(row, characterNext, Map.CHARACTER_RED);
				Map.INSTANCE.setEmpty(row, stoneColumn);
				drawGrid(row, stoneColumn, Map.EMPTY_GREEN);
				stoneColumn = stoneNext;
				Map.INSTANCE.setStone(row, stoneNext);
				drawGrid(row, stoneNext, Map.STONE_WHITE);
				if (Map.INSTANCE.getPrizeRow() == row) {
					if (Map.INSTANCE.getPrizeColumn() == characterColumn) {
						Map.INSTANCE.scorePlus();
						Map.INSTANCE.deletePrize();
						showScore(Map.INSTANCE.getScore());
					} else if (Map.INSTANCE.getPrizeColumn() == stoneColumn) {
						Map.INSTANCE.deletePrize();
					}
				}
				distance -= characterStep;
				refresh();
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		Map.INSTANCE.setCharacterColumn(characterColumn);
		movedSet.add(stoneColumn);
		return movedSet;
	}
	
	// 处理上下移动过程，返回移动的石头行集合。
	private Set<Integer> verticalMove(int distance) throws GameOverException {
		int characterStep = (distance > 0 ? 1 : -1), stoneStep = -characterStep,
				column = Map.INSTANCE.getCharacterColumn(),
				characterRow = Map.INSTANCE.getCharacterRow(),
				stoneRow = characterRow;
		Set<Integer> movedSet = new HashSet<>();
		Map.INSTANCE.setStone(stoneRow, column);
		while (distance != 0) {
			int characterNext = characterRow + characterStep,
					stoneNext = stoneRow + stoneStep;
			if (characterNext < 0 || characterNext >= Map.ROW_AMOUNT ||
					Map.INSTANCE.isStone(characterNext, column) ||
					characterNext == stoneNext) {
				throw new GameOverException(Map.INSTANCE.getScore());
			} else if (stoneNext < 0 || stoneNext >= Map.ROW_AMOUNT) {
				stoneStep = -stoneStep;
			} else if (Map.INSTANCE.isStone(stoneNext, column)) {
				movedSet.add(stoneRow);
				stoneRow = stoneNext;
			} else {
				drawGrid(characterRow, column,
						(Map.INSTANCE.isStone(characterRow, column) ?
								Map.STONE_WHITE : Map.EMPTY_GREEN));
				characterRow = characterNext;
				drawGrid(characterNext, column, Map.CHARACTER_RED);
				Map.INSTANCE.setEmpty(stoneRow, column);
				drawGrid(stoneRow, column, Map.EMPTY_GREEN);
				stoneRow = stoneNext;
				Map.INSTANCE.setStone(stoneNext, column);
				drawGrid(stoneNext, column, Map.STONE_WHITE);
				if (Map.INSTANCE.getPrizeColumn() == column) {
					if (Map.INSTANCE.getPrizeRow() == characterRow) {
						Map.INSTANCE.scorePlus();
						Map.INSTANCE.deletePrize();
						showScore(Map.INSTANCE.getScore());
					} else if (Map.INSTANCE.getPrizeRow() == stoneRow) {
						Map.INSTANCE.deletePrize();
					}
				}
				distance -= characterStep;
				refresh();
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		Map.INSTANCE.setCharacterRow(characterRow);
		movedSet.add(stoneRow);
		return movedSet;
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