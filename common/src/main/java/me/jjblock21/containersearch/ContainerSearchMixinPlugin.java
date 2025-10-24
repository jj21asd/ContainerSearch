package me.jjblock21.containersearch;

import com.google.common.collect.ImmutableMap;
import eu.midnightdust.lib.util.PlatformFunctions;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class ContainerSearchMixinPlugin implements IMixinConfigPlugin {
    private static final Map<String, Supplier<Boolean>> APPLY_CONDITIONS = ImmutableMap.of(
        "me.jjblock21.containersearch.fabric.mixins.containers.ReinforcedStorageScreenMixin",
        () -> PlatformFunctions.isModLoaded("reinfcore"),

        "me.jjblock21.containersearch.fabric.mixins.containers.MythicChestScreenMixin",
        () -> PlatformFunctions.isModLoaded("mythicmetals_decorations")
    );

    @Override
    public boolean shouldApplyMixin(String targetName, String mixinName) {
        return APPLY_CONDITIONS.getOrDefault(mixinName, () -> true).get();
    }

    // unused
    @Override public void onLoad(String mixinPackage) { }
    @Override public String getRefMapperConfig() { return null; }
    @Override public void acceptTargets(Set<String> mine, Set<String> other) { }
    @Override public List<String> getMixins() { return null; }
    @Override public void preApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) { }
    @Override public void postApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) { }
}
