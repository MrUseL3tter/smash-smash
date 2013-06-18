import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.noobs2d.smashsmash.Settings;
import com.noobs2d.smashsmash.SmashSmash;

public class SmashSmashDesktop {

    public static void main(String[] args) {
	new LwjglApplication(new SmashSmash(), "Smash! Smash!", Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT, false);
    }

}
