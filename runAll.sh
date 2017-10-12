#!/bin/bash
javac mScalaLex.java
javac mScalaSyn.java

for i in $(seq 1 6);
do
	java mScalaSyn < Test$i.scala > output$i.txt
done
