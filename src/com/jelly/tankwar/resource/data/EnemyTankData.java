package com.jelly.tankwar.resource.data;

import com.jelly.tankwar.tank.Tank;

public class EnemyTankData {
	public  static int   sceneNumber=1;//关卡数  第几关
    public  static int   speed=3;//移动速度 
    public  static long sleepInterval=100L;//休眠期
    public  static int launchZdRate=65;//子弹发射速率
	public static int blood=9;//血量
	public static int orientation=Tank.TANK_ORIENTATION_SOUTH;//坦克初始化朝向
	public static int zdSpeed=2;
    public static long  minSleepInterval=50L;//最小休眠期
    public static int maxZdSpeed=9;//最大子弹速度 9
    public  static int maxSpeed=9;//最大速度为9
    public static int maxLaunchZdRate=100;//最大的子弹发射几率
    public static int maxBlood=9;//最大血量
    public static void initData(){
    	   sceneNumber=1;
           blood=9;
           orientation=Tank.TANK_ORIENTATION_SOUTH;
           speed=3;           
           sleepInterval=100L; 
          launchZdRate=65;
          zdSpeed=3;
    }
	public static void passScene() {//通关后的效果
		  if(sceneNumber%3==0){
			  if(zdSpeed<maxZdSpeed){
				  zdSpeed++;  
			  }
		  }
		  if(sceneNumber%4==0){
			  if(speed<maxSpeed){
				  speed++;// 
			  }
		  }
		  if(sleepInterval>minSleepInterval){
			  sleepInterval-=5;//坦克休眠期减5
		  }
		  if(launchZdRate<maxLaunchZdRate){
			  launchZdRate+=5;//发射子弹的几率 增加5
		  }
	} 
     
}

