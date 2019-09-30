package clud.kid7.entitymanager.util;

import org.bukkit.Chunk;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityUtil {
    public static final int EntityAmountLimitation = 16;
    public static final int SingleTypeEntityAmountLimitation = 12;

    public static boolean checkChunkLimit(Entity entity) {
        Chunk chunk = entity.getChunk();
        //該區塊所有實體
        Entity[] entities = chunk.getEntities();
        //該區塊所有非怪物生物
        List<Entity> filteredEntities = filterEntities(entities);
        //計算生物數量
        long entityCount = filteredEntities.size();
        if (entityCount > EntityAmountLimitation) {
            return false;
        }
        //計算同種類生物數量
        long sameTypeEntityCount = filteredEntities.stream().filter(entity1 -> entity1.getType() == entity.getType()).count();
        return sameTypeEntityCount <= SingleTypeEntityAmountLimitation;
    }

    public static List<Entity> filterEntities(Entity[] entities) {
        return Arrays.stream(entities)
            .filter(entity1 -> entity1 instanceof Creature)
            .filter(entity1 -> !(entity1 instanceof Monster))
            .collect(Collectors.toList());
    }
}
