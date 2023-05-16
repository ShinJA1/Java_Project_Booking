package model;

import java.util.Date;

public class RTVO {
public final String ClassName = "RT";
	
	private int rt_num;   //식당번호
	private String rt_name;  //식당이름
	private String rt_type;  //식당 카테고리
	private int rt_inwon;  //수용인원
	private Date rt_start;  //오픈시간
	private Date rt_end;  //마감시간
	public int getRt_num() {
		return rt_num;
	}
	public void setRt_num(int rt_num) {
		this.rt_num = rt_num;
	}
	public String getRt_name() {
		return rt_name;
	}
	public void setRt_name(String rt_name) {
		this.rt_name = rt_name;
	}
	public String getRt_type() {
		return rt_type;
	}
	public void setRt_type(String rt_type) {
		this.rt_type = rt_type;
	}
	public int getRt_inwon() {
		return rt_inwon;
	}
	public void setRt_inwon(int rt_inwon) {
		this.rt_inwon = rt_inwon;
	}
	public Date getRt_start() {
		return rt_start;
	}
	public void setRt_start(Date rt_start) {
		this.rt_start = rt_start;
	}
	public Date getRt_end() {
		return rt_end;
	}
	public void setRt_end(Date rt_end) {
		this.rt_end = rt_end;
	}
	public String getClassName() {
		return ClassName;
	}
	
	
}
