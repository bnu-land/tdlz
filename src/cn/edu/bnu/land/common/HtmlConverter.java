package cn.edu.bnu.land.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


public class HtmlConverter {

 public static void main(String[] args) throws JDOMException, IOException {
  String xls = "D:\\T.xls";
  //String url="http://localhost:8088/customReport/execute/g02.jsp?selReport=RPT_642_2006921_171441";
  FileInputStream inp = new FileInputStream(xls);
  HSSFWorkbook workbook = new HSSFWorkbook(inp);
  HSSFSheet sheet = workbook.getSheetAt(0);
  HSSFRow row = sheet.getRow(0);
  
  int rows = sheet.getLastRowNum();
  int cols = row.getLastCellNum();
  int dstart = -1; // 明细内容起始行
  int dend = -1;  // 明细内容结束行
  for(int i=0;i<=rows;i++){
   boolean find = false;
   for(int j=0;j<cols&&!find;j++){
    row = sheet.getRow(i);
    HSSFCell cell = row.getCell((short) j);
    if(cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_STRING && cell.getStringCellValue() != null && cell.getStringCellValue().startsWith("D{")){
     if(dstart == -1){
      dstart = i;
     }
     dend = i;
     find = true;
    }
   }
  }
  int dnum = dend - dstart + 1; // 明细内容行数
  List mergeds = getMergeds(sheet,dstart,dend); // 取得明细行中被合并的单元格的信息   
  List data = getData(); // 取数据
  // 新增明细行
  for(int i=0;i<data.size()-1;i++){
   sheet.shiftRows(dend+1+i*dnum, dend+2+i*dnum, dnum);
   RowCopy(sheet,dstart,dnum,dend+1+i*dnum,mergeds);
  }
  // 数据填充
  for(int i=0;i<data.size();i++){
   Map m = (HashMap)data.get(i);
   if(i==0){
    fillHeader(sheet,dstart,m);
   }
   fillDetail(sheet,dstart+i*dnum,dnum,m);
   if(i==data.size()-1){
    fillHeader(sheet,dstart+data.size()*dnum,m);
    //fillTail(sheet,dstart+data.size()*dnum,m);
   }
  }
  // 转换为html
  String html = ConvertToHtml(sheet,workbook);
  
  File out = new File("D:\\TT.xls");
  FileOutputStream fo = new FileOutputStream(out);
  OutputStream toClient = new BufferedOutputStream(fo);
  workbook.write(toClient);
  toClient.flush();
  toClient.close();
  
  File _html = new File("D:\\TT.html");
  /*FileWriter fw = new FileWriter(_html);
  fw.write(html);
  fw.flush();
  fw.close();
  */
  FileOutputStream stream;// provides file access 
  OutputStreamWriter writer;// writes to the file 
  try { 
        stream = new FileOutputStream(_html); 
        writer = new OutputStreamWriter(stream,"UTF-8"); 
        writer.write(html);
        writer.flush();
        writer.close();
  }catch(Exception e){
  }


  
 }
 
 /**
  * 取RGB颜色
  * @param c
  * @return
  */
 private static String getColor(short c){
  if(c<8||c>63){
   return "";
  }
  String color = ((HSSFColor)HSSFColor.getIndexHash().get(new Integer(c))).getHexString();
  String[] cs = color.split(":");
  color = "#";
  for(int j=0;j<cs.length;j++){
   if(cs[j].length()==1){
    color+=cs[j]+cs[j];
   }else if(cs[j].length()==4){
    color+=cs[j].substring(2);
   }else{
    color+=cs[j];
   }
  }
  return color;
 }
 
