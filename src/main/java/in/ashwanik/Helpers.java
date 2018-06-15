package in.ashwanik;

/**
 * Created by Ashwani Kumar on 15/06/18.
 */
public class Helpers {
    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
