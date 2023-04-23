package net.gitko.vacuumhopper.gui;

import net.gitko.vacuumhopper.VacuumHopper;
import net.gitko.vacuumhopper.block.custom.VacuumHopperBlockEntity;
import net.gitko.vacuumhopper.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class VacuumHopperScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private BlockPos pos;
    private int redstoneMode;
    private int pushMode;
    private int range;

    //This constructor gets called on the client when the server wants it to open the screenHandler,
    //The client will call the other constructor with an empty Inventory and the screenHandler will automatically
    //sync this empty inventory with the inventory on the server.
    public VacuumHopperScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf packetByteBuf) {
        this(syncId, playerInventory, new SimpleInventory(11));
        this.pos = packetByteBuf.readBlockPos();
        this.redstoneMode = packetByteBuf.readInt();
        this.pushMode = packetByteBuf.readInt();
        this.range = packetByteBuf.readInt();
    }

    //This constructor gets called from the BlockEntity on the server without calling the other constructor first, the server knows the inventory of the container
    //and can therefore directly provide it as an argument. This inventory will then be synced to the client.
    public VacuumHopperScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(VacuumHopper.VACUUM_HOPPER_SCREEN_HANDLER, syncId);

        // placeholder for server
        pos = BlockPos.ORIGIN;
        range = 0;
        redstoneMode = 0;
        pushMode = 0;

        checkSize(inventory, VacuumHopperBlockEntity.INV_SIZE);
        this.inventory = inventory;
        //some inventories do custom logic when a player opens it.
        inventory.onOpen(playerInventory.player);

        //This will place the slot in the correct locations for a 3x3 Grid. The slots exist on both server and client!
        //This will not render the background of the slots however, this is the Screens job
        int m;
        int l;
        //Our inventory
        for (m = 0; m < 2; ++m) {
            for (l = 0; l < 5; ++l) {
                // for "textures/gui/container/dispenser.png"
                //this.addSlot(new Slot(inventory, l + m * 3, 62 + l * 18, 17 + m * 18));

                this.addSlot(new Slot(inventory, l + m * 5, 62 + l * 18, 20 + m * 18));
            }
        }
        // Vacuum filter slot
        this.addSlot(new Slot(inventory, VacuumHopperBlockEntity.FILTER_SLOT_INDEX, 27, 20) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() == ModItems.VACUUM_FILTER;
            }
        });

        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + VacuumHopperBlockEntity.INV_SIZE, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public int getRedstoneMode() {
        return this.redstoneMode;
    }

    public int getPushMode() {
        return this.pushMode;
    }

    public int getRange() {
        return this.range;
    }
}
