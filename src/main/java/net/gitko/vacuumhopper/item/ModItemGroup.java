package net.gitko.vacuumhopper.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.gitko.vacuumhopper.VacuumHopper;
import net.gitko.vacuumhopper.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup TAB = FabricItemGroup.builder(new Identifier(VacuumHopper.MOD_ID, "tab"))
            .icon(() -> new ItemStack(ModBlocks.VACUUM_HOPPER))
            .build();
}
