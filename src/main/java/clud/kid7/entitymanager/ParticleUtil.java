package clud.kid7.entitymanager;

import org.bukkit.Location;
import org.bukkit.Particle;

public class ParticleUtil {
    public static void playEffect(Location location, Particle particle) {
        Location effectLocation = location.clone().add(0, 1.2, 0);
        int particleCount = particle.equals(Particle.BARRIER) ? 1 : 10;
        location.getWorld().spawnParticle(particle, effectLocation, particleCount, 0.2, 0.2, 0.2, 0);
    }
}
