package drzhark.mocreatures.entity.passive;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameable;

public class MoCEntityMole extends MoCEntityTameable {

    public MoCEntityMole(World world) 
    {
        super(world);
        setSize(1F, 0.5F);
    }

    @Override
    public ResourceLocation getTexture()
    {
        return MoCreatures.proxy.getTexture("mole.png");
    }


    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(22, Byte.valueOf((byte) 0)); // state - 0 outside / 1 digging / 2 underground / 3 pick-a-boo 

    }


    public boolean isOnDirt()
    {
        int j = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(this.boundingBox.minY - 0.5D), MathHelper.floor_double(posZ));
        return isDiggableBlock(j);//(j == 2 | j == 3 | j == 12);
    }

    private boolean isDiggableBlock(int i)
    {
        return i == 2 | i == 3 | i == 12;
    }
    
    
    /**
     * Moves entity forward underground
     */
    private void digForward()
    {
        double coordY = posY;
        double coordZ = this.posZ;
        double coordX = this.posX;
        int x = 1;
        double newPosY = coordY - Math.cos( (this.rotationPitch- 90F) / 57.29578F) * x;
        double newPosX = coordX + Math.cos((MoCTools.realAngle(this.rotationYaw- 90F) / 57.29578F)) * (Math.sin( (this.rotationPitch- 90F) / 57.29578F) * x );
        double newPosZ = coordZ + Math.sin((MoCTools.realAngle(this.rotationYaw- 90F) / 57.29578F)) * (Math.sin( (this.rotationPitch- 90F) / 57.29578F) * x );
        int iTarget = this.worldObj.getBlockId( MathHelper.floor_double(newPosX),  MathHelper.floor_double(newPosY),  MathHelper.floor_double(newPosZ)); 
        if (isDiggableBlock(iTarget))
        {
            this.setPosition(newPosX, newPosY, newPosZ);   
        }
    }
    
    /**
     * obtains State
     * @return 0 outside / 1 digging / 2 underground / 3 pick-a-boo
     */
    public byte getState()
    {
        return dataWatcher.getWatchableObjectByte(22);
    }

    /**
     * Changes the state
     * @param b 0 outside / 1 digging / 2 underground / 3 pick-a-boo
     */
    public void setState(byte b)
    {
        dataWatcher.updateObject(22, Byte.valueOf(b));
    }


    @Override
    public int pitchRotationOffset() 
    {

        int i = (int)getState();
        switch (i)
        {
        case 0:
            return 0;
        case 1:
            return -45;
        case 2:
            return 0;
        case 3:
            return 60;
        default: 
            return 0;
        }
    }

    @Override
    public float getAdjustedYOffset()
    {
        int i = (int)getState();
        switch (i)
        {
        case 0:
            return 0F;
        case 1:
            return 0.3F;
        case 2:
            return 1F;
        case 3:
            return 0.1F;
        default: 
            return 0F;
        }
    }


    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();


        if (MoCreatures.isServer())
        {
            if (rand.nextInt(10) == 0 && getState() == 1)
            {
                setState((byte)2);
            }

            if (getState() != 2 && getState() != 1 && isOnDirt())
            {
                EntityLivingBase entityliving = getBoogey(4D);
                if ((entityliving != null) && canEntityBeSeen(entityliving))
                {
                    setState((byte)1);
                    setPathToEntity(null);
                }
            }

            //if underground and no enemies: pick a boo
            if (rand.nextInt(20) == 0 && getState() == 2 && (getBoogey(4D) == null))
            {
                setState((byte)3);
                setPathToEntity(null);
            }

            //if not on dirt, get out!
            if (getState() != 0 && !isOnDirt())
            {
                setState((byte)0);
            }

            
            if (rand.nextInt(30) == 0 && getState() == 3)
            {
                setState((byte)2);
            }
            
            /*if (getState() == 2)
            {
                if (rand.nextInt(50) == 0) digForward();
            }*/
            
            //digging fx
            if ((getState() == 1 || getState() == 2) && !this.inWater)
            {
                setSprinting(true);
            }
            else
            {
                setSprinting(false);
            }
        }
    }

    @Override
    protected boolean isMovementCeased()
    {
        return getState() == 1 || getState() == 3;
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        if (getState() != 2) return super.attackEntityFrom(damagesource, i);
        return false;
    }
    
    @Override
    public boolean canBeCollidedWith()
    {
        return (getState() != 2);
    }
    
    @Override
    public boolean canBePushed()
    {
        return (getState() != 2);
    }
    
    @Override
    protected void collideWithEntity(Entity par1Entity)
    {
        if (getState() != 2) super.collideWithEntity(par1Entity);
//            par1Entity.applyEntityCollision(this);
    }
    
    @Override
    public boolean isEntityInsideOpaqueBlock()
    {
        if (getState() == 2) { return false; }
        return super.isEntityInsideOpaqueBlock();
    }
    
    @Override
    public void onDeath(DamageSource damagesource)
    {
        //System.out.println(this + " is dying with health of " + this.func_110143_aJ() + " and State of " + getState());
        super.onDeath(damagesource);
    }
    
    @Override
    public boolean isEntityInvulnerable()
    {
        if (getState() == 2) return true;
        return super.isEntityInvulnerable();
    }
    
    @Override
    protected int getDropItemId()
    {
        return MoCreatures.fur.itemID;
    }
    
     @Override
        protected String getDeathSound()
        {
            return "mocreatures:rabbitdeath";
        }

        @Override
        protected String getHurtSound()
        {
            return "mocreatures:rabbithurt";
        }

        @Override
        protected String getLivingSound()
        {
            return null;
        }
}