package mykyta.Lounge.events;

import java.awt.Color;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import mykyta.Lounge.util.Database;
import mykyta.Lounge.util.Webhook;

public class PlayerEvent implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        Database db = new Database();

        // Send webhook to Discord channel
        try {
            Webhook webhook = new Webhook("https://discordapp.com/api/webhooks/589587875926900744/tH3izfD0VRKdE9frvl3x4ycoxIHG9mZJw7P6ek7e0ByXatimKiZtcZ0rkPHQAcBme1jg");
            webhook.setAvatarUrl("https://minotar.net/avatar/" + p.getName());
            webhook.setUsername(p.getName());
            webhook.addEmbed(new Webhook.EmbedObject()
            .setDescription(e.getMessage())
            .setColor(Color.RED)); 
            webhook.execute();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        // Green text
        if (e.getMessage().startsWith(">")) e.setMessage(ChatColor.GREEN + e.getMessage());
        e.setFormat(ChatColor.translateAlternateColorCodes('&', "&8" + p.getName() + "&8: &7" + e.getMessage()));

        // Increment messages/levels if linked to Discord
        String discord = db.getDiscordID(p.getName());
        if (discord.length() < 1) return;

        Random rand = new Random(); 
        db.incrementCoins(p, rand.nextInt(30));
    }
}