package com.jelly.tankwar.tank;

import java.util.Random;
import java.util.Vector;

import com.jelly.tankwar.bulet.Bullet;
import com.jelly.tankwar.resource.data.EnemyTankData;
import com.jelly.tankwar.war.Obstruction;

public class EnemyTank  extends Tank implements Runnable {
	
	public static final   int TANK_TYPE=Tank.TANK_TYPE_ENEMY;
	public static final int ENEMY_TANK_MAX_BLOOD=9;
    private int blood=9;//  敌方坦克每辆9滴血
    private  Random random=new Random();
    private int speed=3;
    private int  orientation=Tank.TANK_ORIENTATION_SOUTH;
	//private Bullet zd;
    private Vector<Bullet> zdVector=new Vector<Bullet>();
    private Vector<EnemyTank> dtankVector;
    private  boolean threadAlive=true;
    private long sleepInterval=100L;//坦克线程的休眠期 80毫秒
    private  int launchZdRate=65;//发射子弹的几率
    private int zdSpeed=3;
    
    public  void syncData(){
    	 
    	 this.blood=EnemyTankData.blood;
    	 this.orientation=EnemyTankData.orientation;
    	 this.speed=EnemyTankData.speed;
    	 this.sleepInterval=EnemyTankData.sleepInterval;
    	 this.launchZdRate=EnemyTankData.launchZdRate;
    	 this.zdSpeed=EnemyTankData.zdSpeed;
     }
    
    public long getSleepInterval() {
		return sleepInterval;
	}
	public int getLaunchZdRate() {
		return launchZdRate;
	}
	public void setLaunchZdRate(int launchZdRate) {
		this.launchZdRate = launchZdRate;
	}
	public void setSleepInterval(long sleepInterval) {
		this.sleepInterval = sleepInterval;
	}
	public void reduceBlood(){
    	this.blood-=1;
    }
    public void addBlood(){
    	this.blood+=1;
    }
	public boolean isThreadAlive() {
		return threadAlive;
	}

	public void setThreadAlive(boolean threadAlive) {
		this.threadAlive = threadAlive;
	}
   

	public Vector<EnemyTank> getDtankVector() {
		return dtankVector;
	}
	public void setDtankVector(Vector<EnemyTank> dtankVector) {
		this.dtankVector = dtankVector;
	}
	public Vector<Bullet> getZdVector() {
		return zdVector;
	}

	public void setZdVector(Vector<Bullet> zdVector) {
		this.zdVector = zdVector;
	}

