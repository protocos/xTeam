package me.protocos.xteam.data.translator;

public class BooleanDataTranslator implements IDataTranslator<Boolean>
{
	@Override
	public String decompile(Boolean obj)
	{
		return obj.toString();
	}

	@Override
	public Boolean compile(String compiledString)
	{
		return Boolean.valueOf(compiledString);
	}
}
