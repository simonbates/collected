package com.bitstructures;

import java.lang.Comparable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DSU {
	private static class DecoratedObject<T,C extends Comparable<C>> {
		public T obj;
		public C comparable;
	}

	private static class DecoratedComparator<T,C extends Comparable<C>>
			implements Comparator<DecoratedObject<T,C>> {
		public int compare(DecoratedObject<T,C> o1, DecoratedObject<T,C> o2) {
			return o1.comparable.compareTo(o2.comparable);
		}
	}

	public static <T,C extends Comparable<C>> void sort(List<T> list,
			SortKey<T,C> key) {
		// decorate
		int size = list.size();
		DecoratedObject<T,C>[] decorated
				= (DecoratedObject<T,C>[]) new DecoratedObject[size];
		for (int i = 0; i < size; i++) {
			decorated[i] = new DecoratedObject<T,C>();
			decorated[i].obj = list.get(i);
			decorated[i].comparable = key.getValue(list.get(i));
		}
		// sort
		DecoratedComparator<T,C> comparator = new DecoratedComparator<T,C>();
		Arrays.sort(decorated, comparator);
		// undecorate
		for (int i = 0; i < size; i++) {
			list.set(i, decorated[i].obj);
		}
	}
}
