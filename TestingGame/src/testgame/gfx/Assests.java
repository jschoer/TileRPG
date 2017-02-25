package testgame.gfx;

import java.awt.image.BufferedImage;

public class Assests
{
	private static final int width = 32; 
	private static final int height = 32;
	
	//World Images
	public static BufferedImage grass, bigGrass, smallGrass, flower, hill, tree, tree2, brickWall, brick;
	public static BufferedImage carpet, pot;
	public static BufferedImage[] door, textBubble, swordBubble;
	
	//Entity Images
	public static BufferedImage[] player_down, player_up, player_left, player_right, player_idle, player_attack;
	public static BufferedImage[] maleVillager_up, maleVillager_down, maleVillager_left, maleVillager_right;
	public static BufferedImage[] slime_up, slime_down, slime_left, slime_right;
	
	//Buttons
	public static BufferedImage[] startBtn, closeBtn, newButton, backBtn, saveBtn, exitBtn, partyBtn;
	
	//Battle Images
	public static BufferedImage slime;
	public static BufferedImage battle1;
	public static BufferedImage health;
	
	//Monsters
	public static BufferedImage[] slimeKing;
	
	//Attack Animations
	public static BufferedImage[] slash, slimeAttack, placeHolder;
	public static Animation normalAttack, enemyAttack, phAnimation;
	
	//Battle Buttons
	public static BufferedImage[] attackBtn, defendBtn, itemBtn, spellBtn, abilityBtn, runBtn;
	
	//Other
	public static BufferedImage[] titleScreen;
	
