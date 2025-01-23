import java.util.Random;

public class Randomizer {
   public static Random theInstance = null;

   public static Random getInstance() {
      if (theInstance == null) {
         theInstance = new Random();
      }

      return theInstance;
   }

   public static boolean nextBoolean() {
      return getInstance().nextBoolean();
   }

   public static boolean nextBoolean(double probability) {
      return nextDouble() < probability;
   }

   public static int nextInt() {
      return getInstance().nextInt();
   }

   public static int nextInt(int n) {
      return getInstance().nextInt(n);
   }

   public static int nextInt(int min, int max) {
      return min + nextInt(max - min + 1);
   }

   public static double nextDouble() {
      return getInstance().nextDouble();
   }

   public static double nextDouble(double min, double max) {
      return min + (max - min) * nextDouble();
   }

   public static String nextColor() {
      String randomNum = Integer.toHexString(nextInt(0, 16777216));
      return "'#" + randomNum + "'";
   }
}
