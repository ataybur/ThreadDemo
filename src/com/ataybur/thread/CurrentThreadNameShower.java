package com.ataybur.thread;

public class CurrentThreadNameShower {
	synchronized public static void show() {
		String currentThreadNameMessage = String.format("Current Thread: %s", Thread.currentThread().getName());
		System.out.println(currentThreadNameMessage);
	}
}
