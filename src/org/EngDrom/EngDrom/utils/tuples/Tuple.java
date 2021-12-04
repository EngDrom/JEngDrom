package org.EngDrom.EngDrom.utils.tuples;

class TupleClassException extends RuntimeException {
	public TupleClassException(Class<?> expected, Class<?> cls) {
		super("The class you wanted to set or get could not be reached, expected a " + expected.toString() + ", found a " + cls.toString());
	}
}

public class Tuple {

	private Class<?>[] classes;
	private   Object[] objects;
	private boolean cls_frozen;
	
	public Tuple(boolean cls_frozen, Object... objs) {
		this.cls_frozen = cls_frozen;
		classes         = new Class<?>[objs.length];
		objects         = objs;
		
		for (int idx = 0; idx < objects.length; idx ++) {
			classes[idx] = objects[idx].getClass();
		}
	}
	
	public void set(int idx, Object obj) {
		if (cls_frozen && obj.getClass() != classes[idx])
			throw new TupleClassException(classes[idx], obj.getClass());
		
		objects[idx] = obj;
		classes[idx] = obj.getClass();
	}
	
	public Object get(Class<?> cls_expected, int idx) {
		if (cls_expected != classes[idx])
			throw new TupleClassException(classes[idx], cls_expected);
		
		return objects[idx];
	}
	
	public int get_int(int idx) {
		return (int) this.get(int.class, idx);
	}
	
	public String get_str(int idx) {
		return (String) this.get(String.class, idx);
	}
	
	public float get_float(int idx) {
		return (float) this.get(float.class, idx);
	}
	
}
