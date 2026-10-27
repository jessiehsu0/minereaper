package minereaper.powerup;

import java.util.List;
import java.util.stream.IntStream;

public class PowerUpFactory {
    public IPowerUp createRevealSafePowerUp() {
        return new RevealSafePowerUp();
    }

    public IPowerUp createHideTilesPowerUp() {
        return new HideTilesPowerUp();
    }

    public List<IPowerUp> createRevealSafePowerUps(int numOfPowerUps) {
        return IntStream.range(0, numOfPowerUps)
                .mapToObj(_ -> createRevealSafePowerUp())
                .toList();
    }

    public List<IPowerUp> createHideTilesPowerUps(int numOfPowerUps) {
        return IntStream.range(0, numOfPowerUps)
                .mapToObj(_ -> createHideTilesPowerUp())
                .toList();
    }
}
