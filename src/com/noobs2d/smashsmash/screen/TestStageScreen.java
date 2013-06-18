package com.noobs2d.smashsmash.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input.Keys;
import com.noobs2d.smashsmash.Settings;
import com.noobs2d.smashsmash.User;
import com.noobs2d.smashsmash.alien.Jelly;
import com.noobs2d.smashsmash.buffeffect.BuffEffect;

/**
 * For the sole purpose of different tests. No time limit, toggleable cheats.
 * 
 * @author MrUseL3tter
 */
public class TestStageScreen extends SmashSmashStage {

    private static final boolean ALL_HOSTILE = true;
    private static final boolean INFINITE_HAMMERTIME = false;
    private static final boolean INFINITE_INVULNERABILITY = false;
    private static final boolean INFINITE_SCORE_FRENZY = false;
    private static final boolean COINS_ALLOWED = false;
    private static final boolean GOLDBAR_ALLOWED = false;
    private static final boolean PUKE_ALLOWED = false;

    public TestStageScreen(Game game) {
	super(game);
	if (INFINITE_HAMMERTIME)
	    User.addBuffEffect(BuffEffect.HAMMER_TIME, Long.MAX_VALUE);
	if (INFINITE_INVULNERABILITY)
	    User.addBuffEffect(BuffEffect.INVULNERABILITY, Long.MAX_VALUE);
	if (INFINITE_SCORE_FRENZY)
	    User.addBuffEffect(BuffEffect.SCORE_FRENZY, Long.MAX_VALUE);
    }

    @Override
    public boolean keyUp(int keycode) {
	// restarts the game if the user presses left CTRL on keyboard or MENU on phone
	// DEBUG MODE only
	if (Settings.DEBUG_MODE && keycode == Keys.CONTROL_LEFT || keycode == Keys.MENU)
	    game.setScreen(new TestStageScreen(game));
	return super.keyUp(keycode);
    }

    @Override
    protected void addCoin(float x, float y) {
	if (COINS_ALLOWED)
	    super.addCoin(x, y);
    }

    @Override
    protected void addGoldBar(float x, float y) {
	if (GOLDBAR_ALLOWED)
	    super.addGoldBar(x, y);
    }

    @Override
    protected void addPukes() {
	if (PUKE_ALLOWED)
	    super.addPukes();
    }

    @Override
    protected void initAliens() {
	aliens.add(new Jelly(this));
	aliens.add(new Jelly(this));
	aliens.add(new Jelly(this));
	aliens.add(new Jelly(this));
	aliens.add(new Jelly(this));
	aliens.add(new Jelly(this));
	aliens.add(new Jelly(this));
	//	aliens.add(new Jelly(this));
	for (int i = 0; i < aliens.size(); i++)
	    aliens.get(i).setVisible(false);
	setAliensHostile(ALL_HOSTILE);
    }

    @Override
    protected void setSpawnRate() {
	spawnRate = aliens.size();
    }

}
