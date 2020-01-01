package co.insou.multihotbar;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HotbarState {

    private final UUID uuid;
    private final Map<Integer, Map<Integer, ItemStack>> items = new HashMap<>();

    private int currSlot;
    private boolean enabled;

    public HotbarState(UUID uuid)
    {
        this.uuid = uuid;

        this.currSlot = 0;
        this.enabled = false;
    }

    public void switchTo(int slot)
    {
        if (slot == this.currSlot)
        {
            return;
        }

        Player player = Bukkit.getPlayer(this.uuid);
        Map<Integer, ItemStack> oldHotbar = this.items.computeIfAbsent(this.currSlot, x -> new HashMap<>());
        Map<Integer, ItemStack> newHotbar = this.items.computeIfAbsent(slot, x -> new HashMap<>());

        for (int i = 0; i < 9; i++)
        {
            oldHotbar.put(i, player.getInventory().getItem(i));

            ItemStack newItem = newHotbar.computeIfAbsent(i, x -> new ItemStack(Material.AIR));
            player.getInventory().setItem(i, newItem);
        }

        this.currSlot = slot;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public boolean isEnabled()
    {
        return this.enabled;
    }

}
