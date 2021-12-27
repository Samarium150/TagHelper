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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;

public final class Remove {
    
    private Remove() { }
    
    private static int executes(@Nonnull CommandContext<CommandSource> context, String tag)
            throws CommandSyntaxException {
        CommandSource source = context.getSource();
        if (TagHelperConfig.enableRemoveCommand.get()) {
            ItemStack item = CommandUtil.getMainHandItem(source);
            if (item == null) return Command.SINGLE_SUCCESS;
            CompoundNBT targetNBT = item.getTag();
            if (targetNBT == null) return Command.SINGLE_SUCCESS;
            targetNBT.remove(tag);
            item.setTag(targetNBT);
            IFormattableTextComponent text = new StringTextComponent("current NBT: ")
                    .append(targetNBT.getPrettyDisplay());
            source.sendSuccess(text, false);
        } else
            source.sendFailure(new StringTextComponent("remove command is disabled in config"));
        return Command.SINGLE_SUCCESS;
    }
    
    private static int executes(@Nonnull CommandContext<CommandSource> context) throws CommandSyntaxException {
        CommandSource source = context.getSource();
        if (TagHelperConfig.enableRemoveCommand.get()) {
            ItemStack item = CommandUtil.getMainHandItem(source);
            if (item == null) return Command.SINGLE_SUCCESS;
            item.setTag(null);
            source.sendSuccess(new StringTextComponent("NBT is removed"), false);
        } else
            source.sendFailure(new StringTextComponent("remove command is disabled in config"));
        return Command.SINGLE_SUCCESS;
    }
    
    public static void register(@Nonnull CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> literal = CommandUtil.literal
                .then(Commands.literal("remove")
                        .then(Commands.argument("key", StringArgumentType.string())
                                .executes(context -> executes(context,
                                        StringArgumentType.getString(context, "key")))
                        )
                        .executes(Remove::executes)
                );
        LiteralCommandNode<CommandSource> cmd = dispatcher.register(literal);
        dispatcher.register(CommandUtil.alias.redirect(cmd));
    }
}
