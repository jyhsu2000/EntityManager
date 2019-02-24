package clud.kid7.entitymanager;

import org.bukkit.Location;
import org.bukkit.Particle;

public class ParticleUtil {
    public static void playEffect(Location location, Particle particle) {
        Location effectLocation = location.clone().add(0, 1.2, 0);
        location.getWorld().spawnParticle(particle, effectLocation, 10, 0.2, 0.2, 0.2, 0);
    }
}
