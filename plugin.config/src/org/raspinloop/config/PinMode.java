package org.raspinloop.config;

public enum PinMode {
  IN(0,"input"),
  OUT(1,"output");
 
  final private int value;
  final private String name;

  PinMode(int value, String name){
	this.value = value;
	this.name = name;
	  
  }

public int getValue() {
	return value;
}

public String getName() {
	return name;
}
}
