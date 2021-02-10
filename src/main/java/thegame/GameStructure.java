	package thegame;
	  
	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Font;
	import java.awt.Graphics;
	import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.KeyEvent;
	import java.awt.event.KeyListener;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.concurrent.ThreadLocalRandom;


import javax.swing.JPanel;
	import javax.swing.Timer;

	public class GameStructure extends JPanel implements ActionListener, KeyListener {
	    private static final long serialVersionUID = 6260582674762246325L;

	    private boolean gameOver, started;
	    private Timer timer;
	    private List<Rectangle> pipes;
	    private Rectangle test;
	    private Rectangle Birb;
		public int ticks, yFall, score;

	    public GameStructure(final int width, final int height) {
	        this.gameOver = false;
	        this.started = false;
	        this.pipes = new ArrayList<>();
	        this.score = 0;	// making the round score == 0

	        for (int i = 0; i < 5; ++i) {
	            addPipes(width, height);
	        }

	        this.Birb = new Rectangle(20, width/2-15, 30, 20);
	        test = new Rectangle(20, 1, 10, 80);

	        this.timer = new Timer(20, this);
	        this.timer.start();
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        repaint(g);
	    }

	    private void addPipes(final int width, final int height) {
	        int a = ThreadLocalRandom.current().nextInt(110,140);
	        int y2 = -180 + a;
	    	int x = 400;
	        int y = 120 + a;
	        pipes.add(new Rectangle(x, y, 20, 300));
	        pipes.add(new Rectangle(x, y2, 20, 100));
	        
	    }

	    private void repaint(Graphics g) {
	        final Dimension d = this.getSize();

	        
	        if (gameOver) {
	            g.setColor(Color.red);
	            g.fillRect(0, 0, d.width, d.height);    
	            g.setColor(Color.black);
	            g.setFont(new Font("Arial", Font.BOLD, 65));
	            g.drawString("Game over!", 15, d.width/2-24);
	            
	            
	            g.setFont(new Font("Arial", Font.BOLD, 50));
	            g.drawString("Score: " + String.valueOf(getScore()/7), 80, 300); // drawning final score of the round
	            
	            
	            return;
	        }

	
	        g.setColor(Color.cyan);
	        g.fillRect(0, 0, d.width, d.height);
	        
	        g.setColor(Color.black);
	        g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString(String.valueOf(getScore()/7), 180, 100); // updating score

	
	        for (Rectangle pipe : pipes) {
	            
	        	g.setColor(Color.green);
	            g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
 
	        }

	
	        g.setColor(Color.red);
	        g.fillRect(test.x, test.y, test.width, test.height);
	        
	        g.setColor(Color.black);
	        g.fillRect(Birb.x, Birb.y, Birb.width, Birb.height);
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {

	        if (gameOver) {
	            timer.stop();
	            return;
	        }
	        
	        int speed = 4;
	        
	        ticks++; // g�r att det bli varannan g�ng
	        
	        if (started)
	        {
	        	for (int i = 0; i < pipes.size(); i++) // best�mmer hur snabbt positionen p� de gr�na pellarna ska f�rflyttas
				{
					Rectangle column = pipes.get(i);
					column.x -= speed;
				}

	        	
				if (ticks % 2 == 0 && yFall < 15)
				{
					yFall += 1;
				}

	        	
	        	Birb.y += yFall;
	        	
	    
	        }
	        
	        if(test.intersects(pipes.get(1)))
	        {
	        	setScore();
	        }
	  
	        
	        final List<Rectangle> toRemove = new ArrayList<>();

	        for (Rectangle pipe : pipes) {
	            if (pipe.x + pipe.width < 0) {
	                toRemove.add(pipe);
	            }
	            
	            if (pipe.intersects(Birb)) {
	                gameOver = true;
	            }
	            
	        }

	        pipes.removeAll(toRemove);


	        for (int i = 0; i < toRemove.size()/2; ++i) {
	            Dimension d = getSize();
	            addPipes(d.width, d.height);
	        }
	        
	        
	        this.repaint();
	    }

	    /**
	     * If the player presses SPACE then it calls the function jump
	     */
	    @Override
	    public void keyReleased(KeyEvent e) {

	        final int kc = e.getKeyCode();

	        if (kc == KeyEvent.VK_SPACE) {
	            
	            	jump();
	        }

	    
	    }
	    
	    /**
	     * 
	     * This controls the jump effect with the interact function above.
	     */
	    public void jump()
		{
	    	
			if (!started)
			{
				started = true;
			}
			else if (!gameOver)
			{
				if (yFall > 0)
				{
					yFall = 0;
				}

				yFall -= 10;
			}
		}
	    

	    public void setScore() {
	    	score++;
	    }
	    
	    public int getScore() {
			return score;
		}


		
		
		
		
		
		@Override
	    public void keyTyped(KeyEvent e) {
	        // do nothing
	    }

	    @Override
	    public void keyPressed(KeyEvent e) {
	        // do nothing
	    }
	}
	

