package maru.serverstat;

import cn.nukkit.command.CommandMap;
import cn.nukkit.plugin.PluginBase;
import maru.serverstat.commands.ServerInfoCommand;
import maru.serverstat.task.InfoSendTask;

public class Main extends PluginBase {
	@Override
	public void onEnable() {
		registerCommands();
		getServer().getScheduler().scheduleRepeatingTask(new InfoSendTask(this), 10);
	}
	
	private void registerCommands() {
		CommandMap map = getServer().getCommandMap();
		map.register("ShowServerStatus", new ServerInfoCommand());
	}
}
