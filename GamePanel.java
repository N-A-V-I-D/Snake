import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = ((SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE);
	static final int DELAY =75;
	final int x[] = new int[GAME_UNITS];// will hold x coords
	final int y[] = new int[GAME_UNITS];//will hold y coords
	int bodyParts = 6;
	int applesEaten;
	int applesX;
	int applesY;
	char direction = 'S';
	boolean running =  false;
	Timer timer;
	Random random;
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		//after setting the window size, colors, etc., the game starts
	}
	public void startGame() {// starts game
		newApple();//when we start the game apples form
		running = true; //it's false to begin with
		timer = new Timer(DELAY, this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);	
	}
	public void draw(Graphics g) {
		
		if(running) {
			for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}
			g.setColor(Color.red);//sets the color of the apple to red
			g.fillOval(applesX, applesY, UNIT_SIZE, UNIT_SIZE);// sets the shape of the  apple to be an oval which has a length and width of the variable unit size
		
			for (int i = 0;i<bodyParts;i++) {
				if( i == 0){
					//head of the snake
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			
				}
				else {
					g.setColor(new Color(45, 180, 0));//if it isn't 0(not the head) then it will be a different color so you will always know which part of the snake is the head
					g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				
			}
			g.setColor(Color.red);
			g.setFont(new Font("Ink Tree",Font.BOLD,45));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten, (SCREEN_WIDTH-metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
			
			 
		 }
		else {
			gameOver(g);
		}
		
		}
	public void newApple() {
		applesX = random.nextInt((int) ( (SCREEN_WIDTH/UNIT_SIZE) ))*UNIT_SIZE;//generates a random x for the apple
		applesY = random.nextInt((int) ( (SCREEN_HEIGHT/UNIT_SIZE) ))*UNIT_SIZE;//generates a random y for the apple
		
		
		
	}
	public void move() {
		for(int i = bodyParts; i>0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}

		switch(direction) {//the wasd movement of the snake
		case 'W':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'S':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'A':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'D':
			x[0] = x[0] + UNIT_SIZE;
			break;
			
		
		}
		
	
	}
	public void checkApple() {
		if((x[0] == applesX) && (y[0] == applesY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
		
	}
	public void checkCollisions() {
		for (int i=bodyParts; i>0;i--) {
			if((x[0]==x[i])&& (y[0]==y[i])) {//if the head collides with the body part
				running = false; //if it collides then the game isn't running anymore, it ends
			}
		}
		if(x[0] < 0) {//if the head collides with the left border
			running = false;
		}
		
		if(x[0] > SCREEN_WIDTH) {// if the head collides with the right border
			running = false;
		}
		
		if(y[0] < 0) {//if the head collides with the top border
			running = false;
		}
		
		if(y[0] > SCREEN_HEIGHT) {// if the head collides with the bottom border
			running = false;
		}
		
		if(!running) {// the previous if statements make it so that if the snake touches the borders or itself the game stops running, this makes it so that when the game stops running the timer also stops
			timer.stop();
		}
		
	}
	public void gameOver(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Ink Tree",Font.BOLD,75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH-metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
		
		g.setColor(Color.red);
		g.setFont(new Font("Ink Tree",Font.BOLD,45));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten, (SCREEN_WIDTH-metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}

	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if( direction != 'D') {
					direction = 'A';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if( direction != 'A') {
					direction = 'D';
				}
				break;
			case KeyEvent.VK_UP:
				if( direction != 'S') {
					direction = 'W';
				}
				break;
			case KeyEvent.VK_DOWN:
				if( direction != 'W') {
					direction = 'S';
				}
				break;
			}
			
		}
		
	}

}
