package me.protocos.xteam.model;

public enum ColumnType
{
	BOOLEAN(Boolean.class, "BOOLEAN"),
	INTEGER(Integer.class, "INTEGER"),
	LONG(Long.class, "BIGINT"),
	DOUBLE(Double.class, "DECMIAL"),
	STRING(String.class, "TEXT"),
	VARCHAR(String.class, "VARCHAR(255)");
	
	private Class<?> clazz;
	private String sqlType;

	private ColumnType(Class<?> clazz, String sqlType)
	{
		this.clazz = clazz;
		this.sqlType = sqlType;
	}
	
	public Class<?> getType()
	{
		return clazz;
	}
	
	public String getSqlType()
	{
		return sqlType;
	}
	
	public static ColumnType fromClass(Class<?> clazz)
	{
		for(ColumnType columnType:ColumnType.values())
			if (columnType.getType().equals(clazz))
				return columnType;
		throw new IllegalArgumentException();
	}
	
	public static ColumnType fromSqlType(String sqlType)
	{
		for(ColumnType columnType:ColumnType.values())
			if (columnType.getSqlType().equals(sqlType))
				return columnType;
		throw new IllegalArgumentException();
	}
}
