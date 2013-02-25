package at.bench;

import java.io.File;

public class IATSettings {

	// Default location for AmbientTalk library
	public static final File _ENV_AT_HOME_ = new File(Constants._ENV_AT_BASE_,
			Constants._AT_HOME_RELATIVE_PATH_);
	public static final File _ENV_AT_INIT_ = new File(_ENV_AT_HOME_,
			"/at/init/init.at");

	public static String getMyIp() {
		return "127.0.0.1";
	}

	public static IATOptions getDefaultIATOptions() {
		return new IATOptions();
	}

	public static class IATOptions {
		public String ipAddress_;
		public String networkName_;
		public String AT_HOME_;
		public String AT_INIT_;
		public String logFilePath_;
		public String startFile_;

		// construct default iat options.
		IATOptions() {
			ipAddress_ = getMyIp();
			networkName_ = "AmbientTalk";
			AT_HOME_ = _ENV_AT_HOME_.toString();
			AT_INIT_ = _ENV_AT_INIT_.toString();
			logFilePath_ = _ENV_AT_HOME_ + "/at.log";
		}

		// construct a custom iat options instance.
		IATOptions(String ipAddress, String networkName, String atHome,
				String atInit) {
			ipAddress_ = ipAddress;
			networkName_ = networkName;
			AT_HOME_ = atHome;
			AT_INIT_ = atInit;
		}
	}
}