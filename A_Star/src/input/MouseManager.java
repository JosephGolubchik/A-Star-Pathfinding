package input;


import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import gui.A_Star_GUI;




public class MouseManager implements MouseListener{

	public A_Star_GUI gui;
	public Point mousePos;
	
	public MouseManager(A_Star_GUI gui) {
		this.gui = gui;
		mousePos = new Point(0,0);
		System.out.println("1  "+mousePos); 
	}

	public void tick() {
		mousePos = gui.display.getFrame().getMousePosition();
		mousePos = new Point((int)mousePos.getX()-8, (int)mousePos.getY()-60);
		System.out.println("2  "+mousePos); 
	}
	
	
	public void mouseClicked(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {
	}

}
