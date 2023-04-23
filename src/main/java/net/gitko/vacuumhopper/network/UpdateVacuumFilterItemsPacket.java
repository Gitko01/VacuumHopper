package net.gitko.vacuumhopper.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.gitko.vacuumhopper.VacuumHopper;
import net.gitko.vacuumhopper.item.ModItems;
import net.gitko.vacuumhopper.item.custom.VacuumFilterItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.Hashtable;

public class UpdateVacuumFilterItemsPacket {
    private static final Identifier UPDATE_VACUUM_FILTER_ITEMS_PACKET_ID = new Identifier(VacuumHopper.MOD_ID, "update_vacuum_filter_items_packet");

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(UPDATE_VACUUM_FILTER_ITEMS_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            Hashtable<Integer, ItemStack> itemsToFilter = VacuumFilterItem.readItemsToFilterBuf(buf);

            server.execute(() -> {
                if (player.getStackInHand(player.getActiveHand()).getItem() == ModItems.VACUUM_FILTER) {
                    VacuumFilterItem.saveItemsToFilterToNbt(player.getStackInHand(player.getActiveHand()), itemsToFilter);
                }
            });
        });
    }
}
