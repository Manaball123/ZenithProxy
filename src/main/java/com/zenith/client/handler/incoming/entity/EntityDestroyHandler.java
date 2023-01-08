package com.zenith.client.handler.incoming.entity;

import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityDestroyPacket;
import com.zenith.cache.data.entity.EntityPlayer;
import com.zenith.client.ClientSession;
import com.zenith.event.proxy.PlayerLeftVisualRangeEvent;
import com.zenith.util.handler.HandlerRegistry;
import lombok.NonNull;

import java.util.Arrays;

import static com.zenith.util.Constants.*;

public class EntityDestroyHandler implements HandlerRegistry.AsyncIncomingHandler<ServerEntityDestroyPacket, ClientSession> {
    @Override
    public boolean applyAsync(@NonNull ServerEntityDestroyPacket packet, @NonNull ClientSession session) {
        CACHE.getEntityCache().getEntities().entrySet().stream()
                .filter(entry -> Arrays.stream(packet.getEntityIds()).anyMatch((id) -> entry.getKey() == id))
                .filter(entry -> entry.getValue() instanceof EntityPlayer)
                .forEach(entry -> {
                    CACHE.getTabListCache().getTabList().get(entry.getValue().getUuid())
                            .ifPresent(playerEntry -> EVENT_BUS.dispatch(new PlayerLeftVisualRangeEvent(
                                    playerEntry, entry.getValue()))
                            );
                });
        for (int id : packet.getEntityIds()) {
            CACHE.getEntityCache().remove(id);
        }
        return true;
    }

    @Override
    public Class<ServerEntityDestroyPacket> getPacketClass() {
        return ServerEntityDestroyPacket.class;
    }
}
