package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/** Low four-legged body, staggered dorsal plates and spiked tail. */
public class ModelCrazycraftStegosaurus extends ModelBase
{
    private final ModelRenderer[] parts;
    private final ModelRenderer[] legs;
    private final ModelRenderer tail;
    private final ModelRenderer head;
    private final ModelRenderer jaw;

    public ModelCrazycraftStegosaurus()
    {
        this.textureWidth = 128;
        this.textureHeight = 64;
        ModelRenderer body = box(0, 0, -11, -6, -16, 22, 12, 32, 0, 11, 0);
        ModelRenderer neck = box(0, 46, -5, -4, -8, 10, 8, 13, 0, 12, -15);
        head = box(50, 42, -4, -3, -10, 8, 6, 12, 0, 12, -25);
        ModelRenderer muzzle = box(50, 42, -3, -2, -15, 6, 4, 8, 0, 12, -26);
        jaw = box(50, 42, -3, 0, -14, 6, 2, 8, 0, 14, -25);
        ModelRenderer leftEye = box(104, 0, -1, -2, -2, 3, 3, 3, -4, 10, -29);
        ModelRenderer rightEye = box(104, 0, -2, -2, -2, 3, 3, 3, 4, 10, -29);
        tail = box(0, 18, -4, -3, 0, 8, 6, 24, 0, 11, 15);
        legs = new ModelRenderer[] {
            box(88, 32, -4, 0, -4, 8, 14, 8, -8, 12, -10),
            box(88, 32, -4, 0, -4, 8, 14, 8, 8, 12, -10),
            box(88, 32, -4, 0, -4, 8, 14, 8, -8, 12, 10),
            box(88, 32, -4, 0, -4, 8, 14, 8, 8, 12, 10)
        };
        parts = new ModelRenderer[2 + 5 + 1 + legs.length + 8 + 4];
        int i = 0;
        parts[i++] = body;
        parts[i++] = neck;
        parts[i++] = head;
        parts[i++] = muzzle;
        parts[i++] = jaw;
        parts[i++] = leftEye;
        parts[i++] = rightEye;
        parts[i++] = tail;
        for (ModelRenderer leg : legs) parts[i++] = leg;
        for (int p = 0; p < 8; ++p)
        {
            int z = -13 + p * 4;
            int height = p < 2 || p > 5 ? 5 : 8;
            parts[i++] = box(64, 0, -2, -height, -2, 4, height, 4,
                p % 2 == 0 ? -3 : 3, 5, z);
        }
        for (int side : new int[] {-1, 1})
            for (int z : new int[] {21, 27})
                parts[i++] = box(84, 0, side < 0 ? -9 : 0, -1, 0, 9, 2, 2,
                    side * 3, 10, z);
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
        float stride = MathHelper.cos(limbSwing * 0.6F) * 0.7F * limbSwingAmount;
        legs[0].rotateAngleX = stride;
        legs[1].rotateAngleX = -stride;
        legs[2].rotateAngleX = -stride;
        legs[3].rotateAngleX = stride;
        tail.rotateAngleY = MathHelper.sin(ageInTicks * 0.13F) * 0.14F;
        boolean attacking = entity instanceof net.minecraft.entity.EntityLiving
            && ((net.minecraft.entity.EntityLiving)entity).getAttackTarget() != null;
        head.rotateAngleY = netHeadYaw * 0.017453292F;
        head.rotateAngleX = attacking ? MathHelper.sin(ageInTicks * 0.42F) * 0.14F : 0.0F;
        jaw.rotateAngleX = attacking ? Math.abs(MathHelper.sin(ageInTicks * 0.5F)) * 0.18F : 0.0F;
        for (ModelRenderer part : parts) part.render(scale);
    }
}
