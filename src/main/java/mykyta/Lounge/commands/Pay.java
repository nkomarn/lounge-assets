package mykyta.Lounge.commands;

import java.text.DecimalFormat;

import org.apache.commons.lang.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import mykyta.Lounge.Lounge;
import mykyta.Lounge.util.Database;

public class Pay implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Database db = new Database();

            if (args.length < 2) {
                Lounge.nms.sendTitle(p, "&c&lWho to pay?", "&7Specify a player to pay, as well as the amount of coins.");
                p.playSound(p.getLocation(), Sound.ENTITY_HORSE_HURT, 1.0f, 1.0f);
                return true;
            }
            if (!NumberUtils.isNumber(args[1])) {
                Lounge.nms.sendTitle(p, "&c&lNumbers, please.", "&7Can't really count coins in symbols, can you?");
                p.playSound(p.getLocation(), Sound.ENTITY_HORSE_HURT, 1.0f, 1.0f);
                return true;
            }
            
            double amount = Double.parseDouble(args[1]);
            OfflinePlayer pl = Bukkit.getOfflinePlayer(args[0]);

            // Check if other player has an account
            if (db.getDiscordID(pl.getName()) == "") {
                Lounge.nms.sendTitle(p, "&c&lRecipient doesn't have an account.", "&7Let them know to link their Discord with '~link (MC account)'.");
                p.playSound(p.getLocation(), Sound.ENTITY_HORSE_HURT, 1.0f, 1.0f);
                return true;
            }

            // Negative coins protection
            System.out.println(amount);
            if (amount < 1) {
                Lounge.nms.sendTitle(p, "&c&lNEGATIVE COINS?!?!", "&7You'd be stealing if you sent negative coins...");
                p.playSound(p.getLocation(), Sound.ENTITY_HORSE_HURT, 1.0f, 1.0f);
                return true;
            }

            Lounge.coins.withdrawPlayer(p, amount);
            Lounge.coins.depositPlayer(pl, amount);

            DecimalFormat formatter = new DecimalFormat("#,###");
            Lounge.nms.sendTitle(p, "&a&lSent " + formatter.format(Integer.parseInt(args[1])) + " ⛃", "&7" + pl.getName() + " will be very happy :)");
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            if (pl.isOnline()) {
                Player online = (Player) pl;
                online.playSound(online.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                Lounge.nms.sendTitle(online, "&a&lRecieved " + formatter.format(Integer.parseInt(args[1])) + " ⛃", "&7Greeting from " + p.getName() + "!");
            }
        }
        return true;
    }
}