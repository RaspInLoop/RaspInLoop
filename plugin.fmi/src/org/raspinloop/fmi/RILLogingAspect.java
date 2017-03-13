package org.raspinloop.fmi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.raspinloop.fmi.plugin.Trace;

@Aspect
public class RILLogingAspect {


	@Pointcut("execution(* org.raspinloop.fmi.CoSimulation.Iface.*(..))")
	public void rilTraceCall() {
	}

	@Around("org.raspinloop.fmi.RILLogingAspect.rilTraceCall()")
	public Object myTrace(ProceedingJoinPoint joinPoint) throws Throwable {
		
			StringBuilder sb = new StringBuilder("ril:--> "
					+ joinPoint.getTarget().getClass().getName() + "."
					+ joinPoint.getSignature().getName() + ": ");
			MethodSignature signature = (MethodSignature) joinPoint
					.getSignature();
			Method method = signature.getMethod();
			Annotation[][] annotations = method.getParameterAnnotations();
			for (int i = 0; i < annotations.length; ++i) {
				sb.append(joinPoint.getArgs()[i]);
				if (i != annotations.length - 1)
					sb.append(", ");
			}
			Trace.fmiProxy(sb.toString());		

		Object retVal = null;
		try {
			retVal = joinPoint.proceed();
		} finally {
			
			Trace.fmiProxy("ril:<-- "
						+ joinPoint.getTarget().getClass().getName() + "."
						+ joinPoint.getSignature().getName() + " retval="
						+ retVal);
		}
		return retVal;
	}

}