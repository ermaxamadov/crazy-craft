package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/** Long, animated wings and pointed beak for the flying Old Dimension reptile. */
public class ModelCrazycraftPteranodon extends ModelBase
{
    private final ModelRenderer body;
    private final ModelRenderer head;
    private final ModelRenderer beak;
    private final ModelRenderer lowerBeak;
    private final ModelRenderer leftEye;
    private final ModelRenderer rightEye;
    private final ModelRenderer crest;
    private final ModelRenderer leftWing;
    private final ModelRenderer rightWing;
    private final ModelRenderer tail;

    public ModelCrazycraftPteranodon()
    {
        this.textureWidth = 128;
        this.textureHeight = 64;
        body = box(0, 0, -4, -2, -8, 8, 4, 17, 0, 0, 0);
        head = box(0, 24, -3, -2, -5, 6, 4, 7, 0, 0, -9);
        beak = box(27, 24, -1, 0, -16, 2, 2, 17, 0, 0, -12);
        lowerBeak = box(27, 24, -1, 1, -15, 2, 1, 15, 0, 1, -12);
        leftEye = box(104, 0, -1, -2, -2, 3, 3, 3, -3, -1, -12);
        rightEye = box(104, 0, -2, -2, -2, 3, 3, 3, 3, -1, -12);
        crest = box(66, 20, -1, -3, 0, 2, 2, 9, 0, -1, -12);
        leftWing = box(0, 39, 0, 0, -5, 34, 1, 11, 4, -1, -2);
        rightWing = box(0, 39, -34, 0, -5, 34, 1, 11, -4, -1, -2);
        tail = box(0, 19, -1, -1, 0, 2, 2, 13, 0, 0, 8);
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
        float flap = MathHelper.sin(ageInTicks * 0.35F) * 0.35F;
        boolean diving = entity instanceof net.minecraft.crazycraft.old.EntityPteranodon
            && ((net.minecraft.crazycraft.old.EntityPteranodon)entity).isDiving();
        leftWing.rotateAngleZ = flap;
        rightWing.rotateAngleZ = -flap;
        head.rotateAngleX = diving ? -0.22F : 0.0F;
        head.rotateAngleY = netHeadYaw * 0.017453292F;
        lowerBeak.rotateAngleX = diving ? 0.3F : 0.0F;
        body.render(scale);
        head.render(scale);
        beak.render(scale);
        lowerBeak.render(scale);
        leftEye.render(scale);
        rightEye.render(scale);
        crest.render(scale);
        leftWing.render(scale);
        rightWing.render(scale);
        tail.render(scale);
    }
}
