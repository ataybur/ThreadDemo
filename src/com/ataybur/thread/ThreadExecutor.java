package com.ataybur.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadExecutor {
	public void execution1() {
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

	public void execution2() {
		CurrentThreadNameShower.show();
		StopWatch stopWatch = new StopWatch();
		Hi hi = new Hi();
		Hello hello = new Hello();
		stopWatch.start();
		final CompletableFuture<Void> future1 = CompletableFuture //
				.runAsync(hi::run);
		final CompletableFuture<Void> future2 = CompletableFuture //
				.runAsync(hello::run);
		final CompletableFuture<Void> future = CompletableFuture //
				.allOf(future1, future2) //
				.thenRun(stopWatch::lap) //
				.thenRun(stopWatch::printLaps);

		try {
			future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	public void execution3() {
		CurrentThreadNameShower.show();
		StopWatch stopWatch = new StopWatch();
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

	public void execution4() {
		CurrentThreadNameShower.show();
		StopWatch stopWatch = new StopWatch();
		Hi hi = new Hi();
		Hello hello = new Hello();
		stopWatch.start();
		final CompletableFuture<Void> future1 = CompletableFuture //
				.runAsync(hi::run);
		final CompletableFuture<Void> future2 = CompletableFuture //
				.runAsync(hello::run);
		final CompletableFuture<Void> future = future1 //
				.runAfterBothAsync(future2, stopWatch::lap) //
				.thenRun(stopWatch::printLaps);

		try {
			future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}
