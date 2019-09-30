package clud.kid7.entitymanager;

import clud.kid7.entitymanager.runnable.TaskRunner;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class EntityManager extends JavaPlugin {
    public static EntityManager instance;
    private BukkitTask taskRunnerTask;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new EntityListener(), this);

        // 重複任務
        taskRunnerTask = Bukkit.getScheduler().runTaskTimer(this, new TaskRunner(), 0L, 20L);

        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        // 結束所有任務
        Bukkit.getScheduler().cancelTask(taskRunnerTask.getTaskId());

        instance = null;
    }
}
