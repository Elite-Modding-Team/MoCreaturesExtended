/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hunter;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import javax.annotation.Nullable;

public class MoCEntityLeopard extends MoCEntityBigCat {
    public MoCEntityLeopard(World world) {
        super(world);
        setSize(1.165F, 1.01F);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    }

    @Override
    public void selectType() {

        if (getType() == 0) {
            checkSpawningBiome();
        }
        super.selectType();
    }

    @Override
    public boolean checkSpawningBiome() {
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(getEntityBoundingBox().minY);
        int k = MathHelper.floor(this.posZ);
        BlockPos pos = new BlockPos(i, j, k);

        Biome currentbiome = MoCTools.biomeKind(this.world, pos);
        try {
            if (BiomeDictionary.hasType(currentbiome, Type.SNOWY)) {
                setType(2); //snow leopard
                return true;
            }
        } catch (Exception ignored) {
        }
        setType(1);
        return true;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        if (this.getIsRideable() && this.getIsAdult() && (!this.getIsChested() || !player.isSneaking()) && !this.isBeingRidden()) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                setSitting(false);
            }

            return true;
        }

        return super.processInteract(player, hand);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        if (!getIsAdult()) {
            return null;
        }

        return MoCLootTables.LEOPARD;
    }

    @Override
    public String getOffspringClazz(IMoCTameable mate) {
        if (mate instanceof MoCEntityPanther && mate.getType() == 1) {
            return "Panthard";//"Panther";
        }
        if (mate instanceof MoCEntityTiger && mate.getType() == 1) {
            return "Leoger";//"Tiger";
        }
        if (mate instanceof MoCEntityLion && mate.getType() == 2) {
            return "Liard";//"Lion";
        }
        return "Leopard";
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        if (mate instanceof MoCEntityPanther && mate.getType() == 1) {
            return 1;//3; //panthard
        }
        if (mate instanceof MoCEntityTiger && mate.getType() == 1) {
            return 1;//4; //leoger
        }
        if (mate instanceof MoCEntityLion && mate.getType() == 2) {
            return 1;//4; //liard
        }
        return this.getType();
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        return (mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getType() == this.getType())
                || (mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getType() == 1)
                || (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getType() == 1)
                || (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getType() == 2);
    }

    @Override
    public int getMaxAge() {
        return 95;
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        if (!this.getIsAdult() && (this.getAge() < this.getMaxAge() * 0.8)) {
            return false;
        }
        if (entity instanceof MoCEntityLeopard) {
            return false;
        }
        return entity.height < 1.3F && entity.width < 1.3F;
    }

    public float getEyeHeight() {
        return this.height * 0.92F;
    }
}
