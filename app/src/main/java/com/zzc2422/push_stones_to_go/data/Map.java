package com.zzc2422.push_stones_to_go.data;

import android.graphics.Color;

import com.zzc2422.push_stones_to_go.Rand;

public final class Map {
	
	public final static int
			EMPTY_GREEN = Color.GREEN,
			STONE_WHITE = Color.WHITE,
			PRIZE_YELLOW = Color.YELLOW,
			CHARACTER_RED = Color.RED;
	public final static int ROW_AMOUNT = 7, COLUMN_AMOUNT = 7,
			DEFAULT_CHARACTER_ROW = 3, DEFAULT_CHARACTER_COLUMN = 3,
			MIN_DELETE = 4;
	public final static Map INSTANCE = new Map();
	
	private boolean hasPrize;
	private int characterRow, characterColumn, prizeRow, prizeColumn, score;
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
		score = 0;
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
	
	public void deletePrize() {
		hasPrize = false;
	}
	
	public void makePrize() {
		hasPrize = true;
		do {
			prizeRow = Rand.get_rand_int(ROW_AMOUNT);
			prizeColumn = Rand.get_rand_int(COLUMN_AMOUNT);
		} while (isStone(prizeRow, prizeColumn) ||
				(prizeRow == characterRow && prizeColumn == characterColumn));
	}
	
	public int getScore() {
		return score;
	}
	
	public void scorePlus() {
		score++;
	}
}