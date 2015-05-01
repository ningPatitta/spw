package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RotFruit extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	private String picRotApple;
	private String picRotOrange;
	private Image picture;
	private double genPic = 0; 
	
	private int step = 8;
	private boolean alive = true;
	
	public RotFruit(int x, int y) {
		super(x, y, 25, 25);
		try{
			picRotApple = "f2/spw/img/rotApple.gif";
			picRotOrange = "f2/spw/img/rotOrange.gif";
			genPic = Math.random();
			if(genPic >= 0.5){
				picture = ImageIO.read(new File(picRotApple));
			}
			else{
				picture = ImageIO.read(new File(picRotOrange));
			}
			
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