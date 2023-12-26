package net.jujulioed.dukasutilitiesmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jujulioed.dukasutilitiesmod.DukasUtilitiesMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class UncraftMachineScreen extends AbstractContainerScreen<UncraftMachineMenu> {
        private static final ResourceLocation TEXTURE =
                new ResourceLocation(DukasUtilitiesMod.MOD_ID, "textures/gui/uncraft_machine_gui.png");
        private static final Component BUTTON_TEXT = Component.translatable("gui." + DukasUtilitiesMod.MOD_ID + ".uncraft_machine.uncraft_button");

        private DukasUtilitiesButton button;

        public UncraftMachineScreen(UncraftMachineMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
                super(pMenu, pPlayerInventory, pTitle);
                }

        @Override
        protected void init() {
                super.init();
                this.inventoryLabelY = 10000;
                this.titleLabelY = 10000;

                this.button = new DukasUtilitiesButton(this.leftPos + 57, this.topPos + 8, 60, 15, BUTTON_TEXT);
                this.addRenderableWidget(this.button);
        }

        @Override
        protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, TEXTURE);
                int x = (width - imageWidth) / 2;
                int y = (height - imageHeight) / 2;

                guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

                renderProgressArrow(guiGraphics, x, y);
                }

        private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
                if(menu.isCrafting()) {
                guiGraphics.blit(TEXTURE, x + 74, y + 38, 176, 0, menu.getScaledProgress(), 14);
                }
                }

        @Override
        public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
                renderBackground(guiGraphics);
                super.render(guiGraphics, mouseX, mouseY, delta);
                renderTooltip(guiGraphics, mouseX, mouseY);

                }

}
