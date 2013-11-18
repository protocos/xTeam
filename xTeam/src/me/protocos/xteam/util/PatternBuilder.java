package me.protocos.xteam.util;

import java.util.regex.Pattern;

public class PatternBuilder
{
	private static final String WHITE_SPACE = "\\s+";
	private static final String OPTIONAL_WHITE_SPACE = "\\s*";
	private static final String NUMBERS = "\\d+";
	private static final String OPTIONAL_NUMBERS = "\\d*";
	private static final String ANY_CHARS = "\\S+";
	private static final String OPTIONAL_ANY_CHARS = "\\S*";
	private static final String ALPHA_NUMERIC = "\\w+";
	private static final String OPTIONAL_ALPHA_NUMERIC = "\\w*";
	private static final String IGNORE_CASE = "(?i)";
	private static final String CASE_SENSITIVE = "(?-i)";

	private StringBuilder pattern;

	public PatternBuilder()
	{
		this("");
	}

	public PatternBuilder(Pattern pattern)
	{
		this(pattern.pattern());
	}

	public PatternBuilder(String pattern)
	{
		this.pattern = new StringBuilder(pattern);
	}

	public PatternBuilder append(Pattern append)
	{
		this.append(append.pattern());
		return this;
	}

	public PatternBuilder append(String append)
	{
		//		append = append
		//				.replaceAll("\\\\", "\\\\\\\\")
		//				.replaceAll("\\*", "\\\\*")
		//				.replaceAll("\\+", "\\\\+")
		//				.replaceAll("\\^", "\\\\^")
		//				.replaceAll("\\.", "\\\\.")
		//				.replaceAll("\\?", "\\\\?")
		//				.replaceAll("\\(", "\\\\(")
		//				.replaceAll("\\)", "\\\\)")
		//				.replaceAll("\\[", "\\\\[")
		//				.replaceAll("\\]", "\\\\]")
		//				.replaceAll("\\{", "\\\\{")
		//				.replaceAll("\\}", "\\\\}");
		this.pattern.append(append);
		return this;
	}

	public PatternBuilder noneOrMore(String append)
	{
		this.pattern.append(this.patternNoneOrMore(append));
		return this;
	}

	public PatternBuilder oneOrMore(String append)
	{
		this.pattern.append(this.patternOneOrMore(append));
		return this;
	}

	public PatternBuilder oneOrMoreIgnoreCase(String append)
	{
		this.pattern.append(IGNORE_CASE + this.patternOneOrMore(append) + CASE_SENSITIVE);
		return this;
	}

	public PatternBuilder whiteSpace(boolean optional)
	{
		this.pattern.append(optional ? OPTIONAL_WHITE_SPACE : WHITE_SPACE);
		return this;
	}

	public PatternBuilder numbers(boolean optional)
	{
		this.pattern.append(optional ? OPTIONAL_NUMBERS : NUMBERS);
		return this;
	}

	public PatternBuilder alphaNumeric(boolean optional)
	{
		this.pattern.append(optional ? OPTIONAL_ALPHA_NUMERIC : ALPHA_NUMERIC);
		return this;
	}

	public PatternBuilder anyCharacters(boolean optional)
	{
		this.pattern.append(optional ? OPTIONAL_ANY_CHARS : ANY_CHARS);
		return this;
	}

	public PatternBuilder anyOne(String append)
	{
		this.pattern.append("[" + append + "]");
		return this;
	}

	public PatternBuilder anyUnlimited(String append)
	{
		this.pattern.append("[" + append + "]+");
		return this;
	}

	public PatternBuilder excludeOne(String append)
	{
		this.pattern.append("[^" + append + "]");
		return this;
	}

	public PatternBuilder excludeUnlimited(String append)
	{
		this.pattern.append("[^" + append + "]+");
		return this;
	}

	public PatternBuilder repeat(String append, int times)
	{
		this.pattern.append("(" + append + "){" + times + "}");
		return this;
	}

	public PatternBuilder ignoreCase(String append)
	{
		this.pattern.append(IGNORE_CASE + append + CASE_SENSITIVE);
		return this;
	}

	public PatternBuilder lowerCase(String append)
	{
		this.pattern.append(append.toLowerCase());
		return this;
	}

	public PatternBuilder upperCase(String append)
	{
		this.pattern.append(append.toUpperCase());
		return this;
	}

	public Pattern build()
	{
		return Pattern.compile(pattern.toString());
	}

	public String toString()
	{
		return pattern.toString();
	}

	private Object patternNoneOrMore(String append)
	{
		String noneOrMore = "";
		for (char character : append.toCharArray())
		{
			noneOrMore += "(" + character + ")?";
		}
		return noneOrMore;
	}

	private String patternOneOrMore(String append)
	{
		String noneOrMore = "";
		boolean first = true;
		for (char character : append.toCharArray())
		{
			noneOrMore += "(" + character + ")" + (first ? "" : "?");
			first = false;
		}
		return noneOrMore;
	}
}
