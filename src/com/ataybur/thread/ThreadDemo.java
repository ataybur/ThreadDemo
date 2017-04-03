package com.ataybur.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

class Hi extends SaySomething implements Runnable {
	public void run() {
		say("Hi");
	}
}

class Hello extends SaySomething implements Runnable {
	public void run() {
		say("Hello");
	}
}

abstract class SaySomething {
	synchronized public void say(String something) {
		// synchronized (SaySomething.class) {
		IntStream //
				.range(0, 5) //
				.parallel() //
				.forEach((i) -> {
					CurrentThreadNameShower.show();
					System.out.println(something);
					try {
						Thread.sleep(3000);
					} catch (Exception e) {
					}
				});
	}
	// }
}

class CurrentThreadNameShower {
	synchronized public static void show() {
		String currentThreadNameMessage = String.format("Current Thread: %s", Thread.currentThread().getName());
		System.out.println(currentThreadNameMessage);
	}
}

public class ThreadDemo {
	static volatile StopWatch stopWatch = new StopWatch();

	public static void main(String[] args) {
		// main1();
//		main2();
		main3();

	}

	public static void main1() {
		CurrentThreadNameShower.show();
		Hi hi = new Hi();
		Hello hello = new Hello();
		new Thread(hi).start();
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
		}
		new Thread(hello).start();
	}

	public static void main2() {
		CurrentThreadNameShower.show();
		Thread hi = new Thread(new Hi());
		Thread hello = new Thread(new Hello());

		final CompletableFuture future = CompletableFuture //
				.runAsync(hi::start) //
				.runAsync(hello::start);

		try {
			stopWatch.start();
			future.get();
			while (future.isDone()) {
				stopWatch.lap();
				stopWatch.printLaps();
				break;
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	public static void main3() {
		CurrentThreadNameShower.show();
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(new Hi());
		executor.execute(new Hello());
		executor.shutdown();
		stopWatch.start();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		stopWatch.lap();
		stopWatch.printLaps();
	}
}
