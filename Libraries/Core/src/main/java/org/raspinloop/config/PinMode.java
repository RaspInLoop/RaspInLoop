package org.raspinloop.config;

import java.util.EnumSet;

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

 public static EnumSet<PinMode> allDigital() {
        return EnumSet.of(PinMode.IN, PinMode.OUT);
    }

    public static EnumSet<PinMode> all() {
        return EnumSet.allOf(PinMode.class);
    }

    public static EnumSet<PinMode> allInputs() {
        return EnumSet.of(PinMode.IN);
    }
}
