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
import java.awt.Font;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;	
	Graphics2D big;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private String picBg;
	private String picHeart;
	private String picHeart2;
	private String picBomb;
	private String picUmbel;
	private String picClear;
	
	private Image pictureBg;
	private Image pictureHeart;
	private Image pictureHeart2;
	private Image pictureBomb;
	private Image pictureUmbel;
	private Image pictureClear;
	
	public GamePanel() {
		try{
			picBg = "f2/spw/img/bgCrop.jpg";
			picHeart = "f2/spw/img/heart.gif";
			picHeart2 = "f2/spw/img/itemheart.gif";
			picBomb = "f2/spw/img/bomb.jpg";
			picUmbel = "f2/spw/img/umbelitem.gif";
			picClear = "f2/spw/img/clear.gif";
			
			pictureBg = ImageIO.read(new File(picBg));
			pictureHeart = ImageIO.read(new File(picHeart));
			pictureHeart2 = ImageIO.read(new File(picHeart2));	
			pictureBomb = ImageIO.read(new File(picBomb));
			pictureUmbel = ImageIO.read(new File(picUmbel));
			pictureClear = ImageIO.read(new File(picClear));
			
		}catch(IOException e){
			e.printStackTrace();
		}
		bi = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		
	}

	public void updateGameUI(GameReporter reporter){
		if(!reporter.get_over()){
			big.clearRect(0, 0, 400, 600);
			big.drawImage(pictureBg, 0, 0, null);
			big.setColor(Color.WHITE);
			big.fillRect(0, 0, 400, 45);
			big.setColor(Color.BLACK);
			//Font f = new Font ("", Font.BOLD, 12);
			//big.setFont (f);			
			big.drawString(String.format("Score : %06d", reporter.getScore()), 5, 40);
			//big.setFont(big.getFont("Cordia New",2,20).deriveFont(12.0f));
			//Font f2 = new Font ("", Font.PLAIN, 12);
			//big.setFont (f2);
			big.drawString(String.format("Umbella for protect", reporter.getScore()), 150, 20);
			big.drawString(String.format("Clear rot fruit", reporter.getScore()), 150, 40);
			big.drawString(String.format("Get one life", reporter.getScore()), 300, 20);
			big.drawString(String.format("Bomb to die", reporter.getScore()), 300, 40);
			
			big.drawImage(pictureHeart2, 290, 10, 10, 10, null);
			big.drawImage(pictureBomb, 290, 30, 10, 10, null);
			big.drawImage(pictureUmbel, 140, 10, 10, 10, null);
			big.drawImage(pictureClear, 140, 30, 10, 10, null);
			
			for(Sprite s : sprites){
				s.draw(big);
			}
			for(int i=0, j=0 ; i<reporter.getLife() ; i++, j+= 25){
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
