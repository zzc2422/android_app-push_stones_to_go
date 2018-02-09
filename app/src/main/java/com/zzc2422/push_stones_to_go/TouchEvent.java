package com.zzc2422.push_stones_to_go;

import com.zzc2422.push_stones_to_go.data.Behavior;

public final class TouchEvent {
	
	private final static int MIN_DXY = 10;
	public final static TouchEvent INSTANCE = new TouchEvent();
	
	private int downX, downY;
	
	private TouchEvent() {
		downX = downY = 0;
	}
	
	public void down(int x, int y) {
		downX = x;
		downY = y;
	}
	
	public int up(int upX, int upY) {
		int dx = upX - downX, dy = upY - downY,
				dxAbs = (dx >= 0 ? dx : -dx), dyAbs = (dy >= 0 ? dy : -dy);
		if (dxAbs >= dyAbs && dxAbs >= MIN_DXY) {
			return (dx > 0 ? Behavior.RIGHT : Behavior.LEFT);
		} else if (dxAbs < dyAbs && dyAbs >= MIN_DXY) {
			return (dy > 0 ? Behavior.DOWN : Behavior.UP);
		} else {
			return Behavior.NO_BEHAVIOR;
		}
	}
}