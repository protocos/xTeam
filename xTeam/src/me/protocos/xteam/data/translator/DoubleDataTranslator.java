package me.protocos.xteam.data.translator;

public class DoubleDataTranslator implements IDataTranslator<Double>
{
	@Override
	public String decompile(Double obj)
	{
		return obj.toString();
	}

	@Override
	public Double compile(String compiledString)
	{
		return Double.valueOf(compiledString);
	}
}
