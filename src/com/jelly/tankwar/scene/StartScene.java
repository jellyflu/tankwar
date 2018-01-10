package com.jelly.tankwar.scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 游戏开始 场景
 * @author jelly
 *
 */
public class StartScene extends JPanel implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	private int x;
	private int y;
	private int width;
	private int height;
	private int times=0;
	private boolean threadAlive=true;  //线程存活
	 
	 
	public boolean isThreadAlive() {
		return threadAlive;
	}
	public void setThreadAlive(boolean threadAlive) {
		this.threadAlive = threadAlive;
	}
	@Override
    public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width, height);
		times++;
		if(times%3==0){
			g.setColor(Color.GREEN);
			Font font=new Font("华文行楷",Font.BOLD,40) ;
			g.setFont(font);
			g.drawString("点击新游戏开始!", 80, 200);
		}else if(times%3==1){
			g.setColor(Color.cyan);
			Font font=new Font("华文行楷",Font.BOLD,40) ;
			g.setFont(font);
			g.drawString("点击新游戏开始!", 80, 200);
		}else{
			g.setColor(Color.pink);
			Font font=new Font("华文行楷",Font.BOLD,40) ;
			g.setFont(font);
			g.drawString("点击新游戏开始!", 80, 200);
		}
		g.setColor(Color.white);
		g.setFont(new Font("华文行楷",Font.BOLD,25));
		g.drawString("—— jelly出品", 300, 400);
	}
	public  StartScene(){
		super();
	}
    public  StartScene(int x,int y,int width,int height){
    	super();
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		 
	}
	@Override
	public void run() {
 		while(threadAlive){
 			try {
				Thread.sleep(600);
			} catch (InterruptedException e) {
			}
 			 repaint();
 		}
	}
}
