package net.minecraft.client.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class ModelCrazycraftGeo extends ModelBase
{
    private final String modelName;
    private final Map<String, ModelRenderer> bones = new LinkedHashMap<String, ModelRenderer>();
    private final Map<String, float[]> defaultRotations = new LinkedHashMap<String, float[]>();
    private final Map<String, float[]> bonePivots = new LinkedHashMap<String, float[]>();
    private final List<ModelRenderer> roots = new ArrayList<ModelRenderer>();
    private boolean loaded;

    public ModelCrazycraftGeo(String modelName)
    {
        this.modelName = modelName;
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                       float netHeadYaw, float headPitch, float scale)
    {
        if (!this.loaded && !this.loadModel()) return;
        float walk = MathHelper.sin(limbSwing * 0.7F) * limbSwingAmount;
        boolean attacking = entity instanceof EntityLiving && ((EntityLiving)entity).getAttackTarget() != null;
        for (Map.Entry<String, ModelRenderer> entry : this.bones.entrySet())
        {
            String name = entry.getKey().toLowerCase();
            ModelRenderer bone = entry.getValue();
            float[] base = this.defaultRotations.get(entry.getKey());
            bone.rotateAngleX = base[0];
            bone.rotateAngleY = base[1];
            bone.rotateAngleZ = base[2];
            if (name.contains("thigh") || name.contains("leg"))
                bone.rotateAngleX += (name.startsWith("left") ? walk : -walk);
            if (name.contains("tail")) bone.rotateAngleY += MathHelper.sin(ageInTicks * 0.12F) * 0.08F;
            if ((name.contains("head") || name.contains("neck")) && attacking)
                bone.rotateAngleX -= MathHelper.sin(ageInTicks * 0.45F) * 0.06F;
            if (name.contains("jaw") && attacking)
                bone.rotateAngleX += Math.abs(MathHelper.sin(ageInTicks * 0.45F)) * 0.18F;
        }
        for (ModelRenderer root : this.roots) root.render(scale);
    }

    private boolean loadModel()
    {
        this.loaded = true;
        ResourceLocation location = new ResourceLocation("minecraft", "geo/crazycraft/jurassicrevived/" + this.modelName + ".geo.json");
        try (IResource resource = Minecraft.getMinecraft().getResourceManager().getResource(location);
             Reader reader = new InputStreamReader(resource.getInputStream(), "UTF-8"))
        {
            JsonObject geometry = new JsonParser().parse(reader).getAsJsonObject()
                .getAsJsonArray("minecraft:geometry").get(0).getAsJsonObject();
            JsonObject description = geometry.getAsJsonObject("description");
            this.textureWidth = description.get("texture_width").getAsInt();
            this.textureHeight = description.get("texture_height").getAsInt();
            JsonArray boneData = geometry.getAsJsonArray("bones");
            for (JsonElement element : boneData)
            {
                JsonObject data = element.getAsJsonObject();
                JsonArray pivot = data.getAsJsonArray("pivot");
                ModelRenderer bone = new ModelRenderer(this, 0, 0);
                if (data.has("rotation"))
                {
                    JsonArray rotation = data.getAsJsonArray("rotation");
                    bone.rotateAngleX = (float)Math.toRadians(-rotation.get(0).getAsDouble());
                    bone.rotateAngleY = (float)Math.toRadians(rotation.get(1).getAsDouble());
                    bone.rotateAngleZ = (float)Math.toRadians(-rotation.get(2).getAsDouble());
                }
                String name = data.get("name").getAsString();
                this.bones.put(name, bone);
                this.bonePivots.put(name, new float[] {pivot.get(0).getAsFloat(), pivot.get(1).getAsFloat(), pivot.get(2).getAsFloat()});
                this.defaultRotations.put(name, new float[] {bone.rotateAngleX, bone.rotateAngleY, bone.rotateAngleZ});
                if (!data.has("parent")) this.roots.add(bone);
            }
            for (JsonElement element : boneData)
            {
                JsonObject data = element.getAsJsonObject();
                String name = data.get("name").getAsString();
                ModelRenderer bone = this.bones.get(name);
                float[] pivot = this.bonePivots.get(name);
                if (data.has("parent"))
                {
                    String parentName = data.get("parent").getAsString();
                    float[] parentPivot = this.bonePivots.get(parentName);
                    bone.setRotationPoint(pivot[0] - parentPivot[0], parentPivot[1] - pivot[1], pivot[2] - parentPivot[2]);
                    this.bones.get(parentName).addChild(bone);
                }
                else
                {
                    bone.setRotationPoint(pivot[0], 24.0F - pivot[1], pivot[2]);
                }
                if (data.has("cubes")) addCubes(data.getAsJsonArray("cubes"), data.getAsJsonArray("pivot"), bone);
            }
            return true;
        }
        catch (Exception exception)
        {
            System.err.println("Unable to load Jurassic Revived model " + this.modelName + ": " + exception.getMessage());
            return false;
        }
    }

    private void addCubes(JsonArray cubes, JsonArray pivot, ModelRenderer bone)
    {
        for (JsonElement element : cubes)
        {
            JsonObject cube = element.getAsJsonObject();
            JsonArray origin = cube.getAsJsonArray("origin");
            JsonArray size = cube.getAsJsonArray("size");
            JsonArray uv = cube.getAsJsonArray("uv");
            bone.setTextureOffset(uv.get(0).getAsInt(), uv.get(1).getAsInt());
            bone.mirror = cube.has("mirror") && cube.get("mirror").getAsBoolean();
            float inflate = cube.has("inflate") ? cube.get("inflate").getAsFloat() : 0.0F;
            bone.addBox(origin.get(0).getAsFloat() - pivot.get(0).getAsFloat(),
                pivot.get(1).getAsFloat() - origin.get(1).getAsFloat() - size.get(1).getAsFloat(),
                origin.get(2).getAsFloat() - pivot.get(2).getAsFloat(),
                Math.max(1, Math.round(size.get(0).getAsFloat())),
                Math.max(1, Math.round(size.get(1).getAsFloat())),
                Math.max(1, Math.round(size.get(2).getAsFloat())), inflate);
        }
    }
}
