package me.protocos.xteam.data.translator;

public interface IDataTranslator<T>
{
	public abstract String decompile(T obj);

	public abstract T compile(String compiledString);
}
