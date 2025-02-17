package com.zenith.module.impl;

import com.github.steveice10.mc.protocol.data.game.ClientRequest;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientRequestPacket;
import com.zenith.Proxy;
import com.zenith.event.Subscription;
import com.zenith.event.module.ClientTickEvent;
import com.zenith.event.proxy.DeathEvent;
import com.zenith.module.Module;
import com.zenith.util.Wait;

import java.util.function.Supplier;

import static com.zenith.Shared.*;
import static com.zenith.event.SimpleEventBus.pair;
import static java.util.Objects.isNull;

public class AutoRespawn extends Module {
    private static final int tickEventRespawnDelay = 100;
    private int tickCounter = 0;
    public AutoRespawn() {
        super();
    }

    @Override
    public Subscription subscribeEvents() {
        return EVENT_BUS.subscribe(
            pair(ClientTickEvent.class, this::handleClientTickEvent),
            pair(DeathEvent.class, this::handleDeathEvent)
        );
    }

    @Override
    public Supplier<Boolean> shouldBeEnabled() {
        return () -> CONFIG.client.extra.autoRespawn.enabled;
    }


    public void handleDeathEvent(final DeathEvent event) {
        tickCounter = -tickEventRespawnDelay - (CONFIG.client.extra.autoRespawn.delayMillis / 50);
        Wait.waitALittleMs(Math.max(CONFIG.client.extra.autoRespawn.delayMillis, 1000));
        checkAndRespawn();
    }


    public void handleClientTickEvent(final ClientTickEvent event) {
        // the purpose of this handler is to also autorespawn when we've logged in and are already dead
        if (tickCounter++ < tickEventRespawnDelay) return;
        tickCounter = 0;
        checkAndRespawn();
    }

    private void checkAndRespawn() {
        if (Proxy.getInstance().isConnected() && CACHE.getPlayerCache().getThePlayer().getHealth() <= 0 && isNull(
                Proxy.getInstance().getCurrentPlayer().get())) {
            MODULE_LOG.info("Performing AutoRespawn");
            sendClientPacketAsync(new ClientRequestPacket(ClientRequest.RESPAWN));
            CACHE.getPlayerCache().getThePlayer().setHealth(20.0f);
        }
    }
}
