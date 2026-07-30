/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import drzhark.mocreatures.network.message.MoCMessageTwoBytes;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MoCEntityGolem extends MoCEntityMob implements IEntityAdditionalSpawnData {

    private static final DataParameter<Integer> GOLEM_STATE = EntityDataManager.createKey(MoCEntityGolem.class, DataSerializers.VARINT);
    public int tCounter;
    private byte[] golemCubes;
    private int dCounter = 0;
    private int sCounter;

    public MoCEntityGolem(World world) {
        super(world);
        this.setSize(1.8F, 4.3F);
        experienceValue = 20;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new MoCEntityGolem.AIGolemAttack(this, 1.0D, true));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new MoCEntityGolem.AIGolemTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new MoCEntityGolem.AIGolemTarget<>(this, EntityIronGolem.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);
    }

    @Override
    public void writeSpawnData(ByteBuf data) {
        for (int i = 0; i < 23; i++) data.writeByte(this.golemCubes[i]);
    }

    @Override
    public void readSpawnData(ByteBuf data) {
        for (int i = 0; i < 23; i++) this.golemCubes[i] = data.readByte();
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.initGolemCubes();
        this.dataManager.register(GOLEM_STATE, 0); // 0: spawned / 1: summoning rocks / 2: has enemy / 3: half life (harder) / 4: dying
    }

    public int getGolemState() {
        return this.dataManager.get(GOLEM_STATE);
    }

    public void setGolemState(int i) {
        this.dataManager.set(GOLEM_STATE, i);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.world.isRemote) {
            if (getGolemState() != 4) {
                if (getGolemState() == 0) // just spawned
                {
                    EntityPlayer entityplayer1 = this.world.getClosestPlayerToEntity(this, 8D);
                    if (entityplayer1 != null) setGolemState(1); // activated
                }

                if (getGolemState() == 1 && !isMissingCubes()) setGolemState(2); // is complete

                if (getGolemState() > 2 && this.getAttackTarget() == null) setGolemState(1);

                if (getGolemState() > 1 && this.getAttackTarget() != null && this.rand.nextInt(20) == 0) {
                    if (getHealth() >= 30) setGolemState(2);
                    if (getHealth() < 30) setGolemState(3); // More dangerous
                }

                if (getGolemState() != 0 && getGolemState() != 4 && isMissingCubes()) {
                    int freq = 42 - (getGolemState() * this.world.getDifficulty().getId());
                    if (getGolemState() == 1) freq = 20;
                    if (this.rand.nextInt(freq) == 0) acquireRock(2);
                }
            } else {
                this.getNavigator().clearPath();
                this.dCounter++;

                if (this.dCounter < 80 && this.rand.nextInt(3) == 0) acquireRock(4);

                if (this.dCounter == 120) {
                    if (MoCreatures.proxy.legacyBigGolemSounds) {
                        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_BIG_GOLEM_DEATH_LEGACY, 1.0F);
                    } else {
                        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_BIG_GOLEM_DEATH, 1.0F);
                    }
                    MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1), new TargetPoint(this.world.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
                }

                if (this.dCounter > 140) {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EXPLODE, 3.0F);
                    destroyGolem();
                }
            }
        }

        if (this.tCounter == 0 && this.getAttackTarget() != null && this.canShoot()) {
            float distanceToTarget = this.getDistance(this.getAttackTarget());
            if (distanceToTarget > 6F) {
                this.tCounter = 1;
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0), new TargetPoint(this.world.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
            }
        }

        if (this.tCounter != 0) {
            if (this.tCounter++ == 70 && this.getAttackTarget() != null && this.canShoot() && !this.getAttackTarget().isDead && this.canEntityBeSeen(this.getAttackTarget())) {
                shootBlock(this.getAttackTarget());
            } else if (this.tCounter > 90) this.tCounter = 0;
        }

        if (this.sCounter != 0) {
            this.sCounter++;
            if (this.sCounter > 90) {
                this.sCounter = 0;
            }
        }

        // TODO: Fix particle positioning
        if (MoCreatures.proxy.getParticleFX() > 0 && getGolemState() == 4 && this.sCounter > 0) {
            for (int i = 0; i < 10; i++) {
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX, this.posY + 0.5D, this.posZ, this.rand.nextGaussian(), this.rand.nextGaussian(), this.rand.nextGaussian());
            }
        }
    }

    private void destroyGolem() {
        if (!this.world.isRemote && this.world instanceof WorldServer) {
            WorldServer worldServer = (WorldServer) this.world;
            worldServer.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX, this.posY + (this.height / 2.0F), this.posZ, 20, 1.0D, 2.0D, 1.0D, 0.2D);
        }

        List<Integer> usedBlocks = usedCubes();
        if (!usedBlocks.isEmpty() && MoCTools.mobGriefing(this.world) && MoCreatures.proxy.golemDestroyBlocks) {
            for (Integer usedBlock : usedBlocks) {
                Block block = Block.getBlockById(generateBlock(this.golemCubes[usedBlock]));
                EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY, this.posZ, new ItemStack(block, 1, 0));
                entityitem.setDefaultPickupDelay();
                this.world.spawnEntity(entityitem);
            }
        }
        this.setDead();
    }

    @Override
    public boolean isMovementCeased() {
        return getGolemState() == 4;
    }

    /*
     * Finds a missing rock spot in its body, looks for a random rock around it
     */
    protected void acquireRock(int type) {
        if (this.world.isRemote) return;

        BlockPos blockPos = MoCTools.getRandomSurfaceBlockPos(this, 12);
        IBlockState blockState = this.world.getBlockState(blockPos);
        Block block = blockState.getBlock();

        boolean canDestroyBlock = MoCTools.mobGriefing(this.world) && MoCreatures.proxy.golemDestroyBlocks;
        if (block instanceof BlockAir || blockState.getBlockHardness(this.world, blockPos) < 0 || blockState.getBlockHardness(this.world, blockPos) > 50)
            canDestroyBlock = false; // skip air and unbreakable rocks
        if (canDestroyBlock) {
            BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(this.world, blockPos, blockState, FakePlayerFactory.get((WorldServer) this.world, MoCreatures.MOCFAKEPLAYER));
            if (!event.isCanceled()) this.world.destroyBlock(blockPos, false); // destroys the original rock
        } else blockState = returnRandomCheapBlock(); // get cheap rocks

        MoCEntityThrowableRock tRock = new MoCEntityThrowableRock(this.world, this, blockPos.getX(), blockPos.getY() + 1, blockPos.getZ());
        tRock.setState(blockState);
        tRock.setBehavior(type); // 2: rock follows the golem / 3: rock gets around the golem
        this.world.spawnEntity(tRock); // spawns the new TRock
    }

    /**
     * Returns a random block when the golem is unable to break blocks
     */
    private IBlockState returnRandomCheapBlock() {
        int i = this.rand.nextInt(4);
        switch (i) {
            case 1:
                return Blocks.COBBLESTONE.getDefaultState();
            case 2:
                return Blocks.PLANKS.getDefaultState();
            case 3:
                return Blocks.ICE.getDefaultState();
            default:
                return Blocks.DIRT.getDefaultState();
        }
    }

    /**
     * When the golem receives the rock, called from within EntityTRock
     */
    public void receiveRock(IBlockState state) {
        if (!this.world.isRemote) {
            byte myBlock = translateOre(Block.getIdFromBlock(state.getBlock()));
            byte slot = (byte) getRandomCubeAdj();
            if (slot != -1 && slot < 23 && myBlock != -1 && getGolemState() != 4) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_BIG_GOLEM_ATTACH, 1.0F);
                int h = this.world.getDifficulty().getId();
                this.setHealth(getHealth() + h);
                if (getHealth() > getMaxHealth()) this.setHealth(getMaxHealth());
                saveGolemCube(slot, myBlock);
            } else {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_BIG_GOLEM_CLANG, 1.0F);
                if ((MoCTools.mobGriefing(this.world)) && (MoCreatures.proxy.golemDestroyBlocks)) {
                    EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY, this.posZ, new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)));
                    entityitem.setDefaultPickupDelay();
                    entityitem.setAgeToCreativeDespawnTime(); // 4800
                }
            }
        }
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 0) this.tCounter = 1; // rock throwing animation
        else if (animationType == 1) this.sCounter = 1; // smoking animation
    }

    /**
     * Shoots one block to the target
     */
    private void shootBlock(Entity entity) {
        if (entity == null) return;

        List<Integer> armBlocks = new ArrayList<>();
        for (int i = 9; i < 15; i++) {
            if (this.golemCubes[i] != 30) armBlocks.add(i);
        }
        if (armBlocks.isEmpty()) return;

        int j = this.rand.nextInt(armBlocks.size());
        int i = armBlocks.get(j);
        int x = i;

        if (i == 9 || i == 12) {
            if (this.golemCubes[i + 2] != 30) x = i + 2;
            else if (this.golemCubes[i + 1] != 30) x = i + 1;
        }

        if (this.golemCubes[i + 1] != 30 && (i == 10 || i == 13)) x = i + 1;
        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_LAUNCH, 3F);
        MoCTools.throwStone(this, entity, Block.getStateById(generateBlock(this.golemCubes[x])), 10D, 0.4D);
        saveGolemCube((byte) x, (byte) 30);
        this.tCounter = 0;
    }

    private boolean canShoot() {
        int x = 0;
        for (byte i = 9; i < 15; i++) {
            if (this.golemCubes[i] != 30) x++;
        }
        return (x != 0) && getGolemState() != 4 && getGolemState() != 1;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (getGolemState() == 4) {
            return false;
        }

        if (this.getHealth() - i <= 0) {
            this.setHealth(1.0F);
            this.setGolemState(4);
            this.getNavigator().clearPath();
            return true;
        }

        List<Integer> missingChestBlocks = missingChestCubes();
        boolean uncoveredChest = (missingChestBlocks.size() == 4);
        if (!openChest() && !uncoveredChest && getGolemState() != 1) {
            int j = this.world.getDifficulty().getId();
            if (!this.world.isRemote && this.rand.nextInt(j) == 0) destroyRandomGolemCube();
            else MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_BIG_GOLEM_CLANG, 1.0F);

            Entity entity = damagesource.getTrueSource();
            if ((entity != this) && (this.world.getDifficulty().getId() > 0) && entity instanceof EntityLivingBase) {
                this.setAttackTarget((EntityLivingBase) entity);
                return true;
            } else return false;
        }
        if (i > 5) i = 5; //so you can't hit a Golem too hard
        if (getGolemState() != 1 && super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();
            if ((entity != this) && (this.world.getDifficulty().getId() > 0) && entity instanceof EntityLivingBase) {
                this.setAttackTarget((EntityLivingBase) entity);
                return true;
            } else return false;
        }
        if (getGolemState() == 1) {
            Entity entity = damagesource.getTrueSource();
            if ((entity != this) && (this.world.getDifficulty().getId() > 0) && entity instanceof EntityLivingBase) {
                this.setAttackTarget((EntityLivingBase) entity);
                return true;
            } else return false;
        } else return false;
    }

    /**
     * Destroys a random cube, with the proper check for extremities and spawns
     * a block in world
     */
    private void destroyRandomGolemCube() {
        int i = getRandomUsedCube();
        if (i == 4) return; // do not destroy the valuable back cube

        int x = i;
        if (i == 10 || i == 13 || i == 16 || i == 19) {
            if (this.golemCubes[i + 1] != 30) x = i + 1;
        }

        if (i == 9 || i == 12 || i == 15 || i == 18) {
            if (this.golemCubes[i + 2] != 30) x = i + 2;
            else if (this.golemCubes[i + 1] != 30) x = i + 1;
        }

        if (x != -1 && this.golemCubes[x] != 30) {
            Block block = Block.getBlockById(generateBlock(this.golemCubes[x]));
            saveGolemCube((byte) x, (byte) 30);
            if (MoCreatures.proxy.legacyBigGolemSounds) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_BIG_GOLEM_HURT_LEGACY, 1.0F);
            } else {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_BIG_GOLEM_HURT, 1.0F);
            }
            if ((MoCTools.mobGriefing(this.world)) && (MoCreatures.proxy.golemDestroyBlocks)) {
                EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY, this.posZ, new ItemStack(block, 1, 0));
                entityitem.setDefaultPickupDelay();
                this.world.spawnEntity(entityitem);
            }
        }
    }

    @Override
    public float getAdjustedYOffset() {
        if (this.golemCubes[17] != 30 || this.golemCubes[20] != 30) return 0F; // has feet
        if (this.golemCubes[16] != 30 || this.golemCubes[19] != 30) return 0.4F; // has knees but not feet
        if (this.golemCubes[15] != 30 || this.golemCubes[18] != 30) return 0.7F; // has thighs but not knees or feet
        if (this.golemCubes[1] != 30 || this.golemCubes[3] != 30) return 0.8F; // has lower chest
        return 1.45F; //missing everything
    }

    /**
     * Stretches the model to that size
     */
    @Override
    public float getSizeFactor() {
        return 1.8F;
    }

    /**
     * @param i = slot
     * @return the block type stored in that slot. 30 = empty
     */
    public byte getBlockText(int i) {
        return this.golemCubes[i];
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("golemState", getGolemState());
        NBTTagList cubeLists = new NBTTagList();

        for (int i = 0; i < 23; i++) {
            NBTTagCompound nbttag = new NBTTagCompound();
            nbttag.setByte("Slot", this.golemCubes[i]);
            cubeLists.appendTag(nbttag);
        }
        nbttagcompound.setTag("GolemBlocks", cubeLists);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setGolemState(nbttagcompound.getInteger("golemState"));
        NBTTagList nbttaglist = nbttagcompound.getTagList("GolemBlocks", 10);
        for (int i = 0; i < 23; i++) {
            NBTTagCompound var4 = nbttaglist.getCompoundTagAt(i);
            this.golemCubes[i] = var4.getByte("Slot");
        }
    }

    /**
     * Initializes the goleCubes array
     */
    private void initGolemCubes() {
        this.golemCubes = new byte[23];

        for (int i = 0; i < 23; i++) this.golemCubes[i] = 30;

        int j = this.rand.nextInt(4);
        switch (j) {
            case 0:
                j = 7;
                break;
            case 1:
                j = 11;
                break;
            case 2:
                j = 15;
                break;
            case 3:
                j = 21;
                break;
        }
        saveGolemCube((byte) 4, (byte) j);
    }

    /**
     * Saves the type of Cube(value) on the given 'slot' if server, then sends a
     * packet to the clients
     */
    public void saveGolemCube(byte slot, byte value) {
        this.golemCubes[slot] = value;
        if (!this.world.isRemote && MoCreatures.proxy.worldInitDone) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageTwoBytes(this.getEntityId(), slot, value), new TargetPoint(this.world.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
        }
    }

    /**
     * returns a list of the empty blocks
     */
    private List<Integer> missingCubes() {
        List<Integer> emptyBlocks = new ArrayList<>();

        for (int i = 0; i < 23; i++) {
            if (this.golemCubes[i] == 30) emptyBlocks.add(i);
        }
        return emptyBlocks;
    }

    /**
     * Returns true if is 'missing' any cube, false if it's full
     */
    public boolean isMissingCubes() {
        for (int i = 0; i < 23; i++) {
            if (this.golemCubes[i] == 30) return true;
        }
        return false;
    }

    private List<Integer> missingChestCubes() {
        List<Integer> emptyChestBlocks = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            if (this.golemCubes[i] == 30) emptyChestBlocks.add(i);
        }
        return emptyChestBlocks;
    }

    /**
     * returns a list of the used block spots
     */
    private List<Integer> usedCubes() {
        List<Integer> usedBlocks = new ArrayList<>();

        for (int i = 0; i < 23; i++) {
            if (this.golemCubes[i] != 30) usedBlocks.add(i);
        }
        return usedBlocks;
    }

    /**
     * Returns a random used cube position if the golem is empty, returns -1
     */
    private int getRandomUsedCube() {
        List<Integer> usedBlocks = usedCubes();
        if (usedBlocks.isEmpty()) return -1;
        int randomEmptyBlock = this.rand.nextInt(usedBlocks.size());
        return usedBlocks.get(randomEmptyBlock);
    }

    /**
     * Returns a random empty cube position if the golem is full, returns -1
     */
    private int getRandomMissingCube() {
        //first it makes sure it has the four chest cubes
        List<Integer> emptyChestBlocks = missingChestCubes();
        if (!emptyChestBlocks.isEmpty()) {
            int randomEmptyBlock = this.rand.nextInt(emptyChestBlocks.size());
            return emptyChestBlocks.get(randomEmptyBlock);
        }

        //otherwise returns any other cube
        List<Integer> emptyBlocks = missingCubes();
        if (emptyBlocks.isEmpty()) {
            return -1;
        }
        int randomEmptyBlock = this.rand.nextInt(emptyBlocks.size());
        return emptyBlocks.get(randomEmptyBlock);
    }

    /**
     * returns the position of the cube to be added, contains logic for the
     * extremities
     */
    private int getRandomCubeAdj() {
        int i = getRandomMissingCube();

        if (i == 10 || i == 13 || i == 16 || i == 19) {
            if (this.golemCubes[i - 1] != 30) saveGolemCube((byte) i, this.golemCubes[i - 1]);
            return i - 1;
        }

        if (i == 11 || i == 14 || i == 17 || i == 20) {
            if (this.golemCubes[i - 2] == 30 && this.golemCubes[i - 1] == 30) return i - 2;
            if (this.golemCubes[i - 1] != 30) saveGolemCube((byte) i, this.golemCubes[i - 1]);
            saveGolemCube((byte) (i - 1), this.golemCubes[i - 2]);
            return i - 2;
        }
        return i;
    }

    @Override
    public float rollRotationOffset() {
        int leftLeg = 0;
        int rightLeg = 0;
        if (this.golemCubes[15] != 30) leftLeg++;
        if (this.golemCubes[16] != 30) leftLeg++;
        if (this.golemCubes[17] != 30) leftLeg++;
        if (this.golemCubes[18] != 30) rightLeg++;
        if (this.golemCubes[19] != 30) rightLeg++;
        if (this.golemCubes[20] != 30) rightLeg++;
        return (leftLeg - rightLeg) * 10F;
    }

    /**
     * The chest opens when the Golem is missing cubes and the summoned blocks
     * are close
     */
    public boolean openChest() {
        if (isMissingCubes()) {
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().grow(2D));
            for (Entity entity1 : list) {
                if (entity1 instanceof MoCEntityThrowableRock) {
                    // TODO: Fix particle positioning
                    if (MoCreatures.proxy.getParticleFX() > 0) MoCreatures.proxy.VacuumFX(this);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Converts the world block into the golem block texture if not found,
     * returns -1
     */
    private byte translateOre(int blockType) {
        switch (blockType) {
            case 0:
            case 1:
                return 0;
            case 18:
                return 10; //leaves
            case 2:
            case 3:
                return 1; //dirt, grass
            case 4:
            case 48:
                return 2; //cobblestones
            case 5:
                return 3;
            case 12:
                return 4;
            case 13:
                return 5;
            case 16:
            case 21:
            case 56:
            case 74:
            case 73:
                return 24; //all ores are transformed into diamond ore
            case 14:
            case 41:
                return 7; //ore gold and block gold = block gold
            case 15:
            case 42:
                return 11;//iron ore and blocks = block iron
            case 57:
                return 15; //block diamond
            case 17:
                return 6; //wood
            case 20:
                return 8;
            case 22:
            case 35: //lapis and cloths
                return 9;
            case 45:
                return 12; //brick
            case 49:
                return 14; //obsidian
            case 58:
                return 16; //workbench
            case 61:
            case 62:
                return 17; //stonebench
            case 78:
            case 79:
                return 18; //ice
            case 81:
                return 19; //cactus
            case 82:
                return 20; //clay
            case 86:
            case 91:
            case 103:
                return 22; //pumpkin pumpkin lantern melon
            case 87:
                return 23; //netherrack
            case 89:
                return 25; //glowstone
            case 98:
                return 26; //stonebrick
            case 112:
                return 27; //netherbrick
            case 129:
            case 133:
                return 21; //emeralds
            default:
                return -1;
        }
    }

    /**
     * Provides the blockID originated from the golem's block
     */
    private int generateBlock(int golemBlock) {
        switch (golemBlock) {
            case 0:
                return 1;
            case 1:
                return 3;
            case 2:
                return 4;
            case 3:
                return 5;
            case 4:
                return 12;
            case 5:
                return 13;
            case 6:
                return 17;
            case 7:
                return 41;
            case 8:
                return 20;
            case 9:
                return 35;
            case 10:
                return 18;
            case 11:
                return 42;
            case 12:
                return 45;
            case 13: //unused
                return 2;
            case 14:
                return 49;
            case 15:
                return 57;
            case 16:
                return 58;
            case 17:
                return 51;
            case 18:
                return 79;
            case 19:
                return 81;
            case 20:
                return 82;
            case 21:
                return 133;
            case 22:
                return 86;
            case 23:
                return 87;
            case 24:
                return 56;
            case 25:
                return 89;
            case 26:
                return 98;
            case 27:
                return 112;
            default:
                return 2;
        }
    }

    private int countLegBlocks() {
        int x = 0;
        for (byte i = 15; i < 21; i++) {
            if (this.golemCubes[i] != 30) x++;
        }
        return x;
    }

    @Override
    public float getAIMoveSpeed() {
        return 0.15F * (countLegBlocks() / 6F);
    }

    /**
     * Used for the particle FX
     */
    public float colorFX(int i) {
        switch (getGolemState()) {
            case 1:
                if (i == 1) return 65F / 255F;
                if (i == 2) return 157F / 255F;
                if (i == 3) return 254F / 255F;
            case 2:
                if (i == 1) return 244F / 255F;
                if (i == 2) return 248F / 255F;
                if (i == 3) return 36F / 255F;
            case 3:
                if (i == 1) return 1F;
                if (i == 2) return 154F / 255F;
                if (i == 3) return 21F / 255F;
            case 4:
                if (i == 1) return 248F / 255F;
                if (i == 2) return 10F / 255F;
                if (i == 3) return 10F / 255F;
            default:
                return 0;
        }
    }

    @Override
    protected void playStepSound(BlockPos p_180429_1_, Block p_180429_2_) {
        if (MoCreatures.proxy.legacyBigGolemSounds) {
            this.playSound(MoCSoundEvents.ENTITY_GENERIC_STOMP, 1.0F, 1.0F);
        } else {
            this.playSound(MoCSoundEvents.ENTITY_BIG_GOLEM_STEP, 1.0F, 1.0F);
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCreatures.proxy.legacyBigGolemSounds ? MoCSoundEvents.ENTITY_BIG_GOLEM_HURT_LEGACY : MoCSoundEvents.ENTITY_BIG_GOLEM_HURT;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.BIG_GOLEM;
    }

    @Override
    public boolean getCanSpawnHere() {
        return (super.getCanSpawnHere() && this.world.canBlockSeeSky(new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY), MathHelper.floor(this.posZ))) && (this.posY > 50D));
    }

    public float getEyeHeight() {
        return this.height * 0.935F;
    }

    static class AIGolemAttack extends EntityAIAttackMelee {
        public AIGolemAttack(MoCEntityGolem golem, double speed, boolean useLongMemory) {
            super(golem, speed, useLongMemory);
        }

        @Override
        public boolean shouldContinueExecuting() {
            float f = this.attacker.getBrightness();

            if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
                this.attacker.setAttackTarget(null);
                return false;
            } else {
                return super.shouldContinueExecuting();
            }
        }
    }

    static class AIGolemTarget<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {
        public AIGolemTarget(MoCEntityGolem golem, Class<T> classTarget, boolean checkSight) {
            super(golem, classTarget, checkSight);
        }

        @Override
        public boolean shouldExecute() {
            float f = this.taskOwner.getBrightness();
            return f < 0.5F && super.shouldExecute();
        }
    }
}
