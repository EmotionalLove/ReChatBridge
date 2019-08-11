package me.someonelove.rechatbridge;

import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.reminecraft.ReMinecraft;
import com.sasha.reminecraft.api.event.ChatReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;

public class Event {

    public static class Minecraft implements SimpleListener {

        @SimpleEventHandler
        public void onChatEvent(ChatReceivedEvent e) {
            if (e.getMessageAuthor() == null && Main.config.sendOnlyChats) return;
            if (Main.config.sendOnlyChats) {
                Main.discord.getTextChannelById(Main.config.channelId).sendMessage(Util.formatDiscordOnlyChats(e.getMessageAuthor(), e.getMessageText())).submit();
            }
            else {
                Main.discord.getTextChannelById(Main.config.channelId).sendMessage(Util.formatDiscordAll(e.getMessageRaw())).submit();
            }
        }
    }

    public static class Discord {
        @SubscribeEvent
        public void onMsgRx(GuildMessageReceivedEvent e) {
            if (e.getAuthor().isBot() || e.getAuthor().getId().equals(Main.discord.getSelfUser().getId()) || !e.getChannel().getId().equals(Main.config.channelId)) return;
            String format = Util.formatMinecraftChats(e.getMessage());
            if (Main.config.blockCommands && format.startsWith("/")) return;
            if (!Main.config.blockCommands && e.getMessage().getContentDisplay().startsWith("/")) {
                ReMinecraft.INSTANCE.sendFromClient(new ClientChatPacket(e.getMessage().getContentDisplay()));
                return;
            }
            if (format.length() > 256) {
                e.getMessage().addReaction("🚫").submit();
                return;
            }
            ReMinecraft.INSTANCE.sendFromClient(new ClientChatPacket(format));
            e.getMessage().addReaction("✅").submit();
        }
    }
}