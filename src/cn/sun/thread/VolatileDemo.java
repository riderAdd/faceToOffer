package cn.sun.thread;

import java.util.concurrent.atomic.AtomicInteger;

class MyData {

	// volatile可以保证可见性，及时通知其他线程，主物理内存的值已经被修改
	volatile int number = 0;

	public void setTo60() {
		this.number = 60;
	}

	public void add() {
		number++;
	}

	AtomicInteger integer = new AtomicInteger();

	public void addAtomic() {
		integer.getAndIncrement();
	}

}

/**
 * 1.验证volatile可见性
 * 1.1 假如int number=0，number变量之前没有添加volatile关键字修饰，没有可见性
 * 1.2 volatile可以解决可见性问题
 * <p>
 * 2. 验证volatile不保证原子性问题
 * 2.1 原子性指的是什么意思？
 * 不可分割，完整性，也即某个线程在做某个具体业务时，中间不可执行其他线程。需要整体要么同时成功，
 * 要么同时失败。
 * <p>
 * 2.2 不保证原子性的案例演示
 *
 * 2.3 为什么不保证原子性
 *
 * 2.4 如何解决原子性
 *       * 加synchronized
 *       * AutomicInteger
 *
 */
public class VolatileDemo {

	public static void main(String[] args) {

		MyData myData = new MyData();

		for (int i = 1; i <= 20; i++) {
			new Thread(() -> {
				for (int j = 1; j <= 1000; j++) {
					myData.add();
					myData.addAtomic();
				}
			}, String.valueOf(i)).start();
		}

		// 需要等待上面20个线程全部计算完成后，再用main线程取得最终的结果
		while (Thread.activeCount() > 2) {
			Thread.yield();
		}

		System.out.println(Thread.currentThread().getName() + "\t finally data = " + myData.integer);

	}

	// 1.可见性
	private static void seeOkByVolatile() {
		MyData data = new MyData();

		new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + "\t come in");
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			data.setTo60();
			System.out.println(Thread.currentThread().getName() + "\t update number :" + data.number);
		}, "AAA").start();

		// 第二个线程就是我们的main线程
		while (data.number == 0) {
			// main线程就一直在这里等待循环，直到number值补在等于0
		}

		System.out.println(Thread.currentThread().getName() + "\t mission is over");
	}

}
