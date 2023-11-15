package cc.altoya.companions;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;




public class SummonHorseCommand implements CommandExecutor
{
  // Create a HashMap to store the UUIDs of the players and their horse styles
  private final Map<UUID, Horse.Style> playerHorseStyles = new HashMap<>();
  private final Map<UUID, Horse.Color> playerHorseColors = new HashMap<>();

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    if (!command.getName().equalsIgnoreCase("summonHorse"))
    {
      return true;
    }

    if (!sender.hasPermission("companions.permission"))
    {
      return true;
    }

    if (!(sender instanceof Player))
    {
      return true;
    }

    Player player = (Player) sender;

    // Check if the player already has a companion summoned
    if (isCompanionSummoned(player))
    {
      player.sendMessage(ChatColor.RED + "You already have a companion summoned!");
      return true;
    }

    World world = player.getWorld();
    Location location = player.getLocation();

    Horse.Style style;
    Horse.Color color;

    // Check if the player has already summoned a companion
    if (playerHorseStyles.containsKey(player.getUniqueId()) && playerHorseColors.containsKey(player.getUniqueId()))
    {
      // Get the style and color of the horse
      style = playerHorseStyles.get(player.getUniqueId());
      color = playerHorseColors.get(player.getUniqueId());
    } else
    {
      // Generate a random style and color for the companion
      style = getRandomStyle();
      color = getRandomColor();

      // Store the UUID of the player and the style and color of the companion in the HashMap
      playerHorseStyles.put(player.getUniqueId(), style);
      playerHorseColors.put(player.getUniqueId(), color);
    }

    // Summon the horse with the style and color
    Horse horse = (Horse) world.spawnEntity(location, EntityType.HORSE);
    horse.setStyle(style);
    horse.setColor(color);
    setCompanionName(horse, args);
    horse.setOwner(player);
    horse.setTamed(true);
    horse.setJumpStrength(1.0);
    horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(.25);
    horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
    horse.setMaxHealth(20.0);
    horse.setHealth(20.0);

    player.sendMessage(ChatColor.YELLOW + "You have summoned your companion!");

    // Bukkit.getScheduler().scheduleSyncDelayedTask(Main, new Runnable() {
    //  @Override
    //  public void run() {
    //    if (horse.isValid()) {
    //      horse.remove();
    //      player.sendMessage(ChatColor.YELLOW + "Your summoned horse has despawned.");
    //    }
    //  }
    // }, 20 * 30);

    return true;
  }

  // Checks for duplicate companions
  private boolean isCompanionSummoned(Player player) {
    for (Entity entity : player.getWorld().getEntities()) {
      if (entity instanceof Horse horse && horse.getOwner() != null && horse.getOwner().equals(player)) {
        return true;
      }
    }
    return false;
  }

  // Generates a random style for the companion
  private Horse.Style getRandomStyle() {
    Horse.Style[] styles = Horse.Style.values();
    return styles[(int) (Math.random() * styles.length)];
  }

  // Generates a random color for the companion
  private Horse.Color getRandomColor() {
    Horse.Color[] colors = Horse.Color.values();
    return colors[(int) (Math.random() * colors.length)];
  }

  // Sets the name of the summoned companion
  private void setCompanionName(Horse horse, String[] args) {
    String name;
    if (args.length > 0) {
      name = ChatColor.GREEN + args[0];
    } else {
      name = ChatColor.GREEN + horse.getOwner().getName() + "'s Companion";
    }
    horse.setCustomName(name);
  }
}
