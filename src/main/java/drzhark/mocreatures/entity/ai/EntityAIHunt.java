/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import com.google.common.base.Predicate;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class EntityAIHunt<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<EntityLivingBase> {
    private final EntityCreature hunter;
    private final List<Class<? extends EntityLivingBase>> targetClasses;

    public EntityAIHunt(EntityCreature entity, List<Class<? extends EntityLivingBase>> targetClasses, int chance, boolean checkSight, boolean onlyNearby, final Predicate<EntityLivingBase> extraPredicate) {
        super(entity, EntityLivingBase.class, chance, checkSight, onlyNearby, potentialTarget -> {
            if (potentialTarget == null) {
                return false;
            }

            if (potentialTarget instanceof MoCEntityTameableAnimal && ((MoCEntityTameableAnimal) potentialTarget).getIsTamed()) {
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
        });

        this.hunter = entity;
        this.targetClasses = targetClasses;
    }

    public EntityAIHunt(EntityCreature entityCreature, List<Class<? extends EntityLivingBase>> targetClasses, boolean checkSight) {
        this(entityCreature, targetClasses, checkSight, false);
    }

    public EntityAIHunt(EntityCreature entity, List<Class<? extends EntityLivingBase>> targetClasses, boolean checkSight, boolean onlyNearby) {
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
        boolean hunterHasOwner = (this.hunter instanceof MoCEntityTameableAnimal) && ((MoCEntityTameableAnimal) this.hunter).getIsTamed();
        if (hunterTargetsPlayers && hunterHasOwner) {
            return false;
        }

        return ((MoCEntityAnimal) this.hunter).getIsHunting() && super.shouldExecute();
    }
}
