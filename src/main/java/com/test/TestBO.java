package com.test;

import java.util.HashSet;

class Square {
	int x;

	@Override
	public int hashCode() {
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Square other = (Square) obj;
		if (x != other.x)
			return false;
		return true;
	}

}

class A1 {
	static {
		System.out.println("static a1");
	}

	public A1() {
		System.out.println("Cons A1");
	}
}

class B1 extends A1 {
	static {
		System.out.println("static B1");
	}

	public B1() {
		System.out.println("Cons B1");
	}
}

public class TestBO {
	public static void main(String[] args) {
		HashSet<Square> set = new HashSet<>();
		set.add(new Square());
		set.add(new Square());
		set.add(new Square());
		set.add(new Square());
		set.add(new Square());

		System.out.println(set.size());

	}
}
