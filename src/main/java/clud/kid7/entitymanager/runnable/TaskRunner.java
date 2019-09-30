package clud.kid7.entitymanager.runnable;

import clud.kid7.entitymanager.EntityManager;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TaskRunner implements Runnable {
    ConcurrentLinkedQueue<Chunk> chunkQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void run() {
        Bukkit.broadcastMessage(MessageFormat.format("chunkQueue: {0}", chunkQueue.size()));
        if (chunkQueue.isEmpty()) {
            // 若佇列是空的，將所有已載入的 Chunk 存入佇列
            Bukkit.getWorlds().forEach(world -> {
                chunkQueue.addAll(Arrays.asList(world.getLoadedChunks()));
            });
            EntityManager.instance.getLogger().info(MessageFormat.format("Add {0} chunks to chunkQueue", chunkQueue.size()));
            return;
        }
        // TODO
    }
}
