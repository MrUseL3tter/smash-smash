import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.nullsys.smashsmash.Settings;
import com.nullsys.smashsmash.SmashSmash;

public class SmashSmashDesktop {

    public static void main(String[] args) {
	new LwjglApplication(new SmashSmash(), "Smash! Smash!", Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT, false);
    }

}
