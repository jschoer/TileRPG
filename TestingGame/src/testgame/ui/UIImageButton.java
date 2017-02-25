package testgame.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class UIImageButton extends UIObject
{
	private BufferedImage[] images;
	private ClickListener clicked;
	
	public UIImageButton(float x, float y, int height, int width, BufferedImage[] images, ClickListener clicked)
	{
		super(x, y, height, width);
		this.images = images;
		this.clicked = clicked;
	}

	@Override
	public void tick()
	{
		
	}

	@Override
	public void render(Graphics g)
	{
		if(hovering)
			g.drawImage(images[1], (int)x, (int)y, width, height, null);
		else
			g.drawImage(images[0], (int)x, (int)y, width, height, null);
	}

	@Override
	public void onClick()
	{
		clicked.OnClick();
	}
	
}
