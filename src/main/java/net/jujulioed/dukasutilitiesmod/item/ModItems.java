package net.jujulioed.dukasutilitiesmod.item;

import net.jujulioed.dukasutilitiesmod.DukasUtilitiesMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DukasUtilitiesMod.MOD_ID);

    public static final RegistryObject<Item> CREATIVE_ITEM_FACADE  = ITEMS.register("creative_item_facade",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> UNCRAFT_MACHINE_FEET  = ITEMS.register("uncraft_machine_feet",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRON_REVERSAL_UNIT  = ITEMS.register("iron_reversal_unit",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLD_REVERSAL_UNIT  = ITEMS.register("gold_reversal_unit",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_REVERSAL_UNIT  = ITEMS.register("diamond_reversal_unit",
            () -> new Item(new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
