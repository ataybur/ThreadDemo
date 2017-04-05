package com.ataybur.thread;

public class Hello extends SaySomething implements Runnable {

	@Override
	public void run() {
		say("Hello");
	}

}
