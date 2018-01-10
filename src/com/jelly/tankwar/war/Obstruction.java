package com.jelly.tankwar.war;

 

import com.jelly.tankwar.bulet.Bullet;
 

/**
 * 障碍物  线程类
 *  20*20
 * @author jelly
 * 
 */
public class Obstruction implements Runnable{
	private int x;
	private int y;
	private int materical;//障碍物 材料类型   0土砖   1钢砖 
    //private boolean threadAlive=true;
	//private boolean  bitten=false;//false 没有击中 ， true 击中
	//private boolean isAlive=true;// 存活着
	private int blood=3;//障碍物有3滴血 
	@Override
	public void run() {
		 while(blood>0){
			try {
			   Thread.sleep(700);
			} catch (Exception e) {
				 e.printStackTrace();
			}
		 }
	}
	/**
	 * 判断子弹是否击中了我(障碍物)
	 * @param zd
	 * @return
	 */
    public boolean isBitten(Bullet zd){
    	if(zd.isThreadAlive()&&zd.getX()>=this.x&&zd.getX()<=this.getX()+20
    			&&zd.getY()>=this.getY()&&zd.getY()<=this.getY()+20){
    		return true;
    	}else{
    		return false;
    	}
    }
   
	 
	//减少一滴血
	public void reduceBlood(){
		this.blood-=1;
	}
 
	public int getBlood() {
		return blood;
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

	public int getMaterical() {
		return materical;
	}

	public void setMaterical(int materical) {
		this.materical = materical;
	}

	public Obstruction() {
		super();
	}

	public Obstruction(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public Obstruction(int x, int y, int materical) {
		super();
		this.x = x;
		this.y = y;
		this.materical = materical;//材料 0 土 1 钢铁
	}
	 
 
   
 
}
