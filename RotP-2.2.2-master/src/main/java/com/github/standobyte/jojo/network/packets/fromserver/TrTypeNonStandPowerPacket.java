package com.github.standobyte.jojo.network.packets.fromserver;

import java.util.function.Supplier;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.client.ui.hud.ActionsOverlayGui;
import com.github.standobyte.jojo.power.IPower;
import com.github.standobyte.jojo.power.IPower.PowerClassification;
import com.github.standobyte.jojo.power.IPowerType;
import com.github.standobyte.jojo.power.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.nonstand.type.NonStandPowerType;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class TrTypeNonStandPowerPacket {
    private final int entityId;
    private final NonStandPowerType<?> powerType;

    public TrTypeNonStandPowerPacket(int entityId, NonStandPowerType<?> powerType) {
        this.entityId = entityId;
        this.powerType = powerType;
    }
    
    public static TrTypeNonStandPowerPacket noPowerType(int entityId) {
        return new TrTypeNonStandPowerPacket(entityId, null);
    }

    public static <P extends IPower<P, T>, T extends IPowerType<P, T>> void encode(TrTypeNonStandPowerPacket msg, PacketBuffer buf) {
        boolean noPowerType = msg.powerType == null;
        buf.writeBoolean(noPowerType);
        buf.writeInt(msg.entityId);
        if (!noPowerType) buf.writeRegistryId(msg.powerType);
    }

    public static TrTypeNonStandPowerPacket decode(PacketBuffer buf) {
        boolean noPowerType = buf.readBoolean();
        if (noPowerType) {
            return noPowerType(buf.readInt());
        }
        return new TrTypeNonStandPowerPacket(buf.readInt(), buf.readRegistryIdSafe(NonStandPowerType.class));
    }

    public static void handle(TrTypeNonStandPowerPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = ClientUtil.getEntityById(msg.entityId);
            if (entity instanceof LivingEntity) {
                INonStandPower.getNonStandPowerOptional((LivingEntity) entity).ifPresent(power -> {
                    if (msg.powerType == null) {
                        if (entity == ClientUtil.getClientPlayer()) {
                            ActionsOverlayGui overlay = ActionsOverlayGui.getInstance();
                            if (PowerClassification.NON_STAND == overlay.getCurrentMode()) {
                                overlay.setMode(null);
                            }
                        }
                        power.clear();
                    }
                    else {
                        power.givePower(msg.powerType);
                    }
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
