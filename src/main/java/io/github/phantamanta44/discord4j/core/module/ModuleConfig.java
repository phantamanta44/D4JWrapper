package io.github.phantamanta44.discord4j.core.module;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.phantamanta44.discord4j.core.Discord;
import io.github.phantamanta44.discord4j.data.wrapper.Guild;
import io.github.phantamanta44.discord4j.util.function.Lambdas;

import java.io.*;
import java.util.function.Consumer;

public class ModuleConfig {

    private final Module modInfo;

    private JsonObject config;
    private File dataRoot, configFile;
    private Consumer<Guild> onEnable = Lambdas.noopUnary(), onDisable = Lambdas.noopUnary();

    public ModuleConfig(Module modInfo) {
        this.modInfo = modInfo;
        dataRoot = new File("mod_data", modInfo.id());
        configFile = new File(dataRoot, "config.json");
        readConfig();
    }

    public Module info() {
        return modInfo;
    }

    public JsonObject globalConfig() {
        if (!config.has("globalCfg"))
            config.add("globalCfg", new JsonObject());
        return config.get("globalCfg").getAsJsonObject();
    }

    public JsonObject configFor(Guild guild) {
        String keyName = "guildCfg" + guild.id();
        if (!config.has(keyName))
            config.add(keyName, new JsonObject());
        return config.get(keyName).getAsJsonObject();
    }

    public File globalDataDir() {
        if (!dataRoot.exists())
            dataRoot.mkdirs();
        return dataRoot;
    }

    public File dataDirFor(Guild guild) {
        File dataDir = new File(dataRoot, "g_" + guild.id());
        if (!dataDir.exists())
            dataDir.mkdirs();
        return dataDir;
    }

    public boolean enabled(Guild guild) {
        JsonObject enableds = enableds();
        if (!enableds.has(guild.id()))
            enableds.addProperty(guild.id(), modInfo.enabledDefault());
        return enableds.get(guild.id()).getAsBoolean();
    }

    public void setEnabled(Guild guild, boolean state) {
        JsonObject enableds = enableds();
        if (enableds.has(guild.id())) {
            if (!state && enableds.get(guild.id()).getAsBoolean()) {
                onEnable.accept(guild);
                enableds.addProperty(guild.id(), false);
            } else if (state && !enableds.get(guild.id()).getAsBoolean()) {
                onDisable.accept(guild);
                enableds.addProperty(guild.id(), true);
            }
        } else {
            if (state)
                onEnable.accept(guild);
            else
                onDisable.accept(guild);
            enableds.addProperty(guild.id(), state);
        }
    }

    public void onEnable(Consumer<Guild> handler) {
        onEnable = onEnable.andThen(handler);
    }

    public void onDisable(Consumer<Guild> handler) {
        onDisable = onDisable.andThen(handler);
    }

    private JsonObject enableds() {
        if (!config.has("modStatus"))
            config.add("modStatus", new JsonObject());
        return config.get("modStatus").getAsJsonObject();
    }

    public void readConfig() {
        if (configFile.exists()) {
            try (BufferedReader in = new BufferedReader(new FileReader(configFile))) {
                config = new JsonParser().parse(in).getAsJsonObject();
            } catch (IOException | ClassCastException e) {
                Discord.logger().warn("Failed to read module config!");
                e.printStackTrace();
            }
        }
        else
            config = new JsonObject();
    }

    public void writeConfig() {
        if (!dataRoot.exists())
            dataRoot.mkdirs();
        try (PrintWriter out = new PrintWriter(new FileWriter(configFile))) {
            out.write(new Gson().toJson(config));
        } catch (IOException e) {
            Discord.logger().warn("Failed to write module config!");
            e.printStackTrace();
        }
    }

}
