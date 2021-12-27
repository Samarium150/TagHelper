package io.github.samarium150.minecraft.mod.taghelper.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github.samarium150.minecraft.mod.taghelper.config.TagHelperConfig;
import io.github.samarium150.minecraft.mod.taghelper.util.CommandUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;

import javax.annotation.Nonnull;

public final class Get {
    
    private Get() { }
    
    private static int executes(@Nonnull CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        if (TagHelperConfig.enableGetCommand.get()) {
            ItemStack item = CommandUtil.getMainHandItem(source);
            if (item == null) return Command.SINGLE_SUCCESS;
            CompoundTag targetNBT = item.getTag();
            MutableComponent text = new TextComponent("NBT: ");
            if (targetNBT == null)
                text.append("null");
            else
                text.append(targetNBT.toString());
            source.sendSuccess(text, false);
        } else
            source.sendFailure(new TextComponent("get command is disabled in config"));
        return Command.SINGLE_SUCCESS;
    }
    
    public static void register(@Nonnull CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> literal = CommandUtil.literal
                .then(Commands.literal("get").executes(Get::executes));
        LiteralCommandNode<CommandSourceStack> cmd = dispatcher.register(literal);
        dispatcher.register(CommandUtil.alias.redirect(cmd));
    }
}
