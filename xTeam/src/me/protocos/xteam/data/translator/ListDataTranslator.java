package me.protocos.xteam.data.translator;

import java.util.List;
import me.protocos.api.util.CommonUtil;

public class ListDataTranslator implements IDataTranslator<List<String>>
{
	@Override
	public String decompile(List<String> objects)
	{
		return CommonUtil.concatenate(objects, ",");
	}

	@Override
	public List<String> compile(String string)
	{
		List<String> list = CommonUtil.emptyList();
		if (!"".equals(string))
			list.addAll(CommonUtil.split(string, "[,\\s]+"));
		return list;
	}
}
