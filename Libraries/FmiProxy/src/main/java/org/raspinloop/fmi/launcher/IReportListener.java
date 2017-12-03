package org.raspinloop.fmi.launcher;

import org.raspinloop.fmi.launcherRunnerIpc.ReportType;

public interface IReportListener {

	Object report(ReportType type, String message);

}
