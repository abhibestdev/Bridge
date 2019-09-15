package bridge.listener;

import bridge.event.ServerMessageReceieveEvent;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class MessageListener implements PluginMessageListener {

    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        if (s.equals("BungeeCord")) {
            ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
            String subChannel = in.readUTF();
            short len = in.readShort();
            byte[] msgbytes = new byte[len];
            in.readFully(msgbytes);
            try {
                DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
                String somedata = msgin.readUTF().replace("  \u0003", "");
                Bukkit.getPluginManager().callEvent(new ServerMessageReceieveEvent(subChannel, somedata));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
