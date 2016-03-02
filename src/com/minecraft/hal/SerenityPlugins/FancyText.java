package com.minecraft.hal.SerenityPlugins;

public class FancyText {

	public static String RUN_COMMAND = "run_command";
	public static String SUGGEST_COMMAND = "suggest_command";
	public static String OPEN_URL = "open_url";
	public static String SHOW_TEXT = "show_text";

	public static String GenerateFancyText(String text, String clickAction,
			String clickValue, String hoverAction, String hoverValue) {
		String val = "{\"text\":\"" + text + "\"";
		if (clickAction != null) {
			val += ",\"clickEvent\":{\"action\":\"" + clickAction + "\",\"value\":\""
					+ clickValue + "\"}";
		}
		if (hoverAction != null) {
			val += ",\"hoverEvent\":{\"action\":\"" + hoverAction + "\",\"value\":\""
					+ hoverValue + "\"}";
		}
		val += "}";
		return val;
	}
}
