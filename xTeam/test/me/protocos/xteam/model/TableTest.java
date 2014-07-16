package me.protocos.xteam.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TableTest
{
	private Table table;
	private PropertyList propertyList;

	@Before
	public void setup()
	{
	}

	@Test
	public void ShouldBeNewTableBuilderGetName()
	{
		//ASSEMBLE
		table = new Table.Builder("test_table", Column.primaryKey("name")).build();
		//ACT
		String expected = table.getTableName();
		//ASSERT
		Assert.assertEquals("test_table", expected);
	}

	@Test
	public void ShouldBeNewTableBuilderGetPrimaryKeyType()
	{
		//ASSEMBLE
		table = new Table.Builder("test_table", Column.primaryKey("name")).build();
		//ACT
		ColumnType expected = table.getPrimaryKeyType();
		//ASSERT
		Assert.assertEquals(ColumnType.VARCHAR, expected);
	}

	@Test
	public void ShouldBeNewTableBuilderGetPrimaryKeyName()
	{
		//ASSEMBLE
		table = new Table.Builder("test_table", Column.primaryKey("name")).build();
		//ACT
		String expected = table.getPrimaryKeyName();
		//ASSERT
		Assert.assertEquals("name", expected);
	}

	@Test
	public void ShouldBeNewTableBuilderContainsNameColumn()
	{
		//ASSEMBLE
		table = new Table.Builder("person_table", Column.primaryKey("name")).addColumn(Column.integer("age")).build();
		//ACT
		//ASSERT
		Assert.assertTrue(table.containsColumn("name"));
	}

	@Test
	public void ShouldBeNewTableBuilderContainsAgeColumn()
	{
		//ASSEMBLE
		table = new Table.Builder("person_table", Column.primaryKey("name")).addColumn(Column.integer("age")).build();
		//ACT
		//ASSERT
		Assert.assertTrue(table.containsColumn("age"));
	}

	@Test
	public void ShouldBeNewTableBuilderAgeColumnTypeIsInteger()
	{
		//ASSEMBLE
		table = new Table.Builder("person_table", Column.primaryKey("name")).addColumn(Column.integer("age")).build();
		//ACT
		//ASSERT
		Assert.assertEquals(ColumnType.INTEGER, table.getTypeOfColumn("age"));
	}

	@Test
	public void ShouldBeTableInsertRow()
	{
		//ASSEMBLE
		setUpBasicTable();
		//ACT
		boolean expected = table.insert(propertyList);
		//ASSERT
		Assert.assertTrue(expected);
	}

	@Test
	public void ShouldBeTableInsertRowAlreadyExists()
	{
		//ASSEMBLE
		setUpBasicTableAndInsertRow();
		//ACT
		boolean expected = table.insert(propertyList);
		//ASSERT
		Assert.assertFalse(expected);
	}

	@Test
	public void ShouldBeTableGetRow()
	{
		setUpBasicTableAndInsertRow();
		//ACT
		//ASSERT
		Assert.assertEquals(propertyList, table.getRow("protocos"));
	}

	@Test
	public void ShouldBeTableUpdateRow()
	{
		setUpBasicTableAndInsertRow();
		propertyList = new PropertyList();
		propertyList.put("name", "protocos");
		propertyList.put("age", "23");
		//ACT
		boolean expected = table.update(propertyList);
		//ASSERT
		Assert.assertTrue(expected);
		Assert.assertEquals("23", table.getRow("protocos").getAsString("age"));
	}

	@Test
	public void ShouldBeTableDeleteRow()
	{
		setUpBasicTableAndInsertRow();
		//ACT
		boolean expected = table.delete("protocos");
		//ASSERT
		Assert.assertTrue(expected);
		Assert.assertNull(table.getRow("protocos"));
	}

	private void setUpBasicTableAndInsertRow()
	{
		setUpBasicTable();
		table.insert(propertyList);
	}

	private void setUpBasicTable()
	{
		table = new Table.Builder("person_table", Column.primaryKey("name")).addColumn(Column.integer("age")).build();
		propertyList = new PropertyList();
		propertyList.put("name", "protocos");
		propertyList.put("age", "22");
	}

	@After
	public void takedown()
	{
	}
}