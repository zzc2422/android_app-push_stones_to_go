package com.zzc2422.push_stones_to_go.data;

public final class Grid {
	
	private boolean isStone, isPrize;
	
	public Grid() {
		isStone = false;
		isPrize = false;
	}
	
	public boolean isEmpty() {
		return !(isStone || isPrize);
	}
	
	public boolean isStone() {
		return isStone;
	}
	
	public boolean isPrize() {
		return isPrize;
	}
}