package me.jjblock21.containersearch;

import com.google.common.collect.ImmutableMap;
import eu.midnightdust.lib.util.PlatformFunctions;
import joptsimple.internal.Strings;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CSMixinConfigPlugin implements IMixinConfigPlugin {
    private static final Map<String, String> REQUIRED_MODS;
    private static final String ROOT_PACKAGE = "me.jjblock21.containersearch";

    static {
        REQUIRED_MODS = ImmutableMap.<String, String>builder()
            .put(ROOT_PACKAGE + ".fabric.mixins.containers.ReinforcedStorageScreenMixin", "reinfcore")
            .put(ROOT_PACKAGE + ".fabric.mixins.containers.MythicChestScreenMixin",       "mythicmetals_decorations")
            .build();
    }

    @Override
    public boolean shouldApplyMixin(String targetName, String mixinName) {
        String modId = REQUIRED_MODS.getOrDefault(mixinName, null);
        if (!Strings.isNullOrEmpty(modId)) {
            return PlatformFunctions.isModLoaded(modId);
        }
        return true;
    }

    @Override
    public void onLoad(String mixinPackage) { }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> mine, Set<String> other) { }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) { }

    @Override
    public void postApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) { }
}
