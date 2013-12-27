package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.Listeners.AncientRPGSpellListener;
import org.bukkit.entity.Player;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class RemoveBuffCommand extends ICommand
{
	@CommandDescription(description = "<html>removes the specified buff of the player so it can't be triggered anymore</html>",
			argnames = {"player", "buffname"}, name = "RemoveBuff", parameters = {ParameterType.Player, ParameterType.String})

	public RemoveBuffCommand()
	{
		ParameterType[] buffer = { ParameterType.Player, ParameterType.String };
		this.paramTypes = buffer;
	}

	@Override
	public boolean playCommand(final EffectArgs ca)
	{
		if (ca.params.size() == 2 && ca.params.get(0) instanceof Player[] && ca.params.get(1) instanceof String)
		{
			final Player[] players = (Player[]) ca.params.get(0);
			final String spellname = (String) ca.params.get(1);
			ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> map = AncientRPGSpellListener.getAllBuffs();
			ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> affectedSpells = map;
			boolean all = false;
			if (spellname.equalsIgnoreCase("all"))
			{
				all = true;
			}
			for (Player p : players)
			{
				if (p == null)
					continue;
				for (Entry<Spell, ConcurrentHashMap<Player[], Integer>> entry : affectedSpells.entrySet())
				{
					ConcurrentHashMap<Player[], Integer> maps = new ConcurrentHashMap<Player[], Integer>();
					for (Entry<Player[], Integer> entry1 : entry.getValue().entrySet())
					{
						if (entry1.getKey()[0].equals(p) && (all || entry.getKey().name.equalsIgnoreCase(spellname)))
						{
							AncientRPGSpellListener.detachBuffOfEvent(entry.getKey().buffEvent, entry.getKey(), p);
						}
					}
					for (Entry<Player[], Integer> entry1 : maps.entrySet())
					{
						entry.getValue().remove(entry1.getKey());
					}
				}
			}
			return true;
		}
		return false;
	}

}
