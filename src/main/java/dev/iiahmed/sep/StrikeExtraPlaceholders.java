package dev.iiahmed.sep;

import dev.iiahmed.sep.command.SEP;
import dev.iiahmed.sep.util.Expantion;
import ga.strikepractice.StrikePractice;
import ga.strikepractice.api.StrikePracticeAPI;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.logging.Level;

@Getter
public final class StrikeExtraPlaceholders extends JavaPlugin {

    @Getter
    private static StrikeExtraPlaceholders instance;
    int taskID;
    private HashMap<String, Integer> queueAmounts;
    private HashMap<String, Integer> fightAmounts;
    private boolean debug;

    @Override
    public void onEnable() {
        instance = this;
        this.queueAmounts = new HashMap<>();
        this.fightAmounts = new HashMap<>();
        saveDefaultConfig();
        runTask();
        new Expantion().register();
        Bukkit.getPluginCommand("SEP").setExecutor(new SEP());
        this.debug = getConfig().getBoolean("settings.debug");
    }

    public void reloadSystem() {
        cancelTask();
        runTask();
    }

    public void debug(String message) {
        if (!debug) return;
        getLogger().log(Level.INFO, message);
    }

    private void runTask() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this::update, 0, 2 * 20);
        if (taskID == -1) runTask();
    }

    @SuppressWarnings("ConstantConditions")
    public void update() {
        StrikePracticeAPI api = StrikePractice.getAPI();
        queueAmounts.clear();
        fightAmounts.clear();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (api.isInFight(player)) {
                String kit = api.getFight(player).getKit().getName();
                int i = fightAmounts.get(kit);
                fightAmounts.put(kit, i + 1);
            } else if (api.isInQueue(player)) {
                String kit = api.getQueuedKit(player).getName();
                int i = queueAmounts.get(kit);
                queueAmounts.put(kit, i + 1);
            }
        }
    }

    private void cancelTask() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

    @Override
    public void onDisable() {
        cancelTask();
    }
}
