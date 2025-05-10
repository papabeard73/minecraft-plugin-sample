package plugin.sample;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

  private int count;

  @Override
  public void onEnable() {
    // イベントリスナーを登録
    Bukkit.getPluginManager().registerEvents(this, this);

    this.getCommand("setlevel").setExecutor(new SetLevelCommand());
    this.getCommand("allsetlevel").setExecutor(new AllSetLevelCommand());

    // コマンドを登録
    this.getCommand("getbed").setExecutor((sender, command, label, args) -> {
      if (sender instanceof Player) {
        Player player = (Player) sender;
        if (!player.getInventory().contains(Material.WHITE_BED)) {
          player.getInventory().addItem(new ItemStack(Material.WHITE_BED, 1));
          player.sendMessage("ベッドをインベントリに追加しました！");
        } else {
          player.sendMessage("すでにベッドを持っています！");
        }
        return true;
      } else {
        sender.sendMessage("このコマンドはプレイヤーから実行してください！");
        return false;
      }
    });
  }

  /**
   * プレイヤーがスニークを開始/終了する際に起動されるイベントハンドラ。
   *
   * @param e イベント
   */
  @EventHandler
  public void onPlayerToggleSneak(PlayerToggleSneakEvent e) throws IOException {
    // イベント発生時のプレイヤーやワールドなどの情報を変数に持つ。
    Player player = e.getPlayer();
    World world = player.getWorld();

    // 01. 花火用
    // BigInteger型の val を定義
    BigInteger val = new BigInteger(String.valueOf(count));

    // 後でループするカラーの定義
    List<Color> colorList = List.of(Color.RED, Color.BLUE, Color.WHITE, Color.YELLOW);

    // val が素数であるかの判定 isProbablePrimeメソッドを使用
    // count = 0, 1 のときも isProbablePrime(1) が通ってしまう可能性があるので2からスタート
    if (count >= 2 && val.isProbablePrime(10)) {

      for (Color c : colorList) {
        // 花火オブジェクトをプレイヤーのロケーション地点に対して出現させる。
        Firework firework = world.spawn(player.getLocation(), Firework.class);

        // 花火オブジェクトが持つメタ情報を取得。
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        // メタ情報に対して設定を追加したり、値の上書きを行う。
        // 今回は青色で星型の花火を打ち上げる。
        fireworkMeta.addEffect(
            FireworkEffect.builder()
                .withColor(c)
                .with(Type.CREEPER)
                .withFlicker()
                .build());
        fireworkMeta.setPower(2);

        // 追加した情報で再設定する。
        firework.setFireworkMeta(fireworkMeta);
      }
      Path path = Path.of("firework.txt");
      Files.writeString(path, "たーまやーー");
      player.sendMessage(Files.readString(path));
    }
    count++;
  }

  @EventHandler
  public void onPlayerBedEnter(PlayerBedEnterEvent e) {
    Player player = e.getPlayer();
    ItemStack[] itemStacks = player.getInventory().getContents();

    IntStream.range(0, itemStacks.length).forEach(i -> {
      ItemStack item = itemStacks[i];
      if (item != null && item.getMaxStackSize() == 64 && item.getAmount() != 64) {
        itemStacks[i] = null; // スロットを空にする
      }
    });
    player.getInventory().setContents(itemStacks);
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
    Player player = e.getPlayer();
    e.setJoinMessage("ようこそ、" + player.getName() + "さん！");
  }
}