package dev.iiahmed.sep.util;

import dev.iiahmed.sep.StrikeExtraPlaceholders;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Expantion extends PlaceholderExpansion {

    private final StrikeExtraPlaceholders instance = StrikeExtraPlaceholders.getInstance();

    @Override
    public @NotNull String getIdentifier() {
        return "SEP";
    }

    @Override
    public @NotNull String getAuthor() {
        return "iiAhmedYT";
    }

    @Override
    public @NotNull String getVersion() {
        return "2.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String placeholder) {
        String kit;
        int count;

        /* inQueueCount */
        if(placeholder.startsWith("inqueuecount_")){
            kit = placeholder.replace("inqueuecount_", "");
            if(instance.getPlayerAmountQueue().containsKey(kit)){
                count = instance.getPlayerAmountQueue().get(kit);
                return String.valueOf(count);
            } else return ChatColor
                    .translateAlternateColorCodes('&', "&cKIT DOESN'T EXIST");
        }

        /* inFightCount */
        if(placeholder.startsWith("infightcount_")){
            kit = placeholder.replace("infightcount_", "");
            if(instance.getPlayerAmountFight().containsKey(kit)){
                count = instance.getPlayerAmountFight().get(kit);
                return String.valueOf(count);
            } else return ChatColor
                    .translateAlternateColorCodes('&', "&cKIT DOESN'T EXIST");
        }
        return ChatColor.translateAlternateColorCodes('&',"&cINVAILID PLACEHOLDER");
    }

}