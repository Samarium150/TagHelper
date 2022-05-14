package io.github.samarium150.minecraft.mod.taghelper.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github.samarium150.minecraft.mod.taghelper.config.TagHelperConfig;
import io.github.samarium150.minecraft.mod.taghelper.util.CommandUtil;
import io.github.samarium150.minecraft.mod.taghelper.util.GeneralUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;

public final class Get {
    
    private Get() { }
    
    private static int executes(@Nonnull CommandContext<CommandSource> context) throws CommandSyntaxException {
        CommandSource source = context.getSource();
        if (TagHelperConfig.enableGetCommand.get()) {
            ItemStack item = CommandUtil.getMainHandItem(source);
            if (item == null) return Command.SINGLE_SUCCESS;
            CompoundNBT targetNBT = item.getTag();
            IFormattableTextComponent text = new StringTextComponent("NBT: ");
            if (targetNBT == null)
                text.append("null");
            else
                text.append(GeneralUtil.getPrettyDisplay(targetNBT.toString()));
            source.sendSuccess(text, false);
        } else
            source.sendFailure(new StringTextComponent("get command is disabled in config"));
        return Command.SINGLE_SUCCESS;
    }
    
    public static void register(@Nonnull CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> literal = CommandUtil.literal
                .then(Commands.literal("get").executes(Get::executes));
        LiteralCommandNode<CommandSource> cmd = dispatcher.register(literal);
        dispatcher.register(CommandUtil.alias.redirect(cmd));
    }
}
