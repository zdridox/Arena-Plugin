package pl.komnatagrubasa.arena;

import org.bukkit.*;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class Fight implements Listener {

    private org.bukkit.Location spawn1, spawn2;
    private static Player p1, p2;

    public void StartGame(Player player1, Player player2, String kit) {
        Arena.GetInstance().getServer().broadcastMessage("§b" + player1.getName() + "§4§l VS §r§b" + player2.getName());
        World world = player1.getWorld();
        spawn1 = new Location(world, 8.5, -60, .5, 0, 0);
        spawn2 = new Location(world, 8.5, -60, 16.5, 180, 0);
        Arena.gameOngoing = true;
        setupPlayer(player1, 1, kit);
        setupPlayer(player2, 2, kit);
    }

    @EventHandler
    void onPlayerDieEvent(PlayerDeathEvent event) {
        if(event.getPlayer().equals(p1)) {
            endGame(p2);
        }
        if(event.getPlayer().equals(p2)) {
            endGame(p1);
        }
    }

    @EventHandler
    void onPlayerRespawn(PlayerRespawnEvent event) {

        Bukkit.getScheduler().runTaskLater(Arena.GetInstance(), () -> {
            event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), 8.5, -54, 8.5, 90, 0));
        }, 5L);
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLater(Arena.GetInstance(), () -> {
            event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), 8.5, -54, 8.5, 90, 0));
        }, 5L);
        event.getPlayer().sendMessage("§lkomenda na 1v1: §c§l/wyzwij §4§l[gracz] [kit]");
    }

    @EventHandler
    void playerDisconnect(PlayerQuitEvent event) {
        if(event.getPlayer().equals(p1)) {
            endGame(p2);
        }
        if(event.getPlayer().equals(p2)) {
            endGame(p1);
        }
    }

    @EventHandler
    void playerGetDamage(EntityDamageByEntityEvent event) {
        //do re-napisania
    }

    @EventHandler
    void playerBreakBlock(BlockBreakEvent event) {
        if(!event.getPlayer().isOp()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    void playerInteractEvent(PlayerInteractEntityEvent event) { // nie działa
        if(event.getRightClicked() instanceof ItemFrame && !event.getPlayer().isOp()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    void playerInteractArmorStand(PlayerArmorStandManipulateEvent event) {
        if(!event.getPlayer().isOp()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    void projectileHitevent(ProjectileHitEvent event) {
        if(!(event.getHitEntity() instanceof Player)) {
            event.getEntity().remove();
        }
    }

    @EventHandler
    void playerDropItem(PlayerDropItemEvent event) {
        if(!event.getPlayer().isOp()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    void playerMove(PlayerMoveEvent event) {
        if(!event.getPlayer().equals(p1) && !event.getPlayer().equals(p2)) {
            event.getPlayer().setFoodLevel(20);
            event.getPlayer().setSaturation(20);
            event.getPlayer().setHealth(20);
        }
    }

    void endGame(Player winner) {
        Arena.GetInstance().getServer().broadcastMessage("§b" + winner.getName() + "(§c§l" + (int) Math.round(winner.getHealth()) + "hp§r)" + " §r§6§lwygrywa!");
        winner.setHealth(20);
        winner.setFoodLevel(20);
        winner.setSaturation(20);
        p1.getInventory().clear();
        p2.getInventory().clear();
        p1.setGameMode(GameMode.ADVENTURE);
        p2.setGameMode(GameMode.ADVENTURE);
        for (PotionEffect effect : p1.getActivePotionEffects()) {
            p1.removePotionEffect(effect.getType());
        }
        for (PotionEffect effect : p2.getActivePotionEffects()) {
            p2.removePotionEffect(effect.getType());
        }
        p1 = null;
        p2 = null;
        Bukkit.getScheduler().runTaskLater(Arena.GetInstance(), () -> {
            winner.teleport(new Location(winner.getWorld(), 8.5, -54, 8.5, 90, 0));
            Arena.gameOngoing = false;
        }, 100L);
    }
    void setupPlayer(Player player, int whichP, String kit) {
        switch (kit){
            case "iron":
                player.getInventory().clear();
                player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
                player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
                player.getInventory().setItem(0, new ItemStack(Material.IRON_AXE));
                player.getInventory().setItem(1, new ItemStack(Material.GOLDEN_APPLE, 1));
                player.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD));
                break;
            case "diamond":
                player.getInventory().clear();
                player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                player.getInventory().setItem(0, new ItemStack(Material.DIAMOND_SWORD));
                player.getInventory().setItem(2, new ItemStack(Material.GOLDEN_APPLE, 2));
                player.getInventory().setItem(1, new ItemStack(Material.BOW));
                player.getInventory().setItem(8, new ItemStack(Material.ARROW, 10));
                break;

        }
        switch (whichP) {
            case 1:
                player.teleport(spawn1);
                p1 = player;
                break;
            case 2:
                player.teleport(spawn2);
                p2 = player;
        }
        player.setGameMode(GameMode.SURVIVAL);
    }

}
