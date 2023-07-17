package kr.co.nninc.ncms.common;

import javax.servlet.http.HttpServletRequest;

/**
 * 페이징 링크문자열 생성 클래스
 * 
 * @author 나눔
 * @since 2018.4.17
 * @version 1.0
 */
public class Paging {
	public int page = 1;			//현재페이지
	public int block = 10;			//페이지링크갯수
	public int totalpage = 0;		//총패이지갯수
	public String pageKeyword = "page";	//페이지파라미터명
	public String url = "";			//url : 현재경로에서 바뀌지 않는다면 입력하지 않아도 됨
	public String atagOption = "";	//생성될 atag에 추가문자열 예)style='' 또는 class='aaa' 등...
	private int temp = 0;
	
	public String execute( String querystring ){
		if(this.page < 1){ this.page = 1; }
		
		String ret_value = "";
		this.temp = (int)Math.ceil( (this.page-1)/ this.block ) * this.block + 1;	//'전체덩어리갯수

		if(this.temp == 1){
			//echo "[이전 ".$this->block."개]";
		}else{
			int num = this.temp-this.block;
			String href = this.url+"?"+pageKeyword+"="+num;
			if(!"".equals(querystring)){ href += "&amp;"+querystring; }
			String tmpstr = "<a href='"+href+"' title='이전' data-page='"+num+"' "+atagOption+">이전</a>&nbsp;";
			ret_value += tmpstr;
		}

		int v_loop = 1;

		while(v_loop <= this.block && this.temp <= this.totalpage){
			if(this.temp == this.page){

				ret_value += "<strong>"+this.temp+"</strong>&nbsp;";

			}else{
				String href = this.url+"?"+pageKeyword+"="+this.temp;
				if(!"".equals( querystring )){ href += "&amp;"+querystring; }
				String tmpstr = "<a href='"+href+"' title='"+this.temp+"페이지로' data-page='"+this.temp+"' "+atagOption+">"+this.temp+"</a>&nbsp;";
				ret_value += tmpstr;
			}
			this.temp = this.temp + 1;
			v_loop = v_loop + 1;
		}

		if(this.temp > this.totalpage){
			//echo "[다음 ".$this->block."개]";
		}else{
			String href = this.url+"?"+pageKeyword+"="+this.temp;
			if(!"".equals( querystring )){ href += "&amp;"+querystring; }
			String tmpstr = "&nbsp;<a href='"+href+"' title='다음' data-page='"+this.temp+"' "+atagOption+">다음</a>";
			ret_value += tmpstr;
		}
		return ret_value;
	}
	
	public String setQueryString(HttpServletRequest request, String fields){
		fields = fields.replaceAll(" ", "");
		String[] fields_arr = fields.split(",");
		String query_string = "";
		for(int i=0;i<=fields_arr.length-1;i++){
			String value = Func.nvl( request.getParameter( fields_arr[i] ) );
			if(!"".equals(value)){
				if(!"".equals(query_string)){query_string += "&";}
				query_string += fields_arr[i]+"="+Func.urlEncode( request.getParameter( fields_arr[i] ) );
			}
		}
		return query_string;
	}

}
