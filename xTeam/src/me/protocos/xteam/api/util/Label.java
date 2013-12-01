package me.protocos.xteam.api.util;

import java.util.HashSet;

public class Label
{
	private final String primaryAlias;
	private HashSet<String> aliases;

	public Label(String primaryAlias)
	{
		this.primaryAlias = primaryAlias;
		this.aliases = new HashSet<String>();
		this.aliases.add(primaryAlias);
	}

	public void addAlias(String alias)
	{
		aliases.add(alias);
	}

	public String getPrimaryAlias()
	{
		return primaryAlias;
	}

	public HashSet<String> getAliases()
	{
		return new HashSet<String>(aliases);
	}

	@Override
	public boolean equals(Object other)
	{
		if (other == null)
			return false;
		if (this == other)
			return true;
		if (!(other instanceof Label))
			return false;

		Label otherLabel = (Label) other;
		HashSet<String> combination = new HashSet<String>();
		combination.addAll(this.getAliases());
		combination.addAll(otherLabel.getAliases());
		if (combination.size() < this.getAliases().size() + otherLabel.getAliases().size())
			return true;
		return false;
	}

	@Override
	public String toString()
	{
		return new StringBuilder().append(this.primaryAlias).append(": ").append(this.getAliases()).toString();
	}
}
