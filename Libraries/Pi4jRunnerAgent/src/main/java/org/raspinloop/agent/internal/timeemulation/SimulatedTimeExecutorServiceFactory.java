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
package org.raspinloop.agent.internal.timeemulation;
/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: Java Library (Core)
 * FILENAME      :  DefaultExecutorServiceFactory.java  
 * 
 * This file is part of the Pi4J project. More information about 
 * this project can be found here:  http://www.pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2015 Pi4J
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import com.pi4j.concurrent.ExecutorServiceFactory;
import com.pi4j.concurrent.ScheduledExecutorServiceWrapper;
import com.pi4j.concurrent.ShutdownDisabledExecutorWrapper;

public class SimulatedTimeExecutorServiceFactory implements ExecutorServiceFactory {

    public static int MAX_THREADS_IN_POOL = 25;
    private static List<ExecutorService> singleThreadExecutorServices = new ArrayList<>();

    private static class ScheduledExecutorServiceHolder {
        private static ScheduledExecutorService heldExecutor = null;
        
        static ScheduledExecutorService get(){
        	if (heldExecutor == null) {
        		heldExecutor = new SimulatedTimeScheduledThreadPoolExecutor(MAX_THREADS_IN_POOL, getThreadFactory("pi4j-scheduled-executor-%d"));
        	}
        	return heldExecutor;
        }
        
        static void reset(){
        	heldExecutor = null;
        }
    }
      
    private static class ScheduledExecutorServiceWrapperHolder {
    	static ScheduledExecutorServiceWrapper get(){
    		if (heldWrapper == null){
    			heldWrapper = new ScheduledExecutorServiceWrapper(ScheduledExecutorServiceHolder.get());
    		}
    		return heldWrapper;
    	}
    	
        private static ScheduledExecutorServiceWrapper heldWrapper = null;

		public static void reinit() {
			ScheduledExecutorServiceHolder.reset();
			heldWrapper = null;
		}
    }
    
    private static ScheduledExecutorServiceWrapper getScheduledExecutorServiceWrapper() {
        return ScheduledExecutorServiceWrapperHolder.get();
    }

    // this seemingly odd pattern is the recommended way to lazy-initialize static fields in effective java.
    // The static "holder" class doesn't have it's static initializer called until it is accessed - and it's not accessed until the
    // getInternalScheduledExecutor() method is called.
    //
    // (see effective java item 71:Use lazy initialization judiciously)

    
    // follow a similar lazy initialization pattern for the gpio events
    private static class GpioEventExecutorServiceHolder {
        static final ExecutorService heldExecutor = Executors.newCachedThreadPool(getThreadFactory("pi4j-gpio-event-executor-%d"));
    }
    private static ExecutorService getInternalGpioExecutorService() {
        return GpioEventExecutorServiceHolder.heldExecutor;
    }
    private static class GpioEventExecutorServiceWrapperHolder {
        static final ExecutorService heldWrapper = new ShutdownDisabledExecutorWrapper(getInternalGpioExecutorService());
    }
    private static ExecutorService getGpioEventExecutorServiceWrapper() {
        return GpioEventExecutorServiceWrapperHolder.heldWrapper;
    }

    public void reinit(){
    	ScheduledExecutorServiceWrapperHolder.reinit();
    }
    
    /**
     * return an instance to the thread factory used to create new executor services
     */
    private static ThreadFactory getThreadFactory(final String nameFormat) {
        final ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();
        return new ThreadFactory() {
            final AtomicLong count = (nameFormat != null) ? new AtomicLong(0) : null;

            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = defaultThreadFactory.newThread(runnable);
                if (nameFormat != null) {
                    thread.setName(String.format(nameFormat, count.getAndIncrement()));
                }
                return thread;
            }
        };
    }

    /**
     * return an instance to the scheduled executor service (wrapper)
     */
    public ScheduledExecutorService getScheduledExecutorService() {
        // we return the protected wrapper to prevent any consumers from 
        // being able to shutdown the scheduled executor service
        return getScheduledExecutorServiceWrapper();
    }

    @Override
    public ExecutorService getGpioEventExecutorService() {
        // we return the protected wrapper to prevent any consumers from
        // being able to shutdown the scheduled executor service
        return getGpioEventExecutorServiceWrapper();
    }

    /**
     * return a new instance of a single thread executor service
     *
     * This method is deprecated in favor of the getGpioEventExecutorService - which provides better guarantees around resource
     * management
     */
    @Override
    public ExecutorService newSingleThreadExecutorService() {

        // create new single thread executor service
        ExecutorService singleThreadExecutorService = Executors.newSingleThreadExecutor(getThreadFactory("pi4j-single-executor-%d"));

        // add new instance to managed collection
        singleThreadExecutorServices.add(singleThreadExecutorService);

        // return new executor service
        return singleThreadExecutorService;
    }

    /**
     * shutdown executor threads
     */
    public void shutdown() {
        // shutdown each single thread executor in the managed collection
        for (ExecutorService singleThreadExecutorService : singleThreadExecutorServices) {
            shutdownExecutor(singleThreadExecutorService);
        }

        // shutdown scheduled executor instance
        shutdownExecutor(ScheduledExecutorServiceHolder.get());
        shutdownExecutor(getInternalGpioExecutorService());

    }

    private void shutdownExecutor(ExecutorService executor) {
        if (executor != null) {
            if (!executor.isShutdown()) {
                // this is a forceful shutdown;
                // don't wait for the scheduled tasks to complete
                executor.shutdownNow();
            }
        }
    }
}


