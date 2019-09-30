package clud.kid7.entitymanager;

import clud.kid7.entitymanager.util.EntityUtil;
import clud.kid7.entitymanager.util.ParticleUtil;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EntityListener implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        //生成原因
        CreatureSpawnEvent.SpawnReason spawnReason = event.getSpawnReason();
        //只處理丟雞蛋與繁殖
        if (spawnReason != CreatureSpawnEvent.SpawnReason.EGG
            && spawnReason != CreatureSpawnEvent.SpawnReason.SPAWNER_EGG
            && spawnReason != CreatureSpawnEvent.SpawnReason.DISPENSE_EGG
            && spawnReason != CreatureSpawnEvent.SpawnReason.BREEDING) {
            return;
        }
        LivingEntity entity = event.getEntity();
        if (!EntityUtil.checkChunkLimit(entity)) {
            ParticleUtil.playEffect(entity.getLocation(), Particle.CLOUD);
            event.setCancelled(true);
        }
    }
}
