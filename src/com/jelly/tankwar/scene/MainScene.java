package com.jelly.tankwar.scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jelly.tankwar.bulet.Bullet;
 
 
import com.jelly.tankwar.effects.ZJeffect;
import com.jelly.tankwar.resource.data.EnemyTankData;
import com.jelly.tankwar.resource.data.MyTankData;
import com.jelly.tankwar.tank.EnemyTank;
import com.jelly.tankwar.tank.MyTank;
import com.jelly.tankwar.tank.Tank;
import com.jelly.tankwar.war.Element;
import com.jelly.tankwar.war.Obstruction;

/**
 * 
 * @author jelly
 * 线程类  游戏主场景
 */
public class MainScene extends JPanel implements KeyListener,Runnable {
	private static final long serialVersionUID = 1L;
	private MyTank mt;              //我方坦克
	private boolean threadAlive=true;
	private int  enemyTankCount=9;//初始的 敌人坦克数量
    private  Vector<EnemyTank>  enemyTankVector;//敌人坦克的集合
    private Vector<ZJeffect> zjeffectVector;//   击中效果集合
    private String zjimg="/com/jelly/tankwar/resource/images/jizhong.png";
    private Vector<Element> eleVector=new Vector<Element>();
    private Random random=new Random();
    private  Element  eleBlood;//血气 元素
	private  Element  eleSpirit;//精神  元素
	private  Element  eleWisdom;//智力  元素
   
     
    private Vector<Obstruction> obstVector=new Vector<Obstruction>();//障碍物集合
    private  int obstcount=38;//障碍物的数量   覆盖率10%
    
	public MainScene() {//构造函数 初始化
	    super();
	    if(enemyTankVector==null){
	    	enemyTankVector=new Vector<EnemyTank>();
	    }
	    zjeffectVector=new Vector<ZJeffect>();
	    
	    if(mt==null){
	         mt = new MyTank(180, 470);
		     mt.setObstVector(obstVector);
	    }
    	 for(int i=1;i<=enemyTankCount;i++){ //初始化敌人的坦克 及子弹
			  EnemyTank dtank= null;
			  if(i<=3){
				  dtank=new EnemyTank(i*160-80,5);
				  dtank.syncData();
			  }else{
				  dtank=new EnemyTank((i-3)*80-40,10);
				  dtank.syncData();
			  }
			   dtank.setDtankVector(enemyTankVector);
			   dtank.setObstVector(obstVector);
			   enemyTankVector.add(dtank);
			  new Thread(dtank).start();//敌人坦克线程启动
			  Bullet dzd=new Bullet(dtank.getX()+10,dtank.getY()+30,Tank.TANK_ORIENTATION_SOUTH);//敌方子弹
			  dtank.getZdVector().add(dzd);
			  new Thread(dzd).start();//敌人发子弹的线程启动
			 
		}
		/*    
	     * 设障碍物出现的坐标为(x,y)
	     * x∈{f(m)|f(m)=m*20+50},m∈{0,1,2,...,21}  随机数 r∈[0,1)  
	     * y∈{f(n)|f(n)=n*20+50},n ∈{0,1,2,...,20}   
	     */
		 for(int i=1;i<=obstcount;i++){
			int x=random.nextInt(21)*20+50;
			int y=random.nextInt(20)*20+50;
			int materical=random.nextInt(2);
			 Obstruction obst=new Obstruction(x,y,materical);
			 obstVector.add(obst);
			 new  Thread(obst).start();
		 }
 
		new Thread(this).start();//面板本身100毫秒重绘一次   的线程启动
	}
    
