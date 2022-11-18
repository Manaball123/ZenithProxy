package com.zenith.module;

import com.collarmc.pounce.Subscribe;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerRotationPacket;
import com.zenith.Proxy;
import com.zenith.cache.data.PlayerCache;
import com.zenith.cache.data.entity.Entity;
import com.zenith.cache.data.entity.EntityPlayer;
import com.zenith.event.module.ClientTickEvent;
import com.zenith.event.proxy.NewPlayerInVisualRangeEvent;
import com.zenith.util.TickTimer;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.zenith.util.Constants.CACHE;
import static com.zenith.util.Constants.CONFIG;
import static java.util.Arrays.asList;
import static java.util.Objects.isNull;

public class Schizo extends Module {

    public Schizo(Proxy proxy) {
        super(proxy);
    }


    public UUID getRandomUUID() {
        Random rand = new Random();
        List<UUID> UUIDList = getUUIDList();
        return UUIDList.get(rand.nextInt(UUIDList.size()));
    }

    public List<UUID> getUUIDList() {
        List<UUID> UUIDList = asList();
        CONFIG.server.schizo.obsessed.forEach( (n) -> {UUIDList.add();});
    }

//    @Subscribe
//    public void handleClientTickEvent(final ClientTickEvent event) {
//        synchronized (focusStack) { // handling this regardless of mode so we don't fill stack indefinitely
//            if (!this.focusStack.isEmpty()) {
//                this.focusStack.removeIf(e -> CACHE.getEntityCache().getEntities().values().stream()
//                        .noneMatch(entity -> Objects.equals(e, entity)));
//            }
//        }
//        if (CONFIG.client.extra.spook.enabled && isNull(this.proxy.getCurrentPlayer().get()) && !proxy.isInQueue()) {
//            stareTick();
//        } else {
//            hasTarget.lazySet(false);
//        }
//    }

//    @Subscribe
//    public void handleNewPlayerInVisualRangeEvent(NewPlayerInVisualRangeEvent event) {
//        synchronized (this.focusStack) {
//            this.focusStack.push(event.playerEntity);
//        }
//    }
//
//
}
