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

@Getter
public final class StrikeExtraPlaceholders extends JavaPlugin {

    @Getter private static StrikeExtraPlaceholders instance;
    private HashMap<String, Integer> playerAmountQueue;
    private HashMap<String, Integer> playerAmountFight;
    int taskID;

    @Override
    public void onEnable() {
        instance = this;
        playerAmountQueue = new HashMap<>();
        playerAmountFight = new HashMap<>();
        runTask();
        new Expantion().register();
        Bukkit.getPluginCommand("SEP").setExecutor(new SEP());
    }

    public void reloadSystem(){
        cancelTask();
        runTask();
    }

    @SuppressWarnings("all")
    private void runTask(){
        StrikePracticeAPI api = StrikePractice.getAPI();
        playerAmountQueue.clear();
        playerAmountFight.clear();
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            api.getKits().forEach(kit -> {
                String kitName = kit.getName();
                int queueAmount = 0;
                int fightAmount = 0;
                for(Player player : Bukkit.getOnlinePlayers()){
                    if(api.isInFight(player) && api.getFight(player) != null){
                        if(api.getFight(player).getKit().getName().equalsIgnoreCase(kitName))
                            fightAmount++;
                    }
                    if(api.isInQueue(player) && api.getQueuedKit(player) != null &&
                            api.getQueuedKit(player).getName().equalsIgnoreCase(kitName)){
                        queueAmount++;
                    }
                }
                playerAmountQueue.put(kitName, queueAmount);
                playerAmountFight.put(kitName, fightAmount);
            });
        }, 0, 2*20);
        if(taskID == -1) runTask();
    }

    private void cancelTask(){
        Bukkit.getScheduler().cancelTask(taskID);
    }

    @Override
    public void onDisable() {
        cancelTask();
    }
}
