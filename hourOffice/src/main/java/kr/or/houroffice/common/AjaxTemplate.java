package kr.or.houroffice.common;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class AjaxTemplate {
	
	public static void resultTF(int result, HttpServletResponse response) throws IOException{
		if(result>0){
			response.getWriter().print(true);
		}else{
			response.getWriter().print(false);
		}
	}

}
