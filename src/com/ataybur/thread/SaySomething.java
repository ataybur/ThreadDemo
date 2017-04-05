package com.ataybur.thread;

import java.util.stream.IntStream;

public abstract class SaySomething {
	synchronized public void say(String something) {
		// synchronized (SaySomething.class) {
		IntStream //
				.range(0, 5) //
//				.parallel() //
				.forEach((i) -> {
					CurrentThreadNameShower.show();
					System.out.println(something);
					Pauser.pause(3);
				});
	}
}
