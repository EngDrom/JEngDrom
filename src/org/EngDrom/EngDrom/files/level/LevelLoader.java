package org.EngDrom.EngDrom.files.level;

/**
 * Load .lvl files
 * 
 * Files specifications : 
 * 
 * bytes 0-3, integer representing level version V
 * bytes 4-7, integer representing the number of unique meshes M
 *   (a unique mesh is a type of mesh)
 * 
 * for each integer from 0 to M
 *   relative bytes 0-3, id representing the unique mesh
 *   relative bytes 4-7, length of string of the class of the unique mesh C
 *   relative bytes 8-(8+C) string for class (.mesh file)
 * 
 * relative bytes 0-3 number of mesh instances MI
 * 
 * for each integer from 0 to MI
 *   relative bytes 0-3, id of the unique mesh for this instance
 *   relative bytes 4-7 parameter count PC
 *   for each integer from 0 to PC
 *   	relative bytes 0-3 parameter id
 *   	relative bytes 4-7 length of the parameter string C
 *      relative bytes 8-(8+C) string for the value of the parameter
 * 
 * @author MrThimote, itai12
 *
 */

public class LevelLoader {

	public Level load_version_0 () {
		return null;
	}
	
}
