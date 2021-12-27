package io.github.samarium150.minecraft.mod.taghelper.util;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.TextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class CommandUtil {
    
    private CommandUtil() { }
    
    public static final LiteralArgumentBuilder<CommandSourceStack> literal = Commands.literal(GeneralUtil.MOD_ID)
            .requires(commandSource -> commandSource.hasPermission(2));
    public static final LiteralArgumentBuilder<CommandSourceStack> alias = Commands.literal("th");
    
    @Nullable
    public static ItemStack getMainHandItem(@Nonnull CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        ItemStack item = player.getMainHandItem();
        if (item.isEmpty()) {
            source.sendFailure(new TextComponent("no item in the main hand"));
            return null;
        }
        return item;
    }
}
