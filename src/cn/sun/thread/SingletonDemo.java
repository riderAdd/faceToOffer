package cn.sun.thread;

public class SingletonDemo {

	private static volatile SingletonDemo singletonDemo = null;

	private SingletonDemo() {
		System.out.println(Thread.currentThread().getName() + "\t :" + singletonDemo);
	}

	// 双检锁单例模式
	public SingletonDemo getSingletonDemo() {
		if (singletonDemo == null) {
			synchronized (SingletonDemo.class) {
				if (singletonDemo == null) {
					singletonDemo = new SingletonDemo();
				}
			}
		}
		return singletonDemo;
	}

}
