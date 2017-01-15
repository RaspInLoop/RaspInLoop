package org.raspinloop.fmi.internal.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class RILLogingAspect {
	final static Logger logger = Logger
			.getLogger("org.raspinloop.fmi.CoSimulation.Iface");

	@Pointcut("execution(* org.raspinloop.fmi.CoSimulation.Iface.*(..))")
	public void rilTraceCall() {
	}

	@Around("org.raspinloop.fmi.internal.aspect.RILLogingAspect.rilTraceCall()")
	public Object myTrace(ProceedingJoinPoint joinPoint) throws Throwable {
		if (logger.isTraceEnabled()) {
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
			logger.trace(sb.toString());
		}

		Object retVal = null;
		try {
			retVal = joinPoint.proceed();
		} finally {
			if (logger.isTraceEnabled())
				logger.trace("ril:<-- "
						+ joinPoint.getTarget().getClass().getName() + "."
						+ joinPoint.getSignature().getName() + " retval="
						+ retVal);
		}
		return retVal;
	}

}