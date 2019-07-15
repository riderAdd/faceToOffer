package cn.sun.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 实现多线程的第三种方式：Callable
 */
class MyThread implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		System.out.println("callable线程进入");
		return 1024;
	}

}

public class CallableDemo {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());

		Thread thread = new Thread(futureTask);
		thread.start();

		while (!futureTask.isDone()) {

		}

		// 要求获得callable的计算结果
		// 如果没有计算完成就去强求，会导致阻塞，直到计算完成
		Integer result = futureTask.get();
		System.out.println("****result:" + result);
	}

}
