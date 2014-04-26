package com.noobs2d.smashsmash.screen;

import com.noobs2d.smashsmash.alien.Alien;
import com.noobs2d.smashsmash.alien.HammerTimeJelly;
import com.noobs2d.smashsmash.alien.InvulnerabilityJelly;
import com.noobs2d.smashsmash.alien.ScoreFrenzyJelly;

/** 
 * Events that are triggered depending on {@link Alien} interaction.
 * 
 * @author Julious Cious Igmen <jcigmen@gmail.com>
 */
public interface AlienEventListener {

    /** Invoked when an alien successfully attacks the user. 
     * @param alien The Alien that invoked this event. */
    public void onAlienAttack(Alien alien);

    /** When an alien gets off the screen without getting smashed. 
     * @param alien The Alien that invoked this event. */
    public void onAlienEscaped(Alien alien);

    /** When an alien is smashed, yet not killed (there is/are still HP left). 
     * @param alien The Alien that invoked this event. */
    public void onAlienHit(Alien alien);

    /** When an alien is successfully smashed; no HP left. 
     * @param alien The Alien that invoked this event. */
    public void onAlienSmashed(Alien alien);

    /** Invoked through <i>Buff Effect Aliens</i> ({@link HammerTimeJelly}, {@link InvulnerabilityJelly}, and {@link ScoreFrenzyJelly}). 
     * @param alien The Alien that invoked this event. 
     * @param bonusEffects The bonus effects triggered by this alien*/
    public void onBonusEffectTrigger(Alien alien, int... bonusEffects);
}
