package com.zenith.network.server.handler.spectator.incoming;

import com.github.steveice10.mc.protocol.data.game.entity.player.PlayerState;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerStatePacket;
import com.zenith.network.registry.IncomingHandler;
import com.zenith.network.server.ServerConnection;

public class PlayerStateSpectatorHandler implements IncomingHandler<ClientPlayerStatePacket, ServerConnection> {

    @Override
    public boolean apply(ClientPlayerStatePacket packet, ServerConnection session) {
        if (packet.getState() == PlayerState.START_SNEAKING || packet.getState() == PlayerState.START_SPRINTING) {
            session.getSoundPacket().ifPresent(p -> {
                session.getProxy().getActiveConnections().forEach(connection -> connection.send(p));
            });
        }
        return false;
    }

    @Override
    public Class<ClientPlayerStatePacket> getPacketClass() {
        return ClientPlayerStatePacket.class;
    }
}
