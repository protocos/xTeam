package me.protocos.xteam;

import java.util.logging.Logger;
import lib.PatPeter.SQLibrary.Database;
import lib.PatPeter.SQLibrary.SQLite;
import org.junit.Test;

public class GeneralTestPad
{
	@Test
	public void testDB()
	{
		Database db = new SQLite(Logger.getLogger("Minecraft"),
				"[xTeam] ",
				"xTeam",
				"xTeam",
				".db");
		if (!db.isOpen())
		{
			db.open();
		}
	}
}