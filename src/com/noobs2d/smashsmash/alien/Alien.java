package com.noobs2d.smashsmash.alien;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.noobs2d.smashsmash.Settings;
import com.noobs2d.smashsmash.User;
import com.noobs2d.smashsmash.buffeffect.BuffEffect;
import com.noobs2d.smashsmash.screen.AlienEventListener;
import com.noobs2d.tweenengine.utils.DynamicDisplayGroup;

/**
 * The mother of all aliens. aw yiss
 * 
 * @author Julious Cious Igmen <jcigmen@gmail.com>
 */
public abstract class Alien {

    /** Maximum seconds that can be added to an alien's idle time. */
    private static final int WAITING_TIME_INCREASE_MAX = 3;

    public enum AlienState {
	ATTACKING, WAITING, RISING, HIDING, SMASHED, HIDDEN, STUNNED, EXPLODING
    }

    public Vector2 position = new Vector2(0, 0);

    protected AlienState state = AlienState.HIDDEN;
    protected DynamicDisplayGroup risingState = new DynamicDisplayGroup();
    protected DynamicDisplayGroup waitingState = new DynamicDisplayGroup();
    protected DynamicDisplayGroup attackingState = new DynamicDisplayGroup();
    protected DynamicDisplayGroup stunnedState = new DynamicDisplayGroup();
    protected DynamicDisplayGroup smashedState = new DynamicDisplayGroup();
    protected DynamicDisplayGroup hidingState = new DynamicDisplayGroup();
    protected DynamicDisplayGroup explodeState = new DynamicDisplayGroup();

    /** Used for checking if this alien collides with other rectangles. */
    protected Rectangle collisionBounds = new Rectangle(0, 0, 96, 276);

    /** Used for checking whether this alien is hit. */
    protected Rectangle hitBounds = new Rectangle(0, 0, 0, 0);

    /** Event listener for Alien interactions. Will never be <code>null</code> because it must be set on every instantiation. */
    private AlienEventListener alienEventListener;

    /** Duration in seconds an alien is rising. Must be the same duration as the animation itself. */
    protected float risingStateTime = .5f;

    /** Seconds an alien will remain idle and do nothing. By default, this
     * value will be increased by a random value with a max of {@link Alien#WAITING_TIME_INCREASE_MAX}
     * unless {@link Alien#rise(float, float)} is overriden. */
    protected float waitingStateTime = 3f;

    /** Duration in seconds an alien is attacking. Must be the same duration as the animation itself. */
    protected float attackingStateTime = 1f;

    /** Duration in seconds an alien is exploding. Must be the same duration as the animation itself. */
    protected float explodeStateTime = .3f;

    /** Duration in seconds an alien remains stunned. */
    protected float stunnedStateTime = 3f;

    /** Duration in seconds an alien remains smashed. */
    protected float smashedStateTime = .3f;

    /** Duration in seconds an alien is hiding. Must be the same duration as the animation itself. */
    protected float hidingStateTime = .25f;

    /** Smash remaining to be killed. If becomes zero, this alien is smashed successfully. */
    private int hitPointsCurrent = 1;

    /** Total smash to take before being killed. */
    private int hitPointsTotal = 1;

    private Sound attackSound;
    private Sound smashSound;
    private Sound spawnSound;
    private Sound stunSound;

    /** Elapsed seconds being visible. */
    private float elapsedTimeVisible = 0f;

    /** Delay before actually showing up. */
    private float riseDelay = 0f;

    /** If this alien can attack. */
    private boolean hostile = true;

    private boolean visible = true;

    private boolean stunnable = true;

    /** Set through {@link Alien#addRandomValueToWaitingTime()}. This needs to be kept track so it can be subtracted and avoid
     * the waiting time to accumulate over time, causing the alien to idle/wait for a long time. */
    private float previouslySecondsAddedToWaitingTime;

    /** Percentage by which a critical may happen. 1-100. */
    private int criticalChance = 10;

