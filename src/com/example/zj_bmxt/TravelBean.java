package com.example.zj_bmxt;

import android.graphics.Bitmap;

public class TravelBean {
	
	public String id,name,type,introduce,phone,play,open,ticket,img1,img2,img3;
	int sup_count;
	static Bitmap bitmap1,bitmap2,bitmap3;
	/*set方法*/
	public void setId(String id){
		this.id=id;
	}	
	public void setName(String name){
		this.name=name;
	}	
	public void setType(String type){
		this.type=type;
	}	
	public void setIntroduce(String introduce){
		this.introduce=introduce;
	}	
	public void setPhone(String phone){
		this.phone=phone;
	}	
	public void setPlay(String play){
		this.play=play;
	}	
	public void setOpen(String open){
		this.open=open;
	}	
	public void setTicket(String ticket){
		this.ticket=ticket;
	}	
	public void setSub_count(int sup_count){
		this.sup_count=sup_count;
	}	
	public void setImg1(String img1){
		this.img1=img1;
	}	
	public void setImg2(String img2){
		this.img2=img2;
	}	
	public void setImg3(String img3){
		this.img3=img3;
	}
	
	/*get方法*/
	public String getType(){
		return type;
	}	
	public String getIntroduce(){
		return introduce;
	}
	public String getId(){
		return id;
	}	
	public String getPhone(){
		return phone;
	}
	public String getName(){
		return name;
	}
	public String getTicket(){
		return ticket;
	}
	public String getPlay(){
		return play;
	}
	public String getOpen(){
		return open;
	}
	public String getImg1(){
		return img1;
	}
	public String getImg2(){
		return img2;
	}
	public String getImg3(){
		return img3;
	}
	public int getSub_count(){
		return sup_count;
	}
}
