package mykyta.Lounge.nms;

import org.bukkit.entity.Player;

public interface NMS {
    public void sendActionbar(Player player, String message);
    public void sendJSONMessage(Player player, String json);
    public void sendTitle(Player player, String top, String bottom);
}