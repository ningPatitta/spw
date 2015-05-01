package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<RotFruit> rotFruits = new ArrayList<RotFruit>();	
	private ArrayList<Fruit> fruits = new ArrayList<Fruit>();	
	private ArrayList<ItemUmbella> umbellas = new ArrayList<ItemUmbella>();
	private ArrayList<ItemClear> clears = new ArrayList<ItemClear>();
	private ArrayList<ItemHeart> hearts = new ArrayList<ItemHeart>();
	private ArrayList<ItemBomb> bombs = new ArrayList<ItemBomb>();
	
	private Basket b;
	
	private Timer timer;
	
	private long score = 0;
	private double genHeart = 0.002;
	private double genRotFruit = 0.05;
	private double genFruit = 0.1;
	private double genUmbel = 0.007;
	private double genClear = 0.005;
	private double genBomb = 0.002;
	
	private Umbella umbel = null;
	private int timeUmbel = 0;
	private boolean statusUmbel = false;
	private boolean get_over = false;
	private boolean game_over = false ;
	
	public GameEngine(GamePanel gp, Basket b) {
		this.gp = gp;
		this.b = b;		
		gp.sprites.add(b);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);	
	}
	
	public void start(){
		timer.start();
	}
	
	private void generateRotFruit(){
		RotFruit rt = new RotFruit((int)(Math.random()*390), 30);
		gp.sprites.add(rt);
		rotFruits.add(rt);
	}
	
	private void generateFruit(){
		Fruit f = new Fruit((int)(Math.random()*390), 30);
		gp.sprites.add(f);
		fruits.add(f);
	}
	
	private void generateItemUmbella(){
		ItemUmbella u = new ItemUmbella((int)(Math.random()*390), 10); 
		gp.sprites.add(u);
		umbellas.add(u);
	}
	
	private void generateItemClear(){
		ItemClear c = new ItemClear((int)(Math.random()*390), 10);
		gp.sprites.add(c);
		clears.add(c);
	}
	
	private void generateItemHeart(){
		ItemHeart h = new ItemHeart((int)(Math.random()*390), 30);
		gp.sprites.add(h);
		hearts.add(h);
	}
	
	private void generateItemBomb(){
		ItemBomb b = new ItemBomb((int)(Math.random()*390), 10);
		gp.sprites.add(b);
		bombs.add(b);
	}
	
	private void process(){
		if(Math.random() < genRotFruit){
			generateRotFruit();
		}
		if(Math.random() < genFruit){
			generateFruit();
		}
		if(Math.random() < genUmbel){
			generateItemUmbella();
		}
		if(Math.random() < genClear){
			generateItemClear();
		}
		if(Math.random() < genHeart){
			generateItemHeart();
		}
		if(Math.random() < genBomb){
			generateItemBomb();
		}
		
		Iterator<RotFruit> rt_iter = rotFruits.iterator();
		while(rt_iter.hasNext()){
			RotFruit rt = rt_iter.next();
			rt.proceed();
			
			if(!rt.isAlive()){
				rt_iter.remove();
				gp.sprites.remove(rt);
				//score += 100;
			}
		}
		
		Iterator<Fruit> f_iter = fruits.iterator();
		while(f_iter.hasNext()){
			Fruit f = f_iter.next();
			f.proceed();
			
			if(!f.isAlive()){
				f_iter.remove();
				gp.sprites.remove(f);
				//score += 100;
			}
		}
		
		Iterator<ItemUmbella> u_iter = umbellas.iterator();
		while(u_iter.hasNext()){
			ItemUmbella u = u_iter.next();
			u.proceed();
			
			if(!u.isAlive()){
				u_iter.remove();
				gp.sprites.remove(u);
				//score += 300;
			}
		}
		
		Iterator<ItemClear> c_iter = clears.iterator();
		while(c_iter.hasNext()){
			ItemClear c = c_iter.next();
			c.proceed();
			
			if(!c.isAlive()){
				c_iter.remove();
				gp.sprites.remove(c);
				//score += 500;
			}
		}
		
		Iterator<ItemHeart> h_iter = hearts.iterator();
		while(h_iter.hasNext()){
			ItemHeart h = h_iter.next();
			h.proceed();
			
			if(!h.isAlive()){
				h_iter.remove();
				gp.sprites.remove(h);
			}
		}
		
		Iterator<ItemBomb> b_iter = bombs.iterator();
		while(b_iter.hasNext()){
			ItemBomb b = b_iter.next();
			b.proceed();
			
			if(!b.isAlive()){
				b_iter.remove();
				gp.sprites.remove(b);
			}
		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double bs = b.getRectangle();
		Rectangle2D.Double rotfruit;
		Rectangle2D.Double fruit;
		Rectangle2D.Double itemUmbel;
		Rectangle2D.Double umbella;
		Rectangle2D.Double itemClears;
		Rectangle2D.Double heart;
		Rectangle2D.Double bomb;
		for(RotFruit r : rotFruits){
			rotfruit = r.getRectangle();
			if(rotfruit.intersects(bs)){
				r.notAlive();
				b.setLife();
				//score -= 1000;
				return;
			}
			if(statusUmbel){
				umbella = umbel.getRectangle();
				if(rotfruit.intersects(umbella)){
					timeUmbel = 0;	
					r.notAlive();
				}
			}
		}
		
		for(Fruit f : fruits){
			fruit = f.getRectangle();
			if(fruit.intersects(bs)){
				f.notAlive();
				score += 200;
				return;
			}
		}
		
		for(ItemHeart h : hearts){
			heart = h.getRectangle();
			if(heart.intersects(bs)){
				b.setItemHeart();
				h.notAlive();
				return;
			}
		}
		
		for(ItemUmbella u : umbellas){
			itemUmbel = u.getRectangle();
			if(itemUmbel.intersects(bs)){
				u.notAlive();
				score += 500;
				if(!statusUmbel){
					generateUmbella();
					timeUmbel = 320;
					statusUmbel = true;
				}
			}
		}
		for(ItemClear c : clears){
			itemClears = c.getRectangle();
			if(itemClears.intersects(bs)){
				c.notAlive();
				score += 500;
				clearRotFruit();
			}
		}
		for(ItemBomb bo : bombs){
			bomb = bo.getRectangle();
			if(umbel != null){
				umbella = umbel.getRectangle();
				if(bomb.intersects(umbella)){
					bo.notAlive();
					timeUmbel = 0;
				}
			}
			if(bomb.intersects(bs)){
				bo.notAlive();
				//die();
				game_over = true;
				return;
			}
		}
		if(b.getLife() == 0){
			//die();
			game_over = true;
		}
			
		if(timeUmbel > 0){
			timeUmbel--;
		}
		
		else{
			statusUmbel = false;
			gp.sprites.remove(umbel);
			umbel = null;
		}
	}
	public void die(){
		timer.stop();
	}
	
	void generateUmbella(){
		umbel = new Umbella(b);
		gp.sprites.add(umbel);
	}
	
	public void clearRotFruit(){
		for(RotFruit r : rotFruits){
			r.notAlive();
			gp.sprites.remove(r);
		}
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			b.move(-1);
			if(umbel != null){
				umbel.getPosition();
			}
			break;
		case KeyEvent.VK_RIGHT:
			b.move(1);
			if(umbel != null){
				umbel.getPosition();
			}
			break;
		case KeyEvent.VK_D:
			genRotFruit += 0.1;
			break;
		case KeyEvent.VK_SPACE:
			reStart();
			break;
		}
	}
	
	public long getScore(){
		return score;
	}
	
	public int getLife(){
		return b.getLife();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
	
	public void reStart(){
		if(game_over){
			game_over = false;
			for(RotFruit r : rotFruits){
				r.notAlive();
			}
			for(Fruit f : fruits){
				f.notAlive();
			}
			for(ItemHeart h : hearts){
				h.notAlive();
			}
			for(ItemUmbella u : umbellas){
				u.notAlive();
			}
			for(ItemClear c : clears){
				c.notAlive();
			}
			for(ItemBomb bo : bombs){
				bo.notAlive();
			}
			score = 0;
			int life = b.reStartLife();
			start();
		}
	}
	
	public boolean get_over(){
		return game_over;
	}
}