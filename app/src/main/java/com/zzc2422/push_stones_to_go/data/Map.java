package com.zzc2422.push_stones_to_go.data;

import com.zzc2422.push_stones_to_go.Rand;

public final class Map {
	
	public final static int ROW_AMOUNT = 7, COLUMN_AMOUNT = 7,
			DEFAULT_CHARACTER_ROW = 2, DEFAULT_CHARACTER_COLUMN = 2;
	public final static Map GAME_MAP = new Map();
	
	private boolean hasPrize;
	private int characterRow, characterColumn, prizeRow, prizeColumn;
	private final boolean[][] IS_GRID_STONE;
	
	private Map() {
		IS_GRID_STONE = new boolean[ROW_AMOUNT][COLUMN_AMOUNT];
		init();
	}
	
	public void init() {
		for (int row = 0; row < ROW_AMOUNT; row++) {
			for (int column = 0; column < COLUMN_AMOUNT; column++) {
				IS_GRID_STONE[row][column] = false;
			}
		}
		characterRow = DEFAULT_CHARACTER_ROW;
		characterColumn = DEFAULT_CHARACTER_COLUMN;
		makePrize();
		hasPrize = true;
	}
	
	public void setEmpty(int row, int column) {
		IS_GRID_STONE[row][column] = false;
	}
	
	public void setStone(int row, int column) {
		IS_GRID_STONE[row][column] = true;
	}
	
	public void setCharacterRow(int row) {
		characterRow = row;
	}
	
	public void setCharacterColumn(int column) {
		characterColumn = column;
	}
	
	public boolean isStone(int row, int column) {
		return IS_GRID_STONE[row][column];
	}
	
	public boolean hasPrize() {
		return hasPrize;
	}
	
	public int getPrizeRow() {
		return prizeRow;
	}
	
	public int getPrizeColumn() {
		return prizeColumn;
	}
	
	public int getCharacterRow() {
		return characterRow;
	}
	
	public int getCharacterColumn() {
		return characterColumn;
	}
	
	public void makePrize() {
		hasPrize = true;
		do {
			prizeRow = Rand.get_rand_int(ROW_AMOUNT);
			prizeColumn = Rand.get_rand_int(COLUMN_AMOUNT);
		} while (isStone(prizeRow, prizeColumn) ||
				(prizeRow == characterRow && prizeColumn == characterColumn));
	}
}