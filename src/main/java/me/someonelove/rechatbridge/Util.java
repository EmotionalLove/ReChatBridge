package me.someonelove.rechatbridge;

import net.dv8tion.jda.core.entities.Message;

public class Util {

    public static boolean configCheck(ReChatBridgeConfig config) {
        return config.discordToken != null &&
                config.discordAllFormat != null &&
                config.discordOnlyChatsFormat != null &&
                config.minecraftFormat != null &&
                config.channelId != null;
    }

    public static String formatDiscordOnlyChats(String user, String message) {
        return Main.config.discordOnlyChatsFormat
                .replace("[nl]", "\n")
                .replace("[name]", user)
                .replace("[msg]", message);
    }
    public static String formatDiscordAll(String message) {
        return Main.config.discordAllFormat
                .replace("[nl]", "\n")
                .replace("[msg]", message);
    }
    public static String formatMinecraftChats(Message message) {
        return Main.config.minecraftFormat
                .replace("[nl]", "\n")
                .replace("[name]", message.getAuthor().getName())
                .replace("[discrim]", message.getAuthor().getDiscriminator())
                .replace("[msg]", message.getContentDisplay());
    }

}
