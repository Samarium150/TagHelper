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
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.NBTCompoundTagArgument;
import net.minecraft.command.arguments.NBTTagArgument;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;

public final class Set {
    
    private Set() { }
    
    private static int executes(@Nonnull CommandContext<CommandSource> context, String tag, INBT value)
            throws CommandSyntaxException {
        CommandSource source = context.getSource();
        if (TagHelperConfig.enableSetCommand.get()) {
            ItemStack item = CommandUtil.getMainHandItem(source);
            if (item == null) return Command.SINGLE_SUCCESS;
            CompoundNBT targetNBT = item.getOrCreateTag();
            targetNBT.put(tag, value);
            item.setTag(targetNBT);
            ITextComponent text = new StringTextComponent("current NBT: ")
                    .appendSibling(targetNBT.toFormattedComponent());
            source.sendFeedback(text, false);
        } else
            source.sendErrorMessage(new StringTextComponent("set command is disabled in config"));
        return Command.SINGLE_SUCCESS;
    }
    
    private static int executes(@Nonnull CommandContext<CommandSource> context, CompoundNBT targetNBT)
            throws CommandSyntaxException {
        CommandSource source = context.getSource();
        if (TagHelperConfig.enableSetCommand.get()) {
            ItemStack item = CommandUtil.getMainHandItem(source);
            if (item == null) return Command.SINGLE_SUCCESS;
            item.setTag(targetNBT);
            ITextComponent text = new StringTextComponent("current NBT: ")
                    .appendSibling(targetNBT.toFormattedComponent());
            source.sendFeedback(text, false);
        } else
            source.sendErrorMessage(new StringTextComponent("set command is disabled in config"));
        return Command.SINGLE_SUCCESS;
    }
    
    public static void register(@Nonnull CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> literal = CommandUtil.literal
                .then(Commands.literal("set")
                        .then(Commands.argument("key", StringArgumentType.string())
                                .then(Commands.argument("value", NBTTagArgument.func_218085_a())
                                        .executes(context -> executes(context,
                                                StringArgumentType.getString(context, "key"),
                                                NBTTagArgument.func_218086_a(context, "value"))
                                        )
                                )
                        )
                        .then(Commands.argument("NBT", NBTCompoundTagArgument.nbt())
                                .executes(context -> executes(context,
                                        NBTCompoundTagArgument.getNbt(context, "NBT"))
                                )
                        )
                );
        LiteralCommandNode<CommandSource> cmd = dispatcher.register(literal);
        dispatcher.register(CommandUtil.alias.redirect(cmd));
    }
}
