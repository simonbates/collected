package com.bitstructures;

import java.lang.Comparable;

public interface SortKey<T,C extends Comparable<C>> {
	public C getValue(T obj);
}
