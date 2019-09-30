package clud.kid7.entitymanager.util;

import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class ChunkUtil {
    public static List<Chunk> getNearByChunks(Chunk chunk, int radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("Radius should be non-negative");
        }
        World world = chunk.getWorld();
        List<Chunk> chunks = new ArrayList<>();
        for (int deltaX = -radius; deltaX <= radius; deltaX++) {
            for (int deltaZ = -radius; deltaZ <= radius; deltaZ++) {
                int chunkX = chunk.getX() + deltaX;
                int chunkZ = chunk.getZ() + deltaZ;
                chunks.add(world.getChunkAt(chunkX, chunkZ));
            }
        }
        return chunks;
    }
}
