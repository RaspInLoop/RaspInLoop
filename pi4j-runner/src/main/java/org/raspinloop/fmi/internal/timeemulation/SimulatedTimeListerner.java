package org.raspinloop.fmi.internal.timeemulation;

public interface SimulatedTimeListerner {
	void onRequestingSleep(long requestedNanos);
}
