package mykyta.Lounge.commands;

import java.text.DecimalFormat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import mykyta.Lounge.Lounge;
import mykyta.Lounge.util.Database;

public class Coins implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Database db = new Database();
            String discord = db.getDiscordID(p.getName());
            if (discord.length() < 1) {
                Lounge.nms.sendTitle(p, "&c&lYou don't have a bank account!", "&7Link your MC account on Discord with '~link (MC name)'.");
                return true;
            }

            double coins = Lounge.coins.getBalance(p.getName());
            DecimalFormat formatter = new DecimalFormat("#,###");
            Lounge.nms.sendTitle(p, "&a&l" + formatter.format((int) coins) + " ⛃", "&7You can pay others coins with /pay.");
        }
        return true;
    }
}