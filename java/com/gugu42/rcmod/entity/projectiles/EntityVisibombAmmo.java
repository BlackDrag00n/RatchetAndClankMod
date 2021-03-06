package com.gugu42.rcmod.entity.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityVisibombAmmo extends EntityThrowable
 {
	private int ticksInAir;
	private EntityLivingBase entityFiring;
    private int entityFiringID = -1;

	public EntityVisibombAmmo(World par1World) {
		super(par1World);
		double speed = 0.5;
		this.motionX *= speed;
		this.motionY *= speed;
		this.motionZ *= speed;
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 0.0001f, 0.05F);
	}

	public EntityVisibombAmmo(World par1World,
			EntityLivingBase par2EntityLivingBase) {
		super(par1World, par2EntityLivingBase);
		double speed = 0.5;
        this.motionX *= speed;
        this.motionY *= speed;
        this.motionZ *= speed;
		this.entityFiring = par2EntityLivingBase; 
		this.entityFiringID = entityFiring.getEntityId();
	}

	public EntityVisibombAmmo(World par1World, double par2, double par4,
			double par6) {
		super(par1World, par2, par4, par6);
	}

	@Override
	protected void onImpact(MovingObjectPosition movingobjectposition) {
		if (movingobjectposition.entityHit != null) {
			this.worldObj.createExplosion(this, this.posX, this.posY,
					this.posZ, 0.1F, true);
			movingobjectposition.entityHit.attackEntityFrom(
					DamageSource.causeThrownDamage(this, this.getThrower()),
					12.0F);
			// movingobjectposition.entityHit.setFire(5);
			this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY,
					this.posZ, 0.0D, 0.0D, 0.0D);
			this.setDead();
		} else {
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ,
					0.0F, true);
			this.setDead();
		}
//		this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ,
//				0.0F, true);
//		this.setDead();
	}

	protected float getGravityVelocity() {
		return 0.0F;
	}

	public void onUpdate() {
		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;
		super.onUpdate();
		++this.ticksInAir;
		
		if (this.ticksInAir == 150) {
			this.setDead();
		}
	}
	
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
//        entityFiringID = nbt.getInteger("entityFiringID");
    }
	
	public void writeToNBT(NBTTagCompound nbt)
	{
	    super.writeToNBT(nbt);
//	    nbt.setInteger("entityFiringID", entityFiringID);
	}

  /*  public int getFiringEntityID()
    {
        return entityFiringID;
    }*/
}