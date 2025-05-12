package plugin.sample;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetLevelCommand implements CommandExecutor {
  private Main main;
  public SetLevelCommand(Main main) {
    this.main = main;
  }
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
      @NotNull String label, @NotNull String[] args) {

    if (sender instanceof Player player) {
      if (args.length == 1) {
        try {
          int number = Integer.parseInt(args[0]);
          sender.sendMessage("入力されたレベルは: " + number);
          player.setLevel(number);
        } catch (NumberFormatException e) {
          sender.sendMessage("'" + args[0] + "' は数値ではありません。");
        }
      } else {
        sender.sendMessage(main.getConfig().getString("Message"));
      }
    }
    return false; // public boolean と記述している箇所に対応する記述。処理が終わったら何もしない、というような意
  }
}
