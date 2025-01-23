import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DoodleJump extends JPanel implements ActionListener, KeyListener {
   private boolean showTitleScreen = true;
   private boolean playing = false;
   private boolean gameOver = false;
   private int score = 0;
   private Rectangle doodle;
   private int doodleX;
   private int doodleY;
   private int doodleWidth = 40;
   private int doodleHeight = 40;
   private boolean leftPressed = false;
   private boolean rightPressed = false;
   private int doodleSpeed = 3;
   private double velocity = 0.0D;
   private double gravity = 0.2D;
   private double jumpForce = 10.0D;
   private ArrayList<Rectangle> platforms;
   private int numPlatforms = 6;
   private int gap = 100;

   public DoodleJump() {
      this.setPreferredSize(new Dimension(440, 600));
      this.setBackground(Color.DARK_GRAY);
      this.platforms = new ArrayList();
      this.doodle = new Rectangle(220, 400, this.doodleWidth, this.doodleHeight);
      this.doodleX = this.doodle.x;
      this.doodleY = this.doodle.y;
      this.setFocusable(true);
      this.addKeyListener(this);
      Timer timer = new Timer(16, this);
      timer.start();
   }

   public void actionPerformed(ActionEvent e) {
      if (!this.showTitleScreen) {
         if (this.playing) {
            this.move();
            this.generatePlatforms();
            this.collisions();
            this.checkGameOver();
         } else if (this.gameOver) {
         }
      }

      this.repaint();
   }

   public void move() {
      if (this.leftPressed) {
         this.doodleX -= this.doodleSpeed;
         if (this.doodleX + this.doodle.width < 0) {
            this.doodleX = this.getWidth();
         }
      }

      if (this.rightPressed) {
         this.doodleX += this.doodleSpeed;
         if (this.doodleX > this.getWidth()) {
            this.doodleX = -this.doodle.width;
         }
      }

      this.velocity += this.gravity;
      this.doodleY = (int)((double)this.doodleY + this.velocity);
      if (this.doodleY < this.getHeight() / 2) {
         Rectangle platform;
         for(Iterator var1 = this.platforms.iterator(); var1.hasNext(); platform.y += this.getHeight() / 2 - this.doodleY) {
            platform = (Rectangle)var1.next();
         }

         this.doodleY = this.getHeight() / 2;
      }

      this.doodle.setLocation(this.doodleX, this.doodleY);
   }

   public void generatePlatforms() {
      int i;
      if (this.platforms.size() < this.numPlatforms) {
         i = Randomizer.nextInt(0, this.getWidth() - 60);
         int y = this.getHeight() - this.gap;
         if (this.platforms.size() > 0) {
            y = ((Rectangle)this.platforms.get(this.platforms.size() - 1)).y - this.gap;
         }

         this.platforms.add(new Rectangle(220, 500, 60, 10));
         Rectangle platform = new Rectangle(i, y, 60, 10);
         this.platforms.add(platform);
      }

      for(i = 0; i < this.platforms.size(); ++i) {
         Rectangle platform = (Rectangle)this.platforms.get(i);
         if (platform.y > this.getHeight()) {
            this.platforms.remove(i);
            --i;
         }
      }

   }

   public void collisions() {
      Iterator var1 = this.platforms.iterator();

      while(var1.hasNext()) {
         Rectangle platform = (Rectangle)var1.next();
         if (this.doodle.intersects(platform) && this.velocity > 0.0D && this.doodle.y + this.doodle.height <= platform.y + platform.height) {
            this.doodleY = platform.y - this.doodle.height;
            this.velocity = -this.jumpForce;
            ++this.score;
         }
      }

   }

   public void keyPressed(KeyEvent e) {
      if (this.showTitleScreen) {
         if (e.getKeyCode() == 83) {
            this.showTitleScreen = false;
            this.playing = true;
         }
      } else if (this.playing) {
         if (e.getKeyCode() == 37) {
            this.leftPressed = true;
         } else if (e.getKeyCode() == 39) {
            this.rightPressed = true;
         }
      } else if (this.gameOver && e.getKeyCode() == 82) {
         this.resetGame();
      }

   }

   public void keyReleased(KeyEvent e) {
      if (this.playing) {
         if (e.getKeyCode() == 37) {
            this.leftPressed = false;
         } else if (e.getKeyCode() == 39) {
            this.rightPressed = false;
         }
      }

   }

   public void keyTyped(KeyEvent e) {
   }

   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (this.showTitleScreen) {
         g.setColor(Color.WHITE);
         g.setFont(new Font("Dialog", 1, 48));
         g.drawString("Doodle Jump", this.getWidth() / 2 - 170, this.getHeight() / 2 - 20);
         g.setFont(new Font("Dialog", 0, 24));
         g.drawString("Press 'S' to Play", this.getWidth() / 2 - 90, this.getHeight() / 2 + 30);
      } else if (this.playing) {
         g.setColor(Color.MAGENTA);
         g.fillRect(this.doodle.x, this.doodle.y, this.doodle.width, this.doodle.height);
         g.setColor(Color.GREEN);
         Iterator var2 = this.platforms.iterator();

         while(var2.hasNext()) {
            Rectangle platform = (Rectangle)var2.next();
            g.fillRect(platform.x, platform.y, platform.width, platform.height);
         }

         g.setColor(Color.WHITE);
         g.setFont(new Font("Dialog", 1, 24));
         g.drawString("Score " + this.score, 20, 30);
      } else {
         g.setFont(new Font("Dialog", 1, 48));
         g.setColor(Color.RED);
         g.drawString("YOU LOST \ud83d\ude21", this.getWidth() / 2 - 170, this.getHeight() / 2 - 20);
         g.setColor(Color.WHITE);
         g.setFont(new Font("Dialog", 0, 24));
         g.drawString("Press 'R' to play again", this.getWidth() / 2 - 130, this.getHeight() / 2 + 30);
      }

   }

   public void checkGameOver() {
      if (this.doodleY > this.getHeight()) {
         this.playing = false;
         this.gameOver = true;
      }

   }

   private void resetDoodle() {
      this.doodle.setLocation(220, 400);
      this.doodleY = this.doodle.y;
      this.doodleX = this.doodle.x;
   }

   private void resetGame() {
      this.score = 0;
      this.velocity = 0.0D;
      this.platforms.clear();
      this.resetDoodle();
      this.gameOver = false;
      this.playing = false;
      this.showTitleScreen = true;
   }
}
