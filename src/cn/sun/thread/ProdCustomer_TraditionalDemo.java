package cn.sun.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 传统版生产者消费者模式
 * <p>
 * 题目：一个初始值为0的变量，两个线程对其交替操作，一个加1，一个减1，5轮
 */
class ShareData {

	private int data = 0;

	private Lock lock = new ReentrantLock();

	private Condition condition = lock.newCondition();

	public void increment() throws Exception {

		lock.lock();
		try {
			// 1.判断
			while (data != 0) {
				// 等待 不能生产
				condition.await();
			}
			// 2.生产
			data++;
			System.out.println(Thread.currentThread().getName()+"\t"+data);

			// 3.通知唤醒
			condition.signalAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

	}

	public void decrement() throws Exception {

		lock.lock();
		try {
			// 1.判断
			while (data == 0) {
				// 等待 不能消费
				condition.await();
			}
			// 2.消费
			data--;
			System.out.println(Thread.currentThread().getName()+"\t"+data);

			// 3.通知唤醒
			condition.signalAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

	}
}

public class ProdCustomer_TraditionalDemo {

	public static void main(String[] args) {
		ShareData shareData = new ShareData();

		new Thread(()->{
			for(int i=1;i<=5;i++){
				try {
					shareData.increment();
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		},"AAA").start();

		new Thread(()->{
			for(int i=1;i<=5;i++){
				try {
					shareData.decrement();
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		},"BBB").start();
	}

}
