package com.bitstructures;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

public class DSUTest extends TestCase {
	public void testEmptyList() {
		List<String> l = Collections.emptyList();
		DSU.sort(l, new SortKey<String,String>() {
			public String getValue(String s) {
				return s;
			}
		});
		assertEquals(0, l.size());
	}

	public void testToLowerCase() {
		List<String> l = Arrays.asList("This is a test string from Andrew".split("\\s"));
		DSU.sort(l, new SortKey<String,String>() {
			public String getValue(String s) {
				return s.toLowerCase();
			}
		});
		assertEquals(7, l.size());
		assertEquals("a", l.get(0));
		assertEquals("Andrew", l.get(1));
		assertEquals("from", l.get(2));
		assertEquals("is", l.get(3));
		assertEquals("string", l.get(4));
		assertEquals("test", l.get(5));
		assertEquals("This", l.get(6));
	}
}
