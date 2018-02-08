package com.zzc2422.push_stones_to_go.data;

public final class Behavior {
	
	public final static boolean
			HORISONTAL = false, VERTICAL = true, PLUS = true, MINUS = false;
	public final static int MAX_DISTANCE = Map.COLUMN_AMOUNT + Map.ROW_AMOUNT;
	
	private boolean vertiOrHori;
	private int distance;
	
	public final static Behavior INSTANCE = new Behavior();
	
	private Behavior() {
		vertiOrHori = HORISONTAL;
		distance = 0;
	}
	
	public void changeBehaviour(boolean vertiOrHori, boolean plusOrMinus) {
		if (this.vertiOrHori != vertiOrHori) {
			this.vertiOrHori = vertiOrHori;
			distance = (plusOrMinus == PLUS ? 1 : -1);
		} else if (plusOrMinus == PLUS && distance < MAX_DISTANCE) {
			distance++;
		} else if (plusOrMinus == MINUS && distance > -MAX_DISTANCE) {
			distance--;
		}
	}
	
	public boolean getVertiOrHori() {
		return vertiOrHori;
	}
	
	public boolean getPlusOrMinus() {
		return distance >= 0;
	}
	
	public int getDistanceAbs() {
		return (distance >= 0 ? distance : -distance);
	}
}