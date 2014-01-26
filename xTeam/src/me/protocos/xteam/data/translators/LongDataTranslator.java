package me.protocos.xteam.data.translators;


public class LongDataTranslator implements IDataTranslator<Long>
{
	@Override
	public String decompile(Long obj)
	{
		return obj.toString();
	}

	@Override
	public Long compile(String compiledString)
	{
		return Long.valueOf(compiledString);
	}
}
