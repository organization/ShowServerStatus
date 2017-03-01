package maru.serverstat.commands;

import java.util.ArrayList;
import java.util.List;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

public class ServerInfoCommand extends Command {
	private static List<String> players = new ArrayList<>();

	public ServerInfoCommand() {
		super("서버정보", "서버 정보를 팝업으로 보여줍니다.", "/서버정보");
		setPermission("showserverstatus.commands.serverinfo");
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		String name = sender.getName().toLowerCase();
		if (players.contains(name)) {
			players.remove(name);
			sender.sendMessage(TextFormat.DARK_AQUA + "서버 정보 보기 비활성화");
		} else {
			players.add(name);
			sender.sendMessage(TextFormat.DARK_AQUA + "서버 정보 보기 활성화");
		}
		return true;
	}
	
	public static List<String> getInfoPlayers() {
		return players;
	}
}