	/**
	 * paint 绘制 
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, 500, 500);
	      
		//画我坦克
	     if(mt!=null&&MyTankData.currBlood>0){
			this.drawTank(mt.getX(), mt.getY(), g,mt.getTankType(),mt.getOrientation());
		 }
	     //画我方子弹
	     if(mt!=null){
	    	 for(int i=0;i<mt.getZdVector().size();i++){//画出我方坦克的所有子弹
		        	Bullet zd=mt.getZdVector().get(i);
		        	if(zd!=null&&zd.isThreadAlive()){
		        		g.setColor(Color.WHITE);
		        		g.fill3DRect(zd.getX()-1, zd.getY()-1, 3, 3, false);
		        	}
		        	 //如果我方子弹击中敌方则
		        	if(!zd.isThreadAlive()){//vector中子弹对象移除一个
		        		mt.getZdVector().remove(zd);
		        	}
		       }
	     }
	     //画敌方坦克
        for(int i=0;i<enemyTankVector.size();i++){
        	  if(enemyTankVector.get(i).getBlood()>0){
        		  this.drawTank(enemyTankVector.get(i).getX(), 
        	        	   enemyTankVector.get(i).getY(), g, Tank.TANK_TYPE_ENEMY, enemyTankVector.get(i).getOrientation());
        	  }
        }
	    //画敌方子弹
        for(int i=0;i<enemyTankVector.size();i++){
        	EnemyTank etank= enemyTankVector.get(i);//敌方的每一辆坦克
     	     for(int j=0;j<etank.getZdVector().size();j++){  //循环遍历敌方的每一个坦克，画出敌方每一辆坦克的 每一颗子弹
     		   Bullet zd=etank.getZdVector().get(j);//d子弹
     		   if(zd!=null&&zd.isThreadAlive()){
            		g.setColor(Color.WHITE);
            		g.fill3DRect(zd.getX()-1, zd.getY()-1, 3, 3, false);
            	  }
     		   if(!zd.isThreadAlive()){
     			   etank.getZdVector().remove(zd);
     		   }
     	   }
        }
        
        
        //画障碍物
        for(int i=0;i<obstcount;i++){
             Obstruction obst=obstVector.get(i);
             if(obst.getBlood()>0){//只有当障碍物血量大于0时 才画出障碍物
            	 this.drawObst(obst.getX(), obst.getY(), obst.getMaterical(), g);
             }
        }
        //画元素球   血气   精神   智慧
        for(int i=0;i<eleVector.size();i++){
        	 Element ele=eleVector.get(i);
        	if(ele.isThreadAlive()){
              drawElement(ele.getX(), ele.getY(), 10, 10, g, ele.getType());
        	}
        }
        
        
        //画击中开花效果
        for(int i=0;i<zjeffectVector.size();i++){
			ZJeffect zjeffect=zjeffectVector.get(i);
			if(zjeffect.getSurvival()>5){
				Image img=Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(zjeffect.getImgurl()));
				g.drawImage(img,zjeffect.getX(), zjeffect.getY(),15,15, this);
			}else if(zjeffect.getSurvival()>3){
				Image img=Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(zjeffect.getImgurl()));
				g.drawImage(img,zjeffect.getX(), zjeffect.getY(),20,20, this);
			}else if(zjeffect.getSurvival()>0){
				Image img=Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(zjeffect.getImgurl()));
				g.drawImage(img,zjeffect.getX(), zjeffect.getY(),30,30, this);
			}
			zjeffect.surReduce();
			if(zjeffect.getSurvival()==0){
				zjeffectVector.remove(zjeffect);
				zjeffect=null;
			}
		}
        
        
          //画每辆坦克的血量
        for(int i=0;i<12;i++){
        	if(i<9){
        		drawTank(505,i*40+15,g,Tank.TANK_TYPE_ENEMY,Tank.TANK_ORIENTATION_NORTH);
        		 int  currentsize= enemyTankVector.get(i).getBlood();        	
        		 drawBlock(535,i*40+15+5 , 10, 20, 9, currentsize, g, Color.RED);
        	}else if(i==9){
        		drawTank(505,i*40+15,g,Tank.TANK_TYPE_US,Tank.TANK_ORIENTATION_NORTH);
        		drawBlock(535,i*40+15+5, 10, 20, MyTankData.maxBlood, MyTankData.currBlood, g, Color.RED);
        	}else if(i==10){
        		drawBlock(535, 9*40+15+5+25, 10, 20, MyTankData.maxSpeed, MyTankData.speed, g, Color.blue);
        	}else if(i==11){
        		drawBlock(535, 9*40+15+5+25+25, 10, 20, MyTankData.zdMaxSpeed, MyTankData.zdSpeed, g, Color.GREEN);
        	}
        }
        //画关数   得分数
        g.setColor(Color.red);
        g.setFont(new Font("华文行楷",Font.BOLD,20));
        g.drawString("第"+MyTankData.sceneNumber+"关",505, 470);
        g.setColor(Color.MAGENTA);
        g.drawString("得分："+MyTankData.totalScore,505, 490);
        
      //判断是否产生（初始化）元素球 ， 有一定的几率产生元素球
		 int n= random.nextInt(3300);
		 if(n==33){
			 eleBlood=new Element();
			 eleBlood.setType(Element.TYPE_BLOOD);
			 eleBlood.setX(random.nextInt(491));
			 eleBlood.setY(random.nextInt(491));
			 this.eleVector.add(eleBlood);
			  new Thread(eleBlood).start();
		 }else if(n==66){
			 eleSpirit=new Element();
			 eleSpirit.setType(Element.TYPE_SPIRIT);
			 eleSpirit.setX(random.nextInt(491));
			 eleSpirit.setY(random.nextInt(491));
			 this.eleVector.add(eleSpirit);
			 new Thread(eleSpirit).start();
		 }else if(n==99){
			 eleWisdom=new Element();
			 eleWisdom.setType(Element.TYPE_WISDOM);
			 eleWisdom.setX(random.nextInt(491));
			 eleWisdom.setY(random.nextInt(491));
			 this.eleVector.add(eleWisdom);
			 new Thread(eleWisdom).start();
		 }
        
         //判断通关， 进入切换关卡界面
        if(enemyTankVector!=null&&enemyTankVector.size()>0){
       	 if(hasDestroyAll(enemyTankVector)){
       		    MyTankData.sceneNumber++;//关卡数加1
       		    EnemyTankData.sceneNumber++;//关卡数加1
       		    EnemyTankData.passScene();
         	// 主场景对象清除    并从窗体中移除主场景   
 		     if(this.isThreadAlive()){
 		    	  this.setThreadAlive(false);//结束本次主场景线程
 		      }
 		     JFrame f2=FrameAndScene.getFrame(); //将主场景面板从窗体中移除
 		     if(f2!=null){
 		    	f2.remove(this);
 		     }
 		     
 		  	// 新切换关卡对象创建,并将新切换关卡对象添加到窗体中.  保存新创建的切换关卡对象
       		 SwitchScene  newSwScene=new SwitchScene(0, 0, 500, 500);
       		 FrameAndScene.setSwScene(newSwScene);
       		 JFrame f=FrameAndScene.getFrame();
       		
       		 f.add(newSwScene); 
       		 new Thread(newSwScene).start();
       		 f.setVisible(true);
    		 f.setResizable(false);
    		 
       		 newSwScene.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					
					/*
					 * 点击时：   将切换关卡对象清除，并从窗体中移除切换关卡对象。
					 *          创建新的主场景对象，并将主场景对象添加到窗体中.  保存新创建的主场景对象
					 */
				   SwitchScene swsc=FrameAndScene.getSwScene();
					if(swsc!=null ){
						  if(swsc.isThreadAlive()){
							  swsc.setThreadAlive(false);
						  }
						 JFrame f= FrameAndScene.getFrame();
						 f.remove(swsc);
						 swsc=null; 
						 FrameAndScene.setSwScene(null);
					}
					MainScene nextMainScene= new MainScene();//创建新的主场景
				    nextMainScene.setSize(500, 500);
					FrameAndScene.setMainScene(nextMainScene);//保存起来
					JFrame f=FrameAndScene.getFrame();
					f.add(nextMainScene); 
					   f.addKeyListener(nextMainScene);//我方坦克上下左右 发射子弹 监听 
					new Thread(nextMainScene).start();
					f.setVisible(true);
					f.setResizable(false);
				}
       			 
		     });
       	 }
       }  
       //判断我方坦克是否死亡，进入结束游戏界面
        if(MyTankData.currBlood<=0){
        	 if(this.isThreadAlive()){
		    	  this.setThreadAlive(false);//结束本次主场景线程
		      }
		     JFrame f2=FrameAndScene.getFrame(); //将主场景面板从窗体中移除
		     if(f2!=null){
		    	f2.remove(this);
		     }
        	  
		     EndScene  endScene=new EndScene(0, 0, 500, 500);
       		 FrameAndScene.setEndScene(endScene);
       		 JFrame f=FrameAndScene.getFrame();
       		 f.add(endScene); 
       		   new Thread(endScene).start();
       		   
       		    
	       	
       		 f.setVisible(true);
    		 f.setResizable(false);
    		 endScene.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					System.exit(0);
				}
		         
			   
		     });
		   
        }
	}
	
 
	/**
	 * 画出坦克
	 * @param x
	 * @param y
	 * @param g
	 * @param type
	 * @param orientation
	 */
	public void drawTank(int x, int y, Graphics g,int type, int orientation ) {
		switch (type) {
		case Tank.TANK_TYPE_US:
			g.setColor(Color.GREEN);
			break;
		case Tank.TANK_TYPE_ENEMY:
			g.setColor(Color.YELLOW);
			break;
		}
		switch (orientation) {//坦克朝向
		case Tank.TANK_ORIENTATION_NORTH: //北
            g.fill3DRect(x, y, 5, 30, false);
            g.fill3DRect(x+15, y, 5, 30, false);
            g.fill3DRect(x+5, y+5, 10, 20, false);
            g.fillOval(x+5, y+10, 10, 10);
            g.drawLine(x+10, y+15, x+10, y-3);
            
			break;
		case Tank.TANK_ORIENTATION_WEST://西
            g.fill3DRect(x, y, 30, 5, false);
            g.fill3DRect(x, y+15, 30, 5, false);
            g.fill3DRect(x+5, y+5, 20, 10, false);
            g.fillOval(x+10, y+5, 10, 10);
            g.drawLine(x+15,y+10,x-3,y+10);
            
			break;
		case Tank.TANK_ORIENTATION_SOUTH://南
		    g.fill3DRect(x, y, 5, 30, false);
            g.fill3DRect(x+15, y, 5, 30, false);
            g.fill3DRect(x+5, y+5, 10, 20, false);
            g.fillOval(x+5, y+10, 10, 10);
            g.drawLine(x+10,y+15,x+10,y+33);
			break;
		case Tank.TANK_ORIENTATION_EAST://东
	        g.fill3DRect(x, y, 30, 5, false);
            g.fill3DRect(x, y+15, 30, 5, false);
            g.fill3DRect(x+5, y+5, 20, 10, false);
            g.fillOval(x+10, y+5, 10, 10);
            g.drawLine(x+15,y+10,x+33,y+10);
			break;
		}
	}
	
	/**
	 * 画  元素 球
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param g
	 * @param type
	 */
	 public void drawElement(int x,int y,int width,int height,Graphics g,int type){
		 //g.setColor(c);
		 switch(type){
		 case 0:
			 g.setColor(Color.RED);
			 break;
		 case 1:
			 g.setColor(Color.BLUE);
			 break;
		 case 2:
			 g.setColor(Color.GREEN);
			 break;
		 }
		 
		 g.fillOval(x, y, width, height);
	 }
	
	/**
	 * 画 块状物    血  蓝 绿
	 * @param x
	 * @param y
	 * @param totalsize
	 * @param currentsize
	 * @param g
	 */

	public void drawBlock(int x,int y,int width,int height,int totalsize,int currentsize,Graphics g,Color c){
		g.setColor(c);
		g.drawRect(x, y, totalsize*width, height);
		g.fillRect(x, y, currentsize*width,height);
	}
	 
	/**
	 * 画障碍物
	 * @param x
	 * @param y
	 * @param materical
	 * @param g
	 */
    public void drawObst(int x,int y,int materical,Graphics g){
    	switch(materical){
    	case 0:
    		g.setColor(new Color(Integer.parseInt("f27116",16)));
    		break;
    	case 1:
    		g.setColor(new Color(Integer.parseInt("ffffff",16)));
    		break;
    	}
    	g.fill3DRect(x, y, 20, 20, false);
    }
	
	/**
	 * 计算是否击中
	 * @param zd
	 * @param tank
	 * @return
	 */
	private boolean isHit(Bullet zd, Tank tank) {//计算子弹是否击中坦克 
	     switch(tank.getOrientation()){
	     case Tank.TANK_ORIENTATION_NORTH:
	     case Tank.TANK_ORIENTATION_SOUTH:
	    	 if(zd.getX()>=tank.getX()&&zd.getX()<=tank.getX()+20&&zd.getY()>=tank.getY()&&zd.getY()<=tank.getY()+30){
	    		 return true;
	    	 }else{
	    		 return false;
	    	 }
	     case Tank.TANK_ORIENTATION_WEST:
	     case Tank.TANK_ORIENTATION_EAST:
	    	 if(zd.getX()>=tank.getX()&&zd.getX()<=tank.getX()+30&&zd.getY()>=tank.getY()&&zd.getY()<=tank.getY()+20){
	    		 return true;
	    	 }else{
	    		 return false;
	    	 }
	     }
		return false;
	}
	
	/**
	 * 判断是否消灭了敌方所有坦克  
	 * @param dtanks
	 * @return
	 */
	public boolean hasDestroyAll(Vector<EnemyTank> dtanks){
		for(EnemyTank tank:dtanks){
			if(tank.getBlood()>0){
				return false;
			}
		}
		return true;
	} 
	/**
	 * 主线程键盘监听
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
		 if(e.getKeyCode()==KeyEvent.VK_W){ //上移动
			  mt.setOrientation(Tank.TANK_ORIENTATION_NORTH);
			  
			 if(mt.getY()>0&&mt.isCanNorthMove()){
				 mt.northmove();
				    mt.setCanWestMove(true);
		    	    mt.setCanNorthMove(true);
		    	    mt.setCanSouthMove(true);
		    	    mt.setCanEastMove(true);
				for(Obstruction obst :obstVector){
					if(obst.getBlood()>0){
						 if(mt.getX()>=obst.getX()&&mt.getX()<=obst.getX()+20&&mt.getY()>=obst.getY()&&mt.getY()<=obst.getY()+20){
						    	mt.setCanNorthMove(false);
					    	 break;
					      }
						 else if(mt.getX()+20>=obst.getX()&&mt.getX()+20<=obst.getX()+20&&mt.getY()>=obst.getY()&&mt.getY()<=obst.getY()+20){
							  mt.setCanNorthMove(false);
					    	 break;
					      }
					}
				}
			 }
		 }
		 else if(e.getKeyCode()==KeyEvent.VK_A){//左移动
			  mt.setOrientation(Tank.TANK_ORIENTATION_WEST);
			 if(mt.getX()>0&&mt.isCanWestMove()){
				    mt.westmove();
				    mt.setCanWestMove(true);
		    	    mt.setCanNorthMove(true);
		    	    mt.setCanSouthMove(true);
		    	    mt.setCanEastMove(true);
				  for(Obstruction obst :obstVector){
						if(obst.getBlood()>0){
							 if(mt.getX()>=obst.getX()&&mt.getX()<=obst.getX()+20&&mt.getY()>=obst.getY()&&mt.getY()<=obst.getY()+20){
						    	    mt.setCanWestMove(false);
						    	   
						    	 break;
						      }
							 else if(mt.getX()>=obst.getX()&&mt.getX()<=obst.getX()+20&&mt.getY()+20>=obst.getY()&&mt.getY()+20<=obst.getY()+20){
								 
					    	    mt.setCanWestMove(false);
					    	   
						    	 break;
						      }  
						}
					}
			 }
		 }
		 else if(e.getKeyCode()==KeyEvent.VK_S){//下移动
			  mt.setOrientation(Tank.TANK_ORIENTATION_SOUTH);
			 if(mt.getY()<470&&mt.isCanSouthMove()){
				     mt.southmove();
				    mt.setCanWestMove(true);
		    	    mt.setCanNorthMove(true);
		    	    mt.setCanSouthMove(true);
		    	    mt.setCanEastMove(true);
				  for(Obstruction obst :obstVector){
						if(obst.getBlood()>0){
							if(mt.getX()>=obst.getX()&&mt.getX()<=obst.getX()+20&&mt.getY()+30>=obst.getY()&&mt.getY()+30<=obst.getY()+20){
						    	    mt.setCanSouthMove(false);
						    	 break;
						      }
							else if(mt.getX()+20>=obst.getX()&&mt.getX()+20<=obst.getX()+20&&mt.getY()+30>=obst.getY()&&mt.getY()+30<=obst.getY()+20){
					    	    mt.setCanSouthMove(false);
						    	 break;
						      }
						}
					}
			 }
		 }
		 else  if(e.getKeyCode()==KeyEvent.VK_D){//右移动
			  mt.setOrientation(Tank.TANK_ORIENTATION_EAST);
			 if(mt.getX()<470&&mt.isCanEastMove()){
				     mt.eastmove();
				     mt.setCanWestMove(true);
		    	    mt.setCanNorthMove(true);
		    	    mt.setCanSouthMove(true);
		    	    mt.setCanEastMove(true);
				 for(Obstruction obst :obstVector){
						if(obst.getBlood()>0){
							 if(mt.getX()+30>=obst.getX()&&mt.getX()+30<=obst.getX()+20&&mt.getY()>=obst.getY()&&mt.getY()<=obst.getY()+20){
							     
						    	    mt.setCanEastMove(false);
						    	 break;
						      }
							 else if(mt.getX()+30>=obst.getX()&&mt.getX()+30<=obst.getX()+20&&mt.getY()+20>=obst.getY()&&mt.getY()+20<=obst.getY()+20){
						 
					    	    mt.setCanEastMove(false);
						    	 break;
						      }
						}
					}
			 }
		 }
		 if(e.getKeyCode()==KeyEvent.VK_J){
			 if(mt.getZdVector().size()<8){
				  mt.fszd();// 发射子弹  只允许同时有8颗子弹在界面中存在
			 }
		 }
		 this.repaint();//我方坦克只要发射了子弹或移动了位置就会强制重绘
	}

	 /**
	  * 主线程 run 方法
	  */
	@Override
	public void run() {//让面板每隔100毫秒重绘一次 ,这里一定要让线程休息一下，否则很容易造成内存泄露
		while(threadAlive){
			 try {
				Thread.sleep(100);
				//判断我方击中敌方
				for(int i=0;i<mt.getZdVector().size();i++){//对我方坦克的每一颗子弹进行判断  我方坦克子弹击中敌方
					if(mt.getZdVector().get(i).isThreadAlive()){
						for(int j=0;j<enemyTankVector.size();j++){
							 if(enemyTankVector.get(j).getBlood()>0){
								if( isHit(mt.getZdVector().get(i),enemyTankVector.get(j))){//检测是否命中坦克
									
									   mt.getZdVector().get(i).setThreadAlive(false);//我方子弹的存活期设置为false
									   enemyTankVector.get(j).reduceBlood();
									   zjeffectVector.add(new ZJeffect(enemyTankVector.get(j).getX(),enemyTankVector.get(j).getY(),zjimg));
									   MyTankData.totalScore+=5;
								}
							 }
						}
						
					}
				}
				//判断敌方击中我方
				for(int i=0;i<enemyTankVector.size();i++){//循环遍历敌方的所有的坦克
					 EnemyTank etank=  enemyTankVector.get(i);
					 for(int j=0;j<etank.getZdVector().size();j++){//遍历敌方每一个坦克的每一个子弹
						  if(MyTankData.currBlood>0){
							  if(isHit(etank.getZdVector().get(j),mt)){
								     etank.getZdVector().get(j).setThreadAlive(false);
							 	    zjeffectVector.add(new ZJeffect(mt.getX(),mt.getY(),zjimg));//开花
								     MyTankData.currBlood--;//减血
									 
							  }
						  }
					 }
				}
				
				//判断子弹击中障碍物
				for(int i=0;i<obstVector.size();i++){
				  Obstruction obst=	obstVector.get(i);
				   for(EnemyTank tanks :enemyTankVector){
					    if(tanks.getBlood()>0){
					    	for(Bullet zd  :tanks.getZdVector()){
					    		 if(zd.isThreadAlive()&&obst.getBlood()>0){
					    			 
					    			 if(obst.isBitten(zd)){
					    				  if(obst.getMaterical()==0){
					    					  zd.setThreadAlive(false);
											  obst.reduceBlood();
					    				  }else if(obst.getMaterical()==1){
					    					  zd.setThreadAlive(false);
					    				  }
					    			 }
					    		 }
					    	}
					    }
				   }
				  for(Bullet zd :mt.getZdVector()){//判断子弹击中障碍物
					  if(zd.isThreadAlive()&&obst.getBlood()>0){
					    if(obst.isBitten(zd)){
						   if(obst.getMaterical()==0){
							   zd.setThreadAlive(false);
							   obst.reduceBlood();
						   }else if(obst.getMaterical()==1){
							   zd.setThreadAlive(false);
						   }
						  
					      }
				        }
					  }
				}
				
				
				//坦克捡到元素球  血气  精神  智慧
				for(int i=0;i<eleVector.size();i++){
					Element ele=eleVector.get(i);
					if(ele.isThreadAlive()){
						for(int j=0;j< this.enemyTankVector.size();j++){
					     	EnemyTank dtank=enemyTankVector.get(j);
							if(dtank.getBlood()>0){
								if(ele.isCollsion(dtank)){
									ele.setThreadAlive(false);
									switch(ele.getType()){
									case Element.TYPE_BLOOD:
										if(dtank.getBlood()<EnemyTank.ENEMY_TANK_MAX_BLOOD){
											dtank.setBlood(dtank.getBlood()+1);
										}
										break;
							    	case Element.TYPE_SPIRIT:
										 dtank.setSpeed(dtank.getSpeed()+1);							
									break;
								    case Element.TYPE_WISDOM:
									    dtank.setZdSpeed(dtank.getZdSpeed()+1);
									break;
									}
									
								}
							}
					   }
						if(ele.isCollsion(mt)){
							ele.setThreadAlive(false);
							switch(ele.getType()){
							case Element.TYPE_BLOOD:
								if(MyTankData.currBlood<MyTankData.maxBlood){
									MyTankData.currBlood++;
								}
								break;
					    	case Element.TYPE_SPIRIT:
								if(MyTankData.speed<MyTankData.maxSpeed){
									MyTankData.speed++;
								} 	
								break;
					    	case Element.TYPE_WISDOM:
					    	    if(MyTankData.zdSpeed<MyTankData.zdMaxSpeed){
					    	    	MyTankData.zdSpeed++;
					    	    }
							   break;
						    
							}
						}
					}
					
				}
				
			  repaint();
			} catch (Exception e) {
				
			}
		}
		
	}
    

	
	
	public boolean isThreadAlive() {
		return threadAlive;
	}
	 
	public int getEnemyTankCount() {
		return enemyTankCount;
	}
	public void setEnemyTankCount(int enemyTankCount) {
		this.enemyTankCount = enemyTankCount;
	}
	public Vector<EnemyTank> getEnemyTankVector() {
		return enemyTankVector;
	}
	public void setEnemyTankVector(Vector<EnemyTank> enemyTankVector) {
		this.enemyTankVector = enemyTankVector;
	}
	public Vector<ZJeffect> getZjeffectVector() {
		return zjeffectVector;
	}
	public void setZjeffectVector(Vector<ZJeffect> zjeffectVector) {
		this.zjeffectVector = zjeffectVector;
	}
	public String getZjimg() {
		return zjimg;
	}
	public void setZjimg(String zjimg) {
		this.zjimg = zjimg;
	}
	public void setThreadAlive(boolean threadAlive) {
		this.threadAlive = threadAlive;
	}

	public MyTank getMt() {
		return mt;
	}

	public void setMt(MyTank mt) {
		this.mt = mt;
	}

	public Vector<Obstruction> getObstVector() {
		return obstVector;
	}

	public void setObstVector(Vector<Obstruction> obstVector) {
		this.obstVector = obstVector;
	}
	
    
}
