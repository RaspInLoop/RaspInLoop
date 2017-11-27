package org.raspinloop.agent.internal.aspect;

import java.util.concurrent.locks.Condition;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.raspinloop.timeemulation.SimulatedTime;

@Aspect
public class SimulatedTimeAspect {

		
	@Around("call(long System.nanoTime(..)) && within(org.raspinloop.fmi.internal.timeemulation.*)")
	public long nanotime(ProceedingJoinPoint joinPoint) throws Throwable {
		return SimulatedTime.nanotime();
	}
	
	
	@Around("call(long java.util.concurrent.locks.Condition.awaitNanos(..)) && within(org.raspinloop.fmi.internal.timeemulation.*) && !within(org.raspinloop.fmi.internal.timeemulation.SimulatedTime)")
	public long awaitnano(ProceedingJoinPoint joinPoint ) throws Throwable {
		return SimulatedTime.awaitNanos((long)joinPoint.getArgs()[0], (Condition)joinPoint.getTarget());
	}

}
