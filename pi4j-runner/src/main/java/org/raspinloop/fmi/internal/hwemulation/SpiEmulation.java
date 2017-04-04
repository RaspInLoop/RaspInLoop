package org.raspinloop.fmi.internal.hwemulation;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.raspinloop.fmi.hwemulation.SpiCompHwEmulation;

import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiDevice;
import com.pi4j.io.spi.SpiMode;
public enum SpiEmulation {
	// Singleton
	INST;

	private SpiDeviceEmulation spiDevices[] = new SpiDeviceEmulation[SpiChannel.values().length];
		
	public static SpiDevice getInstance(Object[] args) throws IOException {
		return INST.getDdevInstance(args);
	}
	
	private SpiDevice getDdevInstance(Object[] args) throws IOException {
		SpiDeviceEmulation instance;
		if (args.length >= 1 && args[0] instanceof SpiChannel){
			instance = get(((SpiChannel)args[0]));
			
			if (args.length == 2)  {
				if (args[1] instanceof SpiMode)
					instance.setMode((SpiMode)args[1]);
				else if (args[1] instanceof Integer)
					instance.setSpeed((Integer)args[1]);
			} else if (args.length == 3)  {				
					instance.setSpeed((Integer)args[1]);
					instance.setMode((SpiMode)args[2]);				
			}
			return instance;
		} else
			throw new IOException("Invalid parameter in call to SPIFactory.getInstance.");
		
	}

	private SpiDeviceEmulation get(SpiChannel channel) {
		if( spiDevices[channel.getChannel()] == null)
			spiDevices[channel.getChannel()] = new SpiDeviceEmulation(this, channel);
		return spiDevices[channel.getChannel()];
	}

	
	Map<Short, SpiCompHwEmulation> devOnChannel = new HashMap<>();
	
	public void registerHwEmulation(short channel, SpiCompHwEmulation hw){
		devOnChannel.put(channel, hw);
	}
	
	public int wiringPiSPIDataRW(short channel, byte[] buffer) {
		try {
			if (devOnChannel.containsKey(channel))
				return devOnChannel.get(channel).spiDataRW(buffer);
		} catch (Exception e) {
		}
		return 0;
	}

	public int wiringPiSPIDataRW(short channel, short[] buffer) {
		try {
			if (devOnChannel.containsKey(channel))
				return devOnChannel.get(channel).spiDataRW(buffer);
		} catch (Exception e) {
		}
		return 0;
	}
	
	

}
