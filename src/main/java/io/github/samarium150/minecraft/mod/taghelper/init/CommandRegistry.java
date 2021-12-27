package io.github.samarium150.minecraft.mod.taghelper.init;

import com.mojang.brigadier.CommandDispatcher;
import io.github.samarium150.minecraft.mod.taghelper.command.Get;
import io.github.samarium150.minecraft.mod.taghelper.command.Remove;
import io.github.samarium150.minecraft.mod.taghelper.command.Set;
import net.minecraft.command.CommandSource;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber
public final class CommandRegistry {
    
    private CommandRegistry() { }
    
    @SubscribeEvent
    public static void register(@Nonnull final RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
        Get.register(dispatcher);
        Set.register(dispatcher);
        Remove.register(dispatcher);
    }
}
