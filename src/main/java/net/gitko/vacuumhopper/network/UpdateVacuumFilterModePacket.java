package net.gitko.vacuumhopper.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.gitko.vacuumhopper.VacuumHopper;
import net.gitko.vacuumhopper.item.ModItems;
import net.gitko.vacuumhopper.item.custom.VacuumFilterItem;
import net.minecraft.util.Identifier;

public class UpdateVacuumFilterModePacket {
    private static final Identifier UPDATE_VACUUM_FILTER_MODE_PACKET_ID = new Identifier(VacuumHopper.MOD_ID, "update_vacuum_filter_mode_packet");

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(UPDATE_VACUUM_FILTER_MODE_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            int mode = buf.readInt();

            server.execute(() -> {
                if (player.getStackInHand(player.getActiveHand()).getItem() == ModItems.VACUUM_FILTER) {
                    VacuumFilterItem.saveModeNbtData(player.getStackInHand(player.getActiveHand()), mode);
                }
            });
        });
    }
}
