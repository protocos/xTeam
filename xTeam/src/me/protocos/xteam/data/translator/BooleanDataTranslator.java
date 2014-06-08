package me.protocos.xteam.data.translator;

public class BooleanDataTranslator implements IDataTranslator<Boolean>
{
	@Override
	public String decompile(Boolean object)
	{
		return object.toString();
	}

	@Override
	public Boolean compile(String string)
	{
		if ("1".equals(string))
			return Boolean.TRUE;
		return Boolean.valueOf(string);
	}
}
