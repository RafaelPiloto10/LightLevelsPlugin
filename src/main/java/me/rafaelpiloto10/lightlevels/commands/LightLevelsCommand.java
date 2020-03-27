package me.rafaelpiloto10.lightlevels.commands;

import me.rafaelpiloto10.lightlevels.LightLevels;
import me.rafaelpiloto10.lightlevels.utils.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;

public class LightLevelsCommand implements TabExecutor {

    private LightLevels plugin;

    public LightLevelsCommand(LightLevels plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("lightlevels").setExecutor(this);
        this.plugin.getCommand("lightlevels").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player && sender.hasPermission("lightlevels.use")) {
            Player p = (Player) sender;
            Block startBlock = p.getWorld().getBlockAt(p.getLocation());
            List<Block> blocks = Utils.getTopBlocksInRadius(startBlock, 8);
            List<Block> low_level_blocks = new ArrayList<>();

            if (args.length == 0) {
                p.sendMessage(Utils.chat(plugin.getConfig().getString("error-no-toggle")));
            } else if (args[0].equalsIgnoreCase("see")) {
                int light_level = plugin.getConfig().getInt("default-level");

                if (args.length > 1) {
                    try {
                        light_level = Integer.parseInt(args[1]);
                    } catch (NumberFormatException ignored) {
                    }
                }

                for (Block block : blocks) {
                    if (Utils.getBlockLightLevel(block) < light_level) {
                        // Mobs can spawn on this block
                        low_level_blocks.add(block);
                    }
                }

                for (Block block : low_level_blocks) {
                    ArmorStand armorStand = block.getWorld().spawn(block.getLocation().add(0, 1, 0), ArmorStand.class);
                    armorStand.setGlowing(true);
                }
                if (low_level_blocks.size() > 0) {
                    p.sendMessage(Utils.chat(plugin.getConfig().getString("success-see")));
                } else {
                    p.sendMessage(Utils.chat(plugin.getConfig().getString("see-no-low-light")));
                }

            } else if (args[0].equalsIgnoreCase("hide")) {
                for (Entity entity : startBlock.getWorld().getNearbyEntities(startBlock.getLocation(), 10, 10, 10, (e) -> e.getType() == EntityType.ARMOR_STAND)) {
                    ArmorStand armorStand = (ArmorStand) entity;
                    armorStand.remove();
                }
                p.sendMessage(Utils.chat(plugin.getConfig().getString("success-hide")));
            } else {
                p.sendMessage(Utils.chat(plugin.getConfig().getString("error-no-toggle")));
            }

        } else {
            sender.sendMessage(Utils.chat("&cOnly players can run this command!"));
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("see", "hide");
        } else {
            return null;
        }
    }
}
