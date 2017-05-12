package com.runbox.clazz.entry.attribute;

import java.util.List;

public class RuntimeInvisibleParameterAnnotations extends RuntimeParameterAnnotations {

    public RuntimeInvisibleParameterAnnotations(long offset) {
        super(offset, "RuntimeInvisibleParameterAnnotations");
    }
    
    public RuntimeInvisibleParameterAnnotations(long offset, List<Annotations.Annotation[]> parameters) {
        super(offset, "RuntimeInvisibleParameterAnnotations", parameters);
    }
}
