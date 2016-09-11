package io.github.phantamanta44.discord4j.core.module;

import io.github.phantamanta44.discord4j.core.Discord;
import io.github.phantamanta44.discord4j.util.reflection.Reflect;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class ModuleManager {

    private Map<String, ModMeta> loadedMods = new ConcurrentHashMap<>();

    public ModuleManager() {
        initModules();
    }

    private void initModules() {
        Discord.logger().info("Attempting to load modules...");
        Reflect.methods().tagged(Module.class).find().forEach(this::loadModule);
    }

    private void loadModule(Method method) {
        try {
            ModMeta meta = new ModMeta(method.getAnnotation(Module.class));
            method.invoke(null, meta.config);
            loadedMods.put(meta.info.id(), meta);
        } catch (Exception e) {
            Discord.logger().warn("Failed to load module {}!", method.getName());
            e.printStackTrace();
        }
    }

    public ModuleConfig configFor(String modId) {
        ModMeta meta = loadedMods.get(modId);
        return meta == null ? null : meta.config;
    }

    public void saveAll() {
        loadedMods.forEach((id, mod) -> mod.config.writeConfig());
    }

    public Stream<ModMeta> modules() {
        return loadedMods.values().stream();
    }

    public static class ModMeta {

        public final Module info;
        public final ModuleConfig config;

        ModMeta(Module info) {
            this.info = info;
            this.config = new ModuleConfig(info);
        }

    }

}
