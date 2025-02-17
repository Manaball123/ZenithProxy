package com.zenith.feature.spectator.entity.mob;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.EntityMetadata;
import com.github.steveice10.mc.protocol.data.game.entity.type.MobType;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnMobPacket;
import com.github.steveice10.packetlib.packet.Packet;
import com.zenith.cache.data.PlayerCache;
import com.zenith.feature.spectator.entity.SpectatorEntity;

import java.util.UUID;

public abstract class SpectatorMob extends SpectatorEntity {
    abstract MobType getMobType();

    @Override
    public Packet getSpawnPacket(final int entityId, final UUID uuid, final PlayerCache playerCache, final GameProfile gameProfile) {
        return new ServerSpawnMobPacket(
                entityId,
                uuid,
                getMobType(),
                playerCache.getX(),
                playerCache.getY(),
                playerCache.getZ(),
                playerCache.getYaw(),
                playerCache.getPitch(),
                playerCache.getYaw(),
                0f,
                0f,
                0f,
                new EntityMetadata[0]); // we'll set this later on the entity metadata packet
    }
}
