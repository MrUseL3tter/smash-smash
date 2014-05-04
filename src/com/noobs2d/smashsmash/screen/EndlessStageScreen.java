package com.noobs2d.smashsmash.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input.Keys;
import com.noobs2d.smashsmash.User;
import com.noobs2d.smashsmash.alien.Alien;
import com.noobs2d.smashsmash.buffeffect.BuffEffect;

public class EndlessStageScreen extends SmashSmashStage {

    public EndlessStageScreen(Game game) {
	super(game);
    }

    @Override
    public boolean keyUp(int keycode) {
	// TODO disable for stable release
	if (keycode == Keys.CONTROL_LEFT || keycode == Keys.MENU)
	    game.setScreen(new EndlessStageScreen(game));
	return super.keyUp(keycode);
    }

    @Override
    public void onAlienAttack(Alien alien) {
	super.onAlienAttack(alien);
	if (!User.hasBuffEffect(BuffEffect.INVULNERABILITY) && getSession().getLifepoints() > 0) {
	    headsUpDisplay.shakeLifePoint();
	    getSession().decrementLifepoints();
	}
	if (getSession().getLifepoints() <= 0) {
	    game.setScreen(new ResultScreen(game, this));
	    for (int i = 0; i < aliens.size(); i++)
		aliens.get(i).setHostile(false);
	}
    }

    @Override
    public void resume() {
	super.resume();
	//	game.setScreen(new StageLoadingScreen(game, this, Settings.getAssetManager()));
    }

    @Override
    protected void setSpawnRate() {
	spawnRate = 2;
	if (!isSpawnAllowed()) {
	    spawnRate = 0;
	    return;
	} else if (getSession().getStageSecondsElapsed() >= 10 && getSession().getStageSecondsElapsed() < 30)
	    spawnRate = 2;
	else if (getSession().getStageSecondsElapsed() >= 30 && getSession().getStageSecondsElapsed() < 50)
	    spawnRate = 2;
	else if (getSession().getStageSecondsElapsed() >= 50 && getSession().getStageSecondsElapsed() < 70)
	    spawnRate = 3;
	else if (getSession().getStageSecondsElapsed() >= 70 && getSession().getStageSecondsElapsed() < 90)
	    spawnRate = 4;
	else if (getSession().getStageSecondsElapsed() >= 90 && getSession().getStageSecondsElapsed() < 130)
	    spawnRate = 5;
	else if (getSession().getStageSecondsElapsed() >= 130)
	    // FIXME not really a good idea IMO
	    spawnRate = (int) (Math.random() * aliens.size());
    }
}
