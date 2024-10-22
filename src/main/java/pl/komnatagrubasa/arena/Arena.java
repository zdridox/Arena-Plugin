package pl.komnatagrubasa.arena;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import pl.komnatagrubasa.arena.commands.Challange;

import java.util.ArrayList;
import java.util.List;

public final class Arena extends JavaPlugin {

    private static Arena instance;

    public static Arena GetInstance() {
        return instance;
    }

    public static Boolean gameOngoing = false;
    public static List<String> kits = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        System.out.println("Arena 1.0 by zdridox");
        getCommand("wyzwij").setExecutor(new Challange());
        getServer().getPluginManager().registerEvents(new Fight(), this);

        kits.add("iron");
        kits.add("diamond");

        for (World world : Bukkit.getWorlds()) {
            world.setGameRule(GameRule.KEEP_INVENTORY, true);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            world.setDifficulty(Difficulty.NORMAL);
        }
    }

}
