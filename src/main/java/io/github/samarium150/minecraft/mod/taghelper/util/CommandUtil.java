package io.github.samarium150.minecraft.mod.taghelper.util;

import com.google.common.base.Strings;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.TextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    
    public static Component getPrettyDisplay(CompoundTag nbt, String p_199850_1_, int p_199850_2_) {
        Set<String> keys = nbt.getAllKeys();
//        Set<Tag> values = keys.stream().map(nbt::get).collect(Collectors.toSet());
        Map<String, Tag> tags = keys.stream().collect(Collectors.toMap(Function.identity(), nbt::get));
        if (tags.isEmpty()) {
            return new TextComponent("{}");
        } else {
            MutableComponent c = new TextComponent("{");
            Collection<String> collection = tags.keySet();
        
            if (!p_199850_1_.isEmpty()) {
                c.append("\n");
            }
    
            MutableComponent c1 = new TextComponent("");
            for(Iterator<String> iterator = collection.iterator(); iterator.hasNext(); c.append(c1)) {
                String s = iterator.next();
//                c1 = (new TextComponent(Strings.repeat(p_199850_1_, p_199850_2_ + 1))).append(handleEscapePretty(s)).append(String.valueOf(':')).append(" ").append(getPrettyDisplay(tags.get(s), p_199850_1_, p_199850_2_ + 1));
                if (iterator.hasNext()) {
//                    c1.append(String.valueOf(',')).append(p_199850_1_.isEmpty() ? " " : "\n");
                }
            }
        
            if (!p_199850_1_.isEmpty()) {
                c.append("\n").append(Strings.repeat(p_199850_1_, p_199850_2_));
            }
        
            c.append("}");
            return c;
        }
    }
}
