package cn.sun.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 第4中创建多线程的方式 ：线程池
 */
public class MyThreadPoolDemo {

	public static void main(String[] args) {
		// 一个线程池：1个线程
		ExecutorService threadPool = Executors.newCachedThreadPool();

		// 模拟10个用户来办理业务，每个用户就是一个来自外部的请求线程
		try {

			for (int i = 0; i < 10; i++) {
				int finalI = i;
				threadPool.execute(() -> {
					System.out.println(Thread.currentThread().getName() + "\t 办理业务,处理客户：" + finalI);
				});
				// 暂停一会线程
				TimeUnit.MILLISECONDS.sleep(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			threadPool.shutdown();
		}
	}

}
