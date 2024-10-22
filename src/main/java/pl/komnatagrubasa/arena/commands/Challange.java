package pl.komnatagrubasa.arena.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.komnatagrubasa.arena.Arena;
import pl.komnatagrubasa.arena.Fight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Challange implements CommandExecutor, TabExecutor {

    Fight fight = new Fight();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if((commandSender instanceof Player)) {
            Player player1 = ((Player) commandSender).getPlayer();
            Player player2 = Bukkit.getPlayer(strings[0]);

            if(!Arena.gameOngoing && player2 instanceof Player && !player1.equals(player2) && strings.length == 2 && !player2.isDead() && Arena.kits.contains(strings[1])) {
                fight.StartGame(player1, player2, strings[1]);
            } else {
                commandSender.sendMessage("§c§lcos poszlo nie tak");
                return false;
            }

        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length == 2) {
            return Arena.kits;
        } else {
            return null;
        }
    }
}
