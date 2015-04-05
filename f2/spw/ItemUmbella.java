package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ItemUmbella extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	private String picUmbell;
	private Image picture;
	
	private int step = 15;
	private boolean alive = true;
	
	public ItemUmbella(int x, int y){
		super(x, y, 20, 20);
		try{
			//picUmbell = "...//img/umbella.gif";
			picUmbell = "E:/My Couse/242-210 fundamental programmingII/spw/f2/spw/img/umbella.gif";
			picture = ImageIO.read(new File(picUmbell));
		}catch(IOException e){
			e.printStackTrace();
		}
	}	
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(picture,x,y,width,height,null);
	}
	
	public void proceed(){
		y += step;
		if(y > Y_TO_DIE){
			alive = false;
		}
	}
	
	public boolean isAlive(){
		return alive;
	}
}