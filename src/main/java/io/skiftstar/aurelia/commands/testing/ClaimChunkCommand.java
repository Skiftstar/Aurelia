package io.skiftstar.aurelia.commands.testing;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.skiftstar.aurelia.Aurelia;
import io.skiftstar.aurelia.data.player.PlayerData;
import io.skiftstar.aurelia.world.PlayerWorldUtil;

public class ClaimChunkCommand implements CommandExecutor {
    
    public ClaimChunkCommand() {
        Aurelia.getInstance().getCommand("claimchunk").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command arg1, @NotNull String arg2,
            @NotNull String[] args) {
        
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }

        Player player = (Player) sender;
        PlayerData data = PlayerData.getPlayerData(player.getUniqueId());
        
        if (args.length < 2) {
            player.sendMessage("Usage: /claimchunk <x> <z>");
            return false;
        }

        int x, z;
        try {
            x = Integer.parseInt(args[0]);
            z = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage("Invalid coordinates");
            return false;
        }

        Chunk chunk = player.getWorld().getChunkAt(x, z);

        PlayerWorldUtil.claimChunk(data, chunk);
        return true;
    }
}
