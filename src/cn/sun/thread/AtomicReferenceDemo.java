package cn.sun.thread;

import java.util.concurrent.atomic.AtomicReference;

class User {

	String name;

	int age;

	public User(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}

public class AtomicReferenceDemo {

	public static void main(String[] args) {
		User z3 = new User("z3", 18);

		User li4 = new User("li4", 22);

		AtomicReference<User> atomicReference = new AtomicReference();

		atomicReference.set(z3);

		System.out.println(atomicReference.compareAndSet(z3, li4) + "\t" + atomicReference.get().toString());

	}

}
