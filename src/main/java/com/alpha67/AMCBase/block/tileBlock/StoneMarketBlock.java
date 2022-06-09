package com.alpha67.AMCBase.block.tileBlock;

import com.alpha67.AMCBase.container.StoneMarketContainer;
import com.alpha67.AMCBase.init.ModBlocks;
import com.alpha67.AMCBase.init.ModItems;
import com.alpha67.AMCBase.init.ModTileEntities;
import com.alpha67.AMCBase.tileentity.StoneMarketTile;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class StoneMarketBlock extends HorizontalBlock {

    int x;
    int y;
    int z;


    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final BooleanProperty EXTENDED = BlockStateProperties.EXTENDED;

    public StoneMarketBlock(Properties properties) {
        super(properties);
        //this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(EXTENDED, Boolean.valueOf(false)));
    }





    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos,
                                             PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();

        StoneMarketTile tile = (StoneMarketTile) worldIn.getTileEntity(pos);

        if(ModItems.CREDIT_CARD.get() == player.getHeldItemMainhand().getItem())
        {

            if (!tile.getTileData().getString("owner").contains("-"))
            {
                tile.getTileData().putString("owner", String.valueOf(player.getUniqueID()));
            }
        }

        else if (!worldIn.isRemote())
        {

            StoneMarketTile tileEntity = (StoneMarketTile) worldIn.getTileEntity(pos);
            String owner = tileEntity.getTileData().getString("owner");
            System.out.println(owner);
            System.out.println(player.getUniqueID().toString());

            if(owner.contains(String.valueOf(player.getUniqueID())) && worldIn.getBlockState(new BlockPos((int) x, (int) y+1, (int) z)).getBlock() == ModBlocks.ANTENNA.get()) {


                tileEntity.getTileData().putString("player", String.valueOf(player.getUniqueID()));

                if (tileEntity instanceof StoneMarketTile) {
                    INamedContainerProvider containerProvider = createContainerProvider(worldIn, pos);

                    NetworkHooks.openGui(((ServerPlayerEntity) player), containerProvider, tileEntity.getPos());
                } else {
                    throw new IllegalStateException("Our Container provider is missing!");
                }
            }

            else {
            }
        }

        else {
            //System.out.println("can't open GUI");
            player.sendMessage(new TextComponent("Message"));


        }
        return ActionResultType.SUCCESS;
    }

    private INamedContainerProvider createContainerProvider(World worldIn, BlockPos pos) {
        return new INamedContainerProvider() {
            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent("screen.amcmode.stone_market_block");
            }

            @Nullable
            @Override
            public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                return new StoneMarketContainer(i, worldIn, pos, playerInventory, playerEntity);
            }
        };
    }

    @Override
    public void tick(BlockState blockstate, ServerWorld world, BlockPos pos, Random random) {
        super.tick(blockstate, world, pos, random);

    }


    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.STONE_MARKET_TILE.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}
