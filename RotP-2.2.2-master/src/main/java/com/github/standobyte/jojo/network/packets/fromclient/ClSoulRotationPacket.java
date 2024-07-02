package com.github.standobyte.jojo.network.packets.fromclient;

import java.util.function.Supplier;

import com.github.standobyte.jojo.entity.SoulEntity;
import com.google.common.primitives.Floats;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class ClSoulRotationPacket {
    private final int entityId;
    private final float yRot;
    private final float xRot;

    public ClSoulRotationPacket(int entityId, float yRot, float xRot) {
        this.entityId = entityId;
        this.yRot = yRot;
        this.xRot = xRot;
    }

    public static void encode(ClSoulRotationPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
        buf.writeFloat(msg.yRot);
        buf.writeFloat(msg.xRot);
    }

    public static ClSoulRotationPacket decode(PacketBuffer buf) {
        return new ClSoulRotationPacket(buf.readInt(), buf.readFloat(), buf.readFloat());
    }

    public static void handle(ClSoulRotationPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (Floats.isFinite(msg.xRot) && Floats.isFinite(msg.yRot)) {
                Entity entity = ctx.get().getSender().level.getEntity(msg.entityId);
                if (entity instanceof SoulEntity) {
                    ((SoulEntity) entity).handleRotationPacket(msg.yRot, msg.xRot);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
