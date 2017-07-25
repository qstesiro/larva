package com.runbox.clazz.reader;

import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import java.util.Map;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

import com.runbox.clazz.entry.Version;
import com.runbox.clazz.entry.Class;
import com.runbox.clazz.entry.Interface;
import com.runbox.clazz.entry.constant.Constant;

public class ClassReader extends Reader {

    public ClassReader(DataInputStream stream) throws Exception {
        super(stream, null);
    }               
    
    private long magic = 0;    
    private Version version = null;
    private int flags = 0;
    private Class clazz = null;        
    private Interface interfaces = null;
    
    private FieldReader fields = null;
    private MethodReader methods = null;
    private AttributeReader attributes = null;
    
    @Override
    protected ClassReader load() throws Exception {
        magic = readU4(); 
        version = new Version(readU2(), readU2());         
        constants(new ConstantReader(stream(), readU2()).load());        
        flags = readU2();
        clazz = new Class(readU2(), readU2());
        interfaces = loadInterfaces();
        fields = new FieldReader(stream(), readU2(), constants()).load();
        methods = new MethodReader(stream(), readU2(), constants()).load();
        attributes = new AttributeReader(stream(), readU2(), constants()).load();
        return this;
    }                
    
    private Interface loadInterfaces() throws Exception {        
        int length = readU2(); 
        if (0 < length) {
            int[] array = new int[length];
            for (int i = 0; i < length; ++i)  {
                array[i] = readU2();
            }
            return new Interface(array);
        }
        return null;
    }    

    @Override
    public JsonObjectBuilder toJson() {
        JsonObjectBuilder builder = super.toJson()
            .add("magic", String.format("%4x", (int)magic))
            .add("major", version.major())
            .add("minor", version.minor())
            .add("constants", constants().toJson())
            .add("flags", flags)
            .add("this", clazz.thisClass())
            .add("super", clazz.superClass());
        if (null != interfaces) {
            JsonArrayBuilder array = Json.createArrayBuilder();
            for (int i = 0; i < interfaces.get().length; ++i) {
                array.add(interfaces.get()[i]);
            }
            builder.add("interfaces", Json.createObjectBuilder().add("length", interfaces.get().length).add("array", array));
        }
		if (null != fields) {
			JsonObject object = fields.toJson().build();
			if (!object.isEmpty()) builder.add("fields", object);
		}
		if (null != methods) {
			JsonObject object = methods.toJson().build();
			if (!object.isEmpty()) builder.add("methods", object);
		}
		if (null != attributes) {
			JsonObject object = attributes.toJson().build();
			if (!object.isEmpty()) builder.add("attruites", object);
		}
        return builder;
    }
    
    private static class AccessFlags {

        private static final int ACC_PUBLIC = 0x0001;
        private static final int ACC_FINAL = 0x0010;
        private static final int ACC_SUPER = 0x0020;
        private static final int ACC_INTERFACE = 0x0200;
        private static final int ACC_ABSTRACT = 0x0400;
        private static final int ACC_SYNTHETIC = 0x1000;
        private static final int ACC_ANNOTATION = 0x2000;
        private static final int ACC_ENUM = 0x4000;
        
        private static Map<Integer, String> flags = new HashMap<Integer, String>() {{
                put(ACC_PUBLIC, "ACC_PUBLIC");
                put(ACC_FINAL, "ACC_FINAL");
                put(ACC_SUPER, "ACC_SUPER");
                put(ACC_INTERFACE, "ACC_INTERFACE");
                put(ACC_ABSTRACT, "ACC_ABSTRACT");
                put(ACC_SYNTHETIC, "ACC_SYNTHETIC");
                put(ACC_ANNOTATION, "ACC_ANNOTATION");
                put(ACC_ENUM, "ACC_ENUM");                
            }};

        protected static String get(int flags) {
            String string = "";
            for (int flag : AccessFlags.flags.keySet()) {
                if (flag == (flags & flag)) {                    
                    string += AccessFlags.flags.get(flag) + " ";
                }
            }
            return string;            
        }
    }    
}
