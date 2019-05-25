 /******************************************************
 * Copyrights @ 2016，Chanxa Technology Co., Ltd.
 *		linayi
 * All rights reserved.
 * 
 * Filename：
 *		Configuration.java
 * Description：
 * 		项目配置文件加载
 * Author:
 *		chanxa
 * Finished：
 *		2017年11月20日
 ********************************************************/
 package com.linayi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configuration {

	private static Configuration configuration;
	private static Logger logger = LoggerFactory.getLogger(Configuration.class);
	/**如果需要在项目所在目录下加载配置文件，请在项目所在目录添加名字为：项目根目录名称+.properties 的配置文件*/
	private static String fileName = "system.properties";
	private static String postfix = ".properties";
	//检查配置文件的间隔
	private static final long INTERVAL = 60 * 1000;
	private Properties pro;
	private static String fileFullPath = null;

	private Configuration() {
		Thread t = new Thread(new CheckModifiedTask());
		t.setDaemon(true);
		t.start();
	}

	static {
		configuration = new Configuration();
		reloadSetting();
	}
	
	@SuppressWarnings("deprecation")
	private static String decode(String path){
		return URLDecoder.decode(path);
	}

	// 获取配置文件
	public static String getConfigFile() {
		if (fileFullPath == null) {
			synchronized (Configuration.class) {
				if (fileFullPath == null) {
					String cname = null;
					
					//获取类加载路径（tomcat的classes目录）
					String classPath = Configuration.class.getClassLoader().getResource("").getPath();
					logger.debug("classPath->"+classPath);
					//项目名称
					String projectName = "";
					//获取tomcat的webapps目录
					File webapps = new File( classPath );
					int i = 0;
					while( i++ < 3 ){
						if( webapps.exists() ){
							if( i == 3){
								projectName = webapps.getName();
								logger.debug( "projectName->" + projectName);
								logger.debug( "webapps->" + webapps.getParent());
							}
							webapps = new File( webapps.getParent() );
						}
					}
					if (webapps != null) {
						File configFile = new File(webapps, projectName + postfix);
						if (configFile.exists()) {
							cname = configFile.getAbsolutePath();
						}
					}
					if (cname == null) {
						cname = Configuration.class.getResource("/config/" + fileName).getPath();
					}
					cname = decode(cname);
					fileFullPath = cname;
				}
			}
		}
		return fileFullPath;
	}

	public static Configuration getConfig() {
		return configuration;
	}

	public String getValue(String key) {
		return this.pro.getProperty(key);
	}

	/***
	 * 重新加载配置文件
	 */
	public synchronized static void reloadSetting() {
		Properties pro = new Properties();
		InputStream in = null;
		try {
			if (in == null) {
				in = new FileInputStream(getConfigFile());
			}
			logger.debug("项目加载配置文件->"+fileFullPath);
			pro.load(in);
			in.close();
			configuration.pro = pro;
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/** 检查文件是否被修改过 */
	class CheckModifiedTask implements Runnable {
		// 文件最后修改时间,默认值是该类被加载的时间
		private long modifiedTime;

		public CheckModifiedTask() {
			this.modifiedTime = System.currentTimeMillis();
		}


		public void run() {
			while (true) {
				try {
					// 每分钟循环一次
					Thread.sleep(INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String filename = getConfigFile();
				File f = new File(filename);
				long lastModified = f.lastModified();
				if (lastModified > modifiedTime) {
					modifiedTime = lastModified;
					reloadSetting();
				}
			}
		}
	}

}
