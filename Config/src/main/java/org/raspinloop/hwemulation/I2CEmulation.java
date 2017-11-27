package org.raspinloop.hwemulation;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.raspinloop.fmi.I2CCompHwEmulation;

public enum I2CEmulation {
	// Singleton
	INST;

	private HashMap<Integer, I2CBusEmulation> i2cBuses = new HashMap<Integer, I2CBusEmulation>();
		
	public static I2CBus getInstance(Object[] args) throws IOException {
		return INST.getBusInstance(args);
	}
	
	private I2CBus getBusInstance(Object[] args) throws IOException {
		I2CBusEmulation instance;
		if (args.length >= 1 && args[0] instanceof Integer){
			instance = get((int) args[0]);
			
			if (args.length == 3)  {
				if (args[1] instanceof Long && args[2] instanceof TimeUnit)
					instance.setLockAquireTimeout((long)args[1], (TimeUnit)args[2] );		
			}
			return instance;
		} else
			throw new IOException("Invalid parameter in call to I2CFactory.getInstance.");
		
	}

	private I2CBusEmulation get(int busNb) {
		if (! i2cBuses.containsKey(busNb)){
			i2cBuses.put(busNb, new I2CBusEmulation(busNb));
		}
		return i2cBuses.get(busNb);
	}
	
	public static void registerHwEmulation(short busNumber, I2CCompHwEmulation hw) throws Exception{
		if (INST.i2cBuses.containsKey(busNumber)){
			INST.i2cBuses.get(busNumber).register(hw);
		}
		else
			throw new Exception("no bus defined on" + busNumber);
	}	
}
