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
	private SpaceShip s;
	
	public Umbella(SpaceShip s){
		super(s.getXPosition(), s.getYPosition()-s.getHeight(), s.getWidth(), s.getHeight());
		this.s = s;
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
		x = s.getXPosition();
		y = s.getYPosition()-s.getHeight();
		
	}
}