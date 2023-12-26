package net.jujulioed.dukasutilitiesmod.screen;

import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class DukasUtilitiesButton extends AbstractButton {
    public DukasUtilitiesButton(int pX, int pY, int pWidth, int pHeight, Component pMessage) {
        super(pX, pY, pWidth, pHeight, pMessage);

    }

    @Override
    public void onPress() {
        this.active = false;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }

    public void setButtonActive(boolean activated) {
        this.active = activated;
    }
}
