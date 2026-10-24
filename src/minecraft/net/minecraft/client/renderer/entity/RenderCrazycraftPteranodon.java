package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelCrazycraftPteranodon;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.crazycraft.old.EntityPteranodon;
import net.minecraft.util.ResourceLocation;

public class RenderCrazycraftPteranodon extends RenderLiving<EntityPteranodon>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/crazycraft/pteranodon.png");

    public RenderCrazycraftPteranodon(RenderManager manager)
    {
        super(manager, new ModelCrazycraftPteranodon(), 1.0F);
    }

    @Override
    protected void preRenderCallback(EntityPteranodon entity, float partialTick)
    {
        GlStateManager.scale(2.5F, 2.5F, 2.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityPteranodon entity)
    {
        return TEXTURE;
    }
}
