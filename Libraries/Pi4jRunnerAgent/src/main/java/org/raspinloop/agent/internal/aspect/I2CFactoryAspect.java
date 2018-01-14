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
import org.raspinloop.hwemulation.I2CEmulation;


@Aspect
public class I2CFactoryAspect {

	@Around("call(com.pi4j.io.i2c.I2CBus com.pi4j.io.i2c.I2CFactory.getInstance(..)) ")
	public com.pi4j.io.i2c.I2CBus i2CGetInstance(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();		
		return Adapters.forPi4j(I2CEmulation.getInstance(args));
	}	
}