    public void attack() {
	state = AlienState.ATTACKING;
	alienEventListener.onAlienAttack(this);
	interpolateAttacking();
    }

    public boolean collides(Alien alien) {
	collisionBounds.x = position.x - 48;
	collisionBounds.y = position.y - 110;
	alien.collisionBounds.x = alien.position.x - 48;
	alien.collisionBounds.y = alien.position.y - 110;
	return collisionBounds.overlaps(alien.getHitBounds());
    }

    public void explode() {
	state = AlienState.EXPLODING;
	interpolateExplode();
    }

    public AlienEventListener getAlienEventListener() {
	return alienEventListener;
    }

    public float getAttackingStateTime() {
	return attackingStateTime;
    }

    public int getCriticalChance() {
	return criticalChance;
    }

    public float getElapsedTimeVisible() {
	return elapsedTimeVisible;
    }

    public float getExplodeStateTime() {
	return explodeStateTime;
    }

    public float getHidingStateTime() {
	return hidingStateTime;
    }

    public Rectangle getHitBounds() {
	if (getStateAnimation() != null) {
	    float scaleX = getStateAnimation().getLargestAreaDisplay().scale.x;
	    float scaleY = getStateAnimation().getLargestAreaDisplay().scale.y;
	    float x = position.x - getStateAnimation().getLargestAreaDisplay().getWidth() * scaleX / 2;
	    float y = position.y;
	    float width = getStateAnimation().getLargestAreaDisplay().getWidth() * scaleX;
	    float height = getStateAnimation().getLargestAreaDisplay().getHeight() * scaleY;
	    hitBounds.set(x, y, width, height);
	}
	return hitBounds;
    }

    public int getHitPointsCurrent() {
	return hitPointsCurrent;
    }

    public int getHitPointsTotal() {
	return hitPointsTotal;
    }

    public float getRisingStateTime() {
	return risingStateTime;
    }

    public int getScore() {
	return 1;
    }

    public float getSmashedStateTime() {
	return smashedStateTime;
    }

    public DynamicDisplayGroup getStateAnimation() {
	if (state == AlienState.ATTACKING)
	    return attackingState;
	else if (state == AlienState.HIDING)
	    return hidingState;
	else if (state == AlienState.RISING)
	    return risingState;
	else if (state == AlienState.SMASHED)
	    return smashedState;
	else if (state == AlienState.STUNNED)
	    return stunnedState;
	else if (state == AlienState.WAITING)
	    return waitingState;
	else if (state == AlienState.EXPLODING)
	    return explodeState;
	else
	    return waitingState;
    }

    /**
     * @return the stunnedStateTime
     */
    public float getStunnedStateTime() {
	return stunnedStateTime;
    }

    /**
     * @return the upElapsedTime
     */
    public float getUpElapsedTime() {
	return elapsedTimeVisible;
    }

    /**
     * @return the waitingStateTime
     */
    public float getWaitingStateTime() {
	return waitingStateTime;
    }

    public void hide() {
	interpolateHiding();
	state = AlienState.HIDING;
	elapsedTimeVisible = 0;
    }

    public boolean hit(Rectangle rectangle) {
	return visible && state != AlienState.SMASHED && getHitBounds().overlaps(rectangle);
    }

    public boolean isHostile() {
	return hostile;
    }

    public boolean isStunnable() {
	return stunnable;
    }

    public boolean isVisible() {
	return visible;
    }

    public void pauseTween() {
	risingState.pauseTween();
	waitingState.pauseTween();
	attackingState.pauseTween();
	stunnedState.pauseTween();
	smashedState.pauseTween();
	hidingState.pauseTween();
    }

    public void render(SpriteBatch spriteBatch) {
	if (visible) {
	    getStateAnimation().position.add(position);
	    getStateAnimation().render(spriteBatch);
	    getStateAnimation().position.sub(position);
	}
    }

