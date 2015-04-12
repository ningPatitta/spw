package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Enemy extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	private String picEnemy;
	private Image picture;
	
	private int step = 10;
	private boolean alive = true;
	
	public Enemy(int x, int y) {
		super(x, y, 20, 20);
		try{
			//picEnemy = "...//img/enemy.gif";
			picEnemy = "E:/My Couse/242-210 fundamental programmingII/spw/f2/spw/img/enemy.gif";
			picture = ImageIO.read(new File(picEnemy));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(picture,x,y,width,height,null);
		//if(y < Y_TO_FADE)
		//	g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		//else{
		//	g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
		//			(float)(Y_TO_DIE - y)/(Y_TO_DIE - Y_TO_FADE)));
		//}
		//g.setColor(Color.RED);
		//g.fillRect(x, y, width, height);
		
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