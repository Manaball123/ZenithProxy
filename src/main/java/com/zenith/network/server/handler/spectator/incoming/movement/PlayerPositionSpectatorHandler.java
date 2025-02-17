package com.zenith.network.server.handler.spectator.incoming.movement;

import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionPacket;
import com.zenith.feature.spectator.SpectatorUtils;
import com.zenith.network.registry.IncomingHandler;
import com.zenith.network.server.ServerConnection;
import lombok.NonNull;

public class PlayerPositionSpectatorHandler implements IncomingHandler<ClientPlayerPositionPacket, ServerConnection> {
    @Override
    public boolean apply(@NonNull ClientPlayerPositionPacket packet, @NonNull ServerConnection session) {
        session.getSpectatorPlayerCache()
                .setX(packet.getX())
                .setY(packet.getY())
                .setZ(packet.getZ());
        PlayerPositionRotationSpectatorHandler.updateSpectatorPosition(session);
        SpectatorUtils.checkSpectatorPositionOutOfRender(session);
        return false;
    }

    @Override
    public Class<ClientPlayerPositionPacket> getPacketClass() {
        return ClientPlayerPositionPacket.class;
    }
}
