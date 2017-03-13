/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


/**
 * Thrift files can namespace, package, or prefix their output in various
 * target languages.
 */

namespace java org.raspinloop.fmi.runner


/* Type definitions */
enum Status{
    OK = 0,
    Warning = 1,
    Discard = 2,
    Error = 3,
    Fatal = 4,
    Pending = 5
}

enum ReportType{
    Error = 0,
    Info = 1,
    Log = 2,
    Trace = 3
}

enum StatusKind{
    DoStepStatus =0,
    PendingStatus = 1,
    LastSuccessfulTime = 2,
    Terminated = 3
}

service LauncherService {

	Status ReadyToStart(1:i32 runnerClientPort),
	
	Status Report(1:ReportType type, 2:string message)

}

service RunnerService {
  
    Status setupExperiment(1:bool toleranceDefined, 2:double tolerance,
                            3:double startTime, 4:bool stopTimeDefined, 5:double stopTime),
	
	Status terminate(),

	list<double> getReal (1:list<i32> refs),
	
	list<i32> getInteger (1:list<i32> refs),
	
	list<bool> getBoolean (1:list<i32> refs),	
	
	Status setReal (1:map<i32, double> ref_values),
	
	Status setInteger (1:map<i32, i32> ref_values),
	
	Status setBoolean (1:map<i32, bool> ref_values),
	
	Status cancelStep(),
	
	Status doStep(1:double currentCommunicationPoint, 2:double communicationStepSize, 3:bool noSetFMUStatePriorToCurrentPoint),
	
	Status getStatus(1:StatusKind s),
	
	double getRealStatus(1:StatusKind s),
	
	bool getBooleanStatus(1:StatusKind s)
}
