package net.earthcomputer.clientcommands.features;

import net.earthcomputer.clientcommands.event.ClientConnectionEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ServerBrandManager {

    private static String serverBrand = "vanilla";
    private static boolean hasWarnedRng = false;

    public static void registerEvents() {
        ClientConnectionEvents.DISCONNECT.register(ServerBrandManager::onDisconnect);
    }

    public static void setServerBrand(String brand) {
        serverBrand = brand;
    }

    public static String getServerBrand() {
        return serverBrand;
    }

    public static boolean isVanilla() {
        return "vanilla".equals(serverBrand);
    }

    private static void onDisconnect() {
        if (hasWarnedRng && Relogger.isRelogging) {
            Relogger.relogSuccessTasks.add(() -> hasWarnedRng = true);
        }
        hasWarnedRng = false;
    }

    public static void rngWarning() {
        if (!isVanilla() && !hasWarnedRng && !Minecraft.getInstance().hasSingleplayerServer()) {
            Minecraft.getInstance().gui.getChat().addMessage(
                    Component.translatable("playerManip.serverBrandWarning").withStyle(ChatFormatting.YELLOW));
            hasWarnedRng = true;
        }
    }

}
