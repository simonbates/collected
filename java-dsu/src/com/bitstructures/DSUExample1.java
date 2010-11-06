package com.bitstructures;

import java.util.Arrays;
import java.util.List;

// see http://wiki.python.org/moin/HowTo/Sorting

public class DSUExample1 {
	public static void main(String[] args) {
		List<String> a = Arrays.asList("This is a test string from Andrew".split("\\s"));
		DSU.sort(a, new SortKey<String,String>() {
			public String getValue(String s) {
				return s.toLowerCase();
			}
		});
		for (String s : a) {
			System.out.println(s);
		}
	}
}
