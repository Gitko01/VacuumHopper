package net.gitko.vacuumhopper.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.gitko.vacuumhopper.VacuumHopper;
import net.gitko.vacuumhopper.block.ModBlocks;
import net.gitko.vacuumhopper.gui.VacuumFilterScreen;
import net.gitko.vacuumhopper.gui.VacuumHopperScreen;
import net.gitko.vacuumhopper.network.*;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class VacuumHopperClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(VacuumHopper.VACUUM_HOPPER_SCREEN_HANDLER, VacuumHopperScreen::new);
        HandledScreens.register(VacuumHopper.VACUUM_FILTER_SCREEN_HANDLER, VacuumFilterScreen::new);

        BlockEntityRendererRegistry.register(ModBlocks.VACUUM_HOPPER_BLOCK_ENTITY, VacuumHopperBlockEntityRenderer::new);

        UpdateVacuumHopperRedstoneModePacket.register();
        UpdateVacuumHopperPushModePacket.register();
        UpdateVacuumHopperReachPacket.register();
        UpdateVacuumHopperIOPacket.register();
        UpdateVacuumFilterItemsPacket.register();
        UpdateVacuumFilterModePacket.register();

        VacuumHopper.LOGGER.info("[Vacuum Hopper] Client mod initialized.");
    }
}
