package erogenousbeef.bigreactors.net.message;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import erogenousbeef.bigreactors.common.multiblock.MultiblockReactor;
import erogenousbeef.bigreactors.common.multiblock.tileentity.TileEntityReactorPart;
import io.netty.buffer.ByteBuf;

public class ReactorWasteEjectionSettingMessage implements IMessage, IMessageHandler<ReactorWasteEjectionSettingMessage, IMessage> {
    private int x, y, z, newSetting;

    public ReactorWasteEjectionSettingMessage(int x, int y, int z, int newSetting) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.newSetting = newSetting;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        newSetting = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(newSetting);
    }

    @Override
    public IMessage onMessage(ReactorWasteEjectionSettingMessage message, MessageContext ctx) {
        TileEntity te = FMLClientHandler.instance().getWorldClient().getTileEntity(x, y, z);
        if(te != null && te instanceof TileEntityReactorPart) {
            ((TileEntityReactorPart)te).getReactorController().setWasteEjection(MultiblockReactor.WasteEjectionSetting.values()[newSetting]);
        }
        return null;
    }
}
