package com.zzc2422.push_stones_to_go;

import java.util.Random;

public final class Rand {
	
	private final static Random random=new Random();
	
	private Rand(){}
	
	public static int get_rand_int(int n){
		return random.nextInt(n);
	}
}