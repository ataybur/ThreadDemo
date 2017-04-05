package com.ataybur.thread;

public class Pauser {
	public static void pause(int second){
		try {
			Thread.sleep(second* 1000);
		} catch (Exception e) {
		}
	}
}
