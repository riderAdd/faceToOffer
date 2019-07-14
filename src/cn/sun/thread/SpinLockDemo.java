package cn.sun.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 题目：实现一个自旋锁
 *
 * 通过CAS操作完成自旋锁，A线程先进来调用myLock方法自己持有锁5s，B随后进来发现
 * 当前有线程持有锁，不是null，所以只能通过自旋等待，直到A释放锁B随后抢到
 */
public class SpinLockDemo {

	// 原子引用线程
	AtomicReference<Thread> atomicReference = new AtomicReference<>();

	public void myLock() {
		Thread thread = Thread.currentThread();
		System.out.println(Thread.currentThread().getName() + "\t come in");

		while (!atomicReference.compareAndSet(null, thread)) {

		}
	}

	public void myUnLock() {
		Thread thread = Thread.currentThread();
		atomicReference.compareAndSet(thread, null);
		System.out.println(Thread.currentThread().getName() + "\t invoked myUnlock()");

	}

	public static void main(String[] args) {
		// 原子引用线程
		SpinLockDemo spinLockDemo = new SpinLockDemo();
		new Thread(()->{
			spinLockDemo.myLock();
			try{ TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) {e.printStackTrace();}
			spinLockDemo.myUnLock();
		},"AA").start();

		try{ TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) {e.printStackTrace();}

		new Thread(()->{
			spinLockDemo.myLock();
			try{ TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) {e.printStackTrace();}
			spinLockDemo.myUnLock();
		},"BB").start();
	}

}
