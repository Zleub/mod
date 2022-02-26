package com.example.examplemod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.example.examplemod.ExampleMod.MAKO;

class FluidMako extends ForgeFlowingFluid {
    public static final ResourceLocation still_mako = new ResourceLocation("examplemod:block/mako_still");
    public static final ResourceLocation flowing_mako = new ResourceLocation("examplemod:block/mako_flowing");

    public static final RegistryObject<Fluid> STILL_MAKO = RegistryObject.of(still_mako, ForgeRegistries.FLUIDS);
    public static final RegistryObject<Fluid> FLOWING_MAKO = RegistryObject.of(flowing_mako, ForgeRegistries.FLUIDS);

    public static final FluidAttributes.Builder attributesBuilder = FluidAttributes.builder(
            new ResourceLocation("examplemod", "block/mako_still"),
            new ResourceLocation("exemplemod", "block/mako_flowing")
    ).density(1024).viscosity(1024);

    public static final ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(STILL_MAKO, FLOWING_MAKO, attributesBuilder);

    public static final Item bucket = new BucketItem(STILL_MAKO, new Item.Properties().tab(MAKO)).setRegistryName("examplemod:mako_bucket");

    protected FluidMako() {
        super( properties );
    }

    @Override
    public boolean isSource(FluidState p_76140_) {
        return true;
    }

    @Override
    public int getAmount(FluidState p_164509_) {
        return 0;
    }

    @Override
    public Item getBucket() {
        return bucket;
    }

    public static void onFluidRegistry(final RegistryEvent.Register<Fluid> fluidRegistryEvent) {
        ExampleMod.LOGGER.info("onFluidRegister");
        // set up attributes
        FluidAttributes.Builder attributesBuilder = FluidAttributes.builder(
                new ResourceLocation("examplemod", "block/mako_still"),
                new ResourceLocation("exemplemod", "block/mako_flowing")
        ).density(1024).viscosity(1024);
        ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(
                STILL_MAKO, FLOWING_MAKO, attributesBuilder
        ).bucket(() -> Items.MILK_BUCKET);
        // register fluids
        fluidRegistryEvent.getRegistry().register(new ForgeFlowingFluid.Source(properties).setRegistryName(STILL_MAKO.getId()));
        fluidRegistryEvent.getRegistry().register(new ForgeFlowingFluid.Flowing(properties).setRegistryName(FLOWING_MAKO.getId()));

    }
}