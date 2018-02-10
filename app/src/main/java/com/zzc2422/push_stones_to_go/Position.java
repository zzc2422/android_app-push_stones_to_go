package com.zzc2422.push_stones_to_go;

public final class Position {
	
	public final int ROW, COLUMN;
	
	public Position(int row, int column) {
		ROW = row;
		COLUMN = column;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other.getClass() == Position.class) {
			Position otherPosition = (Position) other;
			return otherPosition.ROW == ROW && otherPosition.COLUMN == COLUMN;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return ((Integer) ROW).hashCode() ^ ((Integer) COLUMN).hashCode();
	}
}