package net.gitko.vacuumhopper;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.gitko.vacuumhopper.block.ModBlocks;
import net.gitko.vacuumhopper.gui.VacuumFilterScreenHandler;
import net.gitko.vacuumhopper.gui.VacuumHopperScreenHandler;
import net.gitko.vacuumhopper.item.ModItems;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VacuumHopper implements ModInitializer {
	public static final String MOD_ID = "vacuumhopper";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// Screens
	public static final ScreenHandlerType<VacuumHopperScreenHandler> VACUUM_HOPPER_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(VacuumHopperScreenHandler::new);
	public static final ScreenHandlerType<VacuumFilterScreenHandler> VACUUM_FILTER_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(VacuumFilterScreenHandler::new);

	@Override
	public void onInitialize() {
		ModBlocks.register();
		ModItems.register();
		Registry.register(Registries.SCREEN_HANDLER, new Identifier("vacuum_hopper_screen_handler"), VACUUM_HOPPER_SCREEN_HANDLER);
		Registry.register(Registries.SCREEN_HANDLER, new Identifier("vacuum_filter_screen_handler"), VACUUM_FILTER_SCREEN_HANDLER);

		LOGGER.info("[Vacuum Hopper] Mod initialized.");
	}
}
