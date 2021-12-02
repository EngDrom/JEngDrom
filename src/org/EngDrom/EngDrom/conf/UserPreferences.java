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

/**
 * 
 * User Preferences is a file configuration that represents
 * The project you are working on in the engine
 * 
 * A field of the configuration file is represented as :
 * <category>_<field>
 * TODO rename it to project preferences
 * @author MrThimote, Itai12
 *
 */
public class UserPreferences {
	
	// Project configuration
	public String project_height          = "500";
	public String project_width           = "500";
	public int getProjectHeight() {
		try {
			return Integer.parseInt(project_height);
		} catch (Exception e) { return 500; }
	}
	public int getProjectWidth() {
		try {
			return Integer.parseInt(project_width);
		} catch (Exception e) { return 500; }
	}
	
	// BuildVersion configuration
	public String buildversion_major      = EngDromConfig.MAJOR;
	public String buildversion_minor      = EngDromConfig.MINOR;
	public String buildversion_subversion = EngDromConfig.SUBVERSION;
	public String buildversion_release    = EngDromConfig.RELEASE_TYPE;
	
	// Graphics configuration
	public String graphics_engine         = "OpenGL";
	public String graphics_shader_version = "";
	
	// External parameters that will not modify the configuration file
	// Because they are either static or final
	public static final Pattern category_pattern = Pattern.compile("\\[[a-zA-Z]*\\]");
	public static final Pattern      set_pattern = Pattern.compile("[a-zA-Z_]*=[a-zA-Z0-9]*");
	public static final Pattern     name_pattern = Pattern.compile("[a-zA-Z]*_[a-zA-Z_]*");
	public final boolean had_error;
	
	// Create UserPreferences from file
	public UserPreferences(String filepath) {
		// We need to store a copy of had_error before setting it to avoid compilation errors
		boolean had_err = false;
		try {
			// Create file
			File file = new File(filepath);
			
			// Create reader
			FileReader     df_reader = new FileReader(file);
			BufferedReader reader    = new BufferedReader(df_reader);
			
			// Read file
			String message  = "";
			String category = "";
			while ( ( message = reader.readLine() ) != null ) {
				// A category line is of form "[<category>]"
				if   (category_pattern.matcher(message).matches()) {
					category = message.substring(1, message.length() - 1);
				// A set field line is of form <field>=<value>
				} else if (set_pattern.matcher(message).matches()) {
					// Get data from line
					String[] splited_message = message.split("=");
					String   field           = splited_message[0];
					
					String   value           = message.replaceFirst(field + "=", "");
					
					// Store data in field if it exists
					try {
						Field ref_field = UserPreferences.class.getField(category + "_" + field);
						ref_field.set(this, value);
					} catch ( Exception e ) {
						System.out.println("Could not found the field " + field + " in the category " + category + ". Look at your config file if there are no problems.");
					}
				}
			}
			
			// Close readers
			df_reader.close();
			reader.close();
		} catch (Exception e) {
			had_err = true;
		}
		
		this.had_error = had_err;
	}
	
	// Save configuration to file
	public void save(String filepath) {
		try {
			// Get field
			Field[] fields = UserPreferences.class.getDeclaredFields();
			
			// Representation of the configuration
			HashMap<String, HashMap<String, String>> fields_by_category;
			fields_by_category = new HashMap<String, HashMap<String, String>>();
			
			// Store for each field (non static, non final and represented in <category>_<field> way)
			// Store the field and value in the map corresponding to that category
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
			
			// Store for each category the category name and for each field the field followed by value itself
			StringBuffer strbf = new StringBuffer();
			for (Entry<String, HashMap<String, String>> fields_by_entry:fields_by_category.entrySet()) {
				strbf.append("[");
				strbf.append(fields_by_entry.getKey());
				strbf.append("]");
				strbf.append("\n");
				
				for (Entry<String, String> field:fields_by_entry.getValue().entrySet()) {
					strbf.append(field.getKey());
					strbf.append("=");
					strbf.append(field.getValue());
					strbf.append("\n");
				}
				
				strbf.append("\n");
			}
			
			// Store in the file the data
			File       file = new File(filepath);
			if (!file.exists()) file.createNewFile();
			
			FileWriter fw   = new FileWriter(file);
			fw.write(strbf.toString());
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
