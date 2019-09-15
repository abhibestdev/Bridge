package bridge;

import bridge.event.PlayerSwitchServerEvent;
import bridge.listener.MessageListener;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class Bridge extends JavaPlugin {

    @Getter
    private static Bridge instance;

    public void onEnable() {
        instance = this;
        registerChannels();
    }

    private void registerChannels() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new MessageListener());
    }


    public static void sendMessageToServer(Player player, String channel, String message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
        DataOutputStream msgout = new DataOutputStream(msgbytes);
        try {
            out.writeUTF("Forward");
            out.writeUTF("ALL");
            out.writeUTF(channel);
            msgout.writeUTF(message);
            out.writeShort(msgbytes.toByteArray().length);
            out.write(msgbytes.toByteArray());
            player.sendPluginMessage(instance, "BungeeCord", out.toByteArray());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        out.writeShort(msgbytes.toByteArray().length);
        out.write(msgbytes.toByteArray());
    }

    public static void connectToServer(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        Bukkit.getPluginManager().callEvent(new PlayerSwitchServerEvent(player, server));
        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
            out.writeUTF(player.getName());
            player.sendPluginMessage(instance, "BungeeCord", out.toByteArray());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void getCount(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(server);
        player.sendPluginMessage(instance, "BungeeCord", out.toByteArray());

    }

}
