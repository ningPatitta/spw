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
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();	
	private ArrayList<ItemUmbella> umbellas = new ArrayList<ItemUmbella>();
	private ArrayList<ItemClear> clears = new ArrayList<ItemClear>();
	
	private SpaceShip v;	
	
	private Timer timer;
	
	private long score = 0;
	private double difficulty = 0.1;
	private double genUmbel = 0.01;
	private double genClear = 0.005;
	
	private Umbella umbel = null;
	private int timeUmbel = 0;
	private boolean statusUmbel = false;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
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
	
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
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
		if(Math.random() < difficulty){
			generateEnemy();
		}
		if(Math.random() < genUmbel){
			generateItemUmbella();
		}
		if(Math.random() < genClear){
			generateItemClear();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 100;
			}
		}
		
		Iterator<ItemUmbella> u_iter = umbellas.iterator();
		while(u_iter.hasNext()){
			ItemUmbella u = u_iter.next();
			u.proceed();
			
			if(!u.isAlive()){
				u_iter.remove();
				gp.sprites.remove(u);
				score += 300;
			}
		}
		
		Iterator<ItemClear> c_iter = clears.iterator();
		while(c_iter.hasNext()){
			ItemClear c = c_iter.next();
			c.proceed();
			
			if(!c.isAlive()){
				c_iter.remove();
				gp.sprites.remove(c);
				score += 500;
			}
		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		Rectangle2D.Double itemUmbel;
		Rectangle2D.Double umbella;
		Rectangle2D.Double itemClears;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				die();
				return;
			}
			if(statusUmbel){
				umbella = umbel.getRectangle();
				if(er.intersects(umbella)){
					timeUmbel = 0;	
					e.notAlive();
				}
			}
		}
		for(ItemUmbella u : umbellas){
			itemUmbel = u.getRectangle();
			if(itemUmbel.intersects(vr)){
				u.notAlive();
				if(!statusUmbel){
					generateUmbella();
					timeUmbel = 320;
					statusUmbel = true;
				}
			}
		}
		for(ItemClear c : clears){
			itemClears = c.getRectangle();
			if(itemClears.intersects(vr)){
				c.notAlive();
				clearEnemy();
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
		umbel = new Umbella(v);
		gp.sprites.add(umbel);
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v.move(-1);
			if(umbel != null){
				umbel.getPosition();
			}
			break;
		case KeyEvent.VK_RIGHT:
			v.move(1);
			if(umbel != null){
				umbel.getPosition();
			}
			break;
		case KeyEvent.VK_D:
			difficulty += 0.1;
			break;
		}
	}
	
	public void clearEnemy(){
		for(Enemy e : enemies){
			e.notAlive();
			gp.sprites.remove(e);
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
