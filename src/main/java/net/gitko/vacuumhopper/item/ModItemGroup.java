package net.gitko.vacuumhopper.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.gitko.vacuumhopper.VacuumHopper;
import net.gitko.vacuumhopper.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup TAB = FabricItemGroupBuilder.build(new Identifier(VacuumHopper.MOD_ID, "tab"),
    () -> new ItemStack(ModBlocks.VACUUM_HOPPER));
}
