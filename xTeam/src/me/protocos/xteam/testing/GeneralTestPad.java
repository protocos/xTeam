package me.protocos.xteam.testing;

import me.protocos.xteam.util.CommonUtil;
import org.junit.Test;

public class GeneralTestPad
{
	@Test
	public void test()
	{
		CommonUtil.println(new Integer(2), "hello world", new Float(10));
	}
}
