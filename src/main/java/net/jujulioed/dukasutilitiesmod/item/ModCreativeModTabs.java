package net.jujulioed.dukasutilitiesmod.item;

import net.jujulioed.dukasutilitiesmod.DukasUtilitiesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DukasUtilitiesMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("dukas_utilities_mod",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CREATIVE_ITEM_FACADE.get()))
                    .title(Component.translatable("creativetab.dukas_utilities_mod"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(ModItems.UNCRAFT_MACHINE_FEET.get());
                    }).build());

}
