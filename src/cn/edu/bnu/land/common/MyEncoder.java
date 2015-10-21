package cn.edu.bnu.land.common;
import java.nio.charset.Charset;


public class MyEncoder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyEncoder.getCodeType("中文");
	}
	public static String getCodeType(String str){
		String type="UNKOWN";
		if(Charset.forName("GB2312").newEncoder().canEncode(str)){
			type="GB2312";
		}else if(Charset.forName("GBK").newEncoder().canEncode(str)){
			type="GBK";
		}else if(Charset.forName("GB18030").newEncoder().canEncode(str)){
			type="GB18030";
		}else if(Charset.forName("UTF-8").newEncoder().canEncode(str)){
			type="UTF-8";
		}else if(Charset.forName("BIG5").newEncoder().canEncode(str)){
			type="BIG5";
		}else if(Charset.forName("ISO-8859-1").newEncoder().canEncode(str)){
			type="ISO-8859-1";
		}else{
			type="UNKOWN";
		}
		System.out.println("code type is:"+type);
		return type;
		
	}

}
