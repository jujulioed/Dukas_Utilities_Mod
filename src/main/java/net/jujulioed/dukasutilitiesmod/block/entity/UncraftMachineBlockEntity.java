package net.jujulioed.dukasutilitiesmod.block.entity;

import net.jujulioed.dukasutilitiesmod.item.ModItems;
import net.jujulioed.dukasutilitiesmod.screen.UncraftMachineMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UncraftMachineBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private static final int TOOL_INPUT_SLOT = 0;
    private static final int UNIT_INPUT_SLOT = 1;
    private static final int ITEM_SLOT_OUTPUT_0 = 2;
    private static final int ITEM_SLOT_OUTPUT_1 = 3;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public UncraftMachineBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.UNCRAFT_MACHINE_BE.get(), pPos, pBlockState);
        //this.itemHandler.setSize(4);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> UncraftMachineBlockEntity.this.progress;
                    case 1 -> UncraftMachineBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> UncraftMachineBlockEntity.this.progress = pValue;
                    case 1 -> UncraftMachineBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.tutorialmod.uncraft_machine_menu");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new UncraftMachineMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("uncraft_machine_menu.progress", progress);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("uncraft_machine.progress");
    }


    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (hasRecipe()) {
            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);

            if (hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress() {
        progress = 0;

    }

    private void craftItem() {
        ItemStack result_slot_0 = new ItemStack(Items.IRON_INGOT, 3);
        ItemStack result_slot_1 = new ItemStack(Items.STICK, 2);
        this.itemHandler.extractItem(TOOL_INPUT_SLOT, 1, false);
        this.itemHandler.extractItem(UNIT_INPUT_SLOT, 1, false);

        this.itemHandler.setStackInSlot(ITEM_SLOT_OUTPUT_0, new ItemStack(result_slot_0.getItem(),
                this.itemHandler.getStackInSlot(ITEM_SLOT_OUTPUT_0).getCount() + result_slot_0.getCount()));
        this.itemHandler.setStackInSlot(ITEM_SLOT_OUTPUT_1, new ItemStack(result_slot_1.getItem(),
                this.itemHandler.getStackInSlot(ITEM_SLOT_OUTPUT_1).getCount() + result_slot_1.getCount()));

    }

    private boolean hasRecipe() {
        boolean hasCraftingItem = this.itemHandler.getStackInSlot(TOOL_INPUT_SLOT).getItem() == Items.IRON_PICKAXE;
        boolean hasUnitItem = this.itemHandler.getStackInSlot(UNIT_INPUT_SLOT).getItem() == ModItems.IRON_REVERSAL_UNIT.get();
        ItemStack result_slot_0 = new ItemStack(Items.IRON_INGOT);
        ItemStack result_slot_1 = new ItemStack(Items.STICK);

        return hasCraftingItem && canInsertAmountIntoOutputSlot(result_slot_0.getCount(), ITEM_SLOT_OUTPUT_0) && canInsertItemIntoOutputSlot(result_slot_0.getItem(), ITEM_SLOT_OUTPUT_0)
                && hasUnitItem && canInsertAmountIntoOutputSlot(result_slot_1.getCount(), ITEM_SLOT_OUTPUT_1) && canInsertItemIntoOutputSlot(result_slot_1.getItem(), ITEM_SLOT_OUTPUT_1);
    }

    private boolean canInsertItemIntoOutputSlot(Item item, int slot) {
        return this.itemHandler.getStackInSlot(slot).isEmpty() || this.itemHandler.getStackInSlot(slot).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count, int slot) {
        return this.itemHandler.getStackInSlot(slot).getCount() + count <= this.itemHandler.getStackInSlot(slot).getMaxStackSize();
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

}
