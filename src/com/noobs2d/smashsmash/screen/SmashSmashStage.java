package com.noobs2d.smashsmash.screen;

import java.util.ArrayList;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Linear;
import aurelienribon.tweenengine.equations.Quad;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.noobs2d.smashsmash.Art;
import com.noobs2d.smashsmash.Coin;
import com.noobs2d.smashsmash.GoldBar;
import com.noobs2d.smashsmash.Session;
import com.noobs2d.smashsmash.Settings;
import com.noobs2d.smashsmash.Sounds;
import com.noobs2d.smashsmash.User;
import com.noobs2d.smashsmash.alien.Alien;
import com.noobs2d.smashsmash.alien.Bomb;
import com.noobs2d.smashsmash.alien.Diabolic;
import com.noobs2d.smashsmash.alien.Fluff;
import com.noobs2d.smashsmash.alien.Golem;
import com.noobs2d.smashsmash.alien.HammerTimeJelly;
import com.noobs2d.smashsmash.alien.InvulnerabilityJelly;
import com.noobs2d.smashsmash.alien.Jelly;
import com.noobs2d.smashsmash.alien.Ogre;
import com.noobs2d.smashsmash.alien.ScoreFrenzyJelly;
import com.noobs2d.smashsmash.alien.Sorcerer;
import com.noobs2d.smashsmash.alien.Tortoise;
import com.noobs2d.smashsmash.buffeffect.BuffEffect;
import com.noobs2d.smashsmash.hammer.HammerEffect;
import com.noobs2d.smashsmash.hammer.HammerEffectPool;
import com.noobs2d.tweenengine.utils.DynamicAnimation;
import com.noobs2d.tweenengine.utils.DynamicCallback.RemoveFromCollectionOnEnd;
import com.noobs2d.tweenengine.utils.DynamicScreen;
import com.noobs2d.tweenengine.utils.DynamicSprite;

/**
 * @author MrUseL3tter
 */
public class SmashSmashStage extends DynamicScreen implements AlienEventListener {

    /** maximum duration before the combo expires in seconds. */
    public static final int COMBO_MAX_DURATION = 3;

    public ArrayList<HammerEffect> hammerEffects = new ArrayList<HammerEffect>();
    public ArrayList<DynamicAnimation> coinsAndGoldBars = new ArrayList<DynamicAnimation>();
    public ArrayList<DynamicSprite> pukes = new ArrayList<DynamicSprite>();
    public ArrayList<Alien> aliens = new ArrayList<Alien>();

    public DynamicSprite buffEffectBlackFill;
    public DynamicSprite buffEffectPinwheelBlue;
    public DynamicSprite buffEffectPinwheelGreen;

    public boolean[] pointers = new boolean[4];

    protected HeadsUpDisplay headsUpDisplay = new HeadsUpDisplay(this);
    private Session session = new Session();

    private boolean showHUD = true;

    private boolean allowSpawn = true;
    private boolean paused = false;
    protected int spawnDelay = 0;
    protected int spawnRate = 0;
    protected int streaks = 0;
    private static final float COIN_DURATION = 7f;

    public SmashSmashStage(Game game) {
	super(game, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
	initAliens();

	buffEffectBlackFill = new DynamicSprite(new TextureRegion(Art.blackFill), Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
	buffEffectBlackFill.scale.set(15f, 15f);
	buffEffectBlackFill.setColor(1f, 1f, 1f, 0f);
	buffEffectPinwheelBlue = new DynamicSprite(new TextureRegion(Art.pinwheel), Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
	buffEffectPinwheelBlue.scale.set(3f, 3f);
	buffEffectPinwheelBlue.setColor(1f, 1f, 1f, 0f);
	buffEffectPinwheelBlue.interpolateRotation(360, Linear.INOUT, 3000, false).repeat(Tween.INFINITY, 0).start(buffEffectPinwheelBlue.getTweenManager());
	buffEffectPinwheelGreen = new DynamicSprite(new TextureRegion(Art.pinwheel2), Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
	buffEffectPinwheelGreen.scale.set(3f, 3f);
	buffEffectPinwheelGreen.setColor(1f, 1f, 1f, 0f);
	buffEffectPinwheelGreen.setRotation(-1);
	buffEffectPinwheelGreen.interpolateRotation(-360, Linear.INOUT, 3000, false).repeat(Tween.INFINITY, 0).start(buffEffectPinwheelGreen.getTweenManager());

	Gdx.input.setCatchBackKey(true);
	Gdx.input.setCatchMenuKey(true);

	User.init();
    }

    public int getComboMultiplier() {
	// TODO add constants for multiplier min and max
	int multiplier = 1;
	if (session.getCurrentCombo() > 4 && session.getCurrentCombo() < 25)
	    multiplier = 2;
	else if (session.getCurrentCombo() > 24 && session.getCurrentCombo() < 40)
	    multiplier = 3;
	else if (session.getCurrentCombo() > 39 && session.getCurrentCombo() < 60)
	    multiplier = 4;
	else if (session.getCurrentCombo() > 59 && session.getCurrentCombo() < 100)
	    multiplier = 5;
	else if (session.getCurrentCombo() > 99 && session.getCurrentCombo() < 255)
	    multiplier = 7;
	else if (session.getCurrentCombo() > 254)
	    multiplier = 10;
	if (User.hasBuffEffect(BuffEffect.SCORE_FRENZY))
	    multiplier *= 3;
	return multiplier;
    }

    public Session getSession() {
	return session;
    }

    public int getSpawnRate() {
	return spawnRate;
    }

    public boolean isPaused() {
	return paused;
    }

    public boolean isSpawnAllowed() {
	return allowSpawn;
    }

    public boolean isUIVisible() {
	return showHUD;
    }

    @Override
    public boolean keyUp(int keycode) {
	if (keycode == Keys.ENTER && !paused)
	    pause();
	else if (keycode == Keys.ENTER)
	    resume();
	else if (keycode == Keys.BACK && Gdx.app.getType() == ApplicationType.Android)
	    game.setScreen(new PauseScreen(game, this));
	else if (keycode == Keys.BACKSPACE)
	    game.setScreen(new PauseScreen(game, this));
	return false;
    }

    @Override
    public void onAlienAttack(Alien alien) {
	if (alien instanceof Bomb) {
	    session.resetCurrentCombo();
	    camera.shake();
	    removeBonusEffects();
	    showWhiteFadeEffect();
	} else if (!User.hasBuffEffect(BuffEffect.INVULNERABILITY)) {
	    session.resetCurrentCombo();
	    camera.shake();
	    removeBonusEffects();
	    addPukes();
	}
    }

    @Override
    public void onAlienEscaped(Alien alien) {
	session.incrementEscapedAliens();
    }

    @Override
    public void onAlienHit(Alien alien) {
	addScore(alien);
    }

    @Override
    public void onAlienSmashed(Alien alien) {
	boolean isNotBomb = !(alien instanceof Bomb);
	if (isNotBomb) {
	    addScore(alien);
	    session.incrementSmashedAliens();

	    // TODO add proper percentage calculation like on Alien.isCritical()
	    // Add coin as per percentage
	    int random = (int) (Math.random() * 1000);
	    if (random <= 4)
		addGoldBar(alien.position.x, alien.position.y);
	    else if (random > 4 && random <= 25)
		addCoin(alien.position.x, alien.position.y);
	}
    }

    @Override
    public void onBonusEffectTrigger(Alien alien, int... buffEffects) {
	for (int i = 0; i < buffEffects.length; i++)
	    switch (buffEffects[i]) {
		case BuffEffect.HAMMER_TIME:
		    showBuffEffect(buffEffects[i]);
		    if (!(alien instanceof Sorcerer))
			headsUpDisplay.showBuffEffectPrompt(BuffEffect.HAMMER_TIME, alien.position.x, alien.position.y);
		    else
			User.addBuffEffect(BuffEffect.HAMMER_TIME);
		    break;
		case BuffEffect.INVULNERABILITY:
		    showBuffEffect(buffEffects[i]);
		    if (!(alien instanceof Sorcerer))
			headsUpDisplay.showBuffEffectPrompt(BuffEffect.INVULNERABILITY, alien.position.x, alien.position.y);
		    else
			User.addBuffEffect(BuffEffect.INVULNERABILITY);
		    break;
		case BuffEffect.SCORE_FRENZY:
		    showBuffEffect(buffEffects[i]);
		    if (!(alien instanceof Sorcerer))
			headsUpDisplay.showBuffEffectPrompt(BuffEffect.SCORE_FRENZY, alien.position.x, alien.position.y);
		    else
			User.addBuffEffect(BuffEffect.SCORE_FRENZY);
		    break;
		default:
		    assert false;
		    break;
	    }
    }

    public void onSmashMissed(float x, float y) {
	//	Settings.log(getClass().getName(), "onSmashMissed(float,float)", "");
	session.incrementMissedSmashes();
	if (session.getCurrentCombo() > 1)
	    headsUpDisplay.showMissPrompt(x, y);
	headsUpDisplay.showComboPrompt();
	setHighestCombo();
	session.resetCurrentCombo();
    }

    @Override
    public void onTouchDown(float x, float y, int pointer, int button) {
	if (!paused) {
	    Vector2 position = new Vector2(x, y);
	    position.x *= (float) Settings.SCREEN_WIDTH / Gdx.graphics.getWidth();
	    position.y = (Gdx.graphics.getHeight() * camera.zoom - position.y) * Settings.SCREEN_HEIGHT / Gdx.graphics.getHeight();

	    boolean touchedACoin = inputToCoinsAndGoldBars(position.x, position.y, pointer);
	    // We will only test collisions with the aliens if
	    // there are no collisions with any coin
	    boolean touchedAnAlien = !touchedACoin ? inputToAliens(position.x, position.y, pointer) : false;

	    if (!touchedAnAlien && !touchedACoin && !User.hasBuffEffect(BuffEffect.HAMMER_TIME))
		onSmashMissed(position.x, position.y);

	    if (!touchedACoin) { // Hammer effects will only be added if a coin is not tapped
		session.incrementLandedSmashes();
		if (User.hasBuffEffect(BuffEffect.HAMMER_TIME))
		    camera.shake();
		addHammerEffect(position.x, position.y);
		if (Settings.soundEnabled)
		    Sounds.hammerIceFlakes.play();
	    }
	}
    }

    @Override
    public void pause() {
	Settings.log("SmashSmashStage", "pause()", "");
	paused = true;
	buffEffectBlackFill.pauseTween();
	buffEffectPinwheelBlue.pauseTween();
	buffEffectPinwheelGreen.pauseTween();
	for (int i = 0; i < coinsAndGoldBars.size(); i++)
	    coinsAndGoldBars.get(i).pauseTween();
	for (int i = 0; i < pukes.size(); i++)
	    pukes.get(i).pauseTween();
	for (int i = 0; i < aliens.size(); i++)
	    aliens.get(i).pauseTween();
    }

    @Override
    public void render(float delta) {
	getCamera().update();
	batch.setProjectionMatrix(camera.projection);

	delta = paused ? 0 : delta;

	session.addToStageSecondsElapsed(delta);

	batch.begin();
	renderStage(batch, delta);
	renderAliens(batch, delta);
	renderCoinsAndGoldBars(batch, delta);
	renderHammerEffects(batch, delta);
	renderPukes(batch, delta);
	renderStageEffects(batch, delta);
	if (showHUD) {
	    headsUpDisplay.render(batch);
	    headsUpDisplay.update(delta);
	}
	batch.end();
	batch.setColor(1f, 1f, 1f, 1f);
	checkComboTime();
	checkStreaks();
    }

    @Override
    public void resume() {
	Settings.log("SmashSmashStage", "resume()", "");
	paused = false;
	buffEffectBlackFill.resumeTween();
	buffEffectPinwheelBlue.resumeTween();
	buffEffectPinwheelGreen.resumeTween();
	for (int i = 0; i < coinsAndGoldBars.size(); i++)
	    coinsAndGoldBars.get(i).resumeTween();
	for (int i = 0; i < pukes.size(); i++)
	    pukes.get(i).resumeTween();
	for (int i = 0; i < aliens.size(); i++)
	    aliens.get(i).resumeTween();
    }

    public void setAliensHostile(boolean hostile) {
	for (int i = 0; i < aliens.size(); i++)
	    aliens.get(i).setHostile(hostile);
    }

    public void setAllowSpawn(boolean allowSpawn) {
	this.allowSpawn = allowSpawn;
    }

    public void setUIVisible(boolean showUI) {
	this.showHUD = showUI;
    }

    private boolean hasComboDurationPassed() {
	return session.getStageSecondsElapsed() - session.getComboLastDelta() >= COMBO_MAX_DURATION && session.getCurrentCombo() > 0;
    }

    private void setHighestCombo() {
	int highestCombo = 0;
	if (session.getCurrentCombo() > session.getHighestCombo())
	    highestCombo = session.getCurrentCombo();
	else
	    highestCombo = session.getHighestCombo();
	session.setHighestCombo(highestCombo);
    }

    protected void addCoin(float x, float y) {
	Coin coin = new Coin(Art.coins, 0, 0, 64, 64, 8, 8, .125f);
	coin.position.set(x, y);
	coin.interpolateXY(x, y + 100, Quad.OUT, 200, true);
	coin.interpolateXY(x, y, Quad.IN, 200, true).delay(200);
	coinsAndGoldBars.add(coin);
    }

    protected void addGoldBar(float x, float y) {
	GoldBar g = new GoldBar(1f, Art.goldbar.findRegion("GOLDBAR"));
	g.setPosition(x, y);
	g.interpolateXY(x, y + 100, Quad.OUT, 200, true);
	g.interpolateXY(x, y, Quad.IN, 200, true).delay(200);
	coinsAndGoldBars.add(g);
    }

    protected void addHammerEffect(float x, float y) {
	HammerEffect hammerEffect = HammerEffectPool.obtain();
	hammerEffect.setPosition(x, y);
	hammerEffects.add(hammerEffect);
    }

    /** show pukes in the screen; interpolating, scaling, and positioning them. */
    protected void addPukes() {
	int maxPukes = 5;
	float xOffsetMin = 300f;
	float xOffsetMax = 680f;
	float yOffsetMin = 200f;
	float yOffsetMax = 400f;
	float scaleMin = 0.75f;
	float scaleMax = 1.5f;
	int pukesCount = (int) (maxPukes * Math.random()) + 1;
	for (int i = 0; i < pukesCount; i++) {
	    float x = (float) (xOffsetMin + Math.random() * xOffsetMax);
	    float y = (float) (yOffsetMin + Math.random() * yOffsetMax);
	    float targetScale = (float) (scaleMin + Math.random() * scaleMax);
	    DynamicSprite puke = new DynamicSprite(Art.pukes.findRegion("PUKE_GREEN"), x, y);
	    puke.setScale(0f, 0f);
	    puke.setRotation((float) (360 * Math.random()));
	    puke.interpolateScaleXY(1f * targetScale, 1f * targetScale, 250, true).delay(i * 100);
	    puke.interpolateAlpha(0f, 2000, true).delay(i * 100);
	    pukes.add(puke);
	}
    }

    protected void addScore(Alien alien) {
	int score = alien.getScore() * getComboMultiplier();
	if (streaks >= 1)
	    score *= streaks;
	session.addToScore(score);
    }

    protected boolean buffAliensVisible() {
	boolean visible = false;
	for (int i = 0; i < aliens.size(); i++)
	    if (aliens.get(i).isVisible() && (aliens.get(i) instanceof HammerTimeJelly || aliens.get(i) instanceof InvulnerabilityJelly || aliens.get(i) instanceof ScoreFrenzyJelly)) {
		visible = true;
		break;
	    }
	return visible;
    }

    protected void checkComboTime() {
	if (hasComboDurationPassed()) {
	    headsUpDisplay.showComboPrompt();
	    setHighestCombo();
	    session.resetCurrentCombo();
	}
    }

    protected void checkStreaks() {
	// update streaks
	streaks = 0;
	for (int i = 0; i < pointers.length; i++)
	    if (pointers[i])
		streaks++;
	if (streaks >= 2) {
	    headsUpDisplay.showStreakPrompt(streaks);
	    for (int i = 0; i < pointers.length; i++)
		pointers[i] = false;
	}

	try {
	    if (session.getStageSecondsElapsed() > 0 && Integer.parseInt(("" + session.getStageSecondsElapsed()).split(".")[1]) % 2 == 0) // .2 seconds has passed
		for (int i = 0; i < pointers.length; i++)
		    pointers[i] = false;
	} catch (ArrayIndexOutOfBoundsException e) {
	}
    }

    protected int getVisibleAliens() {
	int visibles = 0;
	for (int i = 0; i < aliens.size(); i++)
	    if (aliens.get(i).isVisible())
		visibles++;
	return visibles;
    }

    protected void initAliens() {
	aliens.add(new Bomb(this));
	aliens.add(new Bomb(this));
	aliens.add(new Bomb(this));
	aliens.add(new Diabolic(this));
	aliens.add(new Diabolic(this));
	aliens.add(new Diabolic(this));
	aliens.add(new Fluff(this));
	aliens.add(new Fluff(this));
	aliens.add(new Fluff(this));
	aliens.add(new Golem(this));
	aliens.add(new Golem(this));
	aliens.add(new Golem(this));
	aliens.add(new Jelly(this));
	aliens.add(new Jelly(this));
	aliens.add(new Jelly(this));
	aliens.add(new Ogre(this));
	aliens.add(new Ogre(this));
	aliens.add(new Ogre(this));
	aliens.add(new Tortoise(this));
	aliens.add(new Tortoise(this));
	aliens.add(new Tortoise(this));
	aliens.add(new Sorcerer(this));
	aliens.add(new InvulnerabilityJelly(this));
	aliens.add(new HammerTimeJelly(this));
	aliens.add(new ScoreFrenzyJelly(this));
	for (int i = 0; i < aliens.size(); i++)
	    aliens.get(i).setVisible(false);
    }

    protected boolean inputToAliens(float x, float y, int pointer) {
	int diameter = User.hammer.getDiameter();
	Rectangle hammerBounds = new Rectangle(x - diameter / 2, y - diameter / 2, diameter, diameter);
	int hitCount = 0;
	for (int i = 0; i < aliens.size(); i++) {
	    boolean hit = aliens.get(i).hit(hammerBounds);
	    if (hit && aliens.get(i) instanceof Bomb) {
		aliens.get(i).smash();
		pointers[pointer] = false;
		return true;
	    } else if (hit) {
		hitCount++;
		session.incrementCurrentCombo();
		session.setCombosLastDelta(session.getStageSecondsElapsed());
		headsUpDisplay.shakeCombos();
		aliens.get(i).smash();
		pointers[pointer] = true;
		break;
	    }
	}
	// FIXME check if actually needed
	// Display a "x + 1!" message
	//	if (hitCount >= 2) {
	//	    DynamicText text = new DynamicText(Fonts.bdCartoonShoutx23orange, hitCount + " in 1!", HAlignment.CENTER);
	//	    text.position.set(x, y - 50);
	//	    text.color.a = 0f;
	//	    text.interpolateXY(x, y + 50, Sine.OUT, 250, true);
	//	    text.interpolateAlpha(1f, Sine.OUT, 250, true);
	//	    text.interpolateAlpha(0f, Sine.OUT, 250, true).delay(1000);
	//	    ui.textPool.add(text);
	//	}
	return hitCount > 0;
    }

    protected boolean inputToCoinsAndGoldBars(float x, float y, int pointer) {
	Rectangle bounds = new Rectangle(x - 25, y - 25, 50, 50);
	boolean hit = false;
	for (int i = coinsAndGoldBars.size() - 1; i >= 0; i--)
	    if (coinsAndGoldBars.get(i).getBounds().overlaps(bounds)) {
		coinsAndGoldBars.get(i).killTween();
		coinsAndGoldBars.get(i).interpolateXY(1280, 800, Linear.INOUT, 350, true);
		coinsAndGoldBars.get(i).setTweenCallback(new RemoveFromCollectionOnEnd(coinsAndGoldBars, coinsAndGoldBars.get(i)));
		if (coinsAndGoldBars.get(i) instanceof GoldBar)
		    User.gold += 1000;
		else
		    User.gold += 10;
		hit = true;
		i = -1;
	    }
	return hit;
    }

    protected boolean isSorcererVisible() {
	boolean visible = false;
	for (int i = 0; i < aliens.size(); i++)
	    if (aliens.get(i) instanceof Sorcerer && aliens.get(i).isVisible()) {
		visible = true;
		break;
	    }
	return visible;
    }

    protected void removeBonusEffects() {
	buffEffectBlackFill.interpolateAlpha(0f, Linear.INOUT, 200, true);
	buffEffectPinwheelBlue.interpolateAlpha(0f, Linear.INOUT, 200, true);
	buffEffectPinwheelGreen.interpolateAlpha(0f, Linear.INOUT, 200, true);
	User.buffEffects.clear();
    }

    protected void renderAliens(SpriteBatch batch, float delta) {
	// Sort the aliens first according to their y-coordinate. Aliens with lowest y are rendered last.
	for (int i = 0; i < aliens.size(); i++)
	    if (aliens.get(i).isVisible())
		for (int j = i + 1; j < aliens.size(); j++)
		    if (aliens.get(j).isVisible() && aliens.get(j).position.y < aliens.get(i).position.y) {
			Alien alien = aliens.remove(j);
			aliens.add(aliens.remove(i));
			aliens.add(i, alien);
		    }
	for (int i = aliens.size() - 1; i > -1; i--) {
	    aliens.get(i).render(batch);
	    aliens.get(i).update(delta);
	}

	// FIXME set to delay before the intro prompt ends
	if (session.getStageSecondsElapsed() > 1) {
	    setSpawnRate();
	    setSpawnPositions();
	    boolean overlaps = false;
	    if (getVisibleAliens() < spawnRate)
		for (int i = 0; i < aliens.size() && getVisibleAliens() < spawnRate; i++) {
		    if (!aliens.get(i).isVisible()) {
			for (int j = 0; j < aliens.size(); j++)
			    if (i != j && aliens.get(j).isVisible() && aliens.get(i).collides(aliens.get(j))) {
				overlaps = true;
				j = aliens.size() + 1; //break this loop
			    }
			spawnDelay = i * (int) (Math.random() * 250);
			boolean buffAlien = aliens.get(i) instanceof HammerTimeJelly || aliens.get(i) instanceof InvulnerabilityJelly || aliens.get(i) instanceof ScoreFrenzyJelly;
			float volume = getVisibleAliens() > 0 ? 1.1f - getVisibleAliens() / aliens.size() : 1f;
			if (aliens.get(i) instanceof Sorcerer && !buffAliensVisible() && User.buffEffects.size() == 0 && !overlaps)
			    aliens.get(i).rise(spawnDelay, volume / 2);
			else if (aliens.get(i) instanceof HammerTimeJelly && !User.hasBuffEffect(BuffEffect.HAMMER_TIME) && !isSorcererVisible() && !overlaps)
			    aliens.get(i).rise(spawnDelay, volume / 2);
			else if (aliens.get(i) instanceof InvulnerabilityJelly && !User.hasBuffEffect(BuffEffect.INVULNERABILITY) && !isSorcererVisible() && !overlaps)
			    aliens.get(i).rise(spawnDelay, volume / 2);
			else if (aliens.get(i) instanceof ScoreFrenzyJelly && !User.hasBuffEffect(BuffEffect.SCORE_FRENZY) && !isSorcererVisible() && !overlaps)
			    aliens.get(i).rise(spawnDelay, volume / 2);
			else if (!buffAlien && !overlaps)
			    aliens.get(i).rise(spawnDelay, volume / 2);
		    }
		    overlaps = false;
		}
	}
    }

    protected void renderCoinsAndGoldBars(SpriteBatch batch, float delta) {
	for (int i = 0; i < coinsAndGoldBars.size(); i++) {
	    coinsAndGoldBars.get(i).render(batch);
	    if (coinsAndGoldBars.get(i).getTimeElapsed() > COIN_DURATION)
		coinsAndGoldBars.remove(i);
	    else
		coinsAndGoldBars.get(i).update(delta);
	}
    }

    protected void renderHammerEffects(SpriteBatch batch, float delta) {
	for (int i = 0; i < hammerEffects.size(); i++) {
	    hammerEffects.get(i).render(batch);
	    if (hammerEffects.get(i).isComplete())//elapsed >= hammerEffects.get(i).duration)
		hammerEffects.get(i).free(hammerEffects, i);
	    //		hammerEffects.remove(hammerEffects.get(i));
	    else
		hammerEffects.get(i).update(delta);
	}
    }

    protected void renderPukes(SpriteBatch batch, float delta) {
	for (int i = 0; i < pukes.size(); i++) {
	    pukes.get(i).render(batch);
	    pukes.get(i).update(delta);
	    if (pukes.get(i).getTweenManager().getRunningTweensCount() == 0)
		pukes.remove(i);
	}
    }

    protected void renderStage(SpriteBatch spriteBatch, float delta) {
	spriteBatch.draw(Art.lawnBackground1, 0, -(1024 - 800));
	spriteBatch.draw(Art.lawnBackground2, 1280 - 256, -(1024 - 800));
    }

    protected void renderStageEffects(SpriteBatch batch, float delta) {
	buffEffectPinwheelGreen.render(batch);
	buffEffectPinwheelBlue.render(batch);
	buffEffectBlackFill.render(batch);
	buffEffectBlackFill.update(delta);
	buffEffectPinwheelBlue.update(delta);
	buffEffectPinwheelGreen.update(delta);
	for (int itemEffectIndex = 0; itemEffectIndex < User.buffEffects.size(); itemEffectIndex++)
	    User.buffEffects.get(itemEffectIndex).update(delta);
    }

    protected void setSpawnPositions() {
	// FIXME remove unneccessary codes
	for (int i = 0; i < aliens.size(); i++)
	    //	    aliens.get(i).getHitBounds().width = targetWidth;
	    //	    aliens.get(i).getHitBounds().height = targetHeight;
	    if (!aliens.get(i).isVisible()) {
		float targetWidth = aliens.get(i).getStateAnimation().getLargestAreaDisplay().getWidth();
		float targetHeight = aliens.get(i).getStateAnimation().getLargestAreaDisplay().getHeight();
		float randomX = (float) (targetWidth / 2 + Math.random() * (Settings.SCREEN_WIDTH - targetWidth * 2));
		float randomY = (float) (Math.random() * (Settings.SCREEN_HEIGHT - targetHeight));
		aliens.get(i).position.set(randomX, randomY);
		//		aliens.get(i).getHitBounds().x = randomX - targetWidth / 2;
		//		aliens.get(i).getHitBounds().y = randomY;
		aliens.add((int) (i * Math.random()), aliens.remove(i));
	    }
    }

    protected void setSpawnRate() {
	// TODO add constants for multiplier min and max
	boolean hasRampage = User.hasBuffEffect(BuffEffect.INVULNERABILITY) && User.hasBuffEffect(BuffEffect.HAMMER_TIME) && User.hasBuffEffect(BuffEffect.SCORE_FRENZY);
	int aliensCount = aliens.size();
	if (allowSpawn && hasRampage)
	    spawnRate = aliensCount;
	else if (!allowSpawn)
	    spawnRate = 0;
	else if (session.getCurrentCombo() > 4 && session.getCurrentCombo() < 25)
	    spawnRate = (int) (aliensCount * .10f);
	else if (session.getCurrentCombo() > 24 && session.getCurrentCombo() < 40)
	    spawnRate = (int) (aliensCount * .35f);
	else if (session.getCurrentCombo() > 39 && session.getCurrentCombo() < 60)
	    spawnRate = (int) (aliensCount * .55f);
	else if (session.getCurrentCombo() > 59 && session.getCurrentCombo() < 100)
	    spawnRate = (int) (aliensCount * .75f);
	else if (session.getCurrentCombo() > 99 && session.getCurrentCombo() < 255)
	    spawnRate = (int) (aliensCount * .90f);
	else if (session.getCurrentCombo() > 254)
	    spawnRate = aliensCount;
    }

    protected void showBuffEffect(int buffEffect) {
	switch (buffEffect) {
	    case BuffEffect.HAMMER_TIME:
		buffEffectPinwheelGreen.color.a = 0f;
		buffEffectPinwheelGreen.interpolateAlpha(1f, Linear.INOUT, 500, true);
		buffEffectPinwheelGreen.interpolateAlpha(0f, Linear.INOUT, 500, true).delay(9000);
		User.addBuffEffect(BuffEffect.HAMMER_TIME);
		break;
	    case BuffEffect.INVULNERABILITY:
		buffEffectBlackFill.color.a = 0f;
		buffEffectBlackFill.interpolateAlpha(.35f, Linear.INOUT, 500, true);
		buffEffectBlackFill.interpolateAlpha(0f, Linear.INOUT, 500, true).delay(9000);
		User.addBuffEffect(BuffEffect.INVULNERABILITY);
		break;
	    case BuffEffect.SCORE_FRENZY:
		buffEffectPinwheelBlue.color.a = 0f;
		buffEffectPinwheelBlue.interpolateAlpha(1f, Linear.INOUT, 500, true);
		buffEffectPinwheelBlue.interpolateAlpha(0f, Linear.INOUT, 500, true).delay(9000);
		User.addBuffEffect(BuffEffect.SCORE_FRENZY);
		break;
	    default:
		assert false;
		break;
	}
    }

    /** when a bomb is smashed */
    protected void showWhiteFadeEffect() {
	setAliensHostile(false);
	DynamicSprite fill = new DynamicSprite(new TextureRegion(Art.whiteFill), Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
	fill.setScale(15f, 15f);
	fill.setAlpha(0f);
	fill.interpolateAlpha(1f, 500, true);
	fill.interpolateAlpha(0f, 1250, true).delay(500).setCallback(new TweenCallback() {

	    @Override
	    public void onEvent(int type, BaseTween<?> source) {
		setAliensHostile(true);
	    }
	});
	pukes.add(fill);
    }
}
