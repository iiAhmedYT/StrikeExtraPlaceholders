package dev.iiahmed.sep.util;

import dev.iiahmed.sep.StrikeExtraPlaceholders;
import net.md_5.bungee.api.ChatColor;

public enum Message {
    // shared placeholders
    INVALID_PLACEHOLDER("&cINVALID PLACEHOLDER", "message.invalid-placeholder"),
    INVALID_KIT("&cKIT DOESN'T EXIST", "message.invalid-kit"),
    INVALID_PARTY("&cINVALID PARTY", "message.invalid-prty"),

    // specified placeholders
    PARTYMEMBER_INVALID_PLAYER("", "message.party-member.invalid-player")

    ;

    private final String defaultMessage, path;

    Message(String defaultMessage, String path){
        this.defaultMessage = defaultMessage;
        this.path = path;
    }

    @Override
    public String toString() {
        String message = StrikeExtraPlaceholders.getInstance().getConfig().getString(path);
        if(message == null){
            StrikeExtraPlaceholders.getInstance().debug("Message in " + path + " is not set.");
            return translate(defaultMessage);
        }
        return translate(message);
    }

    private String translate(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
