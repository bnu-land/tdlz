package cn.edu.bnu.land.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	
	
	private static Properties properties=null;
	static{
		properties=new Properties();
		try {
			properties.load(ConfigReader.class.getResourceAsStream("config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static String getString(String key){
		return properties.getProperty(key);
	}

	public static String getIpAndPort(){
		return properties.getProperty("ip")+properties.getProperty("port");
	}
	
	public static String getWholeurl_jyhoutai(){
		return getIpAndPort()+properties.getProperty("appName_jyhoutai");
	}

	public static String getWholeurl_webSite(){
		return getIpAndPort()+properties.getProperty("appName_webSite");
	}

	
	/*获得工程的发布路径。
	 * 例如E:/springsource2/vfabric-tc-server-developer-2.9.2.RELEASE/base-instance/wtpwebapps/tdlz_houtai
	 *其中tdlz_houtai是工程名称
	*/
	public static String getrootPath()
	{
		String rootPath=ConfigReader.class.getClassLoader().getResource("/").getPath();
		int pos=rootPath.indexOf("/WEB-INF");
		rootPath=rootPath.substring(0, pos);
        System.out.println("rootpath:"+rootPath);
        return rootPath;
         
	}
    
	
	
	
}
