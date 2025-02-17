package com.zenith.network.server.handler.spectator.postoutgoing;

import com.github.steveice10.mc.protocol.data.game.PlayerListEntry;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntryAction;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerPlayerListEntryPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerPluginMessagePacket;
import com.zenith.feature.spectator.SpectatorUtils;
import com.zenith.network.registry.PostOutgoingHandler;
import com.zenith.network.server.ServerConnection;
import com.zenith.util.RefStrings;
import lombok.NonNull;

import static com.github.steveice10.mc.protocol.data.game.entity.player.GameMode.SPECTATOR;
import static com.zenith.Shared.CACHE;

public class JoinGameSpectatorPostHandler implements PostOutgoingHandler<ServerJoinGamePacket, ServerConnection> {
    @Override
    public void accept(@NonNull ServerJoinGamePacket packet, @NonNull ServerConnection session) {
        session.send(new ServerPluginMessagePacket("MC|Brand", RefStrings.BRAND_SUPPLIER.get()));
        session.send(new ServerPlayerListEntryPacket(
                PlayerListEntryAction.ADD_PLAYER,
                new PlayerListEntry[]{new PlayerListEntry(session.getProfileCache().getProfile(), SPECTATOR)}
        ));
        SpectatorUtils.initSpectator(session, () -> CACHE.getAllDataSpectator(session.getSpectatorPlayerCache()));
        //send cached data
        session.getProxy().getActiveConnections().stream()
                .filter(connection -> !connection.equals(session))
                .forEach(connection -> {
                    connection.send(new ServerChatPacket(
                            "§9" + session.getProfileCache().getProfile().getName() + " connected!§r", true
                    ));
                    if (connection.equals(session.getProxy().getCurrentPlayer().get())) {
                        connection.send(new ServerChatPacket(
                                "§9Send private messages: \"!m <message>\"§r", true
                        ));
                    }
                });
        session.setLoggedIn(true);
    }

    @Override
    public Class<ServerJoinGamePacket> getPacketClass() {
        return ServerJoinGamePacket.class;
    }
}
