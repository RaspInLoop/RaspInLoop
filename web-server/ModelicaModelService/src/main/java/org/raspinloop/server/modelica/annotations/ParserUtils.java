package org.raspinloop.server.modelica.annotations;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.openmodelica.corba.parser.ParseException;

public class ParserUtils {

	public static boolean readDelimiter(Reader r, char delimiter) throws IOException, ParseException {
		ParserUtils.skipWhiteSpace(r);
		r.mark(1);
		char c = (char) r.read();
		if (c != delimiter) {
			r.reset();
			return false;
		}
		return true;
	}

	public static void skipUntil(Reader r, char delimiter) throws IOException {
		int i;
		char c;
		do {
			i = r.read();
			if (i == -1)
				return;
			c = (char) i;
		} while (c != delimiter);
	}

	public static void skipWhiteSpace(Reader r) throws IOException {
		int i;
		char c;
		do {
			r.mark(1);
			i = r.read();
			if (i == -1)
				return;
			c = (char) i;
		} while (Character.isWhitespace(c));
		r.reset();
	}

	public static boolean isOpeningBrace(Reader r) throws ParseException, IOException {
		return isNextDelimiterPresent(r, '{');
	}

	public static boolean isClosingBrace(Reader r) throws ParseException, IOException {
		return isNextDelimiterPresent(r, '}');
	}

	private static boolean isNextDelimiterPresent(Reader r, char delimiter) throws ParseException, IOException {
		boolean delimiterFound = false;
		ParserUtils.skipWhiteSpace(r);
		r.mark(1);
		char c = (char) r.read();
		if (c == -1) {
			throw new ParseException("premature end of annotation.");
		} else if (c == delimiter) {
			delimiterFound = true;
		} else {
			r.reset();
		}

		return delimiterFound;
	}

	/**
	 * Returns true if the value is a Real, else returns an Integer
	 */
	public static boolean parseIntOrReal(Reader r, StringBuilder b) throws IOException {
		boolean bool = false;
		int i;
		char ch;
		skipWhiteSpace(r);
		do {
			r.mark(1);
			i = r.read();
			if (i == -1)
				break;
			ch = (char) i;
			if (Character.isDigit(ch) || ch == '-')
				b.append(ch);
			else if (ch == 'e' || ch == 'E' || ch == '+' || ch == '.') {
				b.append(ch);
				bool = true;
			} else {
				r.reset();
				break;
			}
		} while (true);
		return bool;
	}

	public static int parseIntToken(Reader r, char delimiter) throws IOException, ParseException {
		int result = parseIntToken(r);
		if (!ParserUtils.readDelimiter(r, delimiter))
			throw new ParseException("delimiter '" + delimiter + "' not found!");
		return result;
	}

	public static int parseIntToken(Reader r) throws IOException, ParseException {
		StringBuilder sb = new StringBuilder();
		boolean isReal = ParserUtils.parseIntOrReal(r, sb);
		if (isReal)
			throw new ParseException("Int value expected.");
		int i = Integer.parseInt(sb.toString());
		return i;
	}

	public static double parseRealToken(Reader r, char delimiter) throws IOException, ParseException {
		double result;
		try {
			result = parseRealToken(r);
		} catch (NumberFormatException e) {
			result = Double.NaN;
		}
		if (!ParserUtils.readDelimiter(r, delimiter))
			throw new ParseException("delimiter '" + delimiter + "' not found!");
		return result;
	}

	public static double parseRealToken(Reader r) throws IOException, ParseException {
		StringBuilder sb = new StringBuilder();
		ParserUtils.parseIntOrReal(r, sb);
		if (StringUtils.isBlank(sb.toString()))
			throw new ParseException("Int or Real value expected.");
		double d;
		try {
			d = Double.parseDouble(sb.toString());
		} catch (NumberFormatException e) {
			d = Double.NaN;
		}
		return d;
	}

	public static boolean parseBooleanToken(Reader r, char delimiter) throws ParseException, IOException {
		boolean result = parseBooleanToken(r);
		if (!ParserUtils.readDelimiter(r, delimiter))
			throw new ParseException("delimiter '" + delimiter + "' not found!");
		return result;
	}

	public static boolean parseBooleanToken(Reader r) throws ParseException, IOException {
		int i;
		char ch;
		skipWhiteSpace(r);
		i = r.read();
		if (i == -1)
			throw new ParseException("EOF, expected Boolean");
		ch = (char) i;

		char cbuf[];
		if (ch == 't') {
			cbuf = new char[3];
			if (r.read(cbuf, 0, 3) == -1)
				throw new ParseException("EOF, expected Boolean");
			if (cbuf[0] == 'r' && cbuf[1] == 'u' && cbuf[2] == 'e')
				return true;
		} else if (ch == 'f') {
			cbuf = new char[4];
			if (r.read(cbuf, 0, 4) == -1)
				throw new ParseException("EOF, expected Boolean");
			if (cbuf[0] == 'a' && cbuf[1] == 'l' && cbuf[2] == 's' && cbuf[3] == 'e')
				return false;
		}
		throw new ParseException("Expected Boolean");
	}

	public static <T> List<T> ParseList(Reader reader, Function<Reader, T> builder) throws ParseException, IOException {
		List<T> elements = new ArrayList<>();
		boolean cBraceOpen = ParserUtils.isOpeningBrace(reader);
		if (!cBraceOpen)
			throw new ParseException("expected openingBrace");
		do {
			elements.add(builder.apply(reader));
		} while (!ParserUtils.isClosingBrace(reader));
		return elements;
	}

	public static String parseStringToken(Reader reader, char delimiter) throws IOException, ParseException {
		ParserUtils.skipWhiteSpace(reader);
		if (!ParserUtils.readDelimiter(reader, '"'))
			throw new ParseException("expected opening quote");
		StringBuilder stringBuilder = new StringBuilder();
		char c = (char) reader.read();
		while (c != '"') {
			stringBuilder.append(c);
			c = (char) reader.read();
		}
		if (!ParserUtils.readDelimiter(reader, delimiter))
			throw new ParseException("delimiter '" + delimiter + "' not found!");
		return stringBuilder.toString();
	}

}
