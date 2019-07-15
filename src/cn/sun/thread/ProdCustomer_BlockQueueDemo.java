package cn.sun.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 阻塞队列版————消费生产模式
 */
class Resource {

	// 默认开启，进行生产+消费
	private volatile boolean FLAG = true;

	private BlockingQueue<String> blockingQueue = null;

	public Resource(BlockingQueue<String> blockingQueue) {
		this.blockingQueue = blockingQueue;
		System.out.println("正在处理的队列请求：" + blockingQueue.getClass().getName());
	}

	public void myProd() throws Exception {
		String data = null;
		boolean isAdd;
		while (FLAG) {
			data = new AtomicInteger().incrementAndGet() + "";
			isAdd = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
			if (isAdd) {
				System.out.println("向队列添加元素成功 \t" + data);
			} else {
				System.out.println("向队列添加元素失败 \t" + data);
			}
			TimeUnit.SECONDS.sleep(1);
		}
		System.out.println(Thread.currentThread().getName() + "\t 生产停止");
	}

	public void myCustomer() throws Exception {
		String result = null;
		while (FLAG) {
			result = blockingQueue.poll(2L, TimeUnit.SECONDS);
			if (result == null || result.equalsIgnoreCase("")) {
				FLAG = false;
				System.out.println(Thread.currentThread().getName() + "\t 消费超过2s，消费退出");
				System.out.println();
				System.out.println();
				return;
			}
			System.out.println(Thread.currentThread().getName() + "\t 消费成功" + result);
		}
	}

	public void stop() throws Exception{
		this.FLAG = false;
	}

}

public class ProdCustomer_BlockQueueDemo {

	public static void main(String[] args) throws Exception{
		Resource myResource = new Resource(new ArrayBlockingQueue<>(10));

		new Thread(()->{
			System.out.println(Thread.currentThread().getName()+"\t 生产线程启动");
			System.out.println();
			System.out.println();
			try{
				myResource.myProd();
			}catch (Exception e){
				e.printStackTrace();
			}
		},"Prod").start();

		new Thread(()->{
			System.out.println(Thread.currentThread().getName()+"\t 消费线程启动");
			try{
				myResource.myCustomer();
			}catch (Exception e){
				e.printStackTrace();
			}
		},"Consumer").start();

		try{TimeUnit.SECONDS.sleep(5);}catch (InterruptedException e){e.printStackTrace();}

		System.out.println();
		System.out.println();
		System.out.println();

		System.out.println("5秒钟到，main停止");
		myResource.stop();
	}

}