    /** Resets all the animation states to beginning, {@link Alien#elapsedTimeVisible} to 0, and {@link Alien#hitPointsCurrent} 
     * to {@link Alien#hitPointsTotal} if the parameter is <code>true</code>. */
    public void reset(boolean restoreHP) {
	attackingState.reset();
	hidingState.reset();
	risingState.reset();
	stunnedState.reset();
	smashedState.reset();
	waitingState.reset();

	elapsedTimeVisible = 0f;

	if (restoreHP)
	    setHitPointsCurrent(getHitPointsTotal());
    }

    public void resumeTween() {
	risingState.resumeTween();
	waitingState.resumeTween();
	attackingState.resumeTween();
	stunnedState.resumeTween();
	smashedState.resumeTween();
	hidingState.resumeTween();
    }

    /** Shows this alien ONLY if it's state is {@link AlienState#HIDDEN}. <br>
     * If so, these other methods will be invoked: <br> <br>
     * 
     *  {@link Alien#reset()} <br>
     *  {@link Alien#interpolateRising(float)} <br>
     *  {@link Alien#playRisingSound(float)} <br>
     *  {@link Alien#addRandomValueToWaitingTime()} <br><br>
     *  
     *  It may be desired that {@link Alien#addRandomValueToWaitingTime()} not be invoked for some alien
     *  (e.g., the alien must only have a fixed duration of idle time). If so, override it and don't add 
     *  any waiting time manipulation or you know, leave it blank.
     *
     * @param riseDelay Seconds before actually showing up. 
     * @param volume Needs to be manipulated so it can be lowered if there are multiple aliens showing up. 
     */
    public void rise(float riseDelay, float volume) {
	this.riseDelay = riseDelay;
	if (state == AlienState.HIDDEN) {
	    visible = true;
	    state = AlienState.RISING;

	    addRandomValueToWaitingTime();
	    reset(true);
	    interpolateRising(riseDelay);
	    playRisingSound(volume);
	}
    }

    public void setAlienEventListener(AlienEventListener listener) {
	alienEventListener = listener;
    }

    /**
     * @param attackingStateTime the attackingStateTime to set
     */
    public void setAttackingStateTime(float attackingStateTime) {
	this.attackingStateTime = attackingStateTime;
    }

    public void setAttackSound(Sound attackSound) {
	this.attackSound = attackSound;
    }

    /** Only accepts values from 1-100. */
    public void setCriticalChance(int criticalChance) {
	if (criticalChance > 0 && criticalChance < 101)
	    this.criticalChance = criticalChance;
    }

    public void setElapsedTimeVisible(float elapsedTimeVisible) {
	this.elapsedTimeVisible = elapsedTimeVisible;
    }

    /**
     * @param explodeStateTime the explodeStateTime to set
     */
    public void setExplodeStateTime(float explodeStateTime) {
	this.explodeStateTime = explodeStateTime;
    }

    /**
     * @param hidingStateTime the hidingStateTime to set
     */
    public void setHidingStateTime(float hidingStateTime) {
	this.hidingStateTime = hidingStateTime;
    }

    public void setHitPointsCurrent(int hitPointsCurrent) {
	this.hitPointsCurrent = hitPointsCurrent;
    }

    public void setHitPointsTotal(int hitPointsTotal) {
	this.hitPointsTotal = hitPointsTotal;
    }

    public void setHostile(boolean hostile) {
	this.hostile = hostile;
    }

    /**
     * @param risingStateTime the risingStateTime to set
     */
    public void setRisingStateTime(float risingStateTime) {
	this.risingStateTime = risingStateTime;
    }

    /**
     * @param smashedStateTime the smashedStateTime to set
     */
    public void setSmashedStateTime(float smashedStateTime) {
	this.smashedStateTime = smashedStateTime;
    }

    public void setSmashSound(Sound smashSound) {
	this.smashSound = smashSound;
    }

    public void setSpawnSound(Sound spawnSound) {
	this.spawnSound = spawnSound;
    }

    public void setStunnable(boolean stunnable) {
	this.stunnable = stunnable;
    }

