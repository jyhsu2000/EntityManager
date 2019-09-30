package clud.kid7.entitymanager.runnable;

import clud.kid7.entitymanager.EntityManager;
import clud.kid7.entitymanager.util.ChunkUtil;
import clud.kid7.entitymanager.util.EntityUtil;
import clud.kid7.entitymanager.util.ParticleUtil;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class TaskRunner implements Runnable {
    private final ConcurrentLinkedQueue<Chunk> chunkQueue = new ConcurrentLinkedQueue<>();
    private Instant nextRunInstant = Instant.now();
    private Duration runInterval = Duration.ofSeconds(30);

    @Override
    public void run() {
        if (nextRunInstant.isAfter(Instant.now())) {
            // 若還沒到執行時間，直接結束
            return;
        }
        if (chunkQueue.isEmpty()) {
            // 若佇列是空的，將線上玩家附近的 Chunk 存入佇列
            Bukkit.getOnlinePlayers().forEach(player -> {
                // 找出玩家附近的 Chunk
                List<Chunk> nearByChunks = ChunkUtil.getNearByChunks(player.getChunk(), 4);
                // 過濾出指定物種總數量超過單一物種限制者
                nearByChunks = nearByChunks.stream().filter(chunk -> {
                    List<Entity> entityInChunk = EntityUtil.filterEntities(chunk.getEntities());
                    return entityInChunk.size() > EntityUtil.SingleTypeEntityAmountLimitation;
                }).collect(Collectors.toList());
                chunkQueue.addAll(nearByChunks);
            });
//            EntityManager.instance.getLogger().info(MessageFormat.format("Found {0} chunk(s) may have excess entities", chunkQueue.size()));
            // 若佇列已空，暫停一段時間
            if (chunkQueue.isEmpty()) {
                nextRunInstant = Instant.now().plus(runInterval);
            }
            return;
        }
        // 取出一個 Chunk
        Chunk chunk = chunkQueue.poll();
        // 從佇列中移除所有相同 Chunk，避免浪費運算資源
        chunkQueue.removeAll(Collections.singleton(chunk));
        // 僅處理有載入的 Chunk
        if (chunk.isLoaded()) {
            List<Entity> entities = EntityUtil.filterEntities(chunk.getEntities());
//        EntityManager.instance.getLogger().info(MessageFormat.format("Chunk {0} has {1} entities", chunk, entities.size()));
            // 再次檢查，超過單一物種限制，才進行細節判斷
            if (entities.size() > EntityUtil.SingleTypeEntityAmountLimitation) {
                // 處理單一物種超量
                for (Entity entity : entities) {
                    if (entities.stream()
                        .filter(entity1 -> entity1.getType().equals(entity.getType())).count() > EntityUtil.SingleTypeEntityAmountLimitation) {
                        // 移除一隻
                        ParticleUtil.playEffect(entity.getLocation(), Particle.CLOUD);
                        entity.remove();
                        // 將 Chunk 加回佇列，以確保盡快處理
                        chunkQueue.add(chunk);
                        // 結束本次處理
                        return;
                    }
                }

                // 處理總數超量
                if (entities.size() > EntityUtil.EntityAmountLimitation) {
                    // 移除一隻
                    Entity entity = entities.get(0);
                    ParticleUtil.playEffect(entity.getLocation(), Particle.CLOUD);
                    entity.remove();
                    // 將 Chunk 加回佇列，以確保盡快處理
                    chunkQueue.add(chunk);
                    // 結束本次處理
                    return;
                }
            }
            // 若佇列已空，暫停一段時間
            if (chunkQueue.isEmpty()) {
//                EntityManager.instance.getLogger().info(MessageFormat.format("Entity clear done. Next round will started in {0}", runInterval));
                nextRunInstant = Instant.now().plus(runInterval);
            }
        }
    }
}
