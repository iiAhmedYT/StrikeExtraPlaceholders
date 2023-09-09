package dev.iiahmed.sep.util;

import dev.iiahmed.sep.StrikeExtraPlaceholders;
import ga.strikepractice.StrikePractice;
import ga.strikepractice.api.StrikePracticeAPI;
import ga.strikepractice.party.Party;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
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
        return "2.1";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String placeholder) {
        String kit;

        /* queued player count (per kit) */
        if (placeholder.startsWith("inqueuecount_")) {
            kit = placeholder.replace("inqueuecount_", "");

            return instance.getQueueAmounts().containsKey(kit)?
                    String.valueOf(instance.getQueueAmounts().get(kit)) :
                    Message.INVALID_KIT.toString();
        }

        /* dynamic queue count for menus (per kit) */
        if (placeholder.startsWith("dynamicqueue_")) {
            kit = placeholder.replace("dynamicqueue_", "");

            int i = instance.getQueueAmounts().get(kit);
            return i == 0? String.valueOf(1) : String.valueOf(i + 1);
        }


        /* dynamic queue count for menus (per kit) */
        if (placeholder.startsWith("dynamicqueue2_")) {
            kit = placeholder.replace("dynamicqueue2_", "");

            int i = instance.getQueueAmounts().get(kit);
            return i == 0? String.valueOf(1) : String.valueOf(2);
        }

        /* fighting players (per kit) */
        if (placeholder.startsWith("infightcount_")) {
            kit = placeholder.replace("infightcount_", "");

            return instance.getFightAmounts().containsKey(kit) ?
                    String.valueOf(instance.getFightAmounts().get(kit)) :
                    Message.INVALID_KIT.toString();
        }

        /* dynamic queue count (per kit) */
        if (placeholder.startsWith("dynamicfight_")) {
            kit = placeholder.replace("dynamicfight_", "");

            int i = instance.getFightAmounts().get(kit);
            return i == 0? String.valueOf(1) : String.valueOf(i);
        }

        /* partyMember */
        if (placeholder.startsWith("partymember_")) {
            StrikePracticeAPI api = StrikePractice.getAPI();
            Party party = api.getParty(player);
            if (party == null) {
                return Message.INVALID_PARTY.toString();
            }
            String stringnumber = placeholder.replace("partymember_", "");
            int number;
            try {
                number = Integer.parseInt(stringnumber);
            } catch (NumberFormatException ignored) {
                instance.debug("%SEP_" + placeholder + "% has an invalid int");
                return "Invalid Party Number " + stringnumber;
            }

            if (party.getMembersNames().size() >= number) {
                return (String) party.getMembersNames().toArray()[number - 1];
            }
            return Message.PARTYMEMBER_INVALID_PLAYER.toString();
        }

        return Message.INVALID_PLACEHOLDER.toString();
    }

}
