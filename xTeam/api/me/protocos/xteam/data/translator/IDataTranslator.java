package me.protocos.xteam.data.translator;

public interface IDataTranslator<T>
{
	public abstract String decompile(T object);

	public abstract T compile(String string);
}
