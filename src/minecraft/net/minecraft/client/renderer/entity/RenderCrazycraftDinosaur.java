package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelCrazycraftGeo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.ResourceLocation;

public class RenderCrazycraftDinosaur extends RenderLiving<EntityAnimal>
{
    private final ResourceLocation texture;
    private final int species;

    public RenderCrazycraftDinosaur(RenderManager manager, boolean predator)
    {
        this(manager, predator ? 0 : 1);
    }

    public RenderCrazycraftDinosaur(RenderManager manager, int species)
    {
        super(manager, model(species), species == 0 ? 2.3F : species == 3 ? 0.7F : 1.6F);
        this.species = species;
        String modelName = geoName(species);
        this.texture = new ResourceLocation("textures/entity/crazycraft/jurassicrevived/" + modelName + ".png");
    }

    private static String geoName(int species)
    {
        return species == 0 ? "tyrannosaurus_rex" : species == 1 ? "triceratops"
            : species == 2 ? "stegosaurus" : "velociraptor";
    }

    private static ModelCrazycraftGeo model(int species)
    {
        return new ModelCrazycraftGeo(geoName(species));
    }

    @Override
    protected void preRenderCallback(EntityAnimal entity, float partialTick)
    {
        float ageScale = entity.isChild() ? 0.5F : 1.0F;
        float size = this.species == 0 ? 2.3F : this.species == 2 ? 1.6F : this.species == 3 ? 0.7F : 1.6F;
        GlStateManager.scale(size * ageScale, size * ageScale, size * ageScale);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityAnimal entity)
    {
        return this.texture;
    }
}
