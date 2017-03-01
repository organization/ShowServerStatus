package maru.serverstat.task;

import java.util.List;
import java.util.Objects;

import cn.nukkit.Nukkit;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.math.NukkitMath;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.TextFormat;
import maru.serverstat.Main;
import maru.serverstat.commands.ServerInfoCommand;

public class InfoSendTask extends PluginTask<Main> {

	public InfoSendTask(Main owner) {
		super(owner);
	}

	@Override
	public void onRun(int currentTick) {
		List<String> list = ServerInfoCommand.getInfoPlayers();
		for (String name : list) {
			Player player = owner.getServer().getPlayer(name);
			if (player != null && player.hasPermission("showserverstatus.show")) {
		        Server server = player.getServer();
		        String msg = TextFormat.GREEN + "---- " + TextFormat.WHITE + "서버정보" + TextFormat.GREEN + " ----\n";

		        long time = (System.currentTimeMillis() - Nukkit.START_TIME) / 1000;
		        int seconds = NukkitMath.floorDouble(time % 60);
		        int minutes = NukkitMath.floorDouble((time % 3600) / 60);
		        int hours = NukkitMath.floorDouble(time % (3600 * 24) / 3600);
		        int days = NukkitMath.floorDouble(time / (3600 * 24));
		        String upTimeString = TextFormat.RED + "" + days + TextFormat.GOLD + " 일 " +
		                TextFormat.RED + hours + TextFormat.GOLD + " 시간 " +
		                TextFormat.RED + minutes + TextFormat.GOLD + " 분 " +
		                TextFormat.RED + seconds + TextFormat.GOLD + " 초";
		        msg += TextFormat.GOLD + "서버가동시간: " + upTimeString + "\n";

		        TextFormat tpsColor = TextFormat.GREEN;
		        float tps = server.getTicksPerSecond();
		        if (tps < 17) {
		            tpsColor = TextFormat.GOLD;
		        } else if (tps < 12) {
		            tpsColor = TextFormat.RED;
		        }

		        msg += TextFormat.GOLD + "현재 TPS: " + tpsColor + NukkitMath.round(tps, 2) + "\n";

		        msg += TextFormat.GOLD + "Load: " + tpsColor + server.getTickUsage() + "%\n";

		        msg += TextFormat.GOLD + "네트워크 업로드: " + TextFormat.GREEN + NukkitMath.round((server.getNetwork().getUpload() / 1024 * 1000), 2) + " kB/s\n";

		        msg += TextFormat.GOLD + "네트워크 다운로드: " + TextFormat.GREEN + NukkitMath.round((server.getNetwork().getDownload() / 1024 * 1000), 2) + " kB/s\n";

		        msg += TextFormat.GOLD + "스레드 개수: " + TextFormat.GREEN + Thread.getAllStackTraces().size() + "\n";


		        Runtime runtime = Runtime.getRuntime();
		        double totalMB = NukkitMath.round(((double) runtime.totalMemory()) / 1024 / 1024, 2);
		        double usedMB = NukkitMath.round((double) (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024, 2);
		        double maxMB = NukkitMath.round(((double) runtime.maxMemory()) / 1024 / 1024, 2);
		        double usage = usedMB / maxMB * 100;
		        TextFormat usageColor = TextFormat.GREEN;

		        if (usage > 85) {
		            usageColor = TextFormat.GOLD;
		        }

		        msg += TextFormat.GOLD + "사용 메모리: " + usageColor + usedMB + " MB. (" + NukkitMath.round(usage, 2) + "%)\n";

		        msg += TextFormat.GOLD + "총 메모리: " + TextFormat.RED + totalMB + " MB.\n";

		        msg += TextFormat.GOLD + "가상머신 최대 메모리: " + TextFormat.RED + maxMB + " MB.\n";

		        msg += TextFormat.GOLD + "사용가능한 프로세서 개수: " + TextFormat.GREEN + runtime.availableProcessors() + "\n";


		        TextFormat playerColor = TextFormat.GREEN;
		        if (((float) server.getOnlinePlayers().size() / (float) server.getMaxPlayers()) > 0.85) {
		            playerColor = TextFormat.GOLD;
		        }

		        msg += TextFormat.GOLD + "플레이어수: " + playerColor + "온라인 " + server.getOnlinePlayers().size() + TextFormat.GREEN + "명, 최대 " +
		                TextFormat.RED + server.getMaxPlayers() + TextFormat.GREEN + " 명.\n";

		        for (Level level : server.getLevels().values()) {
		        	msg += 
		                    TextFormat.GOLD + "월드 \"" + level.getFolderName() + "\"" + (!Objects.equals(level.getFolderName(), level.getName()) ? " (" + level.getName() + ")" : "") + ": " +
		                            TextFormat.RED + level.getChunks().size() + TextFormat.GREEN + " 청크, " +
		                            TextFormat.RED + level.getEntities().length + TextFormat.GREEN + " 엔티티, " +
		                            TextFormat.RED + level.getBlockEntities().size() + TextFormat.GREEN + " 블럭엔티티." +
		                            " 시간 " + ((level.getTickRate() > 1 || level.getTickRateTime() > 40) ? TextFormat.RED : TextFormat.YELLOW) + NukkitMath.round(level.getTickRateTime(), 2) + "ms" +
		                            (level.getTickRate() > 1 ? " (틱 주기 " + level.getTickRate() + ")" : "") + "\n"
		            ;
		        }
		        msg += "\n\n\n\n\n\n ";
		        player.sendTip(msg);
			}
		}
	}

}
