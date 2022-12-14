package dev.mayaqq.ygasi.gui;

import dev.mayaqq.ygasi.registry.ConfigRegistry;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.SignGui;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;

import static dev.mayaqq.ygasi.Ygasi.click;

public class ConfigGui {

    public static void gui(ServerPlayerEntity player) {
        SimpleGui gui = new SimpleGui(ScreenHandlerType.GENERIC_9X6, player, false) {
            @Override
            public void onClose() {
                ConfigRegistry.save();
                super.onClose();
            }
        };

        gui.setTitle(Text.translatable("config.ygasi.title"));

        gui.setSlot(0, new GuiElementBuilder()
                .setItem(Items.ENDER_EYE)
                .setName(Text.translatable("config.ygasi.pointsRewarded.title"))
                .addLoreLine(Text.translatable("config.ygasi.current").append(Text.of(String.valueOf(ConfigRegistry.CONFIG.pointsRewarded))))
                .setCallback((index, clickType, actionType) -> {
                    textInput(player, "pointsRewarded");
                })
        );

        gui.setSlot(1, new GuiElementBuilder()
                .setItem(Items.OAK_SAPLING)
                .setName(Text.translatable("config.ygasi.branchCost.title"))
                .addLoreLine(Text.translatable("config.ygasi.current").append(Text.of(String.valueOf(ConfigRegistry.CONFIG.branchCost))))
                .setCallback((index, clickType, actionType) -> {
                    textInput(player, "branchCost");
                })
        );

        gui.setSlot(2, new GuiElementBuilder()
                .setItem(Items.BOOK)
                .setName(Text.translatable("config.ygasi.enableSkillBook.title"))
                .addLoreLine(Text.translatable("config.ygasi.current").append(Text.of(String.valueOf(ConfigRegistry.CONFIG.enableSkillBook))))
                .setCallback((index, clickType, actionType) -> {
                    ConfigRegistry.CONFIG.enableSkillBook = !ConfigRegistry.CONFIG.enableSkillBook;
                    player.playSound(SoundEvent.of(click), SoundCategory.PLAYERS, 1.0F, 1.0F);
                    gui.close();
                    gui(player);
                })
        );

        gui.setSlot(3, new GuiElementBuilder()
                .setItem(Items.STICK)
                .setName(Text.translatable("config.ygasi.T1Cost.title"))
                .addLoreLine(Text.translatable("config.ygasi.current").append(Text.of(String.valueOf(ConfigRegistry.CONFIG.T1Cost))))
                .setCallback((index, clickType, actionType) -> {
                    textInput(player, "T1Cost");
                })
        );

        gui.setSlot(4, new GuiElementBuilder()
                .setItem(Items.STICK)
                .setCount(2)
                .setName(Text.translatable("config.ygasi.T2Cost.title"))
                .addLoreLine(Text.translatable("config.ygasi.current").append(Text.of(String.valueOf(ConfigRegistry.CONFIG.T2Cost))))
                .setCallback((index, clickType, actionType) -> {
                    textInput(player, "T2Cost");
                })
        );

        gui.setSlot(5, new GuiElementBuilder()
                .setItem(Items.STICK)
                .setCount(3)
                .setName(Text.translatable("config.ygasi.T3Cost.title"))
                .addLoreLine(Text.translatable("config.ygasi.current").append(Text.of(String.valueOf(ConfigRegistry.CONFIG.T3Cost))))
                .setCallback((index, clickType, actionType) -> {
                    textInput(player, "T3Cost");
                })
        );

        gui.open();
    }

    private static void textInput(ServerPlayerEntity player, String option) {
        player.playSound(SoundEvent.of(click), SoundCategory.PLAYERS, 1.0F, 1.0F);
        try {
            SignGui gui = new SignGui(player) {
                @Override
                public void onClose() {
                    switch (option) {
                        case "pointsRewarded" -> {
                            try {
                                ConfigRegistry.CONFIG.pointsRewarded = Integer.parseInt(this.getLine(0).getString());
                            } catch (NumberFormatException e) {
                                player.sendMessage(Text.translatable("config.ygasi.invalid.number"), true);
                            }
                        }
                        case "branchCost" -> {
                            try {
                                ConfigRegistry.CONFIG.branchCost = Integer.parseInt(this.getLine(0).getString());
                            } catch (NumberFormatException e) {
                                player.sendMessage(Text.translatable("config.ygasi.invalid.number"), true);
                            }
                        }
                        case "T1Cost" -> {
                            try {
                                ConfigRegistry.CONFIG.T1Cost = Integer.parseInt(this.getLine(0).getString());
                            } catch (NumberFormatException e) {
                                player.sendMessage(Text.translatable("config.ygasi.invalid.number"), true);
                            }
                        }
                        case "T2Cost" -> {
                            try {
                                ConfigRegistry.CONFIG.T2Cost = Integer.parseInt(this.getLine(0).getString());
                            } catch (NumberFormatException e) {
                                player.sendMessage(Text.translatable("config.ygasi.invalid.number"), true);
                            }
                        }
                        case "T3Cost" -> {
                            try {
                                ConfigRegistry.CONFIG.T3Cost = Integer.parseInt(this.getLine(0).getString());
                            } catch (NumberFormatException e) {
                                player.sendMessage(Text.translatable("config.ygasi.invalid.number"), true);
                            }
                        }
                    }
                    gui(player);
                }
            };
            gui.open();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}