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
