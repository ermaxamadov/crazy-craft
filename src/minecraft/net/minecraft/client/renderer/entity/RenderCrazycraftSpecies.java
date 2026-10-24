package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelCrazycraftGeo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.crazycraft.old.EntityCrazycraftSpecies;
import net.minecraft.crazycraft.old.EntityCrazycraftSpecies.Species;
import net.minecraft.util.ResourceLocation;

public class RenderCrazycraftSpecies extends RenderLiving<EntityCrazycraftSpecies>
{
    private final Species species;
    private final ResourceLocation texture;

    public RenderCrazycraftSpecies(RenderManager manager, Species species)
    {
        super(manager, new ModelCrazycraftGeo(geoName(species)), species == Species.BRACHIOSAURUS ? 3.5F : 1.8F);
        this.species = species;
        this.texture = new ResourceLocation("textures/entity/crazycraft/jurassicrevived/" + geoName(species) + ".png");
    }

    private static String geoName(Species species)
    {
        if (species == Species.MAPUSAURUS) return "carcharodontosaurus";
        if (species == Species.IGUANODON) return "ouranosaurus";
        return species.id;
    }

    @Override
    protected void preRenderCallback(EntityCrazycraftSpecies entity, float partialTick)
    {
        float size = species == Species.COMPSOGNATHUS ? 0.65F
            : species == Species.GALLIMIMUS ? 1.45F
            : species == Species.OVIRAPTOR ? 0.95F
            : species == Species.BRACHIOSAURUS ? 4.5F
            : species == Species.DILOPHOSAURUS ? 1.4F
            : species == Species.SPINOSAURUS ? 3.0F
            : species == Species.GIGANOTOSAURUS ? 2.7F
            : species == Species.MAPUSAURUS ? 2.5F
            : species.predator ? 2.35F : 2.1F;
        if (entity.isChild()) size *= 0.5F;
        GlStateManager.scale(size, size, size);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCrazycraftSpecies entity) { return this.texture; }
}
