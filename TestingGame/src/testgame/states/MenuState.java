package testgame.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import testgame.Game;
import testgame.Handler;
import testgame.gfx.Animation;
import testgame.gfx.Assests;
import testgame.sound.BGMplayer;
import testgame.ui.ClickListener;
import testgame.ui.UIImageButton;
import testgame.ui.UIManager;

public class MenuState extends State
{
	private UIManager uiManager;
	private BGMplayer bgmPlayer;
	
	private Animation titleScreen;
	
	public MenuState(Handler handler)
	{
		super(handler);
		
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		bgmPlayer = handler.getBgmPlayer();
		bgmPlayer.playMusic("/sound/bgm/title.wav");
		
		titleScreen = new Animation(170, Assests.titleScreen);
		
		uiManager.addObject(new UIImageButton(150, 100, 200, 50, Assests.newButton, new ClickListener() {
			@Override
			public void OnClick()
			{
				handler.getMouseManager().setUIManager(null);
				
				String yourName = JOptionPane.showInputDialog("Enter a name for your Hero: ");
				if (yourName == null || yourName.length() <= 0)
				{
					yourName = "Hero";
				}
				handler.setYourName(yourName);
				handler.getArena().getPlayer().tick();
				
				bgmPlayer.playSound("/sound/sfx/gainlp.wav");
				bgmPlayer.stopMusic();
				bgmPlayer.playMusic("/sound/bgm/town.wav");
				
				State.setState(handler.getGame().gameState);
			}}));
		uiManager.addObject(new UIImageButton(150, 300, 200, 50, Assests.closeBtn, new ClickListener() {
			@Override
			public void OnClick()
			{
				handler.getMouseManager().setUIManager(null);
				bgmPlayer.playSound("/sound/sfx/gainlp.wav");
				int close = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit now?");
				if(close == 0)
					System.exit(0);
			}}));
		uiManager.addObject(new UIImageButton(150, 200, 200, 50, Assests.startBtn, new ClickListener() {
			@Override
			public void OnClick()
			{
				handler.getMouseManager().setUIManager(null);
				
				handler.setYourName("Josh");
				handler.getArena().getPlayer().tick();
				
				bgmPlayer.playSound("/sound/sfx/gainlp.wav");
				bgmPlayer.stopMusic();
				bgmPlayer.playMusic("/sound/bgm/town.wav");
				
				State.setState(handler.getGame().gameState);
			}}));
	}
	
	@Override
	public void tick()
	{
		handler.getMouseManager().setUIManager(uiManager);
		uiManager.tick();
		titleScreen.tick();
	}

	@Override
	public void render(Graphics g)
	{
		Color myColor = new Color(0, 0, 0, 100);
		g.drawImage(titleScreen.getCurrentFrame(), 0, 0, 500, 500, null);
		g.setColor(myColor);
		g.fillRect(0, 0, 500, 500);
		uiManager.render(g);
	}

}
