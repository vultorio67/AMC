package com.alpha67.AMCBase.network;

import com.alpha67.AMCBase.tileentity.StoneMarketTile;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class messageDisplay {

 //   private final String message;


   public messageDisplay(PacketBuffer buf) {
        //message = buf.writ();
    }

    public static void toBytes(messageDisplay md,PacketBuffer buf) {
       // buf.writeU;
    }


    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
           // BlockPos pos = new BlockPos(x, y, z);
          //  StoneMarketTile te = (StoneMarketTile) player.getServerWorld().getTileEntity(pos);


            //te.craftTheItem();


        ctx.get().setPacketHandled(true);
        });
    }
}