package net.gitko.vacuumhopper.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.gitko.vacuumhopper.VacuumHopper;
import net.gitko.vacuumhopper.block.custom.VacuumHopperBlock;
import net.gitko.vacuumhopper.block.custom.VacuumHopperBlockEntity;
import net.gitko.vacuumhopper.item.ModItemGroup;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModBlocks {
    // Blocks
    public static Block VACUUM_HOPPER = null;

    // Block entities
    public static BlockEntityType<VacuumHopperBlockEntity> VACUUM_HOPPER_BLOCK_ENTITY = null;

    // Registry stuff
    private static Block registerBlock(String name, Block block, ItemGroup group, String tooltipKey, Integer tooltipLineAmount, Boolean holdDownShift) {
        registerBlockItem(name, block, group, tooltipKey, tooltipLineAmount, holdDownShift);
        return Registry.register(Registry.BLOCK, new Identifier(VacuumHopper.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group, String tooltipKey, Integer tooltipLineAmount, Boolean holdDownShift) {
        BlockItem item = new BlockItem(block, new FabricItemSettings().group(group)) {
            @Override
            public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
                if (holdDownShift) {
                    if (Screen.hasShiftDown()) {
                        int currentLine = 1;

                        while (tooltipLineAmount >= currentLine) {
                            tooltip.add(Text.translatable(tooltipKey + "_" + currentLine));
                            currentLine += 1;
                        }
                    } else {
                        tooltip.add(Text.translatable("tooltip." + VacuumHopper.MOD_ID + ".hold_shift"));
                    }
                } else {
                    int currentLine = 1;

                    while (tooltipLineAmount >= currentLine) {
                        tooltip.add(Text.translatable(tooltipKey + "_" + currentLine));
                        currentLine += 1;
                    }
                }
            }
        };
        return Registry.register(Registry.ITEM, new Identifier(VacuumHopper.MOD_ID, name),
               item);
    }

    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(VacuumHopper.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        BlockItem item = new BlockItem(block, new FabricItemSettings().group(group));

        return Registry.register(Registry.ITEM, new Identifier(VacuumHopper.MOD_ID, name),
                item);
    }

    public static void register() {
        VacuumHopper.LOGGER.info("[Vacuum Hopper] Registering mod blocks.");

        VACUUM_HOPPER = registerBlock("vacuum_hopper",
                new VacuumHopperBlock(FabricBlockSettings.of(
                        Material.METAL)
                        .strength(5f, 6f)
                        .requiresTool()
                ), ModItemGroup.TAB, "tooltip." + VacuumHopper.MOD_ID + ".vacuum_hopper", 4, true);

        VACUUM_HOPPER_BLOCK_ENTITY = Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                new Identifier(VacuumHopper.MOD_ID, "vacuum_hopper_block_entity"),
                FabricBlockEntityTypeBuilder.create(VacuumHopperBlockEntity::new, VACUUM_HOPPER).build()
        );
    }
}
