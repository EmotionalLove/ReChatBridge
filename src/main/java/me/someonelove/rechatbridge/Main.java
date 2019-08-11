package me.someonelove.rechatbridge;

import com.sasha.reminecraft.api.RePlugin;
import com.sasha.reminecraft.logging.ILogger;
import com.sasha.reminecraft.logging.LoggerBuilder;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.AnnotatedEventManager;
import sun.jvm.hotspot.CommandProcessor;

import javax.security.auth.login.LoginException;

public class Main extends RePlugin {

    public static ILogger logger = LoggerBuilder.buildProperLogger("ReChatBridge");
    public static JDA discord;
    public static ReChatBridgeConfig config = new ReChatBridgeConfig();

    @Override
    public void onPluginInit() {
        if (!Util.configCheck(config)) {
            logger.logError("ReChatBridge couldn't start because some values in the configuration aren't filled in.");
            System.exit(1);
        }
        try {
            discord = new JDABuilder(config.isBotAccount ? AccountType.BOT : AccountType.CLIENT).setToken(config.discordToken).build().awaitReady();
            logger.log("Connected to Discord.");
            discord.setEventManager(new AnnotatedEventManager());
            discord.addEventListener(new Event.Discord());
            this.getReMinecraft().EVENT_BUS.registerListener(new Event.Minecraft());
            logger.log("Event Listeners registered.");
        } catch (InterruptedException | LoginException e) {
            logger.logError("ReChatBridge couldn't start because it couldn't log into Discord. Check the token.");
            e.printStackTrace();
        }
    }

    @Override
    public void onPluginEnable() {

    }

    @Override
    public void onPluginDisable() {

    }

    @Override
    public void onPluginShutdown() {
        discord.shutdown();
    }

    @Override
    public void registerCommands() {

    }

    @Override
    public void registerConfig() {
        this.getReMinecraft().configurations.add(config);
    }
}
