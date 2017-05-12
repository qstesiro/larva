package com.runbox.clazz.entry.attribute;

public class RuntimeVisibleAnnotations extends RuntimeAnnotations {

    public RuntimeVisibleAnnotations(long offset) {
        super(offset, "RuntimeVisibleAnnotation");
    }
    
    public RuntimeVisibleAnnotations(long offset, Annotation[] annotations) {
        super(offset, "RuntimeVisibleAnnotations", annotations);
    }    
}
