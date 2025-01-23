import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Main {
   public static void main(String[] args) {
      JFrame frame = new JFrame("Doodle Jump");
      frame.setLayout(new BorderLayout());
      frame.setLocation(20, 20);
      frame.setDefaultCloseOperation(3);
      DoodleJump doodleJump = new DoodleJump();
      frame.add(doodleJump, "Center");
      frame.setSize(440, 600);
      frame.setVisible(true);
   }
}
