package org.modelica.mdt.core.compiler;

import org.modelica.mdt.core.Element;
import org.modelica.mdt.core.List;
import org.modelica.mdt.core.ModelicaParserException;

public class ComponentParser extends ModelicaParser{
		/**
		 * This function parses Modelica lists, any nesting possible.
		 * @param str the Modelica list to parse
		 * @return a Vector containing Vector:s and String:s. The Vector:s contain
		 * further Vector:s and String:s. Nesting and stuff.
		 * @throws ModelicaParserException 
		 */
		public static List parseList(String str) throws ModelicaParserException
		{
			List elements = new List();
			
			/* Remove whitespace before and after */
			str = str.trim();
			
			/* Make sure this string is not empty */
			if(str == "" || str.length() < 2)
			{
				throw new ModelicaParserException("Empty list: [" + str + "]");
			}

			String dummy= str.substring(1, str.length());
			
			if(str.trim().equals(""))
			{
				/* This is an empty list, so return an empty list! */
				return new List();
			}
			
			/*
			 * { { hej, på } , dig } => [[hej,på],dig]
			 */
			
			/*
			 * Go through the string character by character, looking for commas (,)
			 * and start ({) and end (}) of lists. Take special note of " as they
			 * start and end strings. Inside a string, there can be , { and }
			 * characters and also escaped characters (for example \").
			 */
			
			// TODO Rewrite this using a better way of acumulating the characters.
			// Right now, it uses string += otherString, which generates alot of
			// strings that later are thrown away. Slowness, the slowness!
			String subString = "";
			int depth = 0;
			int tupleDepth = 0;
			boolean listFound = false;
			boolean insideString = false;
			
			String[] tokens = dummy.split(",");
			
			for(int i = 0; i < tokens.length; i++){ 
				if(tokens[i].contains("{") && !tokens[i].contains("}")){
					tokens[i] = tokens[i].substring(1, tokens[i].length());
					Element tempElement = new Element(tokens[i]);
					elements.append(tempElement);
				}
			}
			return elements;
		}
}

