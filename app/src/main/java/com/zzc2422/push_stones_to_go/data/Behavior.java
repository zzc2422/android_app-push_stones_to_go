package com.zzc2422.push_stones_to_go.data;

public final class Behavior {
	
	public final static boolean
			HORISONTAL = false, VERTICAL = true, PLUS = true, MINUS = false;
	public final static int
			UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, NO_BEHAVIOR = -1;
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
	
	@Override
	public String toString() {
		if (distance == 0) {
			return "";
		} else {
			StringBuilder sb = new StringBuilder(20);
			sb.append("向");
			if (vertiOrHori == VERTICAL) {
				sb.append((distance > 0 ? "下" : "上"));
			} else {
				sb.append((distance > 0 ? "右" : "左"));
			}
			sb.append("移动").append(distance > 0 ? distance : -distance)
					.append("格");
			return sb.toString();
		}
	}
}