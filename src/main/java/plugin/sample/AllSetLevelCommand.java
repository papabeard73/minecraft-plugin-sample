package plugin.sample;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AllSetLevelCommand implements CommandExecutor {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
      @NotNull String label, @NotNull String[] args) {
    if (sender instanceof Player player) {
      // プレイヤーがコマンドを入力した場合
      player.sendMessage("ここからは入力できません。");
    } else {
      // サーバーからコマンドを入力した場合
      if (args.length == 1) {
        for (Player player : sender.getServer().getOnlinePlayers()){
          player.setLevel(Integer.parseInt(args[0]));
          System.out.println("プレイヤーのレベルが" + args[0] + "になりました。");
        }
      }
    }
    return false;
  }
}
