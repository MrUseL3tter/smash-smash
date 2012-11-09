package com.nullsys.smashsmash.stage;

import com.nullsys.smashsmash.alien.Alien;

public interface SmashSmashStageCallback {

    public boolean isAttackAllowed();

    public void onAlienAttack(Alien alien);

    public void onAlienEscaped(Alien alien);

    public void onAlienSmashed(Alien alien);

    public void onBonusEffectSpawn(Alien alien);
}
