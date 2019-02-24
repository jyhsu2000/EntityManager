package clud.kid7.entitymanager;

import org.bukkit.Chunk;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityUtil {
    public static boolean checkChunkLimit(Entity entity) {
        Chunk chunk = entity.getChunk();
        //無法取得者不處理
        if (chunk == null) {
            return true;
        }
        //只處理動物
        if (!(entity instanceof Animals)) {
            return true;
        }
        //該區塊所有實體
        Entity[] entities = chunk.getEntities();
        //該區塊所有動物
        List<Entity> livingEntities = Arrays.stream(entities).filter(entity1 -> entity1 instanceof Animals)
            .collect(Collectors.toList());
        //計算生物數量
        long entityCount = livingEntities.size();
        if (entityCount > 24) {
            return false;
        }
        //計算同種類生物數量
        long sameTypeEntityCount = livingEntities.stream().filter(entity1 -> entity1.getType() == entity.getType()).count();
        return sameTypeEntityCount <= 16;
    }
}
