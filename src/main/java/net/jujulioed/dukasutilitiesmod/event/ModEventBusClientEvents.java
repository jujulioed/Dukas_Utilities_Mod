package net.jujulioed.dukasutilitiesmod.event;

import net.jujulioed.dukasutilitiesmod.DukasUtilitiesMod;
import net.jujulioed.dukasutilitiesmod.block.entity.ModBlockEntities;
import net.jujulioed.dukasutilitiesmod.block.entity.renderer.UncraftMachineBlockEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DukasUtilitiesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.UNCRAFT_MACHINE_BE.get(), UncraftMachineBlockEntityRenderer::new);
    }
}
