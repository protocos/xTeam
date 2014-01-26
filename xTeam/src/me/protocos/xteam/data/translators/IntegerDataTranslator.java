package me.protocos.xteam.data.translators;

public class IntegerDataTranslator implements IDataTranslator<Integer>
{
	@Override
	public String decompile(Integer obj)
	{
		return obj.toString();
	}

	@Override
	public Integer compile(String compiledString)
	{
		return Integer.valueOf(compiledString);
	}
}
