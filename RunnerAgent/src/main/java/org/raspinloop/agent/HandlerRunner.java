package org.raspinloop.agent;

import org.raspinloop.agent.Handler;

public interface HandlerRunner extends Runnable {
	void setHandle(Handler handler);
}