    /**
     * @param stunnedStateTime the stunnedStateTime to set
     */
    public void setStunnedStateTime(float stunnedStateTime) {
	this.stunnedStateTime = stunnedStateTime;
    }

    public void setStunSound(Sound stunSound) {
	this.stunSound = stunSound;
    }

    public void setVisible(boolean visible) {
	this.visible = visible;
    }

    /**
     * @param waitingStateTime the waitingStateTime to set
     */
    public void setWaitingStateTime(float waitingStateTime) {
	this.waitingStateTime = waitingStateTime;
    }

    /** This Alien is hit. Here's where the following is determined: <br><br>
     * 
     * 1. This Alien dies from this smash. <br>
     * 2. This Alien is already stunned, then this smash will cause it to explode. <br>
     * 3. This Alien will be stunned by this smash (must be critical and this alien is {@link Alien#stunnable}. <br>
     * 4. This Alien is just normally hit (goes {@link AlienState#SMASHED} but returns to {@link AlienState#WAITING}. <br><br>
     * 
     * Everytime this is invoked, this Alien is reset and loses a single HP, unless the smash is a {@link BuffEffect#HAMMER_TIME}. 
     **/
    public void smash() {
	reset(false);
	playSmashSound();

	hitPointsCurrent--;

	boolean hasHammerEffect = User.hasBuffEffect(BuffEffect.HAMMER_TIME);
	boolean noMoreHP = hitPointsCurrent <= 0;
	boolean notExploding = state != AlienState.EXPLODING;
	boolean alienIsDead = hasHammerEffect || noMoreHP && notExploding;

	boolean stunningSmash = isSmashCritical() && stunnable;

	// can be stunned on its last HP, so this is first checked
	if (stunningSmash)
	    stun();
	// a stunned alien that is smashed will definitely explode
	else if (state == AlienState.STUNNED)
	    explode();
	else if (alienIsDead) {
	    // invoked the callback(s) first before changing state so getScore()
	    // may still utilize the current state of the alien
	    alienEventListener.onAlienSmashed(this);

	    state = AlienState.SMASHED;
	    interpolateSmashed(true);
	} else {
	    // invoked the callback(s) first before changing state so getScore()
	    // may still utilize the current state of the alien
	    alienEventListener.onAlienHit(this);

	    // this alien has more HP left; just switch to SMASHED state, 
	    // which will return to WAITING later on
	    state = AlienState.SMASHED;
	    interpolateSmashed(false);
	}
    }

    public void stun() {
	state = AlienState.STUNNED;
	interpolateStunned();
    }

    public void update(float deltaTime) {
	if (visible) {
	    switch (state) {
		case ATTACKING:
		    updateAttacking(deltaTime);
		    break;
		case HIDING:
		    updateHiding(deltaTime);
		    break;
		case RISING:
		    updateRising(deltaTime);
		    break;
		case WAITING:
		    updateWaiting(deltaTime);
		    break;
		case SMASHED:
		    updateSmashed(deltaTime);
		    break;
		case STUNNED:
		    updateStunned(deltaTime);
		    break;
		case EXPLODING:
		    updateExploding(deltaTime);
		    break;
	    }
	    elapsedTimeVisible += deltaTime;
	}
    }

    private void playRisingSound(float volume) {
	if (Settings.soundEnabled)
	    spawnSound.play(volume);
    }

    /** Generate a random value between 0 and {@link Alien#WAITING_TIME_INCREASE_MAX} then add it to the waiting time. 
     * That value is then subtracted the next time this method is invoked to avoid incrementing {@link Alien#waitingStateTime}.*/
    protected void addRandomValueToWaitingTime() {
	waitingStateTime -= previouslySecondsAddedToWaitingTime;
	previouslySecondsAddedToWaitingTime = (float) (Math.random() * WAITING_TIME_INCREASE_MAX);
	waitingStateTime += previouslySecondsAddedToWaitingTime;
    }

    protected abstract void initAttackingState();

    protected abstract void initExplodeState();

    protected abstract void initHidingState();

    protected abstract void initRisingState();

