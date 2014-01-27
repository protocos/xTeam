package me.protocos.xteam.data.translator;

public class ByteDataTranslator implements IDataTranslator<Byte>
{
	@Override
	public String decompile(Byte obj)
	{
		return obj.toString();
	}

	@Override
	public Byte compile(String compiledString)
	{
		return Byte.valueOf(compiledString);
	}
}
