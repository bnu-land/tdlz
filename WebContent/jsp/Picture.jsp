<%@ page language="java" contentType="application/msword;charset=GBK"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.io.File"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.FileInputStream"%>  
<script type="text/javascript"> 
	  //  openDocObj = new ActiveXObject("SharePoint.OpenDocuments.2");
        //openDocObj.ViewDocument("http://localhost:8080/MyTDJG/jsp/EvaluateReport/04.doc");
</script>  
<html>
  <head>   
    <title>查看申请表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
<% 
try{
 String fileName="F:/eclipsework2/MyTDJG/WebContent/test.doc";
//;.getContextPath("/") "+request.getContextPath()+"

 System.out.println("====="+fileName);
 
 File wordFile = new File(fileName);
 
 response.reset();       
 response.setContentType( "application/msword "); 
 response.setHeader( "Content-Disposition ", "inline;   filename= "+wordFile.getName());   
 InputStream is =new FileInputStream(wordFile); 
 
 OutputStream os = response.getOutputStream(); 
 
 int byteread; 
 byte[] buffer=new byte[1024]; 
 
 while((byteread=is.read(buffer))!=-1){ 
  os.write(buffer,0,byteread); 
 } 
 
 os.flush();
 os.close();
 os.close();
}catch(Exception e){
 e.printStackTrace();
}
%>
</body>
</html>
