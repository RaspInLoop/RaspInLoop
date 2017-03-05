package org.raspinloop.fmi.internal.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.raspinloop.fmi.internal.timeemulation.SimulatedTime;

@Aspect
public class SleepAspect {

	
	@Around("call(void Thread.sleep(..)) && !within(java.*) && !within(org.raspinloop.standalone.*)&& !within(org.raspinloop.internal.timeemulation.*)")
	public void sleep(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		if (args.length == 1 && args[0] instanceof Long)
			SimulatedTime.sleep((Long)args[0]);
		else if (args.length == 2 && args[0] instanceof Long && args[1] instanceof Integer)
			SimulatedTime.sleep((Long)args[0], (Integer)args[1]);			
	}
}
