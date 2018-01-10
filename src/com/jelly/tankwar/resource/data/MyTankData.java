package com.jelly.tankwar.resource.data;
/**
 * 游戏用户数据
 * @author jelly
 *
 */
public class MyTankData {
    public static int totalScore=0;//得的分数  
    public static int sceneNumber=1;//关卡数  表示第几关
    public static int currBlood=3;//用户坦克的当前血量
    public static int maxBlood=9;//用户坦克的最大血量
	public static int zdSpeed=3;//子弹移动速度
	public static int zdMaxSpeed=9;
	public static int speed=3;//坦克移动速度
	public static int maxSpeed=9;//坦克移动
    public static void initData(){
    	 speed=3;//移动速度
    	 zdSpeed=3;//子弹移动速度
    	totalScore=0;//得的分数  
    	sceneNumber=1;//关卡数  表示第几关
    	currBlood=3;//用户坦克的当前血量
    	maxBlood=9;//最大血量
    	zdMaxSpeed=9;//最大速度
    	maxSpeed=9;//子弹移动最大速度
    	
    }
}
