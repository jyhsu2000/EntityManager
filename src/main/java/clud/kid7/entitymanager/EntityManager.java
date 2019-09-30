package clud.kid7.entitymanager;

import org.bukkit.plugin.java.JavaPlugin;

public final class EntityManager extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new EntityListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
