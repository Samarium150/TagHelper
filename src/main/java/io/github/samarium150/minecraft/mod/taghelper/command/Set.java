package io.github.samarium150.minecraft.mod.taghelper.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github.samarium150.minecraft.mod.taghelper.config.TagHelperConfig;
import io.github.samarium150.minecraft.mod.taghelper.util.CommandUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.NbtTagArgument;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;

import javax.annotation.Nonnull;

public final class Set {
    
    private Set() { }
    
    private static int executes(@Nonnull CommandContext<CommandSourceStack> context, String tag, Tag value)
            throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        if (TagHelperConfig.enableSetCommand.get()) {
            ItemStack item = CommandUtil.getMainHandItem(source);
            if (item == null) return Command.SINGLE_SUCCESS;
            CompoundTag targetNBT = item.getOrCreateTag();
            targetNBT.put(tag, value);
            item.setTag(targetNBT);
            MutableComponent text = new TextComponent("current NBT: ")
                    .append(targetNBT.toString());
            source.sendSuccess(text, false);
        } else
            source.sendFailure(new TextComponent("set command is disabled in config"));
        return Command.SINGLE_SUCCESS;
    }
    
    private static int executes(@Nonnull CommandContext<CommandSourceStack> context, CompoundTag targetNBT)
            throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        if (TagHelperConfig.enableSetCommand.get()) {
            ItemStack item = CommandUtil.getMainHandItem(source);
            if (item == null) return Command.SINGLE_SUCCESS;
            item.setTag(targetNBT);
            MutableComponent text = new TextComponent("current NBT: ")
                    .append(targetNBT.toString());
            source.sendSuccess(text, false);
        } else
            source.sendFailure(new TextComponent("set command is disabled in config"));
        return Command.SINGLE_SUCCESS;
    }
    
    public static void register(@Nonnull CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> literal = CommandUtil.literal
                .then(Commands.literal("set")
                        .then(Commands.argument("key", StringArgumentType.string())
                                .then(Commands.argument("value", NbtTagArgument.nbtTag())
                                        .executes(context -> executes(context,
                                                StringArgumentType.getString(context, "key"),
                                                NbtTagArgument.getNbtTag(context, "value"))
                                        )
                                )
                        )
                        .then(Commands.argument("NBT", CompoundTagArgument.compoundTag())
                                .executes(context -> executes(context,
                                        CompoundTagArgument.getCompoundTag(context, "NBT"))
                                )
                        )
                );
        LiteralCommandNode<CommandSourceStack> cmd = dispatcher.register(literal);
        dispatcher.register(CommandUtil.alias.redirect(cmd));
    }
}
