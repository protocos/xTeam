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
		pattern = new StringBuilder();
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
		if (optional)
			this.pattern.append(OPTIONAL_WHITE_SPACE);
		else
			this.pattern.append(WHITE_SPACE);
		return this;
	}

	public PatternBuilder numbers(boolean optional)
	{
		if (optional)
			this.pattern.append(OPTIONAL_NUMBERS);
		else
			this.pattern.append(NUMBERS);
		return this;
	}

	public PatternBuilder alphaNumeric(boolean optional)
	{
		if (optional)
			this.pattern.append(OPTIONAL_ALPHA_NUMERIC);
		else
			this.pattern.append(ALPHA_NUMERIC);
		return this;
	}

	public PatternBuilder anyCharacters(boolean optional)
	{
		if (optional)
			this.pattern.append(OPTIONAL_ANY_CHARS);
		else
			this.pattern.append(ANY_CHARS);
		return this;
	}

	public PatternBuilder anyCharacters(String append)
	{
		this.pattern.append("[" + append + "]");
		return this;
	}

	public PatternBuilder excludeCharacters(String append)
	{
		this.pattern.append("[^" + append + "]");
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

	private String patternOneOrMore(String str)
	{
		String oneOrMore = "" + str.charAt(0);
		String closeParen = "";
		for (int x = 1; x < str.length(); x++)
		{
			if (x != 0)
			{
				oneOrMore += "(";
				closeParen += ")";
			}
			oneOrMore += str.charAt(x);
			if (x != 0)
			{
				oneOrMore += "?";
			}
		}
		oneOrMore += closeParen;
		return oneOrMore;
	}
}
