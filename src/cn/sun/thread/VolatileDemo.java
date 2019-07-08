package cn.sun.thread;

class MyData {

	// volatile
	int number = 0;

	public void setTo60() {
		this.number = 60;
	}

}

/**
 * 1.验证volatile可见性
 * 1.1 假如int number=0，number变量之前没有添加volatile关键字修饰，没有可见性
 */
public class VolatileDemo {

	public static void main(String[] args) {

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
