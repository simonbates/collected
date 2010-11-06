package com.bitstructures;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class DSUExample2 {
	private static class Counter {
		public int i = 0;
		public void increment() {
			i++;
		}
	}

	private static List<String> readWords(String filename) throws IOException {
		BufferedReader r = new BufferedReader(new FileReader(filename));
		List<String> words = new ArrayList<String>();
		String line = r.readLine();
		while(line != null) {
			String[] wordsThisLine = line.split("\\s+");
			for (String word : wordsThisLine) {
				if (!word.trim().equals("")) {
					words.add(word);
				}
			}
			line = r.readLine();
		}
		return words;
	}

	private static void dsu(List<String> words, boolean count, boolean log)
			throws IOException {
		final Counter c = new Counter();
		long t1 = System.currentTimeMillis();
		if (count) {
			DSU.sort(words, new SortKey<String,String>() {
				public String getValue(String s) {
					c.increment();
					return s.toLowerCase();
				}
			});
		} else {
			DSU.sort(words, new SortKey<String,String>() {
				public String getValue(String s) {
					return s.toLowerCase();
				}
			});
		}
		long t2 = System.currentTimeMillis();
		if (log) {
			System.out.println("DSU");
			if (count) {
				System.out.println("called " + c.i + " times");
			} else {
				System.out.println((t2 - t1) + "ms");
			}
		}
	}

	private static void collectionsSortWithComparator(List<String> words,
			boolean count, boolean log) throws IOException {
		final Counter c = new Counter();
		long t1 = System.currentTimeMillis();
		if (count) {
			Collections.sort(words, new Comparator<String>() {
				public int compare(String s1, String s2) {
					c.increment();
					return s1.toLowerCase().compareTo(s2.toLowerCase());
				}
			});
		} else {
			Collections.sort(words, new Comparator<String>() {
				public int compare(String s1, String s2) {
					return s1.toLowerCase().compareTo(s2.toLowerCase());
				}
			});
		}
		long t2 = System.currentTimeMillis();
		if (log) {
			System.out.println("Collections.sort with Comparator");
			if (count) {
				System.out.println("called " + c.i + " times");
			} else {
				System.out.println((t2 - t1) + "ms");
			}
		}
	}

	public static void main(String[] args) throws IOException {
		int DSU = 0;
		int COMPARATOR = 1;
		int which = DSU;
		boolean count = false;
		boolean outputWords = false;
		if (args.length < 1) {
			System.err.println("usage: DSUExample2 OPTIONS FILENAME");
			System.exit(1);
		}
		for (int i = 0; i < args.length - 1; i++) {
			if (args[i].equals("-dsu")) {
				which = DSU;
			}
			if (args[i].equals("-comparator")) {
				which = COMPARATOR;
			}
			if (args[i].equals("-count")) {
				count = true;
			}
			if (args[i].equals("-words")) {
				outputWords = true;
			}
		}
		List<String> words = readWords(args[args.length - 1]);
		if (which == DSU) {
			dsu(words, count, !outputWords);
		} else if (which == COMPARATOR) {
			collectionsSortWithComparator(words, count, !outputWords);
		}
		if (outputWords) {
			for (String s : words) {
				System.out.println(s);
			}
		}
	}
}
