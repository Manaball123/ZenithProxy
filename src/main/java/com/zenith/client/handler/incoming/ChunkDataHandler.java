package com.zenith.client.handler.incoming;

import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerChunkDataPacket;
import com.zenith.client.ClientSession;
import com.zenith.event.proxy.PulseEmittedEvent;
import com.zenith.util.handler.HandlerRegistry;
import lombok.NonNull;

import static com.zenith.util.Constants.CACHE;
import static com.zenith.util.Constants.EVENT_BUS;

public class ChunkDataHandler implements HandlerRegistry.AsyncIncomingHandler<ServerChunkDataPacket, ClientSession> {
    @Override
    public boolean applyAsync(@NonNull ServerChunkDataPacket packet, @NonNull ClientSession session) {
        CACHE.getChunkCache().add(packet.getColumn());
        EVENT_BUS.dispatch(new PulseEmittedEvent());
        return true;
    }

    @Override
    public Class<ServerChunkDataPacket> getPacketClass() {
        return ServerChunkDataPacket.class;
    }
}
