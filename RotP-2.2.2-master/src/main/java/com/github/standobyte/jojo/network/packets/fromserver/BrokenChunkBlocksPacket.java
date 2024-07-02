package com.github.standobyte.jojo.network.packets.fromserver;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Supplier;

import com.github.standobyte.jojo.capability.chunk.ChunkCap.PrevBlockInfo;
import com.github.standobyte.jojo.capability.chunk.ChunkCapProvider;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.network.NetworkUtil;
import com.github.standobyte.jojo.power.stand.IStandPower;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunk;
import net.minecraftforge.fml.network.NetworkEvent;

public class BrokenChunkBlocksPacket {
    private final Collection<PrevBlockInfo> blocks;
    private final boolean reset;

    public BrokenChunkBlocksPacket(Collection<PrevBlockInfo> blocks, boolean reset) {
        this.blocks = blocks;
        this.reset = reset;
    }

    public static void encode(BrokenChunkBlocksPacket msg, PacketBuffer buf) {
        NetworkUtil.writeCollection(buf, msg.blocks, (buffer, block) -> {
            buffer.writeBlockPos(block.pos);
            buf.writeVarInt(Block.getId(block.state));
        }, true);
        buf.writeBoolean(msg.reset);
    }

    public static BrokenChunkBlocksPacket decode(PacketBuffer buf) {
        return new BrokenChunkBlocksPacket(NetworkUtil.readCollection(buf, buffer -> 
        PrevBlockInfo.clientInstance(buffer.readBlockPos(), Block.stateById(buf.readVarInt()))), 
                buf.readBoolean());
    }

    public static void handle(BrokenChunkBlocksPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            IStandPower.getStandPowerOptional(ClientUtil.getClientPlayer()).ifPresent(power -> {
                World world = ClientUtil.getClientWorld();
                msg.blocks.forEach(block -> {
                    IChunk chunk = world.getChunk(block.pos);
                    if (chunk instanceof Chunk) {
                        ((Chunk) chunk).getCapability(ChunkCapProvider.CAPABILITY).ifPresent(cap -> {
                            if (msg.reset) {
                                cap.reset();
                            }
                            if (block.state != Blocks.AIR.defaultBlockState()) {
                                cap.saveBrokenBlock(block.pos, block.state, Optional.empty(), Collections.emptyList());
                            }
                            else {
                                cap.removeBrokenBlock(block.pos);
                            }
                        });
                    }
                });
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
