package io.github.samarium150.minecraft.mod.taghelper.util;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class CommandUtil {
    
    private CommandUtil() { }
    
    public static final LiteralArgumentBuilder<CommandSource> literal = Commands.literal(GeneralUtil.MOD_ID)
            .requires(commandSource -> commandSource.hasPermissionLevel(2));
    public static final LiteralArgumentBuilder<CommandSource> alias = Commands.literal("th");
    
    @Nullable
    public static ItemStack getMainHandItem(@Nonnull CommandSource source) throws CommandSyntaxException {
        ServerPlayerEntity player = source.asPlayer();
        ItemStack item = player.getHeldItem(player.getActiveHand());
        if (item.isEmpty()) {
            source.sendErrorMessage(new StringTextComponent("no item in the main hand"));
            return null;
        }
        return item;
    }
}
