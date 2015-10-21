package cn.edu.bnu.land.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.regex.*;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.gson.*;

import org.aspectj.apache.bcel.util.ClassPath;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.lang.reflect.*;

import com.google.gson.reflect.*;
import com.google.gson.Gson;

import java.lang.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.edu.bnu.land.common.ConfigReader;
import cn.edu.bnu.land.common.Encoder;


public class fileUploadService {

	public static byte[] getByte(File file) throws Exception

    {
        byte[] bytes = null;
        if(file!=null)
        {
            InputStream is = new FileInputStream(file);
            int length = (int) file.length();
            if(length>Integer.MAX_VALUE)   //当文件的长度超过了int的最大值
            {
                System.out.println("this file is max ");
                return null;
            }
            bytes = new byte[length];
            int offset = 0;
            int numRead = 0;
            while(offset<bytes.length&&(numRead=is.read(bytes,offset,bytes.length-offset))>=0)
            {
                offset+=numRead;
            }
            //如果得到的字节长度和file实际的长度不一致就可能出错了
            if(offset<bytes.length)
            {
                System.out.println("file length is error");
                return null;
            }
            is.close();
        }
        return bytes;
    }
	private static String generateFileName() {
		// 获得当前时间
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		// 转换为字符串
		String formatDate = format.format(new Date());
		// 随机生成文件编号
		int random = new Random().nextInt(1000);
		return new StringBuffer().append(formatDate).append(
		random).toString();
		}
 
	public static void fileUpload(HttpServletRequest request,HttpServletResponse response,String filename) throws Exception{
		  MultipartHttpServletRequest multipartHttpservletRequest=(MultipartHttpServletRequest) request;  
	      MultipartFile multipartFile = multipartHttpservletRequest.getFile(filename);  
	      String originalFileName=multipartFile.getOriginalFilename();
	      originalFileName = Encoder.encode(originalFileName);
	      System.out.println("打印originalFileName:"+originalFileName);  
	      String ctxPath = request.getSession().getServletContext().getRealPath("/");
	      //System.out.println("路径打印ctxPath:"+ctxPath);  
	      //System.out.println(multipartFile.getOriginalFilename());  
	      //String suffix=multipartFile.getOriginalFilename().substring (multipartFile.getOriginalFilename().lastIndexOf("."));
	      //String s=ConfigReader.getrootPath();
	      
	      File file=new File(ctxPath+"\\Upload\\"+filename+"\\"+filename+generateFileName()+"");//此处word应从会话中获取
	        System.out.println(file.getName()); 
	        System.out.println(file.getAbsolutePath());  
	        try {  
	            FileOutputStream fileOutputStream=new FileOutputStream(file+originalFileName.substring(originalFileName.lastIndexOf('.'), originalFileName.length()));  
	            fileOutputStream.write(multipartFile.getBytes());  
	            fileOutputStream.flush();  
	            fileOutputStream.close();  
	        } catch (FileNotFoundException e) {   
	            e.printStackTrace();   
	        } catch (IOException e) {  	           
	            e.printStackTrace();  	            
	        }  
	 }
	
 }

