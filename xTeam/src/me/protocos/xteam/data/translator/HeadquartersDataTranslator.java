package me.protocos.xteam.data.translator;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.model.Headquarters;
import me.protocos.xteam.model.IHeadquarters;
import me.protocos.xteam.model.NullHeadquarters;

public class HeadquartersDataTranslator implements IDataTranslator<IHeadquarters>
{
	private TeamPlugin teamPlugin;

	public HeadquartersDataTranslator(TeamPlugin teamPlugin)
	{
		this.teamPlugin = teamPlugin;
	}

	@Override
	public String decompile(IHeadquarters headquarters)
	{
		return headquarters.toString();
	}

	@Override
	public IHeadquarters compile(String string)
	{
		if ("".equals(string))
			return new NullHeadquarters();
		return new Headquarters(teamPlugin, new LocationDataTranslator(teamPlugin).compile(string));
	}
}
