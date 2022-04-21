package dev.iiahmed.sep.util;

import dev.iiahmed.sep.StrikeExtraPlaceholders;
import ga.strikepractice.StrikePractice;
import ga.strikepractice.api.StrikePracticeAPI;
import ga.strikepractice.party.Party;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Expantion extends PlaceholderExpansion {

    private StrikeExtraPlaceholders instance = StrikeExtraPlaceholders.getInstance();

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
        int count;

        /* inQueueCount */
        if(placeholder.startsWith("inqueuecount_")){
            kit = placeholder.replace("inqueuecount_", "");
            if(instance.getPlayerAmountQueue().containsKey(kit)){
                count = instance.getPlayerAmountQueue().get(kit);
                return String.valueOf(count);
            } else return Message.INVALID_KIT.toString();
        }

        /* inFightCount */
        if(placeholder.startsWith("infightcount_")){
            kit = placeholder.replace("infightcount_", "");
            if(instance.getPlayerAmountFight().containsKey(kit)){
                count = instance.getPlayerAmountFight().get(kit);
                return String.valueOf(count);
            } else return Message.INVALID_KIT.toString();
        }

        /* partyMember */
        if(placeholder.startsWith("partymember_")){
            StrikePracticeAPI api = StrikePractice.getAPI();
            Party party = api.getParty(player);
            if (party == null) {
                return Message.INVALID_PARTY.toString();
            }
            String stringnumber = placeholder.replace("partymember_", "");
            int number;
            try {
                number = Integer.parseInt(stringnumber);
            } catch (NumberFormatException ignored){
                instance.debug("%SEP_" + placeholder + "% has in invalid int");
                return "Invalid Party Number " + stringnumber;
            }

            if(party.getMembersNames().size() > number){
                return (String) party.getMembersNames().toArray()[number-1];
            }
            return Message.PARTYMEMBER_INVALID_PLAYER.toString();
        }

        return Message.INVALID_PLACEHOLDER.toString();
    }

}
