package com.linayi.util;

import com.linayi.config.MSGConfig;
import com.linayi.config.SystemPropert;

public class MSGSenderConfigurationImpl implements MSGConfig {

	@Override
	public String getAppKey() {
		return Configuration.getConfig().getValue(SystemPropert.MOBILE_APPKEY );
	}


}
