package me.someonelove.rechatbridge.command;

import me.someonelove.bettercommandsystem.Command;
import me.someonelove.rechatbridge.Event;
import me.someonelove.rechatbridge.util.TickrateUtil;
import net.dv8tion.jda.core.EmbedBuilder;

import java.awt.*;

public class TickrateCommand extends Command {

    public TickrateCommand() {
        super("tps");
    }

    @Override
    public void onCommand(boolean hasArgs, String[] args) {
        Event.Discord.lastEvent.getChannel().sendTyping().queue(e -> Event.Discord.lastEvent.getChannel().sendMessage(createEmbed().build()).submit());
    }

    private EmbedBuilder createEmbed() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Server tickrate information");
        builder.addField("TPS", String.valueOf(TickrateUtil.INSTANCE.getTickRate()), true);
        builder.setColor(getColorForTPS(TickrateUtil.INSTANCE.getTickRate()));
        if (!TickrateUtil.INSTANCE.isServerResponding()) {
            long secs = (System.currentTimeMillis() - TickrateUtil.INSTANCE.timeLastTimeUpdate) / 1000;
            builder.addField("Status", "Server is not responding\n**" + secs + " seconds** since last tick.", true);
            builder.setColor(Color.RED);
        }
        return builder;
    }

    private Color getColorForTPS(float tps) {
        if (tps > 15) return  Color.GREEN;
        else if (tps > 10) return Color.YELLOW;
        else if (tps > 7) return Color.ORANGE;
        else if (tps > 5) return Color.RED;
        else return Color.BLACK;
    }



}
