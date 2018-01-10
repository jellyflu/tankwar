package com.jelly.tankwar.scene;

import javax.swing.JFrame;

 

/**
 * 场景和主类之间交互数据
 * @author jelly
 *
 */
public class FrameAndScene {
    private static StartScene stScene;//开始场景
    private static SwitchScene  swScene;//切换关卡场景
    private static MainScene  mainScene;//游戏主场景
    private static  EndScene endScene;
    private static JFrame frame;//主窗体
   //  private static  MusicPlayer startMP,mainMP,endMP;
    
	public static StartScene getStScene() {
		return stScene;
	}
	public static void setStScene(StartScene stScene) {
		FrameAndScene.stScene = stScene;
	}
 
	public static SwitchScene getSwScene() {
		return swScene;
	}
	public static void setSwScene(SwitchScene swScene) {
		FrameAndScene.swScene = swScene;
	}
	public static MainScene getMainScene() {
		return mainScene;
	}
	public static void setMainScene(MainScene mainScene) {
		FrameAndScene.mainScene = mainScene;
	}
	public static JFrame getFrame() {
		return frame;
	}
	public static void setFrame(JFrame frame) {
		FrameAndScene.frame = frame;
	}
	public static EndScene getEndScene() {
		return endScene;
	}
	public static void setEndScene(EndScene endScene) {
		FrameAndScene.endScene = endScene;
	}
	 
	 
	 
 
	 
    
}
