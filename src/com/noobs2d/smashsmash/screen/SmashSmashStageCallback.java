package com.noobs2d.smashsmash.screen;

import com.noobs2d.smashsmash.alien.Alien;

public interface SmashSmashStageCallback {

    public void onAlienAttack(Alien alien);

    public void onAlienEscaped(Alien alien);

    public void onAlienSmashed(Alien alien);

    public void onBonusEffectTrigger(Alien alien, int... bonusEffects);
}
