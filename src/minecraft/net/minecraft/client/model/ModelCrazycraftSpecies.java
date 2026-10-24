package net.minecraft.client.model;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.crazycraft.old.EntityCrazycraftSpecies.Species;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/** Separate silhouettes for the additional land species; textures remain replaceable assets. */
public class ModelCrazycraftSpecies extends ModelBase
{
    private final List<ModelRenderer> parts = new ArrayList<ModelRenderer>();
    private final ModelRenderer[] legs;
    private final ModelRenderer tail;
    private final ModelRenderer head;
    private final ModelRenderer jaw;
    private final boolean fourLegged;
    private final boolean brachiosaurus;

    public ModelCrazycraftSpecies(Species species)
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.brachiosaurus = species == Species.BRACHIOSAURUS;
        this.fourLegged = species == Species.BRACHIOSAURUS || species == Species.ANKYLOSAURUS
            || species == Species.PARASAUROLOPHUS || species == Species.IGUANODON;
        boolean smallBird = species == Species.COMPSOGNATHUS || species == Species.GALLIMIMUS || species == Species.OVIRAPTOR;
        if (species == Species.BRACHIOSAURUS)
        {
            add(0, 0, -12, -8, -17, 24, 16, 34, 0, 1, 0);
            add(0, 51, -5, -40, -5, 10, 43, 10, 0, 3, -15);
            head = add(42, 53, -5, -5, -12, 10, 8, 15, 0, -36, -16);
            jaw = add(42, 76, -4, -1, -10, 8, 3, 12, 0, -33, -17);
            for (int x : new int[] {-5, 5})
                add(104, 0, -1, -2, -2, 3, 3, 3, x, -38, -28);
            tail = add(0, 28, -5, -4, 0, 10, 8, 26, 0, 3, 17);
            legs = fourLegs(10, 26, 10, 9, 10, 12);
        }
        else if (this.fourLegged)
        {
            int bodyWidth = species == Species.ANKYLOSAURUS ? 23 : 19;
            add(0, 0, -bodyWidth/2, -7, -14, bodyWidth, 14, 29, 0, 8, 0);
            head = add(0, 45, -5, -5, -10, 10, 10, 14, 0, 8, -14);
            jaw = add(48, 45, -4, 1, -12, 8, 4, 12, 0, 12, -16);
            add(48, 45, -4, -3, -15, 8, 5, 8, 0, 7, -18);
            for (int x : new int[] {-5, 5})
                add(104, 0, -1, -2, -2, 3, 3, 3, x, 6, -23);
            add(48, 45, -5, -4, -10, 10, 8, 13, 0, 8, -23);
            tail = add(0, 24, -4, -3, 0, 8, 6, 22, 0, 9, 16);
            legs = fourLegs(8, 16, 8, 8, 10, 10);
            if (species == Species.ANKYLOSAURUS)
            {
                add(72, 0, -11, -10, -13, 22, 5, 27, 0, 8, 0);
                for (int z = -11; z <= 11; z += 5)
                    for (int x : new int[] {-9, 9})
                        add(72, 34, -2, -7, -2, 4, 8, 4, x, 4, z);
                add(64, 51, -6, -6, 18, 12, 12, 10, 0, 9, 18);
            }
            if (species == Species.PARASAUROLOPHUS)
                add(64, 62, -3, -18, 0, 6, 18, 8, 0, 3, -27);
            if (species == Species.IGUANODON)
                for (int x : new int[] {-8, 8})
                    add(72, 34, -2, -2, -3, 4, 5, 6, x, 9, -15);
        }
        else
        {
            int length = species == Species.SPINOSAURUS ? 30
                : species == Species.GIGANOTOSAURUS ? 32
                : species == Species.MAPUSAURUS ? 28
                : species == Species.DILOPHOSAURUS ? 17
                : smallBird ? 15 : 24;
            int bodyWidth = smallBird ? 10 : species == Species.GIGANOTOSAURUS ? 19 : 16;
            int bodyHeight = smallBird ? 9 : 12;
            add(0, 0, -bodyWidth/2, -bodyHeight/2, -length/2, bodyWidth, bodyHeight, length, 0, 7, 0);
            add(0, 43, -5, -5, -9, 10, 10, 12, 0, 7, -length/2);
            head = add(50, 0, -6, -5, -12, 12, 10, 14, 0, 6, -length/2-7);
            jaw = add(50, 24, -5, -1, -14, 10, 3, 13, 0, 9, -length/2-7);
            add(72, 30, -4, -3, -19, 8, 5, 9, 0, 6, -length/2-8);
            for (int x : new int[] {-6, 6})
                add(104, 0, -1, -2, -2, 3, 3, 3, x, 3, -length/2-15);
            tail = add(0, 24, -4, -3, 0, 8, 6, 27, 0, 8, length/2);
            legs = new ModelRenderer[] {
                add(90, 28, -4, 0, -4, 8, 18, 8, -7, 10, 7),
                add(90, 28, -4, 0, -4, 8, 18, 8, 7, 10, 7)
            };
            for (int x : new int[] {-7, 7})
                add(95, 53, -2, 0, -2, 4, 10, 4, x, 9, -length/2);
            if (species == Species.SPINOSAURUS)
                for (int z = -12; z <= 12; z += 4)
                {
                    int height = 9 + (13 - Math.abs(z))/2;
                    add(70, 69, -2, -height, -2, 4, height, 4, 0, 2, z);
                }
            if (species == Species.CARNOTAURUS)
                for (int x : new int[] {-4, 4})
                    add(70, 51, -2, -9, -2, 4, 10, 4, x, 2, -length/2-16);
            if (species == Species.DILOPHOSAURUS)
                for (int x : new int[] {-5, 5})
                    add(72, 0, -2, -8, -3, 4, 10, 6, x, 3, -length/2-12);
            if (species == Species.ALLOSAURUS)
                for (int x : new int[] {-5, 5})
                    add(72, 30, -2, -5, -2, 4, 6, 4, x, 3, -length/2-11);
            if (smallBird)
            {
                add(72, 30, -4, -4, -6, 8, 7, 9, 0, 4, -length/2-5);
                for (int x : new int[] {-5, 5})
                    add(95, 53, -1, -2, -2, 2, 7, 3, x, 9, -length/2+1);
                if (species == Species.OVIRAPTOR)
                    add(72, 0, -2, -8, -2, 4, 5, 4, 0, 1, -length/2-7);
            }
        }
    }

    private ModelRenderer[] fourLegs(int width, int height, int depth, int x, int y, int z)
    {
        return new ModelRenderer[] {
            add(88, 74, -width/2, 0, -depth/2, width, height, depth, -x, y, -z),
            add(88, 74, -width/2, 0, -depth/2, width, height, depth, x, y, -z),
            add(88, 74, -width/2, 0, -depth/2, width, height, depth, -x, y, z),
            add(88, 74, -width/2, 0, -depth/2, width, height, depth, x, y, z)
        };
    }

    private ModelRenderer add(int u, int v, int x, int y, int z, int w, int h, int d, int px, int py, int pz)
    {
        ModelRenderer part = new ModelRenderer(this, u, v);
        part.addBox(x, y, z, w, h, d);
        part.setRotationPoint(px, py, pz);
        parts.add(part);
        return part;
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                       float netHeadYaw, float headPitch, float scale)
    {
        boolean attacking = entity instanceof net.minecraft.entity.EntityLiving
            && ((net.minecraft.entity.EntityLiving)entity).getAttackTarget() != null;
        float stride = MathHelper.cos(limbSwing * 0.65F) * 0.8F * limbSwingAmount;
        legs[0].rotateAngleX = stride;
        legs[1].rotateAngleX = -stride;
        if (fourLegged)
        {
            legs[2].rotateAngleX = -stride;
            legs[3].rotateAngleX = stride;
        }
        int stompTicks = entity instanceof net.minecraft.crazycraft.old.EntityCrazycraftSpecies
            ? ((net.minecraft.crazycraft.old.EntityCrazycraftSpecies)entity).getStompTicks() : 0;
        if (stompTicks > 0 && speciesIsBrachiosaurus())
        {
            float lift = stompTicks > 4 ? Math.min(0.8F, (stompTicks - 4) * 0.08F) : 0.0F;
            legs[0].rotateAngleX -= lift;
            legs[3].rotateAngleX -= lift;
            legs[1].rotateAngleX += lift * 0.35F;
            legs[2].rotateAngleX += lift * 0.35F;
        }
        head.rotateAngleY = netHeadYaw * 0.017453292F;
        head.rotateAngleX = headPitch * 0.017453292F + (attacking ? MathHelper.sin(ageInTicks * 0.38F) * 0.12F : 0.0F);
        jaw.rotateAngleX = attacking ? Math.abs(MathHelper.sin(ageInTicks * 0.42F)) * 0.5F : 0.0F;
        tail.rotateAngleY = MathHelper.sin(ageInTicks * 0.10F) * 0.12F;
        for (ModelRenderer part : parts) part.render(scale);
    }

    private boolean speciesIsBrachiosaurus()
    {
        return this.brachiosaurus;
    }
}
