package f2.spw;
 
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Umbella extends Sprite{
	
	private String picUmbella;
	private Image picture;
	private Basket b;
	
	public Umbella(Basket b){
		super(b.getXPosition(), b.getYPosition()-b.getHeight(), b.getWidth(), b.getHeight());
		this.b = b;
		try{
			picUmbella = "f2/spw/img/umbella.gif";
			picture = ImageIO.read(new File(picUmbella));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(picture,x,y,width,height,null);
	}
	
	public void getPosition(){
		x = b.getXPosition();
		y = b.getYPosition()-b.getHeight();
		
	}
}