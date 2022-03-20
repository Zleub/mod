package com.example.examplemod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.example.examplemod.ExampleMod.MAKO;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
class FluidMako extends ForgeFlowingFluid {
    public static final ResourceLocation still_mako = new ResourceLocation("examplemod:block/mako_still");
    public static final ResourceLocation flowing_mako = new ResourceLocation("examplemod:block/mako_flowing");
    public static final ResourceLocation bucket_mako = new ResourceLocation("examplemod:item/mako_bucket");
    public static final ResourceLocation block_mako = new ResourceLocation("examplemod:mako");
    public static final ResourceLocation blockitem_mako = new ResourceLocation("examplemod:item/mako");

    public static final RegistryObject<Fluid> STILL_MAKO = RegistryObject.of(still_mako, ForgeRegistries.FLUIDS);
    public static final RegistryObject<Fluid> FLOWING_MAKO = RegistryObject.of(flowing_mako, ForgeRegistries.FLUIDS);
    public static final RegistryObject<Item> BUCKET_MAKO = RegistryObject.of(bucket_mako, ForgeRegistries.ITEMS);
    public static final RegistryObject<Block> BLOCK_MAKO = RegistryObject.of(block_mako, ForgeRegistries.BLOCKS);
    public static final RegistryObject<Item> BLOCKITEM_MAKO = RegistryObject.of(blockitem_mako, ForgeRegistries.ITEMS);

    public static final FlowingFluid MAKO_SOURCE = new FluidMako.Source();
    public static final FlowingFluid MAKO_FLOWING = new FluidMako.Flowing();
    public static final Item MAKO_BUCKET = new BucketItem(MAKO_SOURCE, (new Item.Properties()).tab(MAKO));
    public static final LiquidBlock MAKO_LIQUID = new LiquidBlock(MAKO_SOURCE, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops());

    protected FluidMako() {
        super( new ForgeFlowingFluid.Properties(
                STILL_MAKO,
                FLOWING_MAKO,
                FluidAttributes.builder(
                        STILL_MAKO.getId(),
                        FLOWING_MAKO.getId()
                ).density(10).viscosity(10)
        ).bucket(() -> MAKO_BUCKET).block(() -> MAKO_LIQUID) );
    }

    @Override
    public Item getBucket() {
        return MAKO_BUCKET;
    }

    @Override
    public boolean isSource(FluidState p_76140_) {
        return false;
    }

    @Override
    public Fluid getSource() {
        return MAKO_SOURCE;
    }

    @Override
    public int getAmount(FluidState p_164509_) {
        return 0;
    }

    @Override
    public Fluid getFlowing() {
        return MAKO_FLOWING;
    }

    public static class Flowing extends FluidMako {
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> p_76260_) {
            super.createFluidStateDefinition(p_76260_);
            p_76260_.add(LEVEL);
        }

        public int getAmount(FluidState p_76264_) {
            return p_76264_.getValue(LEVEL);
        }

        public boolean isSource(FluidState p_76262_) {
            return false;
        }
    }

    public static class Source extends FluidMako {
        public int getAmount(FluidState p_76269_) {
            return 8;
        }

        public boolean isSource(FluidState p_76267_) {
            return true;
        }
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class Registration {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            blockRegistryEvent.getRegistry().register(MAKO_LIQUID.setRegistryName(BLOCK_MAKO.getId()));
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            itemRegistryEvent.getRegistry().register(
                    new BlockItem(MAKO_LIQUID, new Item.Properties().tab(MAKO))
                    .setRegistryName(BLOCKITEM_MAKO.getId()) );
            itemRegistryEvent.getRegistry().register(MAKO_BUCKET.setRegistryName(BUCKET_MAKO.getId()));
        }

        @SubscribeEvent
        public static void onFluidRegistry(final RegistryEvent.Register<Fluid> fluidRegistryEvent) {
            fluidRegistryEvent.getRegistry().register(MAKO_SOURCE.setRegistryName(STILL_MAKO.getId()));
            fluidRegistryEvent.getRegistry().register(MAKO_FLOWING.setRegistryName(FLOWING_MAKO.getId()));

        }
    }
}