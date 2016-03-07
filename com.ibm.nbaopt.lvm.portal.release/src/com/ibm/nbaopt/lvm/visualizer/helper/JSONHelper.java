/***********************************************
 * Licensed Materials - Property of IBM
 * 
 * 6949-31G
 * 
 * (C) Copyright IBM Corp. 2007 All Rights Reserved. (C) Copyright State of New
 * York 2002 All Rights Reserved.
 * 
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp.
 ***********************************************/

package com.ibm.nbaopt.lvm.visualizer.helper;

import java.text.DecimalFormat;
import java.util.List;

import com.ibm.json.java.JSONArray;

/**
 * @author hammer
 * @date Aug 12, 2013
 * Description:
 */
public class JSONHelper {
	
	/**
	 * @author hammer
	 * @param list
	 * @return
	 */
	public static JSONArray serializeList(List list) {
		JSONArray arr = new JSONArray();
		for (Object o : list) {
			arr.add(o);
		}
		return arr;

	}

	/**
	 * @author hammer
	 * @param params
	 * @return
	 */
	public static JSONArray serializeList(Object... params) {
		JSONArray arr = new JSONArray();
		for (Object o : params) {
			arr.add(o);
		}
		return arr;

	}
	
	/**
	 * Replace one decimal place number to two decimal places in the rule string.
	 * @param rule the rule string
	 * @return contain all of the two decimal places numbers in the rule string
	 */
	public static String showTwoDecimalPlacesNumber(String rule)
	{
		StringBuffer newRuleString = new StringBuffer();
		DecimalFormat dformat = new DecimalFormat("0.00"); 
		String[] rulesArray = rule.split("AND");
		double dValue;
		for (int i = 0; i < rulesArray.length; i++)
		{
			
			if (rulesArray[i].contains("is"))
			{
				newRuleString.append(rulesArray[i]);
			}
			else
			{
				String[] rulesPortion = rulesArray[i].split(" ");
				for (int j = 0; j < rulesPortion.length; j++)
				{
					try
					{
						dValue = Double.valueOf(rulesPortion[j]);
						newRuleString.append(dformat.format(dValue));
					}
					catch(NumberFormatException e)
					{
						newRuleString.append(rulesPortion[j]);
					}
					if (j != rulesPortion.length - 1)
					{
						newRuleString.append(" ");
					}
				}
			}
			if (i != rulesArray.length - 1)
			{
				newRuleString.append(" ");
				newRuleString.append("AND");
				newRuleString.append(" ");
			}
		}
		return newRuleString.toString().trim();
	}

}
