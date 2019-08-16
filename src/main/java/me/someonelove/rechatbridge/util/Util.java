package me.someonelove.rechatbridge.util;

import me.someonelove.rechatbridge.Main;
import me.someonelove.rechatbridge.ReChatBridgeConfig;
import net.dv8tion.jda.core.entities.Message;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
                .replace("[msg]", Main.config.filterDiscordInvites ? message.replace("discord.gg", "discord.invite") : message);
    }
    public static String formatDiscordAll(String message) {
        return Main.config.discordAllFormat
                .replace("[nl]", "\n")
                .replace("[msg]", Main.config.filterDiscordInvites ? message.replace("discord.gg", "discord.invite") : message);
    }
    public static String formatMinecraftChats(Message message) {
        return Main.config.minecraftFormat
                .replace("[nl]", "\n")
                .replace("[name]", message.getAuthor().getName())
                .replace("[discrim]", message.getAuthor().getDiscriminator())
                .replace("[msg]", message.getContentDisplay());
    }

    public static float fround(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value + "");
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    public static float clamp(float bottom, float current, float max) {
        if (current < bottom) return bottom;
        if (current > max) return max;
        return current;
    }

}
