package bridge.event;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class ServerMessageReceieveEvent extends Event {

    private static HandlerList handlerList = new HandlerList();
    private String channel;
    private String message;

    public ServerMessageReceieveEvent(String channel, String message) {
        this.channel = channel;
        this.message = message;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
