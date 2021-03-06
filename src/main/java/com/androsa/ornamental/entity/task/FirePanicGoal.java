package com.androsa.ornamental.entity.task;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.PanicGoal;

public class FirePanicGoal extends PanicGoal {

    public FirePanicGoal(CreatureEntity creature, double speed) {
        super(creature, speed);
    }

    @Override
    public boolean canUse() {
        return mob.isOnFire() && super.canUse();
    }
}