	public int getSpeed() {
		return speed;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getBlood() {
		return this.blood;
	}

	public void setBlood(int blood) {
		this.blood = blood;
	}
	public EnemyTank() {
		super();
		 
	}
	public EnemyTank(int x, int y) {
		super(x, y);
	 
	}
	@Override
	public void run() {
		while(blood>0){ 
			try {
				this.orientation=random.nextInt(4);
				switch(this.orientation){
				case Tank.TANK_ORIENTATION_NORTH:
						for(int i=30;i-->0;){
							if(this.y>0&&!isCollision()&&canNorthMove){
								  Thread.sleep(sleepInterval);
								   this.y-=this.speed;//上移动
								   this.canNorthMove=true;
						    	   this.canWestMove=true;
						    	   this.canSouthMove=true;
						    	   this.canEastMove=true;
								for(int j=0;j<obstVector.size();j++){
									Obstruction obst=obstVector.get(j);
									if(obst.getBlood()>0){
										  if(this.getX()>=obst.getX()&&this.getX()<=obst.getX()+20&&this.getY()>=obst.getY()&&this.getY()<=obst.getY()+20){
									    	   this.canNorthMove=false;
									    	   break;
									      }
									      else if(this.getX()+20>=obst.getX()&&this.getX()+20<=obst.getX()+20&&this.getY()>=obst.getY()&&this.getY()<=obst.getY()+20){
									    	  this.canNorthMove=false;
									    	   break;
									      } 
									}
								} 
								//Thread.sleep(70);
							}
						}
					break;
				case Tank.TANK_ORIENTATION_WEST:
						for(int i=30;i-->0;){
							if(this.x>0&&!isCollision()&&canWestMove){
								Thread.sleep(sleepInterval);
								   this.x-=this.speed;//左移动
							       this.canNorthMove=true;
						    	   this.canWestMove=true;
						    	   this.canSouthMove=true;
						    	   this.canEastMove=true;
						    		for(int j=0;j<obstVector.size();j++){
										Obstruction obst=obstVector.get(j);
										if(obst.getBlood()>0){
										if(this.getX()>=obst.getX()&&this.getX()<=obst.getX()+20&&this.getY()>=obst.getY()&&this.getY()<=obst.getY()+20){
									    	   this.canWestMove=false;
									    	  break;
									      }
										 else if(this.getX()>=obst.getX()&&this.getX()<=obst.getX()+20&&this.getY()+20>=obst.getY()&&this.getY()+20<=obst.getY()+20){
									    	   this.canWestMove=false;
									    	  break;
									      }
									}
								}
								//Thread.sleep(70);
                               }  
							}
					break;
				case Tank.TANK_ORIENTATION_SOUTH:
						for(int i=30;i-->0;){
							if(this.y<470&&!isCollision()&&canSouthMove){
								Thread.sleep(sleepInterval);
								this.y+=this.speed;//上移动
								   this.canNorthMove=true;
						    	   this.canWestMove=true;
						    	   this.canSouthMove=true;
						    	   this.canEastMove=true;
						    		for(int j=0;j<obstVector.size();j++){
										Obstruction obst=obstVector.get(j);
										if(obst.getBlood()>0){
										if(this.getX()>=obst.getX()&&this.getX()<=obst.getX()+20&&this.getY()+30>=obst.getY()&&this.getY()+30<=obst.getY()+20){
									    	   this.canSouthMove=false;
									    	 break;
									      }
										else if(this.getX()+20>=obst.getX()&&this.getX()+20<=obst.getX()+20&&this.getY()+30>=obst.getY()&&this.getY()+30<=obst.getY()+20){
									    	   this.canSouthMove=false;
									    	 break;
									      }
									}
								}
								//Thread.sleep(70);
							}
						}
					break;
				case Tank.TANK_ORIENTATION_EAST:
						for(int i=30;i-->0;){
							if(this.x<470&&!isCollision()&&canEastMove){
								 Thread.sleep(sleepInterval);
								  this.x+=this.speed;//右移动
								  this.canNorthMove=true;
						    	   this.canWestMove=true;
						    	   this.canSouthMove=true;
						    	   this.canEastMove=true;
						    		for(int j=0;j<obstVector.size();j++){
										Obstruction obst=obstVector.get(j);
										if(obst.getBlood()>0){
										 if(this.getX()+30>=obst.getX()&&this.getX()+30<=obst.getX()+20&&this.getY()>=obst.getY()&&this.getY()<=obst.getY()+20){
									    	   this.canEastMove=false;
									    	 break;
									      }
										 else if(this.getX()+30>=obst.getX()&&this.getX()+30<=obst.getX()+20&&this.getY()+20>=obst.getY()&&this.getY()+20<=obst.getY()+20){
									    	   this.canEastMove=false;
									    	break;
									      }
									}
								}
							 
								//Thread.sleep(70);
							}
						}
					break;
				}
				
				int n=random.nextInt(200);
				if(n>=0&&n<launchZdRate){  //有65%的几率发出子弹
					Bullet	zd=null;
					if(this.zdVector.size()<6){//敌方的子弹数量同时间最多不超过6颗
							 switch(this.orientation){
								case  Tank.TANK_ORIENTATION_NORTH: 
									 	zd=new Bullet(x+10,y,Bullet.BULLET_ORIENTATION_NORTH,zdSpeed);
										zdVector.add(zd);
									break;
								case  Tank.TANK_ORIENTATION_WEST:
									 	zd=new Bullet(x,y+10,Bullet.BULLET_ORIENTATION_WEST,zdSpeed);
										zdVector.add(zd);
								 
									break;
								case  Tank.TANK_ORIENTATION_SOUTH:
										zd=new Bullet(x+10,y+30,Bullet.BULLET_ORIENTATION_SOUTH,zdSpeed);
										zdVector.add(zd);
									break;
								case  Tank.TANK_ORIENTATION_EAST:
										zd=new Bullet(x+30,y+10,Bullet.BULLET_ORIENTATION_EAST,zdSpeed);
										zdVector.add(zd);
									break;
								}
								 new Thread(zd).start();
						 }
					}
		 
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	/**
	 * 计算坦克之间是否发生了碰撞
	 * @return
	 */
	private  boolean  isCollision(){//敌方坦克是否发生了碰撞
     switch(this.orientation){
		case Tank.TANK_ORIENTATION_NORTH:
			for(int i=0;i<dtankVector.size();i++)
			{
			  EnemyTank dt=dtankVector.get(i);
			  if(dt!=this){
					if(dt.orientation==0||dt.orientation==2)
					{
						if(this.x>=dt.x && this.x<=dt.x+20 && this.y>=dt.y && this.y<=dt.y+30)
						{
							return true;
						}
						else if(this.x+20>=dt.x && this.x+20<=dt.x+20 && this.y>=dt.y && this.y<=dt.y+30)
						{
							return true;
						}
					 }
					else if(dt.orientation==3||dt.orientation==1)
					{
						if(this.x>=dt.x && this.x<=dt.x+30 && this.y>=dt.y && this.y<=dt.y+20)
						{
							return true;
						}
						else if(this.x+20>=dt.x && this.x+20<=dt.x+30 && this.y>=dt.y && this.y<=dt.y+20)
						{
							return true;
						} 
					}
				}
			}
			break;
			 
		case Tank.TANK_ORIENTATION_WEST:
			for(int i=0;i<dtankVector.size();i++)
			{
				EnemyTank dt=dtankVector.get(i);
				if(dt!=this)
				{
				    if(dt.orientation==0||dt.orientation==2)
					{
						if(this.x>=dt.x&&this.x<=dt.x+20&&this.y>=dt.y&&this.y<=dt.y+30)
						{
							return true;
						}
						//下一点
						else if(this.x>=dt.x&&this.x<=dt.x+20&&this.y+20>=dt.y&&this.y+20<=dt.y+30)
						{
							return true;
						}
					}
				    else if(dt.orientation==3||dt.orientation==1)
					{
						if(this.x>=dt.x&&this.x<=dt.x+30&&this.y>=dt.y&&this.y<=dt.y+20)
						{
							return true;
						}
						if(this.x>=dt.x&&this.x<=dt.x+30&&this.y+20>=dt.y&&this.y+20<=dt.y+20)
						{
							return true;
						}
					}
				}
			}
			break;
		case Tank.TANK_ORIENTATION_SOUTH:  
			for(int i=0;i<dtankVector.size();i++)
			{
				EnemyTank dt=dtankVector.get(i);
				if(dt!=this)
				{
					if(dt.orientation==0||dt.orientation==2)
					{
						if(this.x>=dt.x&&this.x<=dt.x+20&&this.y+30>=dt.y&&this.y+30<=dt.y+30)
						{
							return true;
						}
						else if(this.x+20>=dt.x&&this.x+20<=dt.x+20&&this.y+30>=dt.y&&this.y+30<=dt.y+30)
						{
							return true;
						}
					}
					else if(dt.orientation==3||dt.orientation==1)
					{
						if(this.x>=dt.x&&this.x<=dt.x+30&&this.y+30>=dt.y&&this.y+30<=dt.y+20)
						{
							return true;
						}
						
						else if(this.x+20>=dt.x&&this.x+20<=dt.x+30&&this.y+30>=dt.y&&this.y+30<=dt.y+20)
						{
							return true;
						}
					}
				}
			}
			break;
		case Tank.TANK_ORIENTATION_EAST:
			for(int i=0;i<dtankVector.size();i++)
			{
				EnemyTank dt=dtankVector.get(i);
				if(dt!=this)
				{
					if(dt.orientation==0||dt.orientation==2)
					{
						if(this.x+30>=dt.x && this.x+30<=dt.x+20 && this.y>=dt.y && this.y<=dt.y+30)
						{
							return true;
						}
						//下点 
						else if(this.x+30>=dt.x && this.x+30<=dt.x+20 && this.y+20>=dt.y && this.y+20<=dt.y+30)
						{
							return true;
						}
					}
					else if(dt.orientation==3||dt.orientation==1)
					{
						if(this.x+30>=dt.x && this.x+30<=dt.x+30 && this.y>=dt.y && this.y<=dt.y+20)
						{
							return true;
						}
						else if(this.x+30>=dt.x && this.x+30<=dt.x+30 && this.y+20>=dt.y && this.y+20<=dt.y+20)
						{
							return true;
						}
					}
				}
			}
		} 
		return false;
	}

	public int getZdSpeed() {
		return zdSpeed;
	}

	public void setZdSpeed(int zdSpeed) {
		this.zdSpeed = zdSpeed;
	}
	
	
}

