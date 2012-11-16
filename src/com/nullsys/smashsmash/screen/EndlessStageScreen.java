package com.nullsys.smashsmash.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input.Keys;
import com.nullsys.smashsmash.User;
import com.nullsys.smashsmash.alien.Alien;
import com.nullsys.smashsmash.buffeffect.BuffEffect;

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
	if (!User.hasBuffEffect(BuffEffect.INVULNERABILITY) && session.lifePoints > 0) {
	    ui.shakeLifePoint();
	    session.lifePoints--;
	}
	if (session.lifePoints <= 0) {
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
	} else if (session.stageSecondsElapsed >= 10 && session.stageSecondsElapsed < 30)
	    spawnRate = 2;
	else if (session.stageSecondsElapsed >= 30 && session.stageSecondsElapsed < 50)
	    spawnRate = 2;
	else if (session.stageSecondsElapsed >= 50 && session.stageSecondsElapsed < 70)
	    spawnRate = 3;
	else if (session.stageSecondsElapsed >= 70 && session.stageSecondsElapsed < 90)
	    spawnRate = 4;
	else if (session.stageSecondsElapsed >= 90 && session.stageSecondsElapsed < 130)
	    spawnRate = 5;
	else if (session.stageSecondsElapsed >= 130)
	    spawnRate = (int) (Math.random() * aliens.size());
    }
}
