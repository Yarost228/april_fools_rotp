package com.github.standobyte.jojo.power;

import java.util.EnumMap;
import java.util.Map;

import javax.annotation.Nullable;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.network.PacketManager;
import com.github.standobyte.jojo.network.packets.fromserver.ActionsFullLayoutPacket;
import com.github.standobyte.jojo.power.IPower.ActionType;
import com.github.standobyte.jojo.power.IPower.PowerClassification;
import com.github.standobyte.jojo.util.utils.JojoModUtil;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Util;

public class ActionsLayout<P extends IPower<P, ?>> {
    private Map<ActionType, ActionHotbarData<P>> actions = Util.make(new EnumMap<>(ActionType.class), map -> {
        for (ActionType actionType : ActionType.values()) {
            map.put(actionType, new ActionHotbarData<>());
        }
    });
    private Action<P> mmbActionStarting;
    private Action<P> mmbActionCurrent;

    public ActionHotbarData<P> getHotbar(ActionType actionType) {
        return actions.get(actionType);
    }

    @Nullable
    public Action<P> getQuickAccessAction() {
        return mmbActionCurrent;
    }
    
    public void setQuickAccessAction(Action<P> action) {
        this.mmbActionCurrent = action;
    }

    public void resetLayout() {
        for (ActionType hotbar : ActionType.values()) {
            actions.get(hotbar).resetLayout();
        }
        mmbActionCurrent = mmbActionStarting;
    }

    void onPowerSet(IPowerType<P, ?> type) {
        for (ActionType actionType : ActionType.values()) {
            actions.get(actionType).onNewPowerType(type != null ? 
                    type.getDefaultActions(actionType) : null);
        }
        mmbActionStarting = type != null ? type.getDefaultQuickAccess() : null;
        resetLayout();
    }

    // FIXME !!! (mmb action) keep
    void keepLayoutOnClone(P oldPower) {
        for (ActionType type : ActionType.values()) {
            actions.get(type).keepLayout(oldPower.getActions(type));
        }
    }

    // FIXME !!! (mmb action) sync
    void syncWithUser(ServerPlayerEntity player, PowerClassification powerClassification) {
        for (ActionType hotbar : ActionType.values()) {
            PacketManager.sendToClient(new ActionsFullLayoutPacket(powerClassification, 
                    hotbar, actions.get(hotbar).getLayout()), player);
        }
    }
    
    

    // FIXME !!! (mmb action) save
    CompoundNBT toNBT() {
        CompoundNBT layoutNBT = new CompoundNBT();
        actions.get(ActionType.ATTACK).layoutNBT().ifPresent(hotbarNBT -> layoutNBT.put("AttacksLayout", hotbarNBT));
        actions.get(ActionType.ABILITY).layoutNBT().ifPresent(hotbarNBT -> layoutNBT.put("AbilitiesLayout", hotbarNBT));
        return layoutNBT;
    }

    // FIXME !!! (mmb action) load
    void fromNBT(CompoundNBT nbt) {
        actionLayoutFromNBT(nbt, "AttacksLayout", ActionType.ATTACK);
        actionLayoutFromNBT(nbt, "AbilitiesLayout", ActionType.ABILITY);
    }
    
    private void actionLayoutFromNBT(CompoundNBT nbt, String key, ActionType hotbar) {
        if (nbt.contains(key, JojoModUtil.getNbtId(ListNBT.class))) {
            actions.get(hotbar).updateLayoutFromNBT(nbt.getList(key, JojoModUtil.getNbtId(CompoundNBT.class)));
        }
        else {
            actions.get(hotbar).resetLayout();
        }
    }
}
