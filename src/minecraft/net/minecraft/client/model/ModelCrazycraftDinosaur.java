package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.MathHelper;

/** Chunky block models with distinct predator and horned herbivore silhouettes. */
public class ModelCrazycraftDinosaur extends ModelBase
{
    private final ModelRenderer[] parts;
    private final ModelRenderer[] legs;
    private final ModelRenderer head;
    private final ModelRenderer jaw;
    private final ModelRenderer tail;
    private final boolean predator;

    public ModelCrazycraftDinosaur(boolean predator)
    {
        this.predator = predator;
        this.textureWidth = 128;
        this.textureHeight = 64;

        if (predator)
        {
            ModelRenderer body = box(0, 0, -8, -7, -12, 16, 14, 25, 0, 12, 3);
            ModelRenderer neck = box(0, 39, -5, -5, -7, 10, 10, 10, 0, 7, -11);
            head = box(44, 0, -6, -5, -13, 12, 10, 14, 0, 7, -16);
            ModelRenderer muzzle = box(0, 39, -5, -3, -20, 10, 6, 10, 0, 7, -16);
            jaw = box(52, 25, -5, 1, -18, 10, 4, 18, 0, 10, -16);
            ModelRenderer tongue = box(76, 25, -2, -1, -14, 4, 2, 12, 0, 10, -16);
            ModelRenderer leftEye = box(90, 0, -1, -2, -2, 3, 3, 3, -6, 4, -20);
            ModelRenderer rightEye = box(90, 0, -2, -2, -2, 3, 3, 3, 6, 4, -20);
            ModelRenderer leftBrow = box(90, 7, -3, -2, -3, 5, 3, 5, -6, 1, -20);
            ModelRenderer rightBrow = box(90, 7, -2, -2, -3, 5, 3, 5, 6, 1, -20);
            ModelRenderer[] teeth = new ModelRenderer[8];
            for (int index = 0; index < teeth.length; ++index)
            {
                float side = index % 2 == 0 ? -3.4F : 3.4F;
                float z = -14.0F - index / 2 * 3.0F;
                teeth[index] = box(104, 0, -1, 0, -1, 2, 4, 2, side, 9, z);
            }
            tail = box(0, 0, -5, -4, 0, 10, 8, 21, 0, 10, 14);
            ModelRenderer tailTip = box(0, 30, -3, -2, 0, 6, 5, 17, 0, 10, 31);
            ModelRenderer leftArm = box(88, 0, 0, 0, -8, 4, 4, 9, 6, 9, -9);
            ModelRenderer rightArm = box(88, 0, -4, 0, -8, 4, 4, 9, -6, 9, -9);
            legs = new ModelRenderer[] {
                box(88, 14, -5, 0, -4, 10, 18, 9, -6, 12, 8),
                box(88, 14, -5, 0, -4, 10, 18, 9, 6, 12, 8)
            };
            parts = new ModelRenderer[] {body, neck, head, muzzle, jaw, tongue, leftEye, rightEye, leftBrow, rightBrow,
                teeth[0], teeth[1], teeth[2], teeth[3], teeth[4], teeth[5], teeth[6], teeth[7],
                tail, tailTip, leftArm, rightArm, legs[0], legs[1]};
        }
        else
        {
            ModelRenderer body = box(0, 0, -9, -7, -14, 18, 14, 28, 0, 10, 2);
            head = box(0, 42, -7, -6, -10, 14, 11, 13, 0, 8, -15);
            ModelRenderer frill = box(65, 0, -11, -11, 0, 22, 15, 3, 0, 8, -15);
            ModelRenderer snout = box(48, 42, -5, -2, -17, 10, 6, 9, 0, 8, -15);
            ModelRenderer leftHorn = box(65, 20, -1, -12, -11, 3, 11, 3, -6, 8, -15);
            ModelRenderer rightHorn = box(65, 20, -2, -12, -11, 3, 11, 3, 6, 8, -15);
            ModelRenderer noseHorn = box(65, 20, -2, -9, -21, 4, 8, 4, 0, 8, -15);
            ModelRenderer leftEye = box(104, 0, -1, -2, -2, 3, 3, 3, -7, 4, -19);
            ModelRenderer rightEye = box(104, 0, -2, -2, -2, 3, 3, 3, 7, 4, -19);
            ModelRenderer leftCheek = box(104, 7, -2, -3, -3, 4, 5, 5, -6, 7, -19);
            ModelRenderer rightCheek = box(104, 7, -2, -3, -3, 4, 5, 5, 6, 7, -19);
            tail = box(0, 18, -4, -3, 0, 8, 6, 17, 0, 10, 17);
            legs = new ModelRenderer[] {
                box(88, 34, -4, 0, -4, 8, 15, 8, -7, 11, -9),
                box(88, 34, -4, 0, -4, 8, 15, 8, 7, 11, -9),
                box(88, 34, -4, 0, -4, 8, 15, 8, -7, 11, 12),
                box(88, 34, -4, 0, -4, 8, 15, 8, 7, 11, 12)
            };
            jaw = box(48, 42, -5, 1, -16, 10, 4, 9, 0, 10, -15);
            parts = new ModelRenderer[] {body, head, frill, snout, leftHorn, rightHorn, noseHorn,
                leftEye, rightEye, leftCheek, rightCheek, jaw, tail,
                legs[0], legs[1], legs[2], legs[3]};
        }
    }

    private ModelRenderer box(int u, int v, float x, float y, float z, int width, int height, int depth,
                              float pivotX, float pivotY, float pivotZ)
    {
        ModelRenderer part = new ModelRenderer(this, u, v);
        part.addBox(x, y, z, width, height, depth);
        part.setRotationPoint(pivotX, pivotY, pivotZ);
        return part;
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                       float netHeadYaw, float headPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
        for (ModelRenderer part : this.parts) part.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks,
                                  float netHeadYaw, float headPitch, float scale, Entity entity)
    {
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        boolean attacking = entity instanceof EntityLiving && ((EntityLiving)entity).getAttackTarget() != null;
        float bite = attacking ? (0.15F + Math.abs(MathHelper.sin(ageInTicks * 0.55F)) * 0.42F) : 0.0F;
        this.head.rotateAngleX = headPitch * 0.017453292F + (attacking ? MathHelper.sin(ageInTicks * 0.55F) * 0.12F : 0.0F);
        this.jaw.rotateAngleX = bite;
        float stride = MathHelper.cos(limbSwing * 0.65F) * 0.9F * limbSwingAmount;
        this.legs[0].rotateAngleX = stride;
        this.legs[1].rotateAngleX = -stride;
        if (!this.predator)
        {
            this.legs[2].rotateAngleX = -stride;
            this.legs[3].rotateAngleX = stride;
        }
        this.tail.rotateAngleY = MathHelper.sin(ageInTicks * 0.12F) * 0.12F;
        if (this.predator && attacking)
        {
            this.legs[0].rotateAngleX += MathHelper.sin(ageInTicks * 0.55F) * 0.16F;
            this.legs[1].rotateAngleX -= MathHelper.sin(ageInTicks * 0.55F) * 0.16F;
        }
    }
}
