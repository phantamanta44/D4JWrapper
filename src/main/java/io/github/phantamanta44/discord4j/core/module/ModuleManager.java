package io.github.phantamanta44.discord4j.core.module;

import io.github.phantamanta44.discord4j.core.Discord;
import io.github.phantamanta44.discord4j.util.reflection.Reflect;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
            loadedMods.put(meta.id, meta);
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

    private static class ModMeta {

        final String id;
        final String name;
        final String desc;
        final String author;
        final String[] deps;
        final ModuleConfig config;

        ModMeta(Module data) {
            this.id = data.id();
            this.name = data.name();
            this.desc = data.desc();
            this.author = data.author();
            this.deps = data.deps();
            this.config = new ModuleConfig(data);
        }

    }

}
