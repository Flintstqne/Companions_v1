package cc.altoya.companions;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class NoHorseDropsListener implements Listener {

  @EventHandler
  public void onEntityDeath(EntityDeathEvent event)
  {
    if (event.getEntity() instanceof Horse)
    {
      event.getDrops().clear();
    }
  }
}


