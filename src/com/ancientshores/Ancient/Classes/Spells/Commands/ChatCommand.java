package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.HelpList;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class ChatCommand extends ICommand {
    @CommandDescription(description = "<html>The player says the specified message in the chat</html>",
            argnames = {"player", "message"}, name = "Chat", parameters = {ParameterType.Player, ParameterType.String})
    public ChatCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.String};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof Player[] && ca.getParams().get(1) instanceof String) {
                final Player[] target = (Player[]) ca.getParams().get(0);
                final String message = (String) ca.getParams().get(1);
                if (target != null && target.length > 0 && target[0] instanceof Player && message != null) {
                    Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable() { // ??? wozu der scheduled task

                        @Override
                        public void run() {
                            for (Player p : target) {
                                if (p == null) {
                                    continue;
                                }
                                p.chat(HelpList.replaceChatColor(message));
                            }
                        }
                    });
                    return true;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }
}