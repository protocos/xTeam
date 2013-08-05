package me.protocos.xteam.api.fakeobjects;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.entity.AnimalTamer;

public class FakeAnimalTamer implements AnimalTamer
{
	private String name;

	public FakeAnimalTamer(String name)
	{
		this.name = name;
	}
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof FakeAnimalTamer))
			return false;

		FakeAnimalTamer rhs = (FakeAnimalTamer) obj;
		return new EqualsBuilder().append(name, rhs.name).isEquals();
	}
	@Override
	public String getName()
	{
		return name;
	}
	public int hashCode()
	{
		return new HashCodeBuilder(73, 3).append(name).toHashCode();
	}

}
