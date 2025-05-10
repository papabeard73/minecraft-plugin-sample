package plugin.sample;

import java.util.Objects;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LevelUpCommand implements CommandExecutor {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
      @NotNull String label, @NotNull String[] args) {

    if (sender instanceof Player player) {
      if (args.length > 0) {
        try {
          int number = Integer.parseInt(args[0]);
          sender.sendMessage("入力された数値は: " + number);
          player.setLevel(number);
        } catch (NumberFormatException e) {
          sender.sendMessage("'" + args[0] + "' は数値ではありません。");
        }
      } else {
        player.setLevel(30);
        sender.sendMessage("引数がなかったのでレベル30にしました！");
      }
    }
    return true; // コマンド処理が成功した場合は true を返す
  }
}
