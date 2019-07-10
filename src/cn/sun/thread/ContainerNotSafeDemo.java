package cn.sun.thread;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 集合类线程不安全问题
 * ArrayList
 */
public class ContainerNotSafeDemo {

	public static void main(String[] args) {

		List<String> arrayList = new CopyOnWriteArrayList<>();

		for (int i = 1; i <= 30; i++) {
			new Thread(() -> {
				arrayList.add(UUID.randomUUID().toString().substring(0, 8));
				System.out.println(arrayList);
			}, String.valueOf(i)).start();
		}
		// java.util.ConcurrentModificationException

		/**
		 * 1.故障现象
		 *     java.util.ConcurrentModificationException
		 *
		 * 2.导致原因
		 *
		 * 3.解决方案
		 *   3.1 new Vector()
		 *   3.2 Collections.synchronizedList(new ArrayList<>())
		 *
		 *
		 * 4.优化建议
		 */
	}

}
