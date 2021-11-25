package org.EngDrom.EngDrom.conf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.EngDrom.EngDrom.EngDromConfig;

public class UserPreferences {

	public String buildversion_major      = EngDromConfig.MAJOR;
	public String buildversion_minor      = EngDromConfig.MINOR;
	public String buildversion_subversion = EngDromConfig.SUBVERSION;
	public String buildversion_release    = EngDromConfig.RELEASE_TYPE;
	
	public String graphics_engine         = "OpenGL";
	
	public static final Pattern category_pattern = Pattern.compile("\\[[a-zA-Z]*\\]");
	public static final Pattern      set_pattern = Pattern.compile("[a-zA-Z_]*=[a-zA-Z0-9]*");
	public static final Pattern     name_pattern = Pattern.compile("[a-zA-Z]*_[a-zA-Z_]*");
	
	public UserPreferences(String filepath) {
		try {
			File file = new File(filepath);
			if (!file.exists()) file.createNewFile();
			
			FileReader     df_reader = new FileReader(file);
			BufferedReader reader    = new BufferedReader(df_reader);
			
			String message  = "";
			String category = "";
			while ( ( message = reader.readLine() ) != null ) {
				if   (category_pattern.matcher(message).matches()) {
					category = message.substring(1, message.length() - 1);
				} else if (set_pattern.matcher(message).matches()) {
					String[] splited_message = message.split("=");
					String   field           = splited_message[0];
					
					String   value           = message.replaceFirst(field + "=", "");
					
					Field ref_field = UserPreferences.class.getField(category + "_" + field);
					ref_field.set(this, value);
				}
			}
			
			df_reader.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void save(String filepath) {
		try {
			Field[] fields = UserPreferences.class.getDeclaredFields();
			HashMap<String, HashMap<String, String>> fields_by_category;
			fields_by_category = new HashMap<String, HashMap<String, String>>();
			
			for (Field field:fields) {
				boolean isStatic = Modifier.isStatic(field.getModifiers());
				boolean isFinal  = Modifier.isFinal( field.getModifiers());
				
				if ((!isFinal) && (!isStatic) && name_pattern.matcher(field.getName()).matches()) {
					int eq_idx        = field.getName().indexOf("_");
					String category   = field.getName().substring(0, eq_idx);
					String field_name = field.getName().substring(eq_idx + 1);
					
					if (!fields_by_category.containsKey(category))
						fields_by_category.put(category, new HashMap<String, String>());
				
					fields_by_category.get(category).put(field_name, (String) field.get(this));
				}
			}
			
			StringBuffer strbf = new StringBuffer();
			for (Entry<String, HashMap<String, String>> fields_by_entry:fields_by_category.entrySet()) {
				strbf.append(fields_by_entry.getKey());
				strbf.append("\n");
				
				for (Entry<String, String> field:fields_by_entry.getValue().entrySet()) {
					strbf.append(field.getKey());
					strbf.append("=");
					strbf.append(field.getValue());
					strbf.append("\n");
				}
				
				strbf.append("\n");
			}
			
			File       file = new File(filepath);
			FileWriter fw   = new FileWriter(file);
			fw.write(strbf.toString());
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
