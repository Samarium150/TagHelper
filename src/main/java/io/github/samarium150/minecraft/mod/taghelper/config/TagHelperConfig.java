package io.github.samarium150.minecraft.mod.taghelper.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class TagHelperConfig {
    
    public static final ForgeConfigSpec COMMON_CONFIG;
    public static final ForgeConfigSpec.BooleanValue enableGetCommand;
    public static final ForgeConfigSpec.BooleanValue enableSetCommand;
    public static final ForgeConfigSpec.BooleanValue enableRemoveCommand;
    
    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("configs").push("General");
        enableGetCommand = builder.define("enableGetCommand", true);
        enableSetCommand = builder.define("enableSetCommand", true);
        enableRemoveCommand = builder.define("enableRemoveCommand", true);
        COMMON_CONFIG = builder.build();
        builder.pop();
    }
}
