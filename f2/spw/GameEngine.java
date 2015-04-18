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
	
	private Basket b;	
	
	private Timer timer;
	
	private long score = 0;
	private double genRotFruit = 0.05;
	private double genFruit = 0.1;
	private double genUmbel = 0.007;
	private double genClear = 0.005;
	
	private Umbella umbel = null;
	private int timeUmbel = 0;
	private boolean statusUmbel = false;
	
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
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double bs = b.getRectangle();
		Rectangle2D.Double rt;
		Rectangle2D.Double fr;
		Rectangle2D.Double itemUmbel;
		Rectangle2D.Double umbella;
		Rectangle2D.Double itemClears;
		for(RotFruit r : rotFruits){
			rt = r.getRectangle();
			if(rt.intersects(bs)){
				r.notAlive();
				score -= 1000;
				return;
			}
			if(statusUmbel){
				umbella = umbel.getRectangle();
				if(rt.intersects(umbella)){
					timeUmbel = 0;	
					r.notAlive();
				}
			}
		}
		
		for(Fruit f : fruits){
			fr = f.getRectangle();
			if(fr.intersects(bs)){
				f.notAlive();
				score += 200;
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
		}
	}
	
	public long getScore(){
		return score;
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
}