	public static void init()
	{
		//===================Sprite Sheets============================================================//
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/ChromLordT.png"));
		SpriteSheet world = new SpriteSheet(ImageLoader.loadImage("/textures/Tiny32.png"));
		SpriteSheet world2 = new SpriteSheet(ImageLoader.loadImage("/textures/femap.png"));
		SpriteSheet texts = new SpriteSheet(ImageLoader.loadImage("/textures/talkingBubble.png"));
		
		SpriteSheet button = new SpriteSheet(ImageLoader.loadImage("/textures/startbutton.png"));
		SpriteSheet button2 = new SpriteSheet(ImageLoader.loadImage("/textures/backbutton.png"));
		
		SpriteSheet monsters = new SpriteSheet(ImageLoader.loadImage("/textures/monsters.png"));
		SpriteSheet slimeKingSheet = new SpriteSheet(ImageLoader.loadImage("/textures/slimeKing.png"));
		
		SpriteSheet battle = new SpriteSheet(ImageLoader.loadImage("/textures/background.png"));
		SpriteSheet menus = new SpriteSheet(ImageLoader.loadImage("/textures/Health.png"));
		SpriteSheet battleUI = new SpriteSheet(ImageLoader.loadImage("/textures/battleUI.png"));
		
		SpriteSheet title = new SpriteSheet(ImageLoader.loadImage("/textures/title.png"));
		
		SpriteSheet slashAttack = new SpriteSheet(ImageLoader.loadImage("/textures/slash.png"));
		SpriteSheet slimeAni = new SpriteSheet(ImageLoader.loadImage("/textures/slimeAttack.png"));
		//===========================================================================================//
		
		//Player
		player_down = new BufferedImage[4];
		player_down[0] = sheet.crop(128, 128, width, height);
		player_down[1] = sheet.crop(128 + width, 128, width, height);
		player_down[2] = sheet.crop(128 + 2 * width, 128, width, height);
		player_down[3] = sheet.crop(128 + 3 * width, 128, width, height);
		
		player_up = new BufferedImage[4];
		player_up[0] = sheet.crop(128, 160, width, height);
		player_up[1] = sheet.crop(128 + width, 160, width, height);
		player_up[2] = sheet.crop(128 + 2 * width, 160, width, height);
		player_up[3] = sheet.crop(128 + 3 * width, 160, width, height);
		
		player_left = new BufferedImage[4];
		player_left[0] = sheet.crop(128, 64, width, height);
		player_left[1] = sheet.crop(128 + width, 64, width, height);
		player_left[2] = sheet.crop(128 + 2 * width, 64, width, height);
		player_left[3] = sheet.crop(128 + 3 * width, 64, width, height);
		
		player_right = new BufferedImage[4];
		player_right[0] = sheet.crop(128, 96, width, height);
		player_right[1] = sheet.crop(128 + width, 96, width, height);
		player_right[2] = sheet.crop(128 + 2 * width, 96, width, height);
		player_right[3] = sheet.crop(128 + 3 * width, 96, width, height);
		
		player_idle = new BufferedImage[4];
		player_idle[0] = sheet.crop(128, 0, width, height);
		player_idle[1] = sheet.crop(128 + width, 0, width, height);
		player_idle[2] = sheet.crop(128 + 2 * width, 0, width, height);
		player_idle[3] = sheet.crop(128 + 3 * width, 0, width, height);
		
		player_attack = new BufferedImage[4];
		player_attack[0] = sheet.crop(128, 32, width, height);
		player_attack[1] = sheet.crop(128 + width, 32, width, height);
		player_attack[2] = sheet.crop(128 + 2 * width, 32, width, height);
		player_attack[3] = sheet.crop(128 + 3 * width, 32, width, height);
		
		//Male Villager
		maleVillager_down = new BufferedImage[2];
		maleVillager_down[0] = world.crop(width, 8 * height, width, height);
		maleVillager_down[1] = world.crop(2 * width, 8 * height, width, height);
		
		maleVillager_up = new BufferedImage[2];
		maleVillager_up[0] = world.crop(width, 11* height, width, height);
		maleVillager_up[1] = world.crop(2 * width, 11 * height, width, height);
		
		maleVillager_left = new BufferedImage[2];
		maleVillager_left[0] = world.crop(width, 9 * height, width, height);
		maleVillager_left[1] = world.crop(2 * width, 9 * height, width, height);
		
		maleVillager_right = new BufferedImage[2];
		maleVillager_right[0] = world.crop(width, 10 * height, width, height);
		maleVillager_right[1] = world.crop(2 * width, 10 * height, width, height);
		
		//Slime
		slime_down = new BufferedImage[3];
		slime_down[0] = world.crop(0, 12 * height, width, height);
		slime_down[1] = world.crop(width, 12 * height, width, height);
		slime_down[2] = world.crop(2 * width, 12 * height, width, height);
		
		slime_up = new BufferedImage[3];
		slime_up[0] = world.crop(0, 15* height, width, height);
		slime_up[1] = world.crop(width, 15 * height, width, height);
		slime_up[2] = world.crop(2 * width, 15 * height, width, height);
		
		slime_left = new BufferedImage[3];
		slime_left[0] = world.crop(0, 13 * height, width, height);
		slime_left[1] = world.crop(width, 13 * height, width, height);
		slime_left[2] = world.crop(2 * width, 13 * height, width, height);
		
		slime_right = new BufferedImage[3];
		slime_right[0] = world.crop(0, 14 * height, width, height);
		slime_right[1] = world.crop(width, 14 * height, width, height);
		slime_right[2] = world.crop(2 * width, 14 * height, width, height);
		
		//==============WORLD====================================//
		grass = world.crop(448, 32, width, height);
		bigGrass = world.crop(480, 32, width, height);
		smallGrass = world.crop(448, 64, width, height);
		flower = world.crop(480, 64, width, height);
		hill = world.crop(192, 128, width, height);
		
		tree = world.crop(288, 32, width, height);
		tree2 = world.crop(256, 32, width, height);
		
		brickWall = world.crop(448, 0, width, height);
		brick = world.crop(416, 0, width, height);
		
		pot = world.crop(4 * width, 3 * height, width, height);
		carpet = world.crop(13 * width, 5 * height, width, height);
		
		door = new BufferedImage[2];
		door[0] = world.crop(width * 4, 0, width, height);
		door[1] = world.crop(width * 7, 0, width, height);
		
		textBubble = new BufferedImage[2];
		textBubble[0] = texts.crop(0, 0, 32, 32);
		textBubble[1] = texts.crop(32, 0, 32, 32);
		
		swordBubble = new BufferedImage[2];
		swordBubble[0] = texts.crop(64, 0, 32, 32);
		swordBubble[1] = texts.crop(96, 0, 32, 32);
		//======================================================//
		
		//===============GUI=====================================//
		startBtn = new BufferedImage[2];
		startBtn[0] = button.crop(0, 0, 200, 50);
		startBtn[1] = button.crop(0, 50, 200, 50);
		
		closeBtn = new BufferedImage[2];
		closeBtn[0] = button.crop(0, 100, 200, 50);
		closeBtn[1] = button.crop(0, 150, 200, 50);
		
		newButton = new BufferedImage[2];
		newButton[0] = button.crop(0, 200, 200, 50);
		newButton[1] = button.crop(0, 250, 200, 50);
		
		backBtn = new BufferedImage[2];
		backBtn[0] = button2.crop(0, 0, width, height);
		backBtn[1] = button2.crop(width, 0, width, height);
		
		exitBtn = new BufferedImage[2];
		exitBtn[0] = button2.crop(width * 2, height, width, height);
		exitBtn[1] = button2.crop(width * 3, height, width, height);
		
		saveBtn = new BufferedImage[2];
		saveBtn[0] = button2.crop(width * 2, 0, width, height);
		saveBtn[1] = button2.crop(width * 3, 0, width, height);
		
		partyBtn = new BufferedImage[2];
		partyBtn[0] = button2.crop(0, height, width, height);
		partyBtn[1] = button2.crop(width, height, width, height);
		
		//Battle UI
		attackBtn = new BufferedImage[2];
		attackBtn[0] = battleUI.crop(0, 0, 100, 50);
		attackBtn[1] = battleUI.crop(0, 50, 100, 50);
		
		defendBtn = new BufferedImage[2];
		defendBtn[0] = battleUI.crop(100, 0, 100, 50);
		defendBtn[1] = battleUI.crop(100, 50, 100, 50);
		
		itemBtn = new BufferedImage[2];
		itemBtn[0] = battleUI.crop(200, 0, 100, 50);
		itemBtn[1] = battleUI.crop(200, 50, 100, 50);
		
		spellBtn = new BufferedImage[2];
		spellBtn[0] = battleUI.crop(0, 100, 100, 50);
		spellBtn[1] = battleUI.crop(0, 150, 100, 50);
		
		abilityBtn = new BufferedImage[2];
		abilityBtn[0] = battleUI.crop(100, 100, 100, 50);
		abilityBtn[1] = battleUI.crop(100, 150, 100, 50);
		
		runBtn = new BufferedImage[2];
		runBtn[0] = battleUI.crop(200, 100, 100, 50);
		runBtn[1] = battleUI.crop(200, 150, 100, 50);
		//============================================================//
		
		//=================Battle Stuff============================//
		slime = monsters.crop(0, 0, 100, 100);
		battle1 = battle.crop(0, 0, 500, 500);
		health = menus.crop(0, 0, 100, 50);
		//=========================================================//
		
		//=======================Monsters===============================//
		slimeKing = new BufferedImage[4];
		slimeKing[0] = slimeKingSheet.crop(0, 0, 100, 100);
		slimeKing[1] = slimeKingSheet.crop(100, 0, 100, 100);
		slimeKing[2] = slimeKingSheet.crop(200, 0, 100, 100);
		slimeKing[3] = slimeKingSheet.crop(100, 0, 100, 100);
		//==============================================================//
		
		//=======================Attack Animations========================//
		placeHolder = new BufferedImage[6];
		placeHolder[0] = slimeAni.crop(2500, 0, 500, 500);
		placeHolder[1] = slimeAni.crop(2500, 0, 500, 500);
		placeHolder[2] = slimeAni.crop(2500, 0, 500, 500);
		placeHolder[3] = slimeAni.crop(2500, 0, 500, 500);
		placeHolder[4] = slimeAni.crop(2500, 0, 500, 500);
		placeHolder[5] = slimeAni.crop(2500, 0, 500, 500);
		
		slash = new BufferedImage[6];
		slash[0] = slashAttack.crop(0, 0, 500, 500);
		slash[1] = slashAttack.crop(500, 0, 500, 500);
		slash[2] = slashAttack.crop(1000, 0, 500, 500);
		slash[3] = slashAttack.crop(1500, 0, 500, 500);
		slash[4] = slashAttack.crop(2000, 0, 500, 500);
		slash[5] = slimeAni.crop(2500, 0, 500, 500);
		
		slimeAttack = new BufferedImage[6];
		slimeAttack[0] = slimeAni.crop(0, 0, 500, 500);
		slimeAttack[1] = slimeAni.crop(500, 0, 500, 500);
		slimeAttack[2] = slimeAni.crop(1000, 0, 500, 500);
		slimeAttack[3] = slimeAni.crop(1500, 0, 500, 500);
		slimeAttack[4] = slimeAni.crop(2000, 0, 500, 500);
		slimeAttack[5] = slimeAni.crop(2500, 0, 500, 500);
		//================================================================//
		
		//Title
		titleScreen = new BufferedImage[3];
		titleScreen[0] = title.crop(0, 0, 500, 500);
		titleScreen[1] = title.crop(500, 0, 500, 500);
		titleScreen[2] = title.crop(1000, 0, 500, 500);
		
		//==============Animations=================//
		normalAttack = new Animation(50, slash);
		enemyAttack = new Animation(50, slimeAttack);
		phAnimation = new Animation(0, placeHolder);
		//=========================================//
	}
}
