package com.ataybur.thread;

public class Hi extends SaySomething implements Runnable {

	@Override
	public void run() {
		say("Hi");
	}

}
