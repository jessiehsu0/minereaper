package minereaper.powerup;

import minereaper.player.Player;

public interface IPowerUp {
    String getName();
    void execute(Player user, Player opponent);
}
