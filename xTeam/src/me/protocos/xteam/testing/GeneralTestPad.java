package me.protocos.xteam.testing;

import java.io.IOException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.util.ErrorReportUtil;
import org.junit.Test;

public class GeneralTestPad
{
	@Test
	public void test() throws IOException
	{
		ErrorReportUtil util = new ErrorReportUtil(new xTeam());
		util.sendErrorReport(new NullPointerException());
	}
}
