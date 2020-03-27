package me.rafaelpiloto10.lightlevels.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<Block> getTopBlocksInRadius(Block start, int radius){

        if (radius < 0) {
            return new ArrayList<Block>(0);
        }

        int iterations = (radius * 2) + 1;
        List<Block> blocks = new ArrayList<Block>(iterations * iterations * iterations);
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if(start.getRelative(x, y,z).getRelative(BlockFace.UP).isEmpty() && !start.getRelative(x, y, z).isEmpty()){
                        blocks.add(start.getRelative(x, y, z));
                    }
                }
            }
        }
        return blocks;
    }

    public static int getBlockLightLevel(Block block){
        return block.getRelative(BlockFace.UP).getLightLevel();
    }


    public static String chat(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }


}
