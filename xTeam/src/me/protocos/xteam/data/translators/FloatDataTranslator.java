package me.protocos.xteam.data.translators;

public class FloatDataTranslator implements IDataTranslator<Float>
{
	@Override
	public String decompile(Float obj)
	{
		return obj.toString();
	}

	@Override
	public Float compile(String compiledString)
	{
		return Float.valueOf(compiledString);
	}
}
