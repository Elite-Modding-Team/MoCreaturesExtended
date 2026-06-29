/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import com.google.common.base.Predicate;
import drzhark.mocreatures.entity.MoCEntityAquatic;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAquatic;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;
import javax.annotation.Nullable;

public class EntityAIHuntAquatic extends EntityAINearestAttackableTarget<EntityLivingBase> {
    private final EntityCreature hunter;
    private final List<Class<? extends EntityLivingBase>> targetClasses;

    public EntityAIHuntAquatic(EntityCreature entity, final List<Class<? extends EntityLivingBase>> targetClasses, int chance, boolean checkSight, boolean onlyNearby, @Nullable final Predicate<EntityLivingBase> extraPredicate) {
        super(entity, EntityLivingBase.class, chance, checkSight, onlyNearby, createTargetPredicate(targetClasses, extraPredicate));
        this.hunter = entity;
        this.targetClasses = targetClasses;
    }

    private static Predicate<EntityLivingBase> createTargetPredicate(final List<Class<? extends EntityLivingBase>> targetClasses, @Nullable final Predicate<EntityLivingBase> extraPredicate) {
        return potentialTarget -> {
            if (potentialTarget == null) {
                return false;
            }

            if (potentialTarget instanceof MoCEntityTameableAquatic && ((MoCEntityTameableAquatic) potentialTarget).getIsTamed()) {
                return false;
            }

            boolean isAllowedTarget = false;
            for (Class<? extends EntityLivingBase> allowedClass : targetClasses) {
                if (allowedClass.isAssignableFrom(potentialTarget.getClass())) {
                    isAllowedTarget = true;
                    break;
                }
            }

            if (!isAllowedTarget) {
                return false;
            }

            return extraPredicate == null || extraPredicate.apply(potentialTarget);
        };
    }

    public EntityAIHuntAquatic(EntityCreature entityCreature, List<Class<? extends EntityLivingBase>> targetClasses, boolean checkSight) {
        this(entityCreature, targetClasses, checkSight, false);
    }

    public EntityAIHuntAquatic(EntityCreature entity, List<Class<? extends EntityLivingBase>> targetClasses, boolean checkSight, boolean onlyNearby) {
        this(entity, targetClasses, 10, checkSight, onlyNearby, null);
    }

    @Override
    public boolean shouldExecute() {
        boolean hunterTargetsPlayers = false;
        for (Class<? extends EntityLivingBase> allowedClass : this.targetClasses) {
            if (EntityPlayer.class.isAssignableFrom(allowedClass)) {
                hunterTargetsPlayers = true;
                break;
            }
        }

        // Don't hunt when tamed and target entity is of class Player
        boolean hunterHasOwner = (this.hunter instanceof MoCEntityTameableAquatic) && ((MoCEntityTameableAquatic) this.hunter).getIsTamed();
        if (hunterTargetsPlayers && hunterHasOwner) {
            return false;
        }

        return ((MoCEntityAquatic) this.hunter).getIsHunting() && super.shouldExecute();
    }
}