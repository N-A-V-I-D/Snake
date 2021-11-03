import javax.swing.JFrame;

public class GameFrame extends JFrame{
	GameFrame(){
		this.add(new GamePanel()); //Similar to the first comment on SnakeGame.java
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();// fit the jframe around the things that we will add to the frame
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		
		
	}
}
