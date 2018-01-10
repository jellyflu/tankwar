package com.jelly.tankwar.effects;
/**
 * 击中效果
 * @author jelly
 *
 */
public class ZJeffect {
    private int x;
    private int y;
    private String imgurl=null;
    private int survival=7; 
    
	public int getSurvival() {
		return survival;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public ZJeffect(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public ZJeffect(int x, int y, String imgurl) {
		super();
		this.x = x;
		this.y = y;
		this.imgurl = imgurl;
	}
	public ZJeffect() {
		super();
	
	}
	public void surReduce(){
		survival--;
	}
	 
}



