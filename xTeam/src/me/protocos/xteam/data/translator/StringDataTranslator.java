package me.protocos.xteam.data.translator;

public class StringDataTranslator implements IDataTranslator<String>
{
	@Override
	public String decompile(String string)
	{
		return string;
	}

	@Override
	public String compile(String string)
	{
		return string;
	}
}
