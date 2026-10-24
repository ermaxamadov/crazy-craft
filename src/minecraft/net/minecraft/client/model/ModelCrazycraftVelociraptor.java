package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/** Lean biped with narrow snout, gripping arms and a long balancing tail. */
public class ModelCrazycraftVelociraptor extends ModelBase
{
    private final ModelRenderer[] parts;
    private final ModelRenderer[] legs;
    private final ModelRenderer tail;
    private final ModelRenderer head;
    private final ModelRenderer jaw;
    private final ModelRenderer leftArm;
    private final ModelRenderer rightArm;

    public ModelCrazycraftVelociraptor()
    {
        this.textureWidth = 128;
        this.textureHeight = 64;
        ModelRenderer body = box(0, 0, -5, -4, -9, 10, 8, 18, 0, 10, 1);
        ModelRenderer neck = box(0, 28, -3, -4, -7, 6, 8, 8, 0, 8, -8);
        head = box(30, 20, -4, -4, -9, 8, 7, 11, 0, 5, -15);
        ModelRenderer snout = box(64, 0, -3, -2, -13, 6, 4, 13, 0, 5, -19);
        jaw = box(64, 0, -2, 0, -14, 4, 2, 12, 0, 8, -18);
        ModelRenderer leftEye = box(104, 0, -1, -2, -2, 3, 3, 3, -4, 2, -21);
        ModelRenderer rightEye = box(104, 0, -2, -2, -2, 3, 3, 3, 4, 2, -21);
        ModelRenderer[] teeth = new ModelRenderer[4];
        for (int index = 0; index < teeth.length; ++index)
            teeth[index] = box(104, 0, -1, 0, -1, 2, 3, 2, index % 2 == 0 ? -2 : 2, 7, -17 - index * 2);
        tail = box(0, 42, -3, -3, 0, 6, 6, 24, 0, 9, 9);
        leftArm = box(60, 24, -2, 0, -2, 4, 9, 4, -5, 9, -8);
        rightArm = box(60, 24, -2, 0, -2, 4, 9, 4, 5, 9, -8);
        legs = new ModelRenderer[] {
            box(84, 0, -3, 0, -3, 6, 14, 6, -4, 11, 5),
            box(84, 0, -3, 0, -3, 6, 14, 6, 4, 11, 5)
        };
        parts = new ModelRenderer[] {body, neck, head, snout, jaw, leftEye, rightEye,
            teeth[0], teeth[1], teeth[2], teeth[3], tail, leftArm, rightArm, legs[0], legs[1]};
    }

    private ModelRenderer box(int u, int v, int x, int y, int z, int w, int h, int d,
                              int px, int py, int pz)
    {
        ModelRenderer part = new ModelRenderer(this, u, v);
        part.addBox(x, y, z, w, h, d);
        part.setRotationPoint(px, py, pz);
        return part;
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                       float netHeadYaw, float headPitch, float scale)
    {
        float stride = MathHelper.cos(limbSwing * 0.85F) * 1.1F * limbSwingAmount;
        legs[0].rotateAngleX = stride;
        legs[1].rotateAngleX = -stride;
        tail.rotateAngleY = MathHelper.sin(ageInTicks * 0.18F) * 0.2F;
        boolean attacking = entity instanceof net.minecraft.entity.EntityLiving
            && ((net.minecraft.entity.EntityLiving)entity).getAttackTarget() != null;
        head.rotateAngleY = netHeadYaw * 0.017453292F;
        head.rotateAngleX = attacking ? MathHelper.sin(ageInTicks * 0.58F) * 0.24F : 0.0F;
        jaw.rotateAngleX = attacking ? Math.abs(MathHelper.sin(ageInTicks * 0.58F)) * 0.55F : 0.0F;
        leftArm.rotateAngleX = attacking ? -0.65F : 0.0F;
        rightArm.rotateAngleX = attacking ? -0.65F : 0.0F;
        for (ModelRenderer part : parts) part.render(scale);
    }
}
