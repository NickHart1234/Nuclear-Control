package shedar.mods.ic2.nuclearcontrol.crossmod.gregtech;

import gregapi.tileentity.energy.ITileEntityEnergyDataCapacitor;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import gregapi.code.TagData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import shedar.mods.ic2.nuclearcontrol.crossmod.EnergyStorageData;

import shedar.mods.ic2.nuclearcontrol.utils.NCLog;


public class CrossGregtech{

	public static Item GregtechSensorCard;

    public static void intergrateTE(){
    	GregtechSensorCard = new ItemCardGregtechEnergyLocation();
        GameRegistry.registerItem(GregtechSensorCard, "GregtechSensorCard");
    }

   
	
	
	public static EnergyStorageData getStorageData(TileEntity target) {
		if (target instanceof ITileEntityEnergyDataCapacitor) {
			ITileEntityEnergyDataCapacitor storage = (ITileEntityEnergyDataCapacitor) target;
			
			EnergyStorageData result = new EnergyStorageData();
			result.capacity = 0;
			result.stored = 0;
			result.units = EnergyStorageData.UNITS_GT;
			result.type = EnergyStorageData.TARGET_TYPE_GT;
			return result;
		}
		return null;
	}


}