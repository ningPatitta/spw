package f2.spw;
 
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ItemBomb extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	private String picItemBomb;
	private Image picture;
	
	private int step = 8;
	private boolean alive = true;
	
	public ItemBomb(int x, int y){
		super(x, y, 25, 25);
		try{
			picItemBomb = "f2/spw/img/bomb.gif";
			picture = ImageIO.read(new File(picItemBomb));
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
	
	public void notAlive(){
		alive = false;
	}
}