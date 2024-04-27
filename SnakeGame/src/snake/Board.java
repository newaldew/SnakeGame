package snake;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class Board extends JPanel implements ActionListener{
	private Image apple;
	private Image dot;
	private Image head;
	
	private int apple_x;
	private int apple_y;
	
	private final int ALL_DOTS=300;
	private final int DOT_SIZE=10;
	
	private final int x[]=new int[ALL_DOTS];
	private final int y[]=new int[ALL_DOTS];
	
	private boolean left=false;
	private boolean right=true;
	private boolean up=false;
	private boolean down=false;
	
	private boolean inGame=true;
	private int dots;
	
	private Timer timer;
	
	Board(){
		addKeyListener(new TAdapter());
		
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(300,300));
		setFocusable(true);
		
		loadImages();
		initGame();
	}
	public void loadImages() {

		File sourceimage = new File("C:\\Users\\Computers\\eclipse-workspace\\icons\\apple.png");
		try {
			apple = ImageIO.read(sourceimage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		File sourceimage1 = new File("C:\\Users\\Computers\\eclipse-workspace\\icons\\dot.png");
		try {
			dot = ImageIO.read(sourceimage1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		File sourceimage2 = new File("C:\\Users\\Computers\\eclipse-workspace\\icons\\head.png");
		try {
			head = ImageIO.read(sourceimage2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	void initGame() {
		dots=3;
		
		for(int i=0;i<dots;i++) {
			y[i]=50;
			x[i]=50-i*DOT_SIZE;
		}
		locateApple();
		timer = new Timer(140,this);
		timer.start();
	}
	void locateApple() {
		int r=(int)(Math.random()*28);
		apple_x=r*DOT_SIZE;
		r=(int)(Math.random()*28);
		apple_y=r*DOT_SIZE;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		draw(g);
	}
	public void draw(Graphics g) {
		if(!inGame) {
			gameOver(g);
		}
		else {
			g.drawImage(apple, apple_x, apple_y, this);
			
			for(int i=0;i<dots;i++) {
				if(i==0) {
					g.drawImage(head,x[i],y[i],this);
				}
				else {
					g.drawImage(dot,x[i],y[i],this);
				}
			}
			Toolkit.getDefaultToolkit().sync();
		}
		
	}
	public void gameOver(Graphics g) {
		String msg="GAME OVER!";
		
		Font font = new Font("SAN_SERIF", Font.BOLD, 14);
	    FontMetrics metrices = getFontMetrics(font);    
	    g.setColor(Color.WHITE);
	    g.setFont(font);
	    
	    g.drawString(msg, (300 - metrices.stringWidth(msg)) / 2, 300/2);
	}
	void move() {
		for(int i=dots;i>0;i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
		}	
		if(left) {
			x[0]=x[0]- DOT_SIZE;
		}
		else if(right) {
			x[0]=x[0]+DOT_SIZE;
		}
		else if(up) {
			y[0]=y[0]-DOT_SIZE;
		}
		else if(down) {
			y[0]=y[0]+DOT_SIZE;
		}
	}
	void checkApple() {
		if(apple_x==x[0] && apple_y==y[0]) {
			dots++;
			locateApple();
		}
	}
	void checkCollision() {
		for(int i=1;i<dots;i++) {
			if(i>4 && x[0]==x[i] && y[0]==y[i]) {
				inGame=false;
			}
		}
		if(x[0]>=300 || x[0]<0 || y[0]>=300 || y[0]<0) {
			inGame=false;
		}
		
		if(!inGame) {
			timer.stop();
		}
		
	}
	public void actionPerformed(ActionEvent e) {
		
		if(inGame) {
			checkApple();
			checkCollision();
			move();
		}
		
		repaint();
	}
	public class TAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			int key=e.getKeyCode();
			
			if(key==KeyEvent.VK_LEFT && (!right)) {
				left=true;
				up=false;
				down=false;
			}
			else if(key==KeyEvent.VK_RIGHT && (!left)) {
				right=true;
				up=false;
				down=false;
			}
			if(key==KeyEvent.VK_UP && (!down)) {
				left=false;
				right=false;
				up=true;
			}
			
			if(key==KeyEvent.VK_DOWN && (!up)) {
				left=false;
				right=false;
				down=true;
			}
		}
	}
}
