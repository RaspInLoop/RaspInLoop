package org.raspinloop.agent.internal.aspect;
import java.io.IOException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.raspinloop.hwemulation.SpiEmulation;

import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiMode;

@Aspect
public class SpiFactoryAspect {

	@Around("call(com.pi4j.io.spi.SpiDevice com.pi4j.io.spi.SpiFactory.getInstance(..)) ")
	public com.pi4j.io.spi.SpiDevice spiGetInstance(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();		
		
		if (args.length == 1 
			&& args[0] instanceof SpiChannel)
				return Adapters.forPi4j(SpiEmulation.getInstance(Adapters.forPi4j((SpiChannel)args[0])));
				
		if (args.length == 2 
			&& args[0] instanceof SpiChannel 
			&& args[1] instanceof SpiMode)
			return Adapters.forPi4j(SpiEmulation.getInstance(Adapters.forPi4j((SpiChannel)args[0]),
															 Adapters.forPi4j((SpiMode)args[1])));
		if (args.length == 2 
				&& args[0] instanceof SpiChannel 
				&& args[1] instanceof Integer)
				return Adapters.forPi4j(SpiEmulation.getInstance(Adapters.forPi4j((SpiChannel)args[0]),
																 (Integer)args[1]));

		if (args.length == 3 
			&& args[0] instanceof SpiChannel 
			&& args[1] instanceof Integer
			&& args[2] instanceof SpiMode)
			return Adapters.forPi4j(SpiEmulation.getInstance(Adapters.forPi4j((SpiChannel)args[0]),
															(Integer)args[1],
															 Adapters.forPi4j((SpiMode)args[2])));	
			
		throw new IOException("Invalid parameter in call to SPIFactory.getInstance.");
	
	}	
}
