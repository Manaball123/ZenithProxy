package com.zenith.network.client.handler.incoming;

import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerHealthPacket;
import com.zenith.event.module.PlayerHealthChangedEvent;
import com.zenith.network.client.ClientSession;
import com.zenith.network.registry.AsyncIncomingHandler;
import lombok.NonNull;

import static com.zenith.Shared.*;

public class PlayerHealthHandler implements AsyncIncomingHandler<ServerPlayerHealthPacket, ClientSession> {

    @Override
    public boolean applyAsync(@NonNull ServerPlayerHealthPacket packet, @NonNull ClientSession session) {
        if (packet.getHealth() != CACHE.getPlayerCache().getThePlayer().getHealth()) {
            EVENT_BUS.postAsync(
                new PlayerHealthChangedEvent(packet.getHealth(), CACHE.getPlayerCache().getThePlayer().getHealth()));
        }

        CACHE.getPlayerCache().getThePlayer()
                .setFood(packet.getFood())
                .setSaturation(packet.getSaturation())
                .setHealth(packet.getHealth());
        CACHE_LOG.debug("Player food: {}", packet.getFood());
        CACHE_LOG.debug("Player saturation: {}", packet.getSaturation());
        CACHE_LOG.debug("Player health: {}", packet.getHealth());
        return true;
    }

    @Override
    public Class<ServerPlayerHealthPacket> getPacketClass() {
        return ServerPlayerHealthPacket.class;
    }
}
