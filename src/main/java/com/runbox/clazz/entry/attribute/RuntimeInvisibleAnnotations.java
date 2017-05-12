package com.runbox.clazz.entry.attribute;

public class RuntimeInvisibleAnnotations extends RuntimeAnnotations {

    public RuntimeInvisibleAnnotations(long offset) {
        super(offset, "RuntimeInvisibleAnnotations");
    }
    
    public RuntimeInvisibleAnnotations(long offset, Annotation[] annotations) {
        super(offset, "RuntimeInvisibleAnnotations", annotations);
    }
}
