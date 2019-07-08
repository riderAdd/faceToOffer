package cn.sun.thread;

public class SimpleTest {

	volatile int t = 0;

	public void add() {
		t++;
	}

}
