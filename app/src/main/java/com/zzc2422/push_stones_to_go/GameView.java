package com.zzc2422.push_stones_to_go;

import android.content.Context;
import android.view.MotionEvent;

import com.zzc2422.push_stones_to_go.frame.DrawView;


public final class GameView extends DrawView {
	
	public GameView(Context context) {
		super(context);
	}
	
	@Override
	public void initAll() {
		
		refresh();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			performClick();
			
		}
		return true;
	}
	
	@Override
	public boolean performClick() {
		return super.performClick();
	}
}