package me.someonelove.rechatbridge;

import com.sasha.reminecraft.Configuration;

public class ReChatBridgeConfig extends Configuration {

    /* pkg-private */ ReChatBridgeConfig() {
        super("ReChatBridge");
    }

    @ConfigSetting public String discordToken;
    @ConfigSetting public String channelId;
    @ConfigSetting public boolean isBotAccount = true;
    @ConfigSetting public boolean blockCommands = true;
    @ConfigSetting public boolean sendOnlyChats = false;
    @ConfigSetting public String discordOnlyChatsFormat = "```xml[nl]<[name]> [msg][nl]```";
    @ConfigSetting public String discordAllFormat = "```xml[nl][msg][nl]```";
    @ConfigSetting public String minecraftFormat = "> [name]#[discrim] > [msg]";

}
