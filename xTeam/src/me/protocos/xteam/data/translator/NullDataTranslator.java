package me.protocos.xteam.data.translator;

public class NullDataTranslator implements IDataTranslator<Object>
{
	@Override
	public String decompile(Object object)
	{
		return object.toString();
	}

	@Override
	public Object compile(String string)
	{
		throw new UnsupportedOperationException("Unsupported object has no method to compile");
	}
}
