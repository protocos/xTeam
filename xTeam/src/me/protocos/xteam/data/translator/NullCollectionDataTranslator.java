package me.protocos.xteam.data.translator;

import java.util.Collection;
import me.protocos.xteam.util.CommonUtil;

public class NullCollectionDataTranslator implements IDataTranslator<Collection<String>>
{
	@Override
	public String decompile(Collection<String> objects)
	{
		return CommonUtil.concatenate(objects, ",");
	}

	@Override
	public Collection<String> compile(String string)
	{
		throw new UnsupportedOperationException();
	}
}
