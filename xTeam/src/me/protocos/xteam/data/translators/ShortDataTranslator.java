package me.protocos.xteam.data.translators;

public class ShortDataTranslator implements IDataTranslator<Short>
{
	@Override
	public String decompile(Short obj)
	{
		return obj.toString();
	}

	@Override
	public Short compile(String compiledString)
	{
		return Short.valueOf(compiledString);
	}
}
