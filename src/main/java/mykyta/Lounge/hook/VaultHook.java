package mykyta.Lounge.hook;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.ServicePriority;

import mykyta.Lounge.Lounge;
import net.milkbowl.vault.economy.Economy;

public class VaultHook {
    private Lounge lounge = Lounge.lounge;
    private Economy provider;

    public void hook() {
        provider = Lounge.coins;
        Bukkit.getServicesManager().register(Economy.class, this.provider, this.lounge, ServicePriority.Normal);
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Registered economy."));
    }

    public void unhook() {
        Bukkit.getServicesManager().unregister(Economy.class, this.provider);
    }
}