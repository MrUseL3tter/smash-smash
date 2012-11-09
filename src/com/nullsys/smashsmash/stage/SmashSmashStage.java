package com.nullsys.smashsmash.stage;

import java.util.ArrayList;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Linear;
import aurelienribon.tweenengine.equations.Quad;
import aurelienribon.tweenengine.equations.Sine;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.noobs2d.tweenengine.utils.DynamicAnimation;
import com.noobs2d.tweenengine.utils.DynamicCallback.RemoveFromCollectionOnEnd;
import com.noobs2d.tweenengine.utils.DynamicScreen;
import com.noobs2d.tweenengine.utils.DynamicSprite;
import com.noobs2d.tweenengine.utils.DynamicText;
import com.nullsys.smashsmash.Art;
import com.nullsys.smashsmash.Coin;
import com.nullsys.smashsmash.Fonts;
import com.nullsys.smashsmash.Session;
import com.nullsys.smashsmash.Settings;
import com.nullsys.smashsmash.Sounds;
import com.nullsys.smashsmash.User;
import com.nullsys.smashsmash.alien.Alien;
import com.nullsys.smashsmash.alien.Alien.AlienState;
import com.nullsys.smashsmash.alien.Diabolic;
import com.nullsys.smashsmash.alien.Fluff;
import com.nullsys.smashsmash.alien.Golem;
import com.nullsys.smashsmash.alien.Jelly;
import com.nullsys.smashsmash.alien.Ogre;
import com.nullsys.smashsmash.alien.Sorcerer;
import com.nullsys.smashsmash.alien.Tortoise;
import com.nullsys.smashsmash.bonuseffect.BonusEffect;
import com.nullsys.smashsmash.bonuseffect.HammerTime;
import com.nullsys.smashsmash.bonuseffect.Invulnerability;
import com.nullsys.smashsmash.bonuseffect.ScoreFrenzy;
import com.nullsys.smashsmash.hammer.HammerEffect;

/**
 * @author MrUseL3tter
 */
public class SmashSmashStage extends DynamicScreen implements SmashSmashStageCallback {

    /** maximum duration before the combo expires. */
    public static final int COMBO_MAX_DURATION = 3;
    /** seconds before being able to smash again after being puked on */
    public static final int RECOVERY_DISABILITY_DURATION = 10;

    public ArrayList<BonusEffect> bonusEffects = new ArrayList<BonusEffect>();
    public ArrayList<HammerEffect> hammerEffects = new ArrayList<HammerEffect>();
    public ArrayList<DynamicAnimation> coins = new ArrayList<DynamicAnimation>();
    public ArrayList<DynamicSprite> pukes = new ArrayList<DynamicSprite>();

    public DynamicSprite bonusEffectBlackFill;
    public DynamicSprite bonusEffectPinwheel;

    public final Alien[] aliens = new Alien[17];
    public final boolean[] pointers = new boolean[4];

    protected UserInterface ui;
    protected Session session;

    private boolean showUI = true;
    private boolean allowSpawn = true;
    private boolean paused = false;

    /**
     * becomes RECOVERY_DISABILITY_DURATION and continuously decremented when an alien attacks the
     * player successfully. player can only smash when recoverDelay is below 1.
     */
    private float recoveryDelay = 0;
    private float inputDelay = 0;
    private float sorcererSpawnDelay = 0;
    protected int spawnDelay = 0;
    protected int spawnRate = 0;
    protected int streaks = 0;

    public SmashSmashStage(Game game) {
	super(game, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
	session = new Session();
	initAliens();
	initHUD();

	bonusEffectBlackFill = new DynamicSprite(new TextureRegion(Art.blackFill), Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
	bonusEffectBlackFill.scale.set(15f, 15f);
	bonusEffectBlackFill.setColor(1f, 1f, 1f, .5f);
	bonusEffectPinwheel = new DynamicSprite(new TextureRegion(Art.pinwheel), Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT / 2);
	bonusEffectPinwheel.scale.set(3f, 3f);
	bonusEffectPinwheel.interpolateRotation(360, Linear.INOUT, 3000, false).repeat(Tween.INFINITY, 0).start(bonusEffectPinwheel.tweenManager);

	Gdx.input.setCatchBackKey(true);
	Gdx.input.setCatchMenuKey(true);

	User.init();
	ui.showReadyPrompt();
    }

    public int getComboMultiplier() {
	int multiplier = 1;
	if (session.combosCurrent > 4 && session.combosCurrent < 25)
	    multiplier = 2;
	else if (session.combosCurrent > 24 && session.combosCurrent < 40)
	    multiplier = 3;
	else if (session.combosCurrent > 39 && session.combosCurrent < 60)
	    multiplier = 4;
	else if (session.combosCurrent > 59 && session.combosCurrent < 100)
	    multiplier = 5;
	else if (session.combosCurrent > 99 && session.combosCurrent < 255)
	    multiplier = 7;
	else if (session.combosCurrent > 254)
	    multiplier = 10;
	return multiplier;
    }

    public int getSpawnRate() {
	return spawnRate;
    }

    @Override
    public boolean isAttackAllowed() {
	return recoveryDelay <= RECOVERY_DISABILITY_DURATION / 4f;
    }

    public boolean isPaused() {
	return paused;
    }

    public boolean isSpawnAllowed() {
	return allowSpawn;
    }

    public boolean isUIVisible() {
	return showUI;
    }

    @Override
    public boolean keyUp(int keycode) {
	if (keycode == Keys.BACK && Gdx.app.getType() == ApplicationType.Android)
	    game.setScreen(new PauseScreen(game, this));
	else if (keycode == Keys.BACKSPACE)
	    game.setScreen(new PauseScreen(game, this));
	return false;
    }

    @Override
    public void onAlienAttack(Alien alien) {
	recoveryDelay = RECOVERY_DISABILITY_DURATION;
	session.combosCurrent = 0;
	camera.shake();
	// add 3-5 puke splashes into the screen
	int count = (int) (3 + 1 * Math.random() * 3);
	for (int i = 0; i < count; i++) {
	    float x = (float) (300 + Math.random() * 680);
	    float y = (float) (200 + Math.random() * 400);
	    float targetScale = (float) (0.75f + Math.random() * 1.5f);
	    DynamicSprite puke = new DynamicSprite(Art.pukes.findRegion("PUKE_GREEN"), x, y);
	    puke.setScale(0f, 0f);
	    puke.setRotation((float) (360 * Math.random()));
	    puke.interpolateScaleXY(1f * targetScale, 1f * targetScale, 250, true).delay(i * 100);
	    puke.interpolateAlpha(0f, RECOVERY_DISABILITY_DURATION * 1000, true).delay(i * 100);
	    pukes.add(puke);
	}
    }

    @Override
    public void onAlienEscaped(Alien alien) {
	// TODO Auto-generated method stub
	session.escapedAliens++;
    }

    @Override
    public void onAlienSmashed(Alien alien) {
	addScore(alien);
	session.smashedAliens++;
	// Add coin as per percentage
	int random = (int) (Math.random() * 100);
	if (User.hasEffect(BonusEffect.COIN_RAIN))
	    addCoin(alien.position.x, alien.position.y);
	else if (random >= 18 && random <= 20)
	    addCoin(alien.position.x, alien.position.y);
    }

    @Override
    public void onBonusEffectSpawn(Alien alien) {
	// Spawn the bonus effects
	float width = 130;//waitingState.getLargestAreaDisplay().getKeyFrame().getRegionWidth();
	float randomX = alien.position.x - width;
	float delay = 500;
	randomX += (float) (Math.random() * width * 2);

	HammerTime hammerTime = new HammerTime(new Vector2(randomX, alien.position.y));
	hammerTime.interpolateXY(hammerTime.position.x, hammerTime.position.y + 150, Sine.OUT, 500, true);
	hammerTime.tween.delay(delay);
	hammerTime.interpolateAlpha(1f, Linear.INOUT, 150, true);
	hammerTime.tween.delay(delay);
	hammerTime.interpolateXY(hammerTime.position.x, hammerTime.position.y, Sine.IN, 500, true);
	hammerTime.tween.delay(delay + 500);
	hammerTime.color.a = 0f;
	hammerTime.interpolateAlpha(0f, Linear.INOUT, 500, true).delay(5000); // The BE disappears after 5 seconds.
	//	    hammerTime.tween.setCallback(new RemoveFromCollectionOnEnd(stage.bonusEffects, hammerTime));

	delay += 500;
	randomX = alien.position.x - width;
	randomX += (float) (Math.random() * width * 2);

	Invulnerability invulnerability = new Invulnerability(new Vector2(randomX, alien.position.y));
	invulnerability.interpolateXY(invulnerability.position.x, invulnerability.position.y + 150, Sine.OUT, 500, true);
	invulnerability.tween.delay(delay);
	invulnerability.interpolateAlpha(1f, Linear.INOUT, 150, true);
	invulnerability.tween.delay(delay);
	invulnerability.interpolateXY(invulnerability.position.x, invulnerability.position.y, Sine.IN, 500, true);
	invulnerability.tween.delay(delay + 500);
	invulnerability.color.a = 0f;
	invulnerability.interpolateAlpha(0f, Linear.INOUT, 500, true).delay(5000); // The BE disappears after 5 seconds.
	//	    invulnerability.tween.setCallback(new RemoveFromCollectionOnEnd(stage.bonusEffects, invulnerability));

	delay += 500;
	randomX = alien.position.x - width;
	randomX += (float) (Math.random() * width * 2);

	ScoreFrenzy scoreFrenzy = new ScoreFrenzy(new Vector2(randomX, alien.position.y));
	scoreFrenzy.interpolateXY(scoreFrenzy.position.x, scoreFrenzy.position.y + 150, Sine.OUT, 500, true);
	scoreFrenzy.tween.delay(delay);
	scoreFrenzy.interpolateAlpha(1f, Linear.INOUT, 150, true);
	scoreFrenzy.tween.delay(delay);
	scoreFrenzy.interpolateXY(scoreFrenzy.position.x, scoreFrenzy.position.y, Sine.IN, 500, true);
	scoreFrenzy.tween.delay(delay + 500);
	scoreFrenzy.color.a = 0f;
	scoreFrenzy.interpolateAlpha(0f, Linear.INOUT, 500, true).delay(5000); // The BE disappears after 5 seconds.
	//	    scoreFrenzy.tween.setCallback(new RemoveFromCollectionOnEnd(stage.bonusEffects, scoreFrenzy));

	bonusEffects.add(hammerTime);
	bonusEffects.add(invulnerability);
	bonusEffects.add(scoreFrenzy);
    }

    @Override
    public void onTouchDown(float x, float y, int pointer, int button) {
	Vector2 position = new Vector2(x, y);
	position.x *= (float) Settings.SCREEN_WIDTH / Gdx.graphics.getWidth();
	position.y = (Gdx.graphics.getHeight() * camera.zoom - position.y) * Settings.SCREEN_HEIGHT / Gdx.graphics.getHeight();

	boolean touchedACoin = inputToCoins(position.x, position.y, pointer);
	boolean touchedAnAlien = isAttackAllowed() && !touchedACoin ? inputToAliens(position.x, position.y, pointer) : false; // We will only test collisions with the aliens if
	// there are no collisions with any coin

	boolean touchedABonusEffect = false;
	for (int i = 0; i < bonusEffects.size(); i++)
	    if (bonusEffects.get(i).getBounds().contains(position.x, position.y)) {
		BonusEffect effect = bonusEffects.remove(i);
		effect.trigger();
		touchedABonusEffect = true;
		if (effect instanceof Invulnerability) {
		    bonusEffectBlackFill.color.a = 0f;
		    bonusEffectBlackFill.interpolateAlpha(.35f, Linear.INOUT, 500, true);
		    bonusEffectBlackFill.interpolateAlpha(0f, Linear.INOUT, 500, true).delay(9500);
		} else if (effect instanceof ScoreFrenzy) {
		    bonusEffectPinwheel.color.a = 0f;
		    bonusEffectPinwheel.interpolateAlpha(1f, Linear.INOUT, 500, true);
		    bonusEffectPinwheel.interpolateAlpha(0f, Linear.INOUT, 500, true).delay(9500);
		}
	    }

	if (isAttackAllowed() && !touchedACoin) { // Hammer effects will only be added if a coin is not tapped
	    session.smashLanded++;
	    if (User.hasEffect(BonusEffect.HAMMER_TIME))
		camera.shake();
	    addHammerEffect(position.x, position.y);
	    if (Settings.soundEnabled)
		Sounds.hammerIceFlakes.play();
	}

	if (!touchedAnAlien && !touchedACoin && !touchedABonusEffect && !User.hasEffect(BonusEffect.HAMMER_TIME))
	    onSmashMissed(position.x, position.y);

	pointers[pointer] = touchedAnAlien;
    }

    @Override
    public void onTouchDrag(float x, float y, float pointer) {
	super.onTouchMove(x, y);
	recoveryDelay -= 0.025f;
	for (int i = 0; i < pukes.size(); i++)
	    pukes.get(i).tweenSpeed += .015f;
    }

    @Override
    public void pause() {
	paused = true;
	for (int i = 0; i < bonusEffects.size(); i++)
	    bonusEffects.get(i).pause();
	for (int i = 0; i < coins.size(); i++)
	    coins.get(i).pause();
	for (int i = 0; i < pukes.size(); i++)
	    pukes.get(i).pause();
	for (int i = 0; i < aliens.length; i++)
	    aliens[i].pause();
    }

    @Override
    public void render(float delta) {
	//	getCamera().update();
	//	spriteBatch.setProjectionMatrix(camera.projection);

	delta = paused ? 0 : delta;

	inputDelay += delta;
	sorcererSpawnDelay -= delta;
	session.stageSecondsElapsed += delta;

	spriteBatch.begin();
	renderStage(spriteBatch, delta);
	renderAliens(spriteBatch, delta);
	renderBonusEffects(spriteBatch, delta);
	renderCoins(spriteBatch, delta);
	renderHammerEffects(spriteBatch, delta);
	renderPukes(spriteBatch, delta);
	renderStageEffects(spriteBatch, delta);
	if (showUI) {
	    ui.render(spriteBatch);
	    ui.update(delta);
	}
	spriteBatch.end();
	spriteBatch.setColor(1f, 1f, 1f, 1f);

	// Check if 3 sec. has passed since the last successful hit. If so, the combos are cancelled.
	if (session.stageSecondsElapsed - session.combosLastDelta >= COMBO_MAX_DURATION && session.combosCurrent > 0) {
	    ui.showComboPrompt(session.combosCurrent);
	    session.combosMax = session.combosCurrent > session.combosMax ? session.combosCurrent : session.combosMax;
	    session.combosCurrent = 0;
	}

	// update streaks
	streaks = 0;
	for (int i = 0; i < pointers.length; i++)
	    if (pointers[i])
		streaks++;
	if (streaks >= 2) {
	    ui.showStreakPrompt(streaks);
	    for (int i = 0; i < pointers.length; i++)
		pointers[i] = false;
	}

	try {
	    if (session.stageSecondsElapsed > 0 && Integer.parseInt(("" + session.stageSecondsElapsed).split(".")[1]) % 2 == 0) // .2 seconds has passed
		for (int i = 0; i < pointers.length; i++)
		    pointers[i] = false;
	} catch (ArrayIndexOutOfBoundsException e) {
	    //	    System.out.println("[SmashSmashStage#render(float): ArrayOutOfBoundsException");
	}
	//	System.out.println("[SmashSmashStage#render(float)] blackFill.color.a: " + bonusEffectBlackFill.getColor().a);
    }

    @Override
    public void resume() {
	paused = false;
	for (int i = 0; i < bonusEffects.size(); i++)
	    bonusEffects.get(i).resume();
	for (int i = 0; i < coins.size(); i++)
	    coins.get(i).resume();
	for (int i = 0; i < pukes.size(); i++)
	    pukes.get(i).resume();
	for (int i = 0; i < aliens.length; i++)
	    aliens[i].resume();
    }

    public void setAllowSpawn(boolean allowSpawn) {
	this.allowSpawn = allowSpawn;
    }

    public void setUIVisible(boolean showUI) {
	this.showUI = showUI;
    }

    protected void addCoin(float x, float y) {
	Coin coin = new Coin(Art.coins, 0, 0, 64, 64, 8, 8, .125f);
	coin.position.set(x, y);
	coin.interpolateXY(x, y + 100, Quad.OUT, 200, true);
	coin.interpolateXY(x, y, Quad.IN, 200, true).delay(200);
	coins.add(coin);
	if (User.hasEffect(BonusEffect.COIN_RAIN)) {
	    coin.interpolateXY(1280, 800, Linear.INOUT, 350, true).setCallback(new RemoveFromCollectionOnEnd(coins, coin));
	    User.gold += 10;
	}
    }

    protected void addHammerEffect(float x, float y) {
	Vector2 position = new Vector2(x, y);
	float duration = User.hammer.getEffect().getEmitters().get(0).getDuration().getLowMax() / 1000;//User.hammer.getEffect().getEmitters().get(0).duration / 2;
	HammerEffect hammerEffect = new HammerEffect(User.hammer.getEffect(), position, duration, 0f);
	hammerEffects.add(hammerEffect);
	//	System.out.println(duration);
	//	Array<ParticleEmitter> a = User.hammer.getEffect().getEmitters();
	//	for (ParticleEmitter p : a)
	//	    System.out.println(p.getDuration().getLowMax());
    }

    protected void addScore(Alien alien) {
	int multiplier = getComboMultiplier();
	multiplier *= User.hasEffect(BonusEffect.SCORE_FRENZY) ? 3 : 1;
	session.score += alien.scoreValue * multiplier * (streaks >= 1 ? streaks : 1);
    }

    protected int getVisibleAliens() {
	int visibles = 0;
	for (int i = 0; i < aliens.length; i++)
	    if (aliens[i].visible)
		visibles++;
	return visibles;
    }

    protected void initAliens() {
	aliens[0] = new Diabolic(this);
	aliens[1] = new Diabolic(this);
	aliens[2] = new Diabolic(this);
	aliens[3] = new Fluff(this);
	aliens[4] = new Fluff(this);
	aliens[5] = new Fluff(this);
	aliens[6] = new Golem(this);
	aliens[7] = new Golem(this);
	aliens[8] = new Golem(this);
	aliens[9] = new Jelly(this);
	aliens[10] = new Jelly(this);
	aliens[11] = new Jelly(this);
	aliens[12] = new Ogre(this);
	aliens[13] = new Ogre(this);
	aliens[14] = new Ogre(this);
	aliens[15] = new Tortoise(this);
	aliens[15] = new Tortoise(this);
	aliens[15] = new Tortoise(this);
	aliens[16] = new Sorcerer(this);
	for (int i = 0; i < aliens.length; i++)
	    aliens[i].visible = false;
    }

    protected void initHUD() {
	ui = new UserInterface(this);
    }

    protected boolean inputToAliens(float x, float y, int pointer) {
	int diameter = User.hammer.getDiameter();
	Rectangle bounds = new Rectangle(x - diameter / 2, y - diameter / 2, diameter, diameter);
	int hitCount = 0;
	for (int i = 0; i < aliens.length; i++)
	    if (aliens[i].visible && aliens[i].getBounds().overlaps(bounds) && aliens[i].state != AlienState.SMASHED) {
		aliens[i].smash();
		onAlienSmashed(aliens[i]);
		hitCount++;
		session.combosCurrent++;
		session.combosLastDelta = session.stageSecondsElapsed;
		ui.shakeCombos();
		break;
	    }
	// Display a "x + 1!" message
	if (hitCount >= 2) {
	    DynamicText text = new DynamicText(Fonts.bdCartoonShoutx23orange, hitCount + " in 1!", HAlignment.CENTER);
	    text.position.set(x, y - 50);
	    text.color.a = 0f;
	    text.interpolateXY(x, y + 50, Sine.OUT, 250, true);
	    text.interpolateAlpha(1f, Sine.OUT, 250, true);
	    text.interpolateAlpha(0f, Sine.OUT, 250, true).delay(1000);
	    ui.textPool.add(text);
	}
	return hitCount > 0;
    }

    protected boolean inputToCoins(float x, float y, int pointer) {
	Rectangle bounds = new Rectangle(x - 25, y - 25, 50, 50);
	boolean hit = false;
	for (int coinIndex = coins.size() - 1; coinIndex >= 0; coinIndex--)
	    if (coins.get(coinIndex).getBounds().overlaps(bounds)) {
		coins.get(coinIndex).tweenManager.killAll();
		coins.get(coinIndex).interpolateXY(1280, 800, Linear.INOUT, 350, true);
		coins.get(coinIndex).tween.setCallback(new RemoveFromCollectionOnEnd(coins, coins.get(coinIndex)));
		User.gold += 10;
		hit = true;
		coinIndex = -1;
	    }
	return hit;
    }

    protected void onSmashMissed(float x, float y) {
	if (session.combosCurrent > 1) {
	    ui.missLabel.text = "MISS!";
	    ui.missLabel.color.a = 1f;
	    ui.missLabel.position.set(x, y);
	    ui.missLabel.interpolateXY(x, y + 25, Linear.INOUT, 50, true);
	    ui.missLabel.interpolateXY(x, y, Linear.INOUT, 50, true).delay(50);
	    ui.missLabel.interpolateAlpha(0f, Linear.INOUT, 250, true).delay(2000);
	}
	ui.showComboPrompt(session.combosCurrent);
	session.combosMax = session.combosCurrent > session.combosMax ? session.combosCurrent : session.combosMax;
	session.combosCurrent = 0;
    }

    protected void renderAliens(SpriteBatch batch, float delta) {
	// Sort the aliens first according to their y-coordinate. Aliens with lowest y are rendered last.
	for (int i = 0; i < aliens.length; i++)
	    for (int j = i + 1; j < aliens.length; j++)
		if (aliens[j].position.y < aliens[i].position.y) {
		    Alien alien = aliens[j];
		    aliens[j] = aliens[i];
		    aliens[i] = alien;
		}
	for (int i = aliens.length - 1; i > -1; i--) {
	    aliens[i].render(batch);
	    aliens[i].update(delta);
	}

	// FIXME set to delay before the intro prompt ends
	if (session.stageSecondsElapsed > 1) {
	    setSpawnRate();
	    setSpawnPositions();
	    int visibles = getVisibleAliens();
	    boolean overlaps = false;
	    boolean sorcererShouldAppear = !User.hasEffect(BonusEffect.HAMMER_TIME) && !User.hasEffect(BonusEffect.INVULNERABILITY);
	    sorcererShouldAppear = sorcererSpawnDelay <= 0 && sorcererShouldAppear && !User.hasEffect(BonusEffect.SCORE_FRENZY);
	    for (int i = 0; visibles < spawnRate - 1 && i < aliens.length && i < spawnRate - 1; i++) {
		for (int j = 0; j < aliens.length; j++)
		    if (i != j && !aliens[i].visible && aliens[i].getBounds().overlaps(aliens[j].getBounds())) {
			overlaps = true;
			j = aliens.length; //break this loop
		    }
		spawnDelay = i * (int) (Math.random() * 250);
		// we only show an alien if it doesn't collide with other ones or if it is a sorcerer 
		// and it is allowed to be spawn and doesn't collide to others
		if (!(aliens[i] instanceof Sorcerer) && !overlaps) {
		    float volume = visibles > 0 ? 1.1f - visibles / aliens.length : 1f;
		    aliens[i].rise(spawnDelay, volume / 2);
		} else if (aliens[i] instanceof Sorcerer && sorcererShouldAppear && !overlaps) {
		    sorcererSpawnDelay = (float) (3f + Math.random() * 11f);
		    float volume = visibles > 0 ? 1.1f - visibles / aliens.length : 1f;
		    aliens[i].rise(spawnDelay, volume / 2);
		}
		overlaps = false;
	    }
	}
    }

    protected void renderBonusEffects(SpriteBatch batch, float delta) {
	for (int i = 0; i < bonusEffects.size(); i++) {
	    bonusEffects.get(i).render(batch);
	    if (!paused)
		bonusEffects.get(i).update(delta);
	    if (bonusEffects.get(i).tweenManager.getRunningTweensCount() <= 0)
		bonusEffects.remove(i);
	}
    }

    protected void renderCoins(SpriteBatch batch, float delta) {
	for (int i = 0; i < coins.size(); i++) {
	    coins.get(i).render(batch);
	    if (coins.get(i).timeElapsed > 7f)
		coins.remove(i);
	    else
		coins.get(i).update(delta);
	}
    }

    protected void renderHammerEffects(SpriteBatch batch, float delta) {
	for (int i = 0; i < hammerEffects.size(); i++) {
	    hammerEffects.get(i).render(batch);
	    if (hammerEffects.get(i).elapsed >= hammerEffects.get(i).duration)
		hammerEffects.remove(hammerEffects.get(i));
	    else
		hammerEffects.get(i).update(delta);
	}
    }

    protected void renderPukes(SpriteBatch batch, float delta) {
	for (int i = 0; i < pukes.size(); i++) {
	    pukes.get(i).render(batch);
	    pukes.get(i).update(delta);
	    if (pukes.get(i).tweenManager.getRunningTweensCount() == 0)
		pukes.remove(i);
	}
	recoveryDelay -= delta;
	//	System.out.println("[SurvivalStageScreen#renderPukes(SpriteBatch,float)] recoveryDelay: " + recoveryDelay);
	//	System.out.println("[SurvivalStageScreen#renderPukes(SpriteBatch,float)] Pukes count: " + pukes.size());
    }

    protected void renderStage(SpriteBatch spriteBatch, float delta) {
	spriteBatch.draw(Art.lawnBackground1, 0, -(1024 - 800));
	spriteBatch.draw(Art.lawnBackground2, 1280 - 256, -(1024 - 800));
    }

    protected void renderStageEffects(SpriteBatch batch, float delta) {
	if (User.hasEffect(BonusEffect.SCORE_FRENZY))
	    bonusEffectPinwheel.render(batch);
	if (User.hasEffect(BonusEffect.INVULNERABILITY))
	    bonusEffectBlackFill.render(batch);
	bonusEffectBlackFill.update(delta);
	bonusEffectPinwheel.update(delta);
	for (int itemEffectIndex = 0; itemEffectIndex < User.bonusEffects.size(); itemEffectIndex++)
	    User.bonusEffects.get(itemEffectIndex).update(delta);
    }

    protected void setSpawnPositions() {
	for (int i = 0; i < aliens.length; i++) {
	    float targetWidth = aliens[i].waitingState.getLargestAreaDisplay().getKeyFrame().getRegionWidth();
	    float targetHeight = aliens[i].waitingState.getLargestAreaDisplay().getKeyFrame().getRegionHeight();
	    aliens[i].getBounds().width = targetWidth;
	    aliens[i].getBounds().height = targetHeight;
	    if (!aliens[i].visible) {
		float randomX = (float) (targetWidth / 2 + Math.random() * (Settings.SCREEN_WIDTH - targetWidth * 2));
		float randomY = (float) (Math.random() * (Settings.SCREEN_HEIGHT - targetHeight));
		aliens[i].position.set(randomX, randomY);
		aliens[i].getBounds().x = randomX - targetWidth / 2;
		aliens[i].getBounds().y = randomY;
	    }
	}
    }

    protected void setSpawnRate() {
	boolean hasRampage = User.hasEffect(BonusEffect.COIN_RAIN) && User.hasEffect(BonusEffect.HAMMER_TIME) && User.hasEffect(BonusEffect.SCORE_FRENZY);
	if (allowSpawn && hasRampage)
	    spawnRate = aliens.length;
	else if (!allowSpawn)
	    spawnRate = 0;
	else if (session.combosCurrent > 4 && session.combosCurrent < 25)
	    spawnRate = 4;
	else if (session.combosCurrent > 24 && session.combosCurrent < 40)
	    spawnRate = 5;
	else if (session.combosCurrent > 39 && session.combosCurrent < 60)
	    spawnRate = 6;
	else if (session.combosCurrent > 59 && session.combosCurrent < 100)
	    spawnRate = 8;
	else if (session.combosCurrent > 99 && session.combosCurrent < 255)
	    spawnRate = 10;
	else if (session.combosCurrent > 254)
	    spawnRate = aliens.length;
	else
	    spawnRate = 4;
	//	alienAppearanceRate = aliens.length;
    }
}
