/*******************************************************************************
 * Copyright (C) 2018 RaspInLoop
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.raspinloop.fmi.plugin.configuration;

import java.util.concurrent.TimeUnit;

import org.eclipse.jface.viewers.LabelProvider;

public class TimeUnitLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof TimeUnit)
			switch((TimeUnit)element) {
			case MICROSECONDS: return "Microsecond(s)";
			case DAYS: return "Day(s)";
			case HOURS: return "Hour(s)";
			case MILLISECONDS:return "Millisecond(s)";
			case MINUTES: return "Minute(s)";
			case NANOSECONDS:return "Nanosecond(s)";
			case SECONDS:return "Second(s)";
			default:
				return "Second(s)";
			}
		else
			return super.getText(element);
	}
	
}
