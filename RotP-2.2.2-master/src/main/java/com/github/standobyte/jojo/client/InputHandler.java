package com.github.standobyte.jojo.client;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_B;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_H;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_J;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_K;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_ALT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_M;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_O;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_V;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.client.ui.hud.ActionsOverlayGui;
import com.github.standobyte.jojo.entity.LeavesGliderEntity;
import com.github.standobyte.jojo.entity.itemprojectile.ItemProjectileEntity;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.init.ModEffects;
import com.github.standobyte.jojo.init.power.hamon.ModHamon;
import com.github.standobyte.jojo.init.power.vampirism.ModVampirism;
import com.github.standobyte.jojo.network.PacketManager;
import com.github.standobyte.jojo.network.packets.fromclient.ClHamonStartMeditationPacket;
import com.github.standobyte.jojo.network.packets.fromclient.ClHamonWindowOpenedPacket;
import com.github.standobyte.jojo.network.packets.fromclient.ClHasInputPacket;
import com.github.standobyte.jojo.network.packets.fromclient.ClHeldActionTargetPacket;
import com.github.standobyte.jojo.network.packets.fromclient.ClOnLeapPacket;
import com.github.standobyte.jojo.network.packets.fromclient.ClOnStandDashPacket;
import com.github.standobyte.jojo.network.packets.fromclient.ClStopHeldActionPacket;
import com.github.standobyte.jojo.network.packets.fromclient.ClToggleStandManualControlPacket;
import com.github.standobyte.jojo.network.packets.fromclient.ClToggleStandSummonPacket;
import com.github.standobyte.jojo.power.IPower;
import com.github.standobyte.jojo.power.IPower.ActionType;
import com.github.standobyte.jojo.power.IPower.PowerClassification;
import com.github.standobyte.jojo.power.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.stand.IStandManifestation;
import com.github.standobyte.jojo.power.stand.IStandPower;
import com.github.standobyte.jojo.power.stand.type.NoManifestationStandType;
import com.github.standobyte.jojo.util.utils.JojoModUtil;
import com.github.standobyte.jojo.util.utils.MathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Util;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.InputEvent.ClickInputEvent;
import net.minecraftforge.client.event.InputEvent.MouseScrollEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class InputHandler {
    private static InputHandler instance = null;

    private Minecraft mc;
    private ActionsOverlayGui actionsOverlay;
    private IStandPower standPower;
    private INonStandPower nonStandPower;
    
    public RayTraceResult mouseTarget;

    private static final String MAIN_CATEGORY = new String("key.categories." + JojoMod.MOD_ID);
    private KeyBinding toggleStand;
    private KeyBinding standRemoteControl;
    public KeyBinding hamonSkillsWindow;

    private static final String HUD_CATEGORY = new String("key.categories." + JojoMod.MOD_ID + ".hud");
    public KeyBinding nonStandMode;
    public KeyBinding standMode;
    private KeyBinding scrollMode;
//    public KeyBinding editHotbars;
//    public KeyBinding actionQuickAccess;
    public KeyBinding disableHotbars;
    
    private KeyBinding attackHotbar;
    private KeyBinding abilityHotbar;
    private KeyBinding scrollAttack;
    private KeyBinding scrollAbility;

    private static final String HUD_ALTERNATIVE_CATEGORY = new String("key.categories." + JojoMod.MOD_ID + ".hud.alternative");
//    @Nullable
//    private KeyBinding[] attackSlots;
    @Nullable
    private KeyBinding deselectAttack;
//    @Nullable
//    private KeyBinding[] abilitySlots;
    @Nullable
    private KeyBinding deselectAbility;
    
    private int leftClickBlockDelay;
    
    public boolean hasInput;
    
    private boolean canLeap;
    
    private DoubleShiftDetector doubleShift = new DoubleShiftDetector();
    public boolean pressedDoubleShift = false;
    public boolean cancelingLiquidWalking = false;

    private InputHandler(Minecraft mc) {
        this.mc = mc;
    }

    public static void init(Minecraft mc) {
        if (instance == null) {
            instance = new InputHandler(mc);
            instance.registerKeyBindings();
            MinecraftForge.EVENT_BUS.register(instance);
        }
    }
    
    public static InputHandler getInstance() {
        return instance;
    }
    
    public void setActionsOverlay(ActionsOverlayGui instance) {
        this.actionsOverlay = instance;
    }
    
    public void registerKeyBindings() {
        ClientRegistry.registerKeyBinding(toggleStand = new KeyBinding(JojoMod.MOD_ID + ".key.toggle_stand", GLFW_KEY_M, MAIN_CATEGORY));
        ClientRegistry.registerKeyBinding(standRemoteControl = new KeyBinding(JojoMod.MOD_ID + ".key.stand_remote_control", GLFW_KEY_O, MAIN_CATEGORY));
        ClientRegistry.registerKeyBinding(hamonSkillsWindow = new KeyBinding(JojoMod.MOD_ID + ".key.hamon_skills_window", GLFW_KEY_H, MAIN_CATEGORY));
        
        ClientRegistry.registerKeyBinding(nonStandMode = new KeyBinding(JojoMod.MOD_ID + ".key.non_stand_mode", GLFW_KEY_J, HUD_CATEGORY));
        ClientRegistry.registerKeyBinding(standMode = new KeyBinding(JojoMod.MOD_ID + ".key.stand_mode", GLFW_KEY_K, HUD_CATEGORY));
//        ClientRegistry.registerKeyBinding(editHotbars = new KeyBinding(JojoMod.MOD_ID + ".key.edit_hud", GLFW_KEY_BACKSLASH, HUD_CATEGORY));
//        ClientRegistry.registerKeyBinding(actionQuickAccess = new KeyBinding(JojoMod.MOD_ID + ".key.quick_access", InputMappings.Type.MOUSE, GLFW_MOUSE_BUTTON_MIDDLE, HUD_CATEGORY));
        
        ClientRegistry.registerKeyBinding(attackHotbar = new KeyBinding(JojoMod.MOD_ID + ".key.attack_hotbar", GLFW_KEY_V, HUD_CATEGORY));
        ClientRegistry.registerKeyBinding(abilityHotbar = new KeyBinding(JojoMod.MOD_ID + ".key.ability_hotbar", GLFW_KEY_B, HUD_CATEGORY));
        ClientRegistry.registerKeyBinding(disableHotbars = new KeyBinding(JojoMod.MOD_ID + ".key.disable_hotbars", GLFW_KEY_LEFT_ALT, HUD_CATEGORY));
        
        ClientRegistry.registerKeyBinding(scrollMode = new KeyBinding(JojoMod.MOD_ID + ".key.scroll_mode", GLFW_KEY_UNKNOWN, HUD_ALTERNATIVE_CATEGORY));
        ClientRegistry.registerKeyBinding(scrollAttack = new KeyBinding(JojoMod.MOD_ID + ".key.scroll_attack", GLFW_KEY_UNKNOWN, HUD_ALTERNATIVE_CATEGORY));
        ClientRegistry.registerKeyBinding(scrollAbility = new KeyBinding(JojoMod.MOD_ID + ".key.scroll_ability", GLFW_KEY_UNKNOWN, HUD_ALTERNATIVE_CATEGORY));
        
//        if (JojoModConfig.CLIENT.slotHotkeys.get()) {
//            attackSlots = new KeyBinding[9];
//            abilitySlots = new KeyBinding[9];
//            for (int i = 0; i < 9; i++) {
//                ClientRegistry.registerKeyBinding(attackSlots[i] = new KeyBinding(JojoMod.MOD_ID + ".key.attack_" + (i + 1), GLFW_KEY_UNKNOWN, HUD_ALTERNATIVE_CATEGORY));
//                ClientRegistry.registerKeyBinding(abilitySlots[i] = new KeyBinding(JojoMod.MOD_ID + ".key.ability_" + (i + 1), GLFW_KEY_UNKNOWN, HUD_ALTERNATIVE_CATEGORY));
//            }
//            ClientRegistry.registerKeyBinding(deselectAttack = new KeyBinding(JojoMod.MOD_ID + ".key.deselect_attack", GLFW_KEY_UNKNOWN, HUD_ALTERNATIVE_CATEGORY));
//            ClientRegistry.registerKeyBinding(deselectAbility = new KeyBinding(JojoMod.MOD_ID + ".key.deselect_ability", GLFW_KEY_UNKNOWN, HUD_ALTERNATIVE_CATEGORY));
//        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onMouseScroll(MouseScrollEvent event) {
        if (standPower == null || nonStandPower == null || actionsOverlay == null) {
            return;
        }

        if (actionsOverlay.isActive() && !mc.player.isSpectator()) {
            boolean scrollAttack = attackHotbar.isDown();
            boolean scrollAbility = abilityHotbar.isDown();
            if (scrollAttack || scrollAbility) {
                if (scrollAttack) {
                    actionsOverlay.scrollAction(ActionType.ATTACK, event.getScrollDelta() > 0.0D);
                }
                if (scrollAbility) {
                    actionsOverlay.scrollAction(ActionType.ABILITY, event.getScrollDelta() > 0.0D);
                }
                event.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void handleKeyBindings(ClientTickEvent event) {
        if (mc.overlay != null || (mc.screen != null && !mc.screen.passEvents) || mc.level == null || standPower == null || nonStandPower == null || actionsOverlay == null || mc.player.isSpectator()) {
            return;
        }
        
        if (event.phase == TickEvent.Phase.START) {
            if (actionsOverlay.isActive()) {
                boolean chooseAttack = attackHotbar.isDown();
                boolean chooseAbility = abilityHotbar.isDown();
                actionsOverlay.setHotbarButtonsDows(chooseAttack, chooseAbility);
                actionsOverlay.setHotbarsEnabled(!disableHotbars.isDown());
                if (chooseAttack || chooseAbility) {
                    for (int i = 0; i < 9; i++) {
                        if (mc.options.keyHotbarSlots[i].consumeClick()) {
                            if (chooseAttack) {
                                actionsOverlay.selectAction(ActionType.ATTACK, i);
                            }
                            if (chooseAbility) {
                                actionsOverlay.selectAction(ActionType.ABILITY, i);
                            }
                        }
                    }
                }
//                else {
//                    if (attackSlots != null) {
//                        for (int i = 0; i < 9; i++) {
//                            if (attackSlots[i].consumeClick()) {
//                                actionsOverlay.selectAction(ActionType.ATTACK, i);
//                            }
//                        }
//                    }
//                    if (abilitySlots != null) {
//                        for (int i = 0; i < 9; i++) {
//                            if (abilitySlots[i].consumeClick()) {
//                                actionsOverlay.selectAction(ActionType.ABILITY, i);
//                            }
//                        }
//                    }
//                }
                
                if (scrollAttack.consumeClick()) {
                    actionsOverlay.scrollAction(ActionType.ATTACK, mc.player.isShiftKeyDown());
                }
                
                if (scrollAbility.consumeClick()) {
                    actionsOverlay.scrollAction(ActionType.ABILITY, mc.player.isShiftKeyDown());
                }

//                while (actionQuickAccess.consumeClick()) {
//                    handleMouseClickPowerHud(null, ActionKey.QUICK_ACCESS);
//                }
            }
            
            tickEffects();
        }
        else {
            pickMouseTarget();
            
            if (leftClickBlockDelay > 0) {
                leftClickBlockDelay--;
            }
            
            if (standMode.consumeClick()) {
                actionsOverlay.setMode(PowerClassification.STAND);
            }
            
            if (nonStandMode.consumeClick()) {
                INonStandPower.getNonStandPowerOptional(mc.player).ifPresent(power -> {
                    if (power.getType() == ModHamon.HAMON.get()) {
                        PacketManager.sendToServer(new ClHamonWindowOpenedPacket());
                    }
                });
//                actionsOverlay.setMode(PowerClassification.NON_STAND);
            }
            
            if (scrollMode.consumeClick()) {
                actionsOverlay.scrollMode();
            }
            

            if (toggleStand.consumeClick()) {
                if (standPower.getType() instanceof NoManifestationStandType) {
                    if (actionsOverlay.getCurrentMode() == PowerClassification.STAND) {
                        actionsOverlay.onStandUnsummon();
                    }
                    else {
                        actionsOverlay.onStandSummon();
                    }
                }
                else {
                    if (!standPower.isActive()) {
                        actionsOverlay.onStandSummon();
                    }
                    else {
                        actionsOverlay.onStandUnsummon();
                    }
                }
                PacketManager.sendToServer(new ClToggleStandSummonPacket());
            }
            
            if (standRemoteControl.consumeClick()) {
                PacketManager.sendToServer(new ClToggleStandManualControlPacket());
            }
            
            if (hamonSkillsWindow.consumeClick()) {
                if (nonStandPower.hasPower() && nonStandPower.getType() == ModHamon.HAMON.get()) {
                    if (mc.player.isShiftKeyDown()) {
                        PacketManager.sendToServer(new ClHamonStartMeditationPacket());
                    }
                    else {
                        ClientUtil.openHamonTeacherUi();
                    }
                }
                else {
                    mc.gui.handleChat(ChatType.GAME_INFO, new TranslationTextComponent(
                            nonStandPower.getTypeSpecificData(ModVampirism.VAMPIRISM.get())
                            .map(vampirism -> vampirism.isVampireHamonUser()).orElse(false) ? 
                                    "jojo.chat.message.no_hamon_vampire"
                                    : "jojo.chat.message.no_hamon"), Util.NIL_UUID);
                }
            }
            
//            if (editHotbars.consumeClick() && (standPower.hasPower() || nonStandPower.hasPower())) {
//                mc.setScreen(new HudLayoutEditingScreen());
//            }
            
            if (!mc.options.keyAttack.isDown()) {
                leftClickBlockDelay = 0;
            }
            
            checkHeldAction(standPower);
            checkHeldAction(nonStandPower);
        }
    }
    
    private void pickMouseTarget() {
        mouseTarget = mc.hitResult;
        if (actionsOverlay != null && actionsOverlay.getCurrentPower() != null) {
            IPower<?, ?> power = actionsOverlay.getCurrentPower();
            if (power.hasPower()) {
                mouseTarget = power.clientHitResult(mc.getCameraEntity() != null ? mc.getCameraEntity() : mc.player, mouseTarget);
            }
        }
    }
    
    private final Map<IPower<?, ?>, ActionKey> heldKeys = new HashMap<>();
    
    private enum ActionKey {
        ATTACK(ActionType.ATTACK) {
            @Override
            protected KeyBinding getKey(Minecraft mc, InputHandler modInput) { return mc.options.keyAttack; }
        },
        ABILITY(ActionType.ABILITY) {
            @Override
            protected KeyBinding getKey(Minecraft mc, InputHandler modInput) { return mc.options.keyUse; }
        },
        QUICK_ACCESS(null) {
            @Override
            protected KeyBinding getKey(Minecraft mc, InputHandler modInput) { return null; }
        };
        
        private final ActionType hotbar;
        
        private ActionKey(ActionType hotbar) {
            this.hotbar = hotbar;
        }
        
        protected abstract KeyBinding getKey(Minecraft mc, InputHandler modInput);
        
        @Nullable
        private ActionType getHotbar() {
            return hotbar;
        }
    }
    
    private void checkHeldAction(IPower<?, ?> power) {
        boolean keyHeld;
        if (heldKeys.containsKey(power)) {
            keyHeld = heldKeys.get(power).getKey(mc, this) != null && heldKeys.get(power).getKey(mc, this).isDown();
            if (!keyHeld) {
                heldKeys.remove(power);
            }
        }
        else {
            keyHeld = mc.options.keyAttack.isDown() || mc.options.keyUse.isDown() || mc.options.keyPickItem.isDown();
        }
        
        Action<?> heldAction = power.getHeldAction();
        if (heldAction != null) {
            if (!keyHeld) {
                stopHeldAction(power, power.getPowerClassification() == actionsOverlay.getCurrentMode());
            }
            else {
                PacketManager.sendToServer(ClHeldActionTargetPacket.withRayTraceResult(power.getPowerClassification(), mouseTarget));
            }
        }
    }
    
    public void stopHeldAction(IPower<?, ?> power, boolean shouldFire) {
        if (power.getHeldAction() != null) {
            power.stopHeldAction(shouldFire);
            PacketManager.sendToServer(new ClStopHeldActionPacket(power.getPowerClassification(), shouldFire));
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void modActionClick(ClickInputEvent event) {
        doubleShift.reset();
        
        if (mc.player.isSpectator() || event.getHand() == Hand.OFF_HAND || disableHotbars.isDown()) {
            return;
        }

        ActionKey key;
        if (event.isAttack()) {
            key = ActionKey.ATTACK;
        }
        else if (event.isUseItem()) {
            key = ActionKey.ABILITY;
        }
        else {
            return;
        }
        
        handleMouseClickPowerHud(event, key);
    }

    private void handleMouseClickPowerHud(@Nullable ClickInputEvent event, ActionKey key) {
        if (mc.player.isSpectator()) {
            return;
        }
        
        if (key == ActionKey.ATTACK && leftClickBlockDelay > 0) {
            event.setSwingHand(false);
        	event.setCanceled(true);
        	return;
        }

        IPower<?, ?> power = actionsOverlay.getCurrentPower();

        ActionType actionType = key.getHotbar();
        boolean actionClick = false;
        if (power != null) {
            if (key == ActionKey.QUICK_ACCESS) {
                actionClick = power.getActionsLayout().getQuickAccessAction() != null;
            }
            else {
                actionClick = !actionsOverlay.noActionSelected(actionType);
            }
        }
        
        if (!actionClick) {
        	if (handleStun(event)) {
        		event.setSwingHand(false);
        	}
            return;
        }
        
        if (power != null) {
            onHudClick(power, event, key);
        }
    }
    
    private <P extends IPower<P, ?>> void onHudClick(IPower<?, ?> p, @Nullable ClickInputEvent event, ActionKey key) {
        P power = (P) p;
        boolean leftClickedBlock = key == ActionKey.ATTACK && mc.hitResult.getType() == Type.BLOCK;
        boolean shift = mc.player.isShiftKeyDown();
        
        Pair<Action<P>, Boolean> click = null;
        if (key == ActionKey.QUICK_ACCESS) {
            click = actionsOverlay.onQuickAccessClick(power, shift);
        }
        else if (!(leftClickedBlock && leftClickBlockDelay > 0)) {
            click = actionsOverlay.onClick(power, key.getHotbar(), shift);
        }
        if (click != null && click.getRight()) {
            Action<P> action = click.getLeft();
            if (action != null) {
                if (event != null) {
                    event.setSwingHand(action.swingHand());
                    event.setCanceled(action.cancelsVanillaClick());
                }
                else if (action.swingHand()) {
                    mc.player.swing(Hand.MAIN_HAND);
                }
            }
            if (action.getHoldDurationMax(power) > 0) {
                heldKeys.put(power, key);
            }
            if (leftClickedBlock && leftClickBlockDelay <= 0) {
                leftClickBlockDelay = 4;
            }
        }
        else if (event != null) {
        	if (heldKeys.get(power) == key) {
                event.setSwingHand(false);
                event.setCanceled(true);
            }
            else if (handleStun(event)) {
                event.setSwingHand(false);
            }
        }
    }
    
    private boolean handleStun(InputEvent event) {
    	if (event != null && event.isCancelable() && (ModEffects.isStunned(mc.player) || !mc.player.canUpdate())) {
    		event.setCanceled(true);
    		return true;
    	}
    	return false;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onClickInput(ClickInputEvent event) {
        if (event.isAttack() && mc.hitResult.getType() == Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult) mc.hitResult).getEntity();
            if (entity == mc.player || entity instanceof ItemProjectileEntity) {
                event.setCanceled(true); // prevents kick for "Attempting to attack an invalid entity"
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void invertMovementInput(InputUpdateEvent event) {
        if (event.getPlayer().hasEffect(ModEffects.MISSHAPEN_LEGS.get())) {
            MovementInput input = event.getMovementInput();
            input.forwardImpulse *= -1;
            input.leftImpulse *= -1;
            
            boolean tmp = input.down;
            input.down = input.up;
            input.up = tmp;
            
            tmp = input.left;
            input.left = input.right;
            input.right = tmp;
            
            tmp = input.jumping;
            input.jumping = input.shiftKeyDown;
            input.shiftKeyDown = tmp;
        }
    }
    
    private boolean wasStunned = false;
    private double prevSensitivity = 0.5;
    @SubscribeEvent
    public void setMouseSensitivity(ClientTickEvent event) {
        if (mc.player == null) {
            if (mc.options.sensitivity == -1.0 / 3.0) {
                mc.options.sensitivity = prevSensitivity;
            }
            return;
        }
        
        if (ModEffects.isStunned(mc.player)) {
            if (!wasStunned) {
                prevSensitivity = mc.options.sensitivity;
                wasStunned = true;
            }
            mc.options.sensitivity = -1.0 / 3.0;
            return;
        }
        else if (wasStunned) {
            mc.options.sensitivity = prevSensitivity;
            wasStunned = false;
        }
        
        boolean invert = mc.player.hasEffect(ModEffects.MISSHAPEN_FACE.get());
        if (invert ^ mc.options.sensitivity < 0) {
            mc.options.sensitivity = -mc.options.sensitivity - 2.0 / 3.0;
        }
    }
    
    private boolean mouseButtonsSwapped = false;
    private InputMappings.Input lmbKey;
    private InputMappings.Input rmbKey;
    
    public void mouseButtonsInvertTick() {
        if (!mouseButtonsSwapped) {
            lmbKey = mc.options.keyAttack.getKey();
            rmbKey = mc.options.keyUse.getKey();
            mouseButtonsSwapped = true;
        }
        
        if (mc.options.keyAttack.getKey() != rmbKey || mc.options.keyUse.getKey() != lmbKey) {
            mc.options.keyAttack.setKey(rmbKey);
            mc.options.keyUse.setKey(lmbKey);
            KeyBinding.resetMapping();
        }
    }
    
    public void mouseButtonsInvertEnd() {
        if (mouseButtonsSwapped) {
            mc.options.keyAttack.setKey(lmbKey);
            mc.options.keyUse.setKey(rmbKey);
            mouseButtonsSwapped = false;
            KeyBinding.resetMapping();
        }
    }
    
    private void tickEffects() {
        if (mc.player != null && mc.player.hasEffect(ModEffects.MISSHAPEN_ARMS.get())) {
            mouseButtonsInvertTick();
        }
        else {
            mouseButtonsInvertEnd();
        }
    }
    
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onInputUpdate(InputUpdateEvent event) {
        MovementInput input = event.getMovementInput();
        
        boolean hasInput = input.up || input.down || input.left || input.right || input.jumping || input.shiftKeyDown;
        if (this.hasInput != hasInput) {
            PacketManager.sendToServer(new ClHasInputPacket(hasInput));
            this.hasInput = hasInput;
        }
        
        if (standPower != null && nonStandPower != null) {
            boolean actionSlowedDown = slowDownFromHeldAction(mc.player, input, standPower);
            actionSlowedDown = slowDownFromHeldAction(mc.player, input, nonStandPower) || actionSlowedDown;
            boolean standSlowedDown = slowDownFromStandEntity(mc.player, input);
            
            if (!mc.player.isPassenger()) {
                IPower<?, ?> power = actionsOverlay.getCurrentPower();
                if (power != null) {
                    if (power.canLeap() && !actionSlowedDown && !standSlowedDown) {
                    	boolean onGround = mc.player.isOnGround();
                    	boolean atWall = mc.player.horizontalCollision;
                    	
                    	boolean groundLeap = onGround && input.shiftKeyDown && input.jumping;
                    	// FIXME wall leap without pressing shift
                    	boolean wallLeap = atWall && !groundLeap && input.jumping &&
                    	        (input.shiftKeyDown || false);
                    	
                        if (groundLeap || wallLeap) {
                            float leapStrength = power.leapStrength();
                            if (leapStrength > 0) {
                                input.shiftKeyDown = false;
                                input.jumping = false;
                                PacketManager.sendToServer(new ClOnLeapPacket(power.getPowerClassification()));
                                if (groundLeap) {
                                	leap(mc.player, leapStrength);
                                }
                                else if (wallLeap) {
                                	wallLeap(mc.player, input, leapStrength);
                                }
                            }
                        }
//                        if (onGround && power.getPowerClassification() == PowerClassification.STAND) {
//                            leftDash.inputUpdate(input.left, input.right || input.down, mc.player);
//                            rightDash.inputUpdate(input.right, input.left || input.down, mc.player);
//                            backDash.inputUpdate(input.down, input.left || input.right, mc.player);
//                        }
                        canLeap = onGround || atWall;
                    }
                    else {
                    	canLeap = false;
                    }
                }
                
                if (nonStandPower != null && nonStandPower.getHeldAction() == ModHamon.HAMON_BREATH.get()) {
                    input.shiftKeyDown = false;
                    input.jumping = false;
                }
            }
        }
        else {
        	canLeap = false;
        }
        
        Entity vehicle = mc.player.getVehicle();
        if (vehicle instanceof LeavesGliderEntity) {
            ((LeavesGliderEntity) vehicle).setInput(input.left, input.right);
        }
        
        int shiftPress = doubleShift.inputUpdate(input);
        pressedDoubleShift = shiftPress == 2;
    }
    
    public boolean canPlayerLeap() {
    	return canLeap;
    }
    
    private boolean slowDownFromStandEntity(PlayerEntity player, MovementInput input) {
        IStandManifestation stand = standPower.getStandManifestation();
        if (stand instanceof StandEntity) {
            StandEntity standEntity = (StandEntity) stand;
            float slowDown = standEntity.getUserMovementFactor();
            if (slowDown < 1.0F) {
                input.leftImpulse *= slowDown;
                input.forwardImpulse *= slowDown;
                player.setSprinting(false);
                KeyBinding.set(mc.options.keySprint.getKey(), false);
                return true;
            }
        }
        return false;
    }
    
    private boolean slowDownFromHeldAction(PlayerEntity player, MovementInput input, IPower<?, ?> power) {
        Action<?> heldAction = power.getHeldAction();
        if (heldAction != null && heldAction.getHeldSlowDownFactor() < 1.0F) {
            input.leftImpulse *= heldAction.getHeldSlowDownFactor();
            input.forwardImpulse *= heldAction.getHeldSlowDownFactor();
            player.setSprinting(false);
            KeyBinding.set(mc.options.keySprint.getKey(), false);
            return true;
        }
        return false;
    }
    
    private void leap(ClientPlayerEntity player, float strength) {
        player.setOnGround(false);
        player.hasImpulse = true;
        Vector3d leap = Vector3d.directionFromRotation(Math.min(player.xRot, -30F), player.yRot).scale(strength);
        player.setDeltaMovement(leap.x, leap.y * 0.5, leap.z);
    }
    
    private void wallLeap(ClientPlayerEntity player, MovementInput input, float strength) {
        player.hasImpulse = true;
        Vector3d inputVec = new Vector3d(player.xxa, 0, player.zza)
                .yRot((-player.yRot) * MathUtil.DEG_TO_RAD);
        Vector3d collide = JojoModUtil.collide(player, inputVec);
        Vector3d leap = collide.subtract(inputVec).normalize().scale(strength);
        float leapYRot = (float) -MathHelper.atan2(leap.x, leap.z);
        leap = leap.yRot(leapYRot)
                .xRot(-MathHelper.clamp(player.xRot, -82.5F, -30F) * MathUtil.DEG_TO_RAD)
                .yRot(-leapYRot);
        player.setDeltaMovement(leap.x, leap.y * 0.5, leap.z);
    }

    private final DashTrigger leftDash = new DashTrigger(-90F);
    private final DashTrigger rightDash = new DashTrigger(90F);
    private final DashTrigger backDash = new DashTrigger(180F);
    
    private void dash(ClientPlayerEntity player, float yRot) {
        PacketManager.sendToServer(new ClOnStandDashPacket());
        player.setOnGround(false);
        player.hasImpulse = true;
        Vector3d dash = Vector3d.directionFromRotation(0, player.yRot + yRot).scale(0.5).add(0, 0.2, 0);
        player.setDeltaMovement(player.getDeltaMovement().add(dash));
    }
    
    
    
    public void updatePowersCache() {
        standPower = IStandPower.getPlayerStandPower(mc.player);
        nonStandPower = INonStandPower.getPlayerNonStandPower(mc.player);
        heldKeys.clear();
    }
    
    
    
    private class DashTrigger {
        private final float yRot;
        private int triggerTime;
        private boolean triggerGap;
        
        private DashTrigger(float yRot) {
            this.yRot = yRot;
        }
        
        private void inputUpdate(boolean keyPress, boolean anotherKeyPress, ClientPlayerEntity player) {
            if (anotherKeyPress) {
                triggerTime = 0;
                return;
            }
            if (triggerTime > 0) {
                triggerTime--;
            }
            if (keyPress) {
                if (triggerTime > 0 && triggerGap) {
                    dash(player, yRot);
                }
                triggerTime = 7;
            }
            triggerGap = !keyPress;
        }
    }
    
    
    
    private class DoubleShiftDetector {
        private MovementInput prevTickInput = null;
        private int shiftPresses = 0;
        private int triggerTime = 0;
        private boolean triggerGap = false;
        
        private int inputUpdate(MovementInput playerInput) {
            boolean isShiftPressed = playerInput.shiftKeyDown;
            boolean trigger = false;
            
            if (shiftPresses > 0 && (playerInput.jumping || !checkInputUpdate(prevTickInput, playerInput))) {
                reset();
            }
            
            if (triggerTime > 0) {
                if (--triggerTime == 0) {
                    reset();
                }
            }
            
            if (isShiftPressed) {
                trigger = triggerGap && (shiftPresses == 0 || triggerTime > 0);
                if (trigger) {
                    triggerTime = 7;
                    if (shiftPresses++ == 0) {
                        saveInputState(playerInput);
                    }
                }
            }
            
            triggerGap = !isShiftPressed;
            
            return trigger ? shiftPresses : 0;
        }
        
        private boolean checkInputUpdate(@Nullable MovementInput prevTick, MovementInput thisTick) {
            if (prevTick == null) {
                saveInputState(thisTick);
                return true;
            }
            
            return  prevTick.up == thisTick.up &&
                    prevTick.down == thisTick.down &&
                    prevTick.left == thisTick.left &&
                    prevTick.right == thisTick.right;
        }
        
        private void saveInputState(MovementInput input) {
            this.prevTickInput = new MovementInput();
            this.prevTickInput.up = input.up;
            this.prevTickInput.down = input.down;
            this.prevTickInput.left = input.left;
            this.prevTickInput.right = input.right;
        }
        
        private void reset() {
            shiftPresses = 0;
            triggerTime = 0;
        }
    }
    
    
    
    public enum MouseButton {
        LEFT,
        RIGHT,
        MIDDLE;
        
        public static MouseButton getButtonFromId(int id) {
            if (id >= 0 && id < MouseButton.values().length) {
                return MouseButton.values()[id];
            }
            return null;
        }
    }
}