 /**
  * Excel Sheet转换为html
  * @param sheet
  * @param workbook 
  * @return
  * @throws UnsupportedEncodingException 
  */
 private static String ConvertToHtml(HSSFSheet sheet, HSSFWorkbook workbook) throws UnsupportedEncodingException {
  int row = sheet.getLastRowNum();
  HSSFRow _row = sheet.getRow(0);
  int col = _row.getLastCellNum();
  String[][][] tdinfo = new String[row][col][2];
  Map style = new HashMap();
  // 合并单元格
  for(int i=0;i<sheet.getNumMergedRegions();i++){
   Region m = sheet.getMergedRegionAt(i);
   int rs = m.getRowFrom();
   int re = m.getRowTo();
   int cs = m.getColumnFrom();
   int ce = m.getColumnTo();
   tdinfo[rs][cs][0] = "";
   if(re > rs){
    tdinfo[rs][cs][0] += " rowspan='"+(re-rs+1)+"'";
   }
   if(ce>cs){
    tdinfo[rs][cs][0] += " colspan='"+(ce-cs+1)+"'";
   }
   for(int x=rs;x<=re;x++){
    for(int y=cs;y<=ce;y++){
     if(x!=rs || y!=cs){
      tdinfo[x][y] = null;
     }
    }
   }
  }
  float[] width = new float[col];
  int widthsum = 0;
  int max = 0;
  // 列宽
  for(int i=0;i<col;i++){
   width[i] = sheet.getColumnWidth((short) i);
   if(width[i]>=width[max]){
    max = i;
   }
   widthsum += width[i];
  }
  // 最宽的一列不指定宽度
  width[max] = 0;
  // 设置单元格内容
  for(int i=0;i<row;i++){
   _row = sheet.getRow(i);
   for(int j=0;j<col;j++){
    if(tdinfo[i][j] == null){
     continue;
    }
    HSSFCell cell = _row.getCell((short) j);
    if(cell != null){
     HSSFCellStyle s = cell.getCellStyle();
     if(tdinfo[i][j][0] == null){
      tdinfo[i][j][0] = "";
     }
     // 设置单元格的样式
     tdinfo[i][j][0] += " class='"+getCssByStyle(s,workbook,style)+"'";
     // 设置单元格的值
     if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING){
      tdinfo[i][j][1] = cell.getStringCellValue();
     }else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
      tdinfo[i][j][1] = cell.getNumericCellValue()+"";
     }
    }else{
     tdinfo[i][j] = null;
    }
   }
  }
    
  StringBuffer br = new StringBuffer();
  br.append("<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:x='urn:schemas-microsoft-com:office:excel' xmlns='http://www.w3.org/TR/REC-html40'>");
  br.append("<head><meta http-equiv=Content-Type content='text/html; charset=utf-8'><meta name=ProgId content=Excel.Sheet>");
  br.append("<style>");
  Iterator it = style.values().iterator();
  while(it.hasNext()){
   String[] css = (String[])it.next();
   br.append(css[1]);
  }
  br.append("</style></head><body>");
  br.append("<table cellspacing='0' cellpadding='0' style='border-collapse:collapse;'>");
  // 设置单元格的宽度
  for(int i=0;i<col;i++){
   if(i != max){
    br.append("<col width='"+Math.rint(width[i]/widthsum*100)+"%'>");
   }else{
    br.append("<col>");
   }
  }
  for(int i=0;i<row;i++){
   br.append("<tr>");
   for(int j=0;j<col;j++){
    if(tdinfo[i][j] != null){
     if(tdinfo[i][j][0] == null){
      tdinfo[i][j][0] = "";
     }
     if(tdinfo[i][j][1]==null){
      tdinfo[i][j][1] = " ";
     }
     br.append("<td "+tdinfo[i][j][0]+">"+tdinfo[i][j][1]+"</td>");
    }
   }
   br.append("</tr>");
  }
  br.append("</table></body></html>");
  return br.toString();
 }
 
 
 private static String getCssByStyle(HSSFCellStyle s, HSSFWorkbook workbook,Map style) {
  if(style.containsKey(s)){
   String[] css = (String[])style.get(s);
   return css[0];
  }else{
   String[] css = new String[2];
   css[0] = "c"+style.size();
   StringBuffer cssinfo = new StringBuffer();
   // 文字对齐方式
   switch(s.getAlignment()){
    case HSSFCellStyle.ALIGN_CENTER:
     cssinfo.append("text-align:center;");break;
    case HSSFCellStyle.ALIGN_LEFT:
     cssinfo.append("text-align:left;");break;
    case HSSFCellStyle.ALIGN_RIGHT:
     cssinfo.append("text-align:right;");break;
   }
   // 背景色
   cssinfo.append("background-color:"+getColor(s.getFillForegroundColor())+";");
   // 设置边框
   cssinfo.append("border-top:"+s.getBorderTop()+"px solid #000000;");
   cssinfo.append("border-left:"+s.getBorderLeft()+"px solid #000000;");
   cssinfo.append("border-right:"+s.getBorderRight()+"px solid #000000;");
   cssinfo.append("border-bottom:"+s.getBorderBottom()+"px solid #000000;");
   // 设置字体
   HSSFFont font = workbook.getFontAt(s.getFontIndex());
   cssinfo.append("font-size:"+font.getFontHeightInPoints()+"pt;");
   if(HSSFFont.BOLDWEIGHT_BOLD == font.getBoldweight()){
    cssinfo.append("font-weight: bold;");
   }
   cssinfo.append("font-family: "+font.getFontName()+";");
   if(font.getItalic()){
    cssinfo.append("font-style: italic;");
   }
   String fontcolor = getColor(font.getColor());{
    if(fontcolor.trim().length() > 0){
     cssinfo.append("color: "+fontcolor+";");
    }
   }
   css[1] = "."+css[0]+"{"+cssinfo.toString()+"}";
   style.put(s, css);
   return css[0];
  }
 }

 /**
  * 填充明细项
  * @param sheet
  * @param i
  * @param dnum
  * @param m
  */
 private static void fillDetail(HSSFSheet sheet, int start, int dnum, Map m) {
  for(int i=0;i<dnum;i++){
   HSSFRow row = sheet.getRow(start+i);
   for(int j=0;j<=row.getLastCellNum();j++){
    HSSFCell cell = row.getCell((short) j);
    if(cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_STRING && cell.getStringCellValue() != null && cell.getStringCellValue().startsWith("D{")){
     String key = cell.getStringCellValue();
     key = key.replaceAll("(D\\{)|(\\}$)", "");
     //cell.set.setEncoding(HSSFCell.ENCODING_UTF_16);
     cell.setCellValue((String)m.get(key));
    }
   }
  }
  
 }

 /**
  * 填充表头
  * @param sheet
  * @param dstart
  * @param m
  */
 private static void fillHeader(HSSFSheet sheet, int dstart, Map m) {
  for(int i=0;i<dstart;i++){
   HSSFRow row = sheet.getRow(i);
   for(int j=0;j<=row.getLastCellNum();j++){
    HSSFCell cell = row.getCell((short) j);
    if(cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_STRING && cell.getStringCellValue() != null && cell.getStringCellValue().startsWith("H{")){
     String key = cell.getStringCellValue();
     key = key.replaceAll("(T\\{)|(H\\{)|(\\}$)", "");
     //cell.setEncoding(HSSFCell.ENCODING_UTF_16);
     cell.setCellValue((String)m.get(key));
    }
   }
  }
 }

 /**
  * 获取明细项中的被合并的单元格信息
  * @param sheet
  * @param dstart
  * @param dend
  * @return
  */
 private static List getMergeds(HSSFSheet sheet, int dstart, int dend) {
  List mergeds = new ArrayList();
  for(int i=0;i<sheet.getNumMergedRegions();i++){
   Region m = sheet.getMergedRegionAt(i);
   if(dstart<= m.getRowFrom() && m.getRowTo() <= dend){
    mergeds.add(m);
   }
  }
  return mergeds;
 }

 /**
  * 复制行
  * @param sheet
  * @param start 待复制内容起始行
  * @param rows 复制的行数
  * @param to 目标行起始行
  * @param mergeds 待复制行中的合并单元格信息
  */
 public static void RowCopy(HSSFSheet sheet,int start,int rows,int to,List mergeds){
  for(int i=0;i<rows;i++){
   HSSFRow row = sheet.getRow(start+i);
   HSSFRow newrow = sheet.createRow(to+i);
   for(int j=0;j<=row.getLastCellNum();j++){
    HSSFCell cell = row.getCell((short) j);
    HSSFCell newcell = newrow.createCell((short) j);
    if(cell != null){
     switch(cell.getCellType()){
      case HSSFCell.CELL_TYPE_STRING:
       newcell.setCellValue(cell.getStringCellValue());break;
     }
     newcell.setCellStyle(cell.getCellStyle());
    }
   }
  }
  for(int i=0;i<mergeds.size();i++){
   Region m = (Region) mergeds.get(i);
   Region _m = new Region(m.getRowFrom()-start+to,(short)m.getColumnFrom(), m.getRowTo()-start+to,m.getColumnTo());
   sheet.addMergedRegion(_m);
  }
  
 }
 
 public static List getData() throws JDOMException, IOException{
  String xml = "D:\\T.xml";
  FileInputStream fi = new FileInputStream(xml);
  SAXBuilder sb = new SAXBuilder();  
  //Document doc = sb.build(url);
  Document doc = sb.build(fi,"GBK");
  
  
  Element root = doc.getRootElement();
  List l = root.getChildren();
  List data = new ArrayList();
  Map map = null;
  for(int i=0;i<l.size();i++){
   Element node = (Element) l.get(i);
   if("ROW".equalsIgnoreCase(node.getName())){
    List ll = node.getChildren();
    map = new HashMap();
    for(int j=0;j<ll.size();j++){
     Element d = (Element) ll.get(j);
     map.put(d.getName(), d.getText());
    }
    data.add(map);
   }
  }
  return data;
 }
}