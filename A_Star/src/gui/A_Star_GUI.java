package gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import algorithm.A_Star;
import algorithm.A_Star.Cell;
import input.KeyManager;
import input.MouseManager;

public class A_Star_GUI implements Runnable {

	public Display display;
	private int width, height;

	private boolean running = false;
	private Thread thread;

	private BufferStrategy bs;
	private Graphics g;

	//Input
	private KeyManager keyManager;
	private MouseManager mouseManager;

	private A_Star star;

	public A_Star_GUI(A_Star star, int width, int height){
		keyManager = new KeyManager();
		mouseManager = new MouseManager(this);
		this.width = width;
		this.height = height;
		this.star = star;

	}

	private void init(){

		display = new Display("A*", width, height);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);

		JMenuBar menuBar = new JMenuBar();

		JButton runBtn = new JButton("Run");
		menuBar.add(runBtn);

		runBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!star.done) {
					star.algo();
				}
				else {
					star.done = false;
					star.algo();
				}
			}         
		}); 

		display.getFrame().setJMenuBar(menuBar);





	}

	public void setWidth(int width) {
		this.width = width;}

	public void setHeight(int height) {
		this.height = height;}

	private void tick(){
		keyManager.tick();
		mouseManager.tick();
	}

	private void move() {
		if(keyManager.down) {}
		if(keyManager.right) {}

		if(keyManager.up) {}

		if(keyManager.left) {}

	}

	private void render(){
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//Clear Screen
		g.clearRect(0, 0, width, height);
		//Draw Here!

		g.fillRect(0, 0, width, height);

		for (int x = 0; x < star.cols; x++) {
			for (int y = 0; y < star.rows; y++) {
				star.grid[x][y].render(g);
			}
		}

		//End Drawing!
		bs.show();
		g.dispose();
	}

	public void run(){

		init();

		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;

		while(running){
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;

			if(delta >= 1){
				tick();
				if(!star.done)
					render();
				ticks++;
				delta--;
			}

			if(timer >= 1000000000){
				//				System.out.println("FPS: " + ticks);
				ticks = 0;
				timer = 0;
			}
		}

		stop();

	}


	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void drawStringCentered(String s, Color color, int x, int y, int fontSize) {
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, fontSize)); 
		g.setColor(color);

		int text_width = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
		int text_height = (int) g.getFontMetrics().getStringBounds(s, g).getHeight();

		g.drawString(s, x - text_width/2, y - text_height/2);
	}



}

