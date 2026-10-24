package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

/** A dark menu button with a prehistoric amber or future cyan accent. */
public class GuiCrazycraftButton extends GuiButton
{
    private final int accent;

    public GuiCrazycraftButton(int id, int x, int y, String text, int accent)
    {
        this(id, x, y, 200, 20, text, accent);
    }

    public GuiCrazycraftButton(int id, int x, int y, int width, int height, String text, int accent)
    {
        super(id, x, y, width, height, text);
        this.accent = accent;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        if (!this.visible)
        {
            return;
        }

        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        int border = this.enabled && this.hovered ? 0xFFFFFFFF : this.accent;
        int fill = this.enabled && this.hovered ? 0xE6343B48 : 0xD819202A;
        drawRect(this.x, this.y, this.x + this.width, this.y + this.height, border);
        drawRect(this.x + 1, this.y + 1, this.x + this.width - 1, this.y + this.height - 1, fill);
        drawRect(this.x + 1, this.y + 1, this.x + 4, this.y + this.height - 1, this.accent);
        this.mouseDragged(mc, mouseX, mouseY);
        this.drawCenteredString(mc.fontRenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, !this.enabled ? 0x8D929A : this.hovered ? 0xFFFFFF : 0xE2E8EE);
    }
}
