package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.crazycraft.EntityCrazycraftWorkerBot;
import net.minecraft.util.ResourceLocation;

public class RenderCrazycraftWorkerBot extends RenderLiving<EntityCrazycraftWorkerBot>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/crazycraft/worker_bot.png");

    public RenderCrazycraftWorkerBot(RenderManager manager)
    {
        super(manager, new ModelBiped(0.0F), 0.7F);
    }

    @Override
    protected void preRenderCallback(EntityCrazycraftWorkerBot entity, float partialTick)
    {
        GlStateManager.scale(1.25F, 1.25F, 1.25F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCrazycraftWorkerBot entity)
    {
        return TEXTURE;
    }
}
