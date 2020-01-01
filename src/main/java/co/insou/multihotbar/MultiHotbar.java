package co.insou.multihotbar;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MultiHotbar extends JavaPlugin implements Listener {

    private final Map<UUID, HotbarState> states = new HashMap<>();

    @Override
    public void onEnable()
    {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("multihotbar").setExecutor(this);
    }

    @EventHandler
    public void on(PlayerItemHeldEvent event)
    {
        if (!event.getPlayer().isSneaking())
        {
            return;
        }

        HotbarState state = this.getState(event.getPlayer());

        if (!state.isEnabled())
        {
            return;
        }

        state.switchTo(event.getNewSlot());
        event.setCancelled(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
            return false;
        }

        Player player = (Player) sender;
        HotbarState state = this.getState(player);

        state.setEnabled(!state.isEnabled());

        if (state.isEnabled())
        {
            player.sendMessage(ChatColor.GREEN + "MultiHotbar has been enabled.");
        }
        else
        {
            player.sendMessage(ChatColor.RED + "MultiHotbar has been disabled.");
        }

        return false;
    }

    private HotbarState getState(Player player)
    {
        return this.states.computeIfAbsent(player.getUniqueId(), HotbarState::new);
    }

}
