package me.protocos.xteam.data.translator;

import java.util.Set;
import me.protocos.xteam.util.CommonUtil;

public class SetDataTranslator implements IDataTranslator<Set<String>>
{
	@Override
	public String decompile(Set<String> objects)
	{
		return CommonUtil.concatenate(objects, ",");
	}

	@Override
	public Set<String> compile(String string)
	{
		Set<String> set = CommonUtil.emptySet();
		if (!"".equals(string))
			set.addAll(CommonUtil.split(string, ","));
		return set;
	}
}
