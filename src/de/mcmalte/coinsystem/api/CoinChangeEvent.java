package de.mcmalte.coinsystem.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CoinChangeEvent
  extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private Player player;
  
  public CoinChangeEvent(Player p){
	  this.player = p;
  }
  
  public Player getPlayer(){
	  return player;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
}
