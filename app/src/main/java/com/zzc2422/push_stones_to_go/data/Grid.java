package com.zzc2422.push_stones_to_go.data;

public final class Grid {
	
	private boolean isStone;
	
	public Grid() {
		isStone = false;
	}
	
	public boolean isStone() {
		return isStone;
	}
	
	public void setEmpty() {
		isStone = false;
	}
	
	public void setStone() {
		isStone = true;
	}
}