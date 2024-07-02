package com.github.standobyte.jojo.init;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.enchantment.VirusInhibitionEnchantment;
import com.github.standobyte.jojo.item.StandArrowItem;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, JojoMod.MOD_ID);
    
    public static final EnchantmentType STAND_ARROW = EnchantmentType.create("JOJO_STAND_ARROW", item -> item instanceof StandArrowItem);
    
    public static final RegistryObject<Enchantment> VIRUS_INHIBITION = ENCHANTMENTS.register("virus_inhibition", 
            () -> new VirusInhibitionEnchantment(Rarity.RARE, EquipmentSlotType.MAINHAND));

}
