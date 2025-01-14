/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PathNavigateFlyer extends PathNavigate {

    public PathNavigateFlyer(EntityLiving entitylivingIn, World worldIn) {
        super(entitylivingIn, worldIn);
    }

    protected PathFinder getPathFinder() {
        return new PathFinder(new FlyNodeProcessor());
    }

    /**
     * If on ground or swimming and can swim
     */
    protected boolean canNavigate() {
        return true;
    }

    protected Vec3d getEntityPosition() {
        return new Vec3d(this.entity.posX, this.entity.posY + (double) this.entity.height * 0.5D, this.entity.posZ);
    }

    protected void pathFollow() {
        Vec3d vec3 = this.getEntityPosition();
        float f = this.entity.width * this.entity.width;
        byte b0 = 6;

        if (vec3.squareDistanceTo(this.currentPath.getVectorFromIndex(this.entity, this.currentPath.getCurrentPathIndex())) < (double) f) {
            this.currentPath.incrementPathIndex();
        }

        for (int i = Math.min(this.currentPath.getCurrentPathIndex() + b0, this.currentPath.getCurrentPathLength() - 1); i > this.currentPath
                .getCurrentPathIndex(); --i) {
            Vec3d vec31 = this.currentPath.getVectorFromIndex(this.entity, i);

            if (vec31.squareDistanceTo(vec3) <= 36.0D && this.isDirectPathBetweenPoints(vec3, vec31, 0, 0, 0)) {
                this.currentPath.setCurrentPathIndex(i);
                break;
            }
        }

        this.checkForStuck(vec3);
    }

    /**
     * Trims path data from the end to the first sun covered block
     */
    protected void removeSunnyPath() {
        super.removeSunnyPath();
    }

    /**
     * Returns true when an entity of specified size could safely walk in a straight line between the two points. Args:
     * pos1, pos2, entityXSize, entityYSize, entityZSize
     */
    @Override
    protected boolean isDirectPathBetweenPoints(Vec3d posVec31, Vec3d posVec32, int sizeX, int sizeY, int sizeZ) {
        RayTraceResult raytraceresult = this.world.rayTraceBlocks(posVec31, new Vec3d(posVec32.x, posVec32.y + (double) this.entity.height * 0.5D, posVec32.z), false, true, false);
        return raytraceresult == null || raytraceresult.typeOfHit == RayTraceResult.Type.MISS;
    }
}
