package cn.sun.thread;

class Phone{

	public synchronized void sendSms() throws Exception {
		System.out.println(Thread.currentThread().getId() + "\t invoked send Sms");
		sendMail();
	}

	public synchronized void sendMail() throws Exception {
		System.out.println(Thread.currentThread().getId()+"\t ===invoked send Mail");
	}

}

public class ReentrankLockDemo {

	public static void main(String[] args) {
		Phone phone = new Phone();

		new Thread(() -> {
			try {
				phone.sendSms();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, "t1").start();

		new Thread(() -> {
			try {
				phone.sendSms();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, "t2").start();

	}
}
