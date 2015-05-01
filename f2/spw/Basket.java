package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Basket extends Sprite{
	
	private String picbasket;
	private Image picture;
	int step = 8;
	private int life;
	
	public Basket(int x, int y, int width, int height) {
		super(x, y, width, height);
		life = 4;
		try{
			picbasket = "f2/spw/img/basket.gif";
			picture = ImageIO.read(new File(picbasket));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(picture,x,y,width,height,null);
		//g.setColor(Color.PINK);
		//g.fillRect(x, y, width, height);
		
	}

	public void move(int direction){
		x += (step * direction);
		if(x < 0)
			x = 0;
		if(x > 400 - width)
			x = 400 - width;
	}
	
	public int getXPosition(){
		return x;
	}
	
	public int getYPosition(){
		return y;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	public int reStartLife(){
		return life = 4;
	}
	public int getLife(){
		return life;
	}
	
	public void setLife(){
		life--;
	}
	public void setItemHeart(){
		if(life < 4){
			life++;
		}
	}
}
