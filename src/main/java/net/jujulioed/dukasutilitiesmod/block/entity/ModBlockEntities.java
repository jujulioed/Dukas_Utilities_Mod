package net.jujulioed.dukasutilitiesmod.block.entity;

import net.jujulioed.dukasutilitiesmod.DukasUtilitiesMod;
import net.jujulioed.dukasutilitiesmod.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DukasUtilitiesMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<UncraftMachineBlockEntity>> UNCRAFT_MACHINE_BE =
            BLOCK_ENTITIES.register("uncraft_machine_be", () ->
                    BlockEntityType.Builder.of(UncraftMachineBlockEntity::new,
                            ModBlocks.UNCRAFT_MACHINE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
