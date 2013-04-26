package me.protocos.xteam.testing;

import org.bukkit.entity.AnimalTamer;

public class FakeAnimalTamer implements AnimalTamer
{
	private String name;

	public FakeAnimalTamer(String name)
	{
		this.name = name;
	}
	@Override
	public String getName()
	{
		return name;
	}
	public boolean equals(FakeAnimalTamer tamer)
	{
		return getName().equals(tamer.getName());
	}

}
