package me.protocos.xteam.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Column
{
	private String name;
	private ColumnType columnType;

	public Column(String name, Class<?> clazz)
	{
		this(name, ColumnType.fromClass(clazz));
	}

	public Column(String name, ColumnType columnType)
	{
		this.name = name;
		this.columnType = columnType;
	}
	
	public ColumnType getType()
	{
		return columnType;
	}
	
	public String getName()
	{
		return name;
	}

	public static Column primaryKey(String name)
	{
		return new Column(name, ColumnType.VARCHAR);
	}
	
	public static Column bool(String name)
	{
		return new Column(name, Boolean.class);
	}
	
	public static Column integer(String name)
	{
		return new Column(name, Integer.class);
	}
	
	public static Column bigInt(String name)
	{
		return new Column(name, Long.class);
	}
	
	public static Column decimal(String name)
	{
		return new Column(name, Double.class);
	}
	
	public static Column string(String name)
	{
		return new Column(name, String.class);
	}
	
	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(211, 41)
				.append(this.columnType)
				.append(this.name)
				.toHashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof Column))
			return false;

		Column rhs = (Column) obj;
		return new EqualsBuilder()
				.append(this.columnType, rhs.columnType)
				.append(this.name, rhs.name)
				.isEquals();
	}
	
	@Override
	public String toString()
	{
		return "[" + this.name + ", " + this.columnType + "]";
	}
}
