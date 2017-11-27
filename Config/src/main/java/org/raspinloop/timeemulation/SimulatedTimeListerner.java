package org.raspinloop.timeemulation;

public interface SimulatedTimeListerner {
	void onRequestingSleep(long requestedNanos);
}
