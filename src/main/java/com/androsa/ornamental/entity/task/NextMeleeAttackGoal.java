package com.androsa.ornamental.entity.task;

import com.androsa.ornamental.entity.NetheriteGolemEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class NextMeleeAttackGoal extends MeleeAttackGoal {

    public NextMeleeAttackGoal(NetheriteGolemEntity entity) {
        super(entity, 0.4F, false);
    }

    @Override
    public boolean canUse() {
        if (((NetheriteGolemEntity)mob).getFireballs() <= 0) {
            return super.canUse();
        }
        return false;
    }
}
