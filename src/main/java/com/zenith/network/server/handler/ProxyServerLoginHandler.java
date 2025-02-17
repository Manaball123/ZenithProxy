package com.zenith.network.server.handler;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.ServerLoginHandler;
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.packetlib.Session;
import com.zenith.Proxy;
import com.zenith.cache.data.PlayerCache;
import com.zenith.event.proxy.ProxyClientConnectedEvent;
import com.zenith.event.proxy.ProxySpectatorConnectedEvent;
import com.zenith.network.server.ProxyServerListener;
import com.zenith.network.server.ServerConnection;
import com.zenith.util.Wait;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.zenith.Shared.*;
import static java.util.Objects.nonNull;

public class ProxyServerLoginHandler implements ServerLoginHandler {
    private final Proxy proxy;

    public ProxyServerLoginHandler(final Proxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public void loggedIn(Session session) {
        final GameProfile clientGameProfile = session.getFlag(MinecraftConstants.PROFILE_KEY);
        SERVER_LOG.info("Player connected: UUID: {}, Username: {}, Address: {}", clientGameProfile.getId(), clientGameProfile.getName(), session.getRemoteAddress());
        ServerConnection connection = ((ProxyServerListener) this.proxy.getServer().getListeners().stream()
                .filter(ProxyServerListener.class::isInstance)
                .findAny().orElseThrow(IllegalStateException::new))
                .getConnections().get(session);

        if (!Wait.waitUntilCondition(() -> Proxy.getInstance().isConnected()
                        && this.proxy.getConnectTime().isBefore(Instant.now().minus(Duration.of(3, ChronoUnit.SECONDS)))
                        && CACHE.getPlayerCache().getEntityId() != -1
                        && nonNull(CACHE.getProfileCache().getProfile())
                        && nonNull(CACHE.getPlayerCache().getGameMode())
                        && nonNull(CACHE.getPlayerCache().getDifficulty())
                        && nonNull(CACHE.getPlayerCache().getWorldType())
                        && nonNull(CACHE.getTabListCache().getTabList().get(CACHE.getProfileCache().getProfile().getId()))
                        && connection.isWhitelistChecked(),
                20)) {
            session.disconnect("Client login timed out.");
            return;
        }
        connection.setPlayer(true);
        if (!connection.isOnlySpectator() && this.proxy.getCurrentPlayer().compareAndSet(null, connection)) {
            // if we don't have a current player, set player
            connection.setSpectator(false);
            EVENT_BUS.post(new ProxyClientConnectedEvent(clientGameProfile));
            session.send(new ServerJoinGamePacket(
                    CACHE.getPlayerCache().getEntityId(),
                    CACHE.getPlayerCache().isHardcore(),
                    CACHE.getPlayerCache().getGameMode(),
                    CACHE.getPlayerCache().getDimension(),
                    CACHE.getPlayerCache().getDifficulty(),
                    CACHE.getPlayerCache().getMaxPlayers(),
                    CACHE.getPlayerCache().getWorldType(),
                    CACHE.getPlayerCache().isReducedDebugInfo()
            ));
            if (!proxy.isInQueue()) { PlayerCache.sync(); }
        } else {
            if (nonNull(this.proxy.getCurrentPlayer().get())) {
                // if we have a current player, allow login but put in spectator
                connection.setSpectator(true);
                EVENT_BUS.post(new ProxySpectatorConnectedEvent(clientGameProfile));
                session.send(new ServerJoinGamePacket(
                        connection.getSpectatorSelfEntityId(),
                        CACHE.getPlayerCache().isHardcore(),
                        GameMode.SPECTATOR,
                        CACHE.getPlayerCache().getDimension(),
                        CACHE.getPlayerCache().getDifficulty(),
                        CACHE.getPlayerCache().getMaxPlayers(),
                        CACHE.getPlayerCache().getWorldType(),
                        CACHE.getPlayerCache().isReducedDebugInfo()
                ));
            } else {
                // can probably make this state work with some more work but im just gonna block it for now
                connection.disconnect("A player must be connected in order to spectate!");
            }
        }
    }
}
