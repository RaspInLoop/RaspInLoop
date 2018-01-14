/*******************************************************************************
 * Copyright 2018 RaspInLoop
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.raspinloop.agent.internal.aspect;

import java.util.concurrent.locks.Condition;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.raspinloop.timeemulation.SimulatedTime;

@Aspect
public class SimulatedTimeAspect {

		
	@Around("call(long System.nanoTime(..)) && within(org.raspinloop.agent.internal.timeemulation.*)")
	public long nanotime(ProceedingJoinPoint joinPoint) throws Throwable {
		return SimulatedTime.nanotime();
	}
	
	
	@Around("call(long java.util.concurrent.locks.Condition.awaitNanos(..)) && within(org.raspinloop.agent.internal.timeemulation.*) && !within(org.raspinloop.fmi.internal.timeemulation.SimulatedTime)")
	public long awaitnano(ProceedingJoinPoint joinPoint ) throws Throwable {
		return SimulatedTime.awaitNanos((long)joinPoint.getArgs()[0], (Condition)joinPoint.getTarget());
	}

}
