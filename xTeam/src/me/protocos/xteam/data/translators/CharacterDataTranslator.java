package me.protocos.xteam.data.translators;

public class CharacterDataTranslator implements IDataTranslator<Character>
{
	@Override
	public String decompile(Character obj)
	{
		return obj.toString();
	}

	@Override
	public Character compile(String compiledString)
	{
		return Character.valueOf(compiledString.charAt(0));
	}
}
