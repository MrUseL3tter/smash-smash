package com.noobs2d.smashsmash;

/**
 * Model class holding all data related to a single game session.
 * 
 * @author MrUseL3tter
 **/
public class Session {

    private float stageSecondsElapsed = 0;
    private float comboLastDelta = 0;
    private int currentCombo = 0;
    private int highestCombo = 0;
    private int landedSmashes = 0;
    private int missedSmashes = 0;
    private int lifepoints = 3;
    private int score = 0;
    private int smashedAliens = 0;
    private int escapedAliens = 0;

    public void addToScore(int score) {
	this.score += score;
    }

    public void addToStageSecondsElapsed(float delta) {
	stageSecondsElapsed += delta;
    }

    public void decrementLifepoints() {
	lifepoints--;
    }

    public float getComboLastDelta() {
	return comboLastDelta;
    }

    public int getCurrentCombo() {
	return currentCombo;
    }

    public int getEscapedAliens() {
	return escapedAliens;
    }

    public int getHighestCombo() {
	return highestCombo;
    }

    public int getLandedSmashes() {
	return landedSmashes;
    }

    public int getLifepoints() {
	return lifepoints;
    }

    public int getMissedSmashes() {
	return missedSmashes;
    }

    public int getScore() {
	return score;
    }

    public int getSmashedAliens() {
	return smashedAliens;
    }

    public float getStageSecondsElapsed() {
	return stageSecondsElapsed;
    }

    public void incrementCurrentCombo() {
	currentCombo++;
    }

    public void incrementEscapedAliens() {
	escapedAliens++;
    }

    public void incrementLandedSmashes() {
	landedSmashes++;
    }

    public void incrementMissedSmashes() {
	missedSmashes++;
    }

    public void incrementSmashedAliens() {
	smashedAliens++;
    }

    public void resetCurrentCombo() {
	currentCombo = 0;
    }

    public void setCombosLastDelta(float delta) {
	comboLastDelta = delta;
    }

    public void setHighestCombo(int highestCombo) {
	this.highestCombo = highestCombo;
    }

    public void setScore(int score) {
	this.score = score;
    }
}