    protected abstract void initSmashedState();

    protected abstract void initStunnedState();

    protected abstract void initWaitingState();

    /** Do the attacking animation. It's a must that the animation duration matches {@link Alien#attackingStateTime}. */
    protected abstract void interpolateAttacking();

    /** Do the exploding animation. Not all aliens have an explosion animation. 
     * It's a must that the animation duration matches {@link Alien#explodeStateTime}.*/
    protected abstract void interpolateExplode();

    /** Do the hiding (shrinking) animation. It's a must that the animation duration matches {@link Alien#hidingStateTime}.*/
    protected abstract void interpolateHiding();

    /** Do the rising animation. It's a must that the animation duration matches {@link Alien#risingStateTime}.*/
    protected abstract void interpolateRising(float delay);

    /** Do the smashed animation. */
    protected abstract void interpolateSmashed(boolean dead);

    /** Do the stunned animation. Take note that this must always be short; {@link Alien#stunnedStateTime} is not the duration
     * of the stun-animation itself, but of the entire duration of being stunned. It's best to keep the animation duration to less than 500ms. */
    protected abstract void interpolateStunned();

    /**  Do the idling/waiting animation. */
    protected abstract void interpolateWaiting();

    /** Generate a random chance of getting a true based on {@link Alien#criticalChance}.
     * @return <code>true</code> if the random gives it. */
    protected boolean isSmashCritical() {
	return MathUtils.random(1, 101) <= criticalChance;
    }

    protected void playAttackSound() {
	if (Settings.soundEnabled)
	    attackSound.play();
    }

    protected void playSmashSound() {
	if (Settings.soundEnabled)
	    smashSound.play();
    }

    protected void playSpawnSound() {
	if (Settings.soundEnabled)
	    spawnSound.play();
    }

    protected void playStunSound() {
	if (Settings.soundEnabled)
	    stunSound.play();
    }

    protected void updateAttacking(float deltaTime) {
	attackingState.update(deltaTime);
	if (elapsedTimeVisible >= attackingStateTime)
	    hide();
    }

    protected void updateExploding(float deltaTime) {
	explodeState.update(deltaTime);
	if (elapsedTimeVisible >= explodeStateTime) {
	    visible = false;
	    state = AlienState.HIDDEN;
	    elapsedTimeVisible = 0;
	}
    }

    protected void updateHiding(float deltaTime) {
	hidingState.update(deltaTime);
	if (elapsedTimeVisible >= hidingStateTime) {
	    visible = false;
	    state = AlienState.HIDDEN;
	    elapsedTimeVisible = 0;
	    alienEventListener.onAlienEscaped(this);
	}
    }

    protected void updateRising(float deltaTime) {
	risingState.update(deltaTime);
	if (elapsedTimeVisible >= risingStateTime + riseDelay / 1000) {
	    interpolateWaiting();
	    state = AlienState.WAITING;
	    elapsedTimeVisible = 0;
	}
    }

    protected void updateSmashed(float deltaTime) {
	smashedState.update(deltaTime);
	if (elapsedTimeVisible >= smashedStateTime) {
	    elapsedTimeVisible = 0;
	    if (getHitPointsCurrent() <= 0) { // alien is dead after being smashed
		visible = false;
		state = AlienState.HIDDEN;
	    } else {
		reset(false);
		state = AlienState.WAITING;
		interpolateWaiting();
	    }
	}
    }

    protected void updateStunned(float deltaTime) {
	stunnedState.update(deltaTime);
	if (elapsedTimeVisible >= stunnedStateTime) {
	    state = AlienState.HIDING;
	    elapsedTimeVisible = 0;
	    interpolateHiding();
	}
    }

    protected void updateWaiting(float deltaTime) {
	waitingState.update(deltaTime);
	if (hostile && elapsedTimeVisible >= waitingStateTime) {
	    elapsedTimeVisible = 0;
	    attack();
	} else if (elapsedTimeVisible >= waitingStateTime)
	    hide();
    }
}
