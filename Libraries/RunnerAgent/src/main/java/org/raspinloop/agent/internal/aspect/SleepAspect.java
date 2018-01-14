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
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.raspinloop.timeemulation.SimulatedTime;

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
