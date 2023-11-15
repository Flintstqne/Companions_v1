package cc.altoya.companions;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class summonHorseCommand implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (command.getName().equalsIgnoreCase("summonHorse")) {
      if (sender.hasPermission("companions.permission")) {
        // if(args.length != 1 && args.length != 2) return true;
        if(sender instanceof Player)
        {
          Player p = (Player) sender;
          World w = p.getWorld();
          Location loc = p.getLocation();
          Horse horse = (Horse) w.spawnEntity(loc, EntityType.HORSE);
          horse.setCustomName(ChatColor.GREEN + "John Doe");
          horse.setOwner(p);
          horse.setTamed(true);
          horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
          horse.setJumpStrength(1.0);
          horse.getAttribute( Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(1.0);
          horse.setMaxHealth(20.0);
          horse.setHealth(20.0);

          p.sendMessage(ChatColor.YELLOW + "You have summoned a horse!");

          return true;
        }

      }
    }
    return true;
  }
}
