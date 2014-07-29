package me.protocos.xteam.model;

import java.util.Set;
import me.protocos.api.util.CommonUtil;
import org.apache.commons.lang.builder.EqualsBuilder;

public class Table
{
	public static class Builder
	{
		private final String tableName;
		private final Column primaryKey;
		private final Set<Column> columns;

		public Builder(String tableName, Column primaryKey)
		{
			this.tableName = tableName;
			this.primaryKey = primaryKey;
			this.columns = CommonUtil.emptySet();
			this.columns.add(primaryKey);
		}
		
		public Builder addColumn(Column column)
		{
			columns.add(column);
			return this;
		}

		public Table build()
		{
			return new Table(this);
		}
	}

	private String tableName;
	private Column primaryKey;
	private Set<Column> columns;
	private Set<PropertyList> rows;

	private Table(Builder builder)
	{
		this.tableName = builder.tableName;
		this.primaryKey = builder.primaryKey;
		this.columns = builder.columns;
		this.rows = CommonUtil.emptySet();
	}

	public String getTableName()
	{
		return tableName;
	}

	public ColumnType getPrimaryKeyType()
	{
		return primaryKey.getType();
	}

	public String getPrimaryKeyName()
	{
		return primaryKey.getName();
	}

	public boolean containsRow(String string)
	{
		return getRow(string) != null;
	}

	public PropertyList getRow(String string)
	{
		for (PropertyList propertyList : rows)
			if (propertyList.getAsString(primaryKey.getName()).equals(string))
				return propertyList;
		return null;
	}

	public boolean containsColumn(String string)
	{
		for(Column column:columns)
			if (column.getName().equals(string))
				return true;
		return false;
	}

	public ColumnType getTypeOfColumn(String string)
	{
		for (Column column : columns)
			if (column.getName().equals(string))
				return column.getType();
		return null;
	}

	private boolean containsPrimaryKeyValue(PropertyList propertyList)
	{
		return propertyList.containsKey(getPrimaryKeyName());
	}

	private String getPrimaryKeyValue(PropertyList propertyList)
	{
		return propertyList.getAsString(getPrimaryKeyName());
	}

	public boolean insert(PropertyList propertyList)
	{
		if (!containsPrimaryKeyValue(propertyList))
			return false;
		if (containsRow(getPrimaryKeyValue(propertyList)))
			return false;
		pruneColumnsFromPropertyList(propertyList);
		rows.add(propertyList);
		return true;
	}

	public boolean update(PropertyList propertyList)
	{
		if (!containsPrimaryKeyValue(propertyList))
			return false;
		if (!containsRow(getPrimaryKeyValue(propertyList)))
			return false;
		pruneColumnsFromPropertyList(propertyList);
		PropertyList updateRow = getRow(getPrimaryKeyValue(propertyList));
		for (Property property : propertyList)
		{
			updateRow.put(property);
		}
		return true;
	}

	public boolean delete(String string)
	{
		if (!containsRow(string))
			return false;
		PropertyList deleteRow = getRow(string);
		rows.remove(deleteRow);
		return true;
	}

	private void pruneColumnsFromPropertyList(PropertyList propertyList)
	{
		Set<String> propertyNames = CommonUtil.emptySet();
		for (Property property : propertyList)
		{
			propertyNames.add(property.getKey());
		}
		Set<String> columnNames = CommonUtil.emptySet();
		for (Column column : columns)
		{
			columnNames.add(column.getName());
		}
		//pruning properties that don't match a column name
		propertyNames.removeAll(columnNames);
		for (String name : propertyNames)
		{
			propertyList.remove(name);
		}
	}

	public int size()
	{
		return rows.size();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof Table))
			return false;

		Table rhs = (Table) obj;
		return new EqualsBuilder()
				.append(this.tableName, rhs.tableName)
				.append(this.primaryKey, rhs.primaryKey)
				.append(this.columns, rhs.columns)
				.append(this.rows, rhs.rows)
				.isEquals();
	}

	@Override
	public String toString()
	{
		String output = "";
		for (PropertyList propertyList : rows)
		{
			output += propertyList.toString() + "\n";
		}
		return output;
	}
}
