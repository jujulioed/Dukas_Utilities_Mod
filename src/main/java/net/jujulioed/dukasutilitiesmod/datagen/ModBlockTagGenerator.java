package net.jujulioed.dukasutilitiesmod.datagen;

import net.jujulioed.dukasutilitiesmod.DukasUtilitiesMod;
import net.jujulioed.dukasutilitiesmod.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, DukasUtilitiesMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.UNCRAFT_MACHINE_BODY.get(),
                        ModBlocks.UNCRAFT_MACHINE.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.UNCRAFT_MACHINE_BODY.get()).addTag(Tags.Blocks.STORAGE_BLOCKS_IRON)
                .add(ModBlocks.UNCRAFT_MACHINE.getKey()).addTag(Tags.Blocks.STORAGE_BLOCKS_IRON);
    }
}
