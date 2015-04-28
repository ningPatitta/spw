package f2.spw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;	
	Graphics2D big;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private String picBg;
	private String picHeart;
	private Image pictureBg;
	private Image pictureHeart;
	
	public GamePanel() {
		try{
			picBg = "f2/spw/img/bgCrop.jpg";
			picHeart = "f2/spw/img/heart.gif";
			pictureBg = ImageIO.read(new File(picBg));
			pictureHeart = ImageIO.read(new File(picHeart));			
		}catch(IOException e){
			e.printStackTrace();
		}
		bi = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		//big.setBackground(Color.BLACK);
	}

	public void updateGameUI(GameReporter reporter){
		if(!reporter.get_over()){
			big.clearRect(0, 0, 400, 600);
			big.drawImage(pictureBg, 0, 0, null);
			big.setColor(Color.BLACK);		
			big.drawString(String.format("%08d", reporter.getScore()), 300, 40);
			for(Sprite s : sprites){
				s.draw(big);
			}
			for(int i=0, j=0 ; i<reporter.getLife() ; i++, j+= 40){
				big.drawImage(pictureHeart, j, 0, 20, 20, null);
			}
		repaint();
		}
		else if(reporter.get_over()){
			big.drawImage(pictureBg,0,0,400,600,this);
			big.drawString(String.format("GAME OVER"),160,300);
			big.drawString(String.format("Press 'Space Bar' to try again"),115,350);
			repaint();
		}
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}

}
