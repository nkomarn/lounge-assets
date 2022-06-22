package mykyta.Lounge;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import mykyta.Lounge.commands.Pay;
import mykyta.Lounge.economy.Coins;
import mykyta.Lounge.events.PlayerEvent;
import mykyta.Lounge.hook.VaultHook;
import mykyta.Lounge.nms.NMS;
import mykyta.Lounge.nms.NMS_1_14_R1;
import mykyta.Lounge.util.Database;

public class Lounge extends JavaPlugin {

    public static Lounge lounge;
    public static NMS nms;
    public static Coins coins;
    private VaultHook vault;

    Database db = new Database();

    public void onEnable() {
        nms = new NMS_1_14_R1();
        coins = new Coins();
        vault = new VaultHook();
        db.initialize();
        vault.hook();

        Bukkit.getPluginManager().registerEvents(new PlayerEvent(), this);
        getCommand("coins").setExecutor(new mykyta.Lounge.commands.Coins());
        getCommand("pay").setExecutor(new Pay());
    }

    public void onDisable() {
        vault.unhook();
    }
}
