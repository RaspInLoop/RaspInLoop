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
package org.raspinloop.config;


/**
 * Implementation of this interface will be used to create Builders.<p>
 *
 * Builders are required to build Hardware classes based on their properties. Several kind of builders
 * can be created following the policy of class loading. (standard, from osgi bundle, ...)
 * 
 * 
 * @author Motte
 *
 */
public interface  HardwareBuilderFactory {
	HardwareBuilder createBuilder(HardwareProperties hwProps);
}
