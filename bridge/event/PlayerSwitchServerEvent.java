package bridge.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class PlayerSwitchServerEvent extends Event {

    private static HandlerList handlerList = new HandlerList();
    private Player player;
    private String server;

    public PlayerSwitchServerEvent(Player player, String server) {
        this.player = player;
        this.server = server;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
