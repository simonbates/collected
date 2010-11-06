#!/bin/sh

java -cp bin -Xmx8193k com.bitstructures.DSUExample2 -dsu dyssy10.txt
java -cp bin -Xmx6145k com.bitstructures.DSUExample2 -comparator dyssy10.txt
