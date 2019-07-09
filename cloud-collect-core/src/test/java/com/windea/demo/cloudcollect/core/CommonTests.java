package com.windea.demo.cloudcollect.core;

import org.junit.Test;

@SuppressWarnings("unchecked")
public class CommonTests {
	@Test
	public void test() {
		var a = this.<Integer>toT(123);
		System.out.println(a);
	}

	public <T extends Number> T toT(Number number) {
		return (T) number;
	}
}
