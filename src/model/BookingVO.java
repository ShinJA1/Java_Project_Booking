package model;

import java.util.Date;

public class BookingVO {
	public final String ClassName = "Book";
	
	private int b_num;   //예약번호
	private String b_name;   //예약자명 
	private String b_phone;   //예약자 연락처
	private Date b_date;   //예약일자
	private Date b_time;   //예약시간
	private int b_rt_num;   //식당번호
	private int b_inwon;   //예약 인원수
	public int getB_num() {
		return b_num;
	}
	public void setB_num(int b_num) {
		this.b_num = b_num;
	}
	public String getB_name() {
		return b_name;
	}
	public void setB_name(String b_name) {
		this.b_name = b_name;
	}
	public String getB_phone() {
		return b_phone;
	}
	public void setB_phone(String b_phone) {
		this.b_phone = b_phone;
	}
	public Date getB_date() {
		return b_date;
	}
	public void setB_date(Date b_date) {
		this.b_date = b_date;
	}
	public Date getB_time() {
		return b_time;
	}
	public void setB_time(Date b_time) {
		this.b_time = b_time;
	}
	public int getB_rt_num() {
		return b_rt_num;
	}
	public void setB_rt_num(int b_rt_num) {
		this.b_rt_num = b_rt_num;
	}
	public int getB_inwon() {
		return b_inwon;
	}
	public void setB_inwon(int b_inwon) {
		this.b_inwon = b_inwon;
	}
	public String getClassName() {
		return ClassName;
	}
	
	

}
