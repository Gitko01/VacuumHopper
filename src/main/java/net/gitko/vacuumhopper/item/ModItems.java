package net.gitko.vacuumhopper.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.gitko.vacuumhopper.VacuumHopper;
import net.gitko.vacuumhopper.item.custom.VacuumFilterItem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModItems {
    // Items
    public static Item VACUUM_SCREWDRIVER = null;
    public static VacuumFilterItem VACUUM_FILTER = null;

    // Registry stuff
    private static Item registerBasicItem(String name, ItemGroup group, int maxCount, String tooltipKey, Integer tooltipLineAmount, Boolean holdDownShift) {
        Item item = new Item(new FabricItemSettings().maxCount(maxCount)) {
            @Override
            public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
                if (holdDownShift) {
                    if (Screen.hasShiftDown()) {
                        Integer currentLine = 1;

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
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));

        return Registry.register(Registries.ITEM, new Identifier(VacuumHopper.MOD_ID, name),
               item);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(VacuumHopper.MOD_ID, name), item);
    }

    public static void register() {
        VacuumHopper.LOGGER.info("[Vacuum Hopper] Registering mod items.");

        VACUUM_SCREWDRIVER = registerBasicItem("vacuum_screwdriver", ModItemGroup.TAB, 1, "tooltip." + VacuumHopper.MOD_ID + ".vacuum_screwdriver", 3, true);

        VACUUM_FILTER = (VacuumFilterItem) registerItem("vacuum_filter",
                new VacuumFilterItem(new FabricItemSettings().maxCount(1)));
        ItemGroupEvents.modifyEntriesEvent(ModItemGroup.TAB).register(entries -> entries.add(VACUUM_FILTER));
    }
}
