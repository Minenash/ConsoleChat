package com.minenash.console_chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ConsoleChat
  extends JavaPlugin
{
  String user = "Console";
  
  public void onEnable()
  {
    getConfig().options().copyDefaults(true);
    saveConfig();
  }
  
  public void onDisable() {}
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if ((cmd.getName().equalsIgnoreCase("chat")) && (args[0].equalsIgnoreCase("reload")))
    {
      reloadConfig();
      sender.sendMessage(ChatColor.GREEN + "Reload Complete");
      return true;
    }
    if ((sender instanceof ConsoleCommandSender))
    {
      if (cmd.getName().equalsIgnoreCase("chat"))
      {
        String user = this.user;
        String msg = "";
        int i = 0;
        if (Bukkit.getPlayer(args[0]) != null)
        {
          user = Bukkit.getPlayer(args[0]).getDisplayName();
          i++;
        }
        for (; i < args.length; i++) {
          msg = msg + args[i] + " ";
        }
        Bukkit.broadcastMessage(makeMsg(user, msg));
        return true;
      }
      if (cmd.getName().equalsIgnoreCase("setplayer"))
      {
        this.user = (Bukkit.getPlayer(args[0]) != null ? (this.user = Bukkit.getPlayer(args[0]).getDisplayName()) : args[0]);
        sender.sendMessage(ChatColor.GREEN + "User set to \"" + this.user + "\".");
        return true;
      }
    }
    if ((sender instanceof Player))
    {
      sender.sendMessage(ChatColor.RED + "You must be on the Console");
      return true;
    }
    return false;
  }
  
  public String makeMsg(String user, String msg)
  {
    String out = "";
    if (getConfig().getBoolean("chat-format.remove-rank-prefix")) {
      user = user.replaceAll("\\[.*\\]", "");
    }
    out = out + getConfig().getString("chat-format.chat-display");
    out = out.replaceAll("\\$prefix\\$", getConfig().getString("chat-format.prefix"));
    out = out.replaceAll("\\$player\\$", user);
    out = out.replaceAll("\\$msg\\$", msg);
    out = ChatColor.translateAlternateColorCodes('&', out);
    return out;
  }
}
