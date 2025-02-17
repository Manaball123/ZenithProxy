package com.zenith.network.server.handler.spectator.outgoing;

import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.zenith.network.registry.OutgoingHandler;
import com.zenith.network.server.ServerConnection;

public class ServerPlayerPositionRotationSpectatorOutgoingHandler implements OutgoingHandler<ServerPlayerPositionRotationPacket, ServerConnection> {
    @Override
    public ServerPlayerPositionRotationPacket apply(ServerPlayerPositionRotationPacket packet, ServerConnection session) {
        if (session.isAllowSpectatorServerPlayerPosRotate()) {
            return packet;
        } else {
            return null;
        }
    }

    @Override
    public Class<ServerPlayerPositionRotationPacket> getPacketClass() {
        return ServerPlayerPositionRotationPacket.class;
    }
}
