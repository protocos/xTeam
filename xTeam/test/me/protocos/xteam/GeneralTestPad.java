package me.protocos.xteam;

import me.protocos.xteam.util.CommonUtil;
import org.junit.Ignore;
import org.junit.Test;

public class GeneralTestPad
{
	@Test
	@Ignore
	public void test()
	{
		Number number = new Double(10);
		Double fromNumber = CommonUtil.subTypeFromSuperType(number, Double.class);
		System.out.println(fromNumber);
	}
}