package org.modelica.mdt.internal.core;

import io.micrometer.core.instrument.util.StringUtils;

public class Packages {

	/**
	 * 
	 * @param prefix
	 * @param packageName
	 * @return true if packageName is apackage included in prefix: ie Modelica.Mechanics.Rotational.Components is included in Modelica.Mechanics.Rotational
	 */
	public static boolean isSubPackage(String prefix, String packageName) {
		int pos = packageName.indexOf(prefix);
		return pos == 0 && (packageName.length() ==  prefix.length()
					     || packageName.charAt(prefix.length())=='.' ); // prefix must match a name border
	}

	public static String rename(String packageName, String alias, String fullName) {
		if (StringUtils.isNotBlank(alias))
			return packageName.replace(alias, fullName);
		else return packageName;
	}

	public static String relativize(String prefix, String packageName) {
		int pos = packageName.indexOf(prefix);
		if (pos == 0 && packageName.charAt(prefix.length())=='.') { // prefix must match a name border
			return packageName.substring(prefix.length()+1);
		}
		return packageName; // no need to relativize
	}

	public static boolean isSamePackage(String fullName, String packageToSearch) {
		return fullName.endsWith(packageToSearch);
	}

	public static String getPackage(String className) {
		int lastPoint = className.lastIndexOf('.');
		if (lastPoint > 0)
			return className.substring(0, lastPoint);
		else return "";			
	}

}
