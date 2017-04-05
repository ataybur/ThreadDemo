package com.ataybur.thread;

import java.util.concurrent.CompletableFuture;

public class ThreadDemo {

	public static void main(String[] args) {
		 ThreadExecutor threadExecutor = new ThreadExecutor();
		// threadExecutor.execution1();
//		 threadExecutor.execution2();
		// threadExecutor.execution3();
		 threadExecutor.execution4();
//		test_run_after_both();
	}

	public static void test_run_after_both() {
		CompletableFuture<Void> run1 = CompletableFuture.runAsync(() -> {
			Pauser.pause(2);
			System.out.println("first task");
		});
		CompletableFuture<Void> run2 = CompletableFuture.runAsync(() -> {
			Pauser.pause(3);
			System.out.println("second task");
		});
		CompletableFuture<Void> finisher = run1.runAfterBothAsync(run2, () -> System.out.println("result"));
		Pauser.pause(4);
		if (finisher.isDone()) {
			System.out.println("HORRRAAAY!!!");
		}
	}
}
