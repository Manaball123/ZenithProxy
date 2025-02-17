package com.zenith.network.server.handler.spectator.outgoing;

import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerOpenTileEntityEditorPacket;
import com.zenith.network.registry.OutgoingHandler;
import com.zenith.network.server.ServerConnection;

public class ServerOpenTileEntityEditorSpectatorOutgoingHandler implements OutgoingHandler<ServerOpenTileEntityEditorPacket, ServerConnection> {
    @Override
    public ServerOpenTileEntityEditorPacket apply(ServerOpenTileEntityEditorPacket packet, ServerConnection session) {
        return null;
    }

    @Override
    public Class<ServerOpenTileEntityEditorPacket> getPacketClass() {
        return ServerOpenTileEntityEditorPacket.class;
    }
}
