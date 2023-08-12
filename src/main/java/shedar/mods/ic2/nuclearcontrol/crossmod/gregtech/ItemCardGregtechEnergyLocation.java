package shedar.mods.ic2.nuclearcontrol.crossmod.gregtech;

import gregapi.tileentity.energy.ITileEntityEnergyDataCapacitor;
import gregapi.code.TagData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import shedar.mods.ic2.nuclearcontrol.api.CardState;
import shedar.mods.ic2.nuclearcontrol.api.ICardWrapper;
import shedar.mods.ic2.nuclearcontrol.api.PanelSetting;
import shedar.mods.ic2.nuclearcontrol.api.PanelString;
import shedar.mods.ic2.nuclearcontrol.items.ItemCardEnergySensorLocation;
import shedar.mods.ic2.nuclearcontrol.utils.LangHelper;
import shedar.mods.ic2.nuclearcontrol.utils.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.Collection;



public class ItemCardGregtechEnergyLocation extends ItemCardEnergySensorLocation {

	public static final int DISPLAY_ENERGY_TYPE = 16;
    public static final UUID CARD_TYPE = new UUID(0, 3);

    public ItemCardGregtechEnergyLocation() {
        this.setUnlocalizedName("GregTechEnergyCard");

    }

    @Override
         public CardState update(TileEntity panel, ICardWrapper card, int range) {
        ChunkCoordinates target = card.getTarget();
        if(target == null) return CardState.NO_TARGET;
        TileEntity tile = panel.getWorldObj().getTileEntity(target.posX, target.posY, target.posZ);
        //NCLog.fatal(tile instanceof IEnergyHandler);
        if(tile instanceof ITileEntityEnergyDataCapacitor) {
        	byte side=(byte)(int)card.getInt("side");
        	ITileEntityEnergyDataCapacitor iTileEntityEnergyDataCapacitor = (ITileEntityEnergyDataCapacitor) tile;
        	Collection<TagData> tagDatas= iTileEntityEnergyDataCapacitor.getEnergyCapacitorTypes(side);
        	for(TagData tagData:tagDatas) {
        		card.setString("energyT", tagData.getLocalisedNameShort()+"("+tagData.getLocalisedNameLong()+")");
        		card.setLong("energyL", iTileEntityEnergyDataCapacitor.getEnergyStored(tagData,side));
	            card.setLong("maxStorageL", iTileEntityEnergyDataCapacitor.getEnergyCapacity(tagData,side));
	            card.setLong("range_trigger_amount", iTileEntityEnergyDataCapacitor.getEnergyStored(tagData,side));
        	}
            return CardState.OK;
        } else {
            return CardState.NO_TARGET;
        }
    }

    @Override
    public CardState update(World world, ICardWrapper card, int range) {
        ChunkCoordinates target = card.getTarget();
        if(target == null) return CardState.NO_TARGET;
        TileEntity tile = world.getTileEntity(target.posX, target.posY, target.posZ);
        //NCLog.fatal(tile instanceof IEnergyHandler);
        if(tile instanceof ITileEntityEnergyDataCapacitor) {
        	byte side=(byte)(int)card.getInt("side");
        	ITileEntityEnergyDataCapacitor iTileEntityEnergyDataCapacitor = (ITileEntityEnergyDataCapacitor) tile;
        	Collection<TagData> tagDatas= iTileEntityEnergyDataCapacitor.getEnergyCapacitorTypes(side);
        	for(TagData tagData:tagDatas) {
        		card.setString("energyT", tagData.getLocalisedNameShort()+"("+tagData.getLocalisedNameLong()+")");
        		card.setLong("energyL", iTileEntityEnergyDataCapacitor.getEnergyStored(tagData,side));
	            card.setLong("maxStorageL", iTileEntityEnergyDataCapacitor.getEnergyCapacity(tagData,side));
	            card.setLong("range_trigger_amount", iTileEntityEnergyDataCapacitor.getEnergyStored(tagData,side));
        	}
            return CardState.OK;
        } else {
            return CardState.NO_TARGET;
        }
    }

    @Override
    public UUID getCardType() {
        return CARD_TYPE;
    }


    @Override
    public List<PanelString> getStringData(int displaySettings,
                                           ICardWrapper card, boolean showLabels) {
        List<PanelString> result = new LinkedList<PanelString>();
        PanelString line;

        double energy = card.getDouble("energyL");
        double storage = card.getDouble("maxStorageL");
        String energyType=card.getString("energyT");

        if ((displaySettings & DISPLAY_ENERGY) > 0) {
            line = new PanelString();
            line.textLeft = StringUtils.getFormatted("msg.nc.InfoPanelEnergy", energy, showLabels);
            result.add(line);
        }
        if ((displaySettings & DISPLAY_FREE) > 0) {
            line = new PanelString();
            line.textLeft = StringUtils.getFormatted(
            		"msg.nc.InfoPanelEnergyFree", storage - energy, showLabels);
            result.add(line);
        }
        if ((displaySettings & DISPLAY_STORAGE) > 0) {
            line = new PanelString();
            line.textLeft = StringUtils.getFormatted(
            		"msg.nc.InfoPanelEnergyStorage", storage, showLabels);
            result.add(line);
        }
        if ((displaySettings & DISPLAY_PERCENTAGE) > 0) {
            line = new PanelString();
            line.textLeft = StringUtils.getFormatted(
            		"msg.nc.InfoPanelEnergyPercentage", storage == 0 ? 100 : (energy * 100 / storage), showLabels);
            result.add(line);
        }
        if ((displaySettings & DISPLAY_ENERGY_TYPE) > 0) {
            line = new PanelString();
            line.textLeft = StringUtils.getFormatted(
            		"msg.nc.InfoPanelEnergyType",energyType, showLabels);
            result.add(line);
        }
        
        return result;
    }

    @Override
	public List<PanelSetting> getSettingsList() {
		List<PanelSetting> result = new ArrayList<PanelSetting>(3);
		result.add(new PanelSetting(LangHelper
				.translate("msg.nc.cbInfoPanelEnergyCurrent"), DISPLAY_ENERGY,
				CARD_TYPE));
		result.add(new PanelSetting(LangHelper
				.translate("msg.nc.cbInfoPanelEnergyStorage"), DISPLAY_STORAGE,
				CARD_TYPE));
		result.add(new PanelSetting(LangHelper
				.translate("msg.nc.cbInfoPanelEnergyFree"), DISPLAY_FREE,
				CARD_TYPE));
		result.add(new PanelSetting(LangHelper
				.translate("msg.nc.cbInfoPanelEnergyPercentage"),
				DISPLAY_PERCENTAGE, CARD_TYPE));
		result.add(new PanelSetting(LangHelper
				.translate("msg.nc.cbInfoPanelEnergyType"),
				DISPLAY_ENERGY_TYPE, CARD_TYPE));
		return result;
	}
}
