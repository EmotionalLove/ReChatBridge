package me.someonelove.rechatbridge.command;

import com.github.steveice10.mc.protocol.data.game.PlayerListEntry;
import com.sasha.reminecraft.client.ReClient;
import me.someonelove.bettercommandsystem.Command;
import me.someonelove.rechatbridge.Event;
import net.dv8tion.jda.core.EmbedBuilder;

import java.awt.*;

public class TabCommand extends Command {

    public TabCommand() {
        super("tab");
    }

    @Override
    public void onCommand(boolean hasArgs, String[] args) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.pink);
        String header = (ReClient.ReClientCache.INSTANCE.tabHeader.getText() + "\n\n").replaceAll("§.", "");
        String footer = "\n\n" + ReClient.ReClientCache.INSTANCE.tabFooter.getText().replaceAll("§.", "");
        StringBuilder body = new StringBuilder();
        int c = 0;
        for (PlayerListEntry playerListEntry : ReClient.ReClientCache.INSTANCE.playerListEntries) {
            body.append(c > 0 ? ", " : "").append(playerListEntry.getProfile().getName().replace("_", "\\_"));
            c++;
        }
        if ((header + body.toString() + footer).length() > 1999) {
            body = new StringBuilder(ReClient.ReClientCache.INSTANCE.playerListEntries.size() + " players not shown");
        }
        final String f = header + body.toString() + footer;
        builder.setDescription(f);
        Event.Discord.lastEvent.getChannel().sendTyping().queue(x -> Event.Discord.lastEvent.getChannel().sendMessage(builder.build()).submit());
    }
}
