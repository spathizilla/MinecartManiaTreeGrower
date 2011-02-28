package com.spathi.minecartmaniatreegrower;


import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;

import com.afforess.minecartmaniacore.MinecartManiaCore;
import com.afforess.minecartmaniacore.MinecartManiaInventory;
import com.afforess.minecartmaniacore.MinecartManiaStorageCart;
import com.afforess.minecartmaniacore.MinecartManiaWorld;

public class StorageMinecartTree {
	public static Logger log;

	public static void doAutoReTree(MinecartManiaStorageCart minecart) {
		log = Logger.getLogger("Minecraft");

		if(minecart.getDataValue("AutoReTree") == null) {
			return;
		}

		if (MinecartManiaWorld.getIntValue(MinecartManiaWorld.getConfigurationValue("Nearby Collection Range")) < 1) {
			return;
		}

		if(minecart.getDataValue("AutoReTree") != null) {
			Location loc = minecart.minecart.getLocation().clone();
			int range = minecart.getEntityDetectionRange();
			for (int dx = -(range); dx <= range; dx++){
				for (int dy = -(range); dy <= range; dy++){
					for (int dz = -(range); dz <= range; dz++){
						//Setup data
						int x = loc.getBlockX() + dx;
						int y = loc.getBlockY() + dy;
						int z = loc.getBlockZ() + dz;
						World w = minecart.minecart.getWorld();

						int id = MinecartManiaWorld.getBlockIdAt(w, x, y, z);

						if (minecart.getDataValue("AutoReTree") != null) {
							if(id == Material.SAPLING.getId()) {

								if (minecart.removeItem(Material.INK_SACK.getId(), 1, (short)15))  {
									// Remove 1 unit of bonemeal and try to dump a tree

									int rand = ((new Random()).nextInt(5));
									TreeType t = null;
									switch (rand) {
									case 0: t = TreeType.BIG_TREE; break;
									case 1: t = TreeType.BIRCH; break;
									case 2: t = TreeType.REDWOOD; break;
									case 3: t = TreeType.TALL_REDWOOD; break;
									case 4: t = TreeType.TREE; break;
									//default: t = TreeType.TREE; 
									}
									MinecartManiaWorld.setBlockAt(w, 0, x, y, z);
									w.generateTree(new Location(w, x, y, z), t);
								}
							}
						}
					}
				}
			}
		}
	}
}
