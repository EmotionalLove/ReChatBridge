package me.someonelove.rechatbridge;

import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerUpdateTimePacket;
import com.sasha.eventsys.SimpleEventHandler;
import com.sasha.eventsys.SimpleListener;
import com.sasha.reminecraft.ReMinecraft;
import com.sasha.reminecraft.api.event.ChatReceivedEvent;
import com.sasha.reminecraft.api.event.RemoteServerPacketRecieveEvent;
import me.someonelove.rechatbridge.util.TickrateUtil;
import me.someonelove.rechatbridge.util.Util;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;

import java.awt.*;

public class Event {

    public static class Minecraft implements SimpleListener {

        @SimpleEventHandler
        public void onChatEvent(ChatReceivedEvent e) {
            if (e.getMessageAuthor() == null && Main.config.sendOnlyChats) return;
            if (Main.config.sendOnlyChats) {
                Main.discord.getTextChannelById(Main.config.channelId).sendMessage(Util.formatDiscordOnlyChats(e.getMessageAuthor(), e.getMessageText().replace("`", "'"))).submit();
            }
            else {
                Main.discord.getTextChannelById(Main.config.channelId).sendMessage(Util.formatDiscordAll(e.getMessageRaw().replace("`", "'"))).submit();
            }
        }

        @SimpleEventHandler
        public void onPckRx(RemoteServerPacketRecieveEvent e) {
            if (e.getRecievedPacket() instanceof ServerUpdateTimePacket) {
                TickrateUtil.INSTANCE.onTimeUpdate();
            }
        }
    }

    public static class Discord {

        public static GuildMessageReceivedEvent lastEvent;

        @SubscribeEvent
        public void onMsgRx(GuildMessageReceivedEvent e) {
            if (e.getAuthor().isBot() || e.getAuthor().getId().equals(Main.discord.getSelfUser().getId()) || !e.getChannel().getId().equals(Main.config.channelId)) return;
            if (e.getMessage().getContentDisplay().startsWith(";")) {
                lastEvent = e;
                try {
                    Main.commandProcessor.processCommand(e.getMessage().getContentDisplay());
                } catch (Exception ex) {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setColor(Color.RED);
                    builder.setDescription("An error occurred whilst processing that command");
                    e.getChannel().sendMessage(builder.build()).submit();
                    ex.printStackTrace();
                }
                return;
            }
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