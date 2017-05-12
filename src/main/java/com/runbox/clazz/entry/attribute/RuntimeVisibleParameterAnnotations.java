package com.runbox.clazz.entry.attribute;

import java.util.List;

public class RuntimeVisibleParameterAnnotations extends RuntimeParameterAnnotations {

    public RuntimeVisibleParameterAnnotations(long offset) {
        super(offset, "RuntimeVisibleParameterAnnotations");
    }
    
    public RuntimeVisibleParameterAnnotations(long offset, List<Annotations.Annotation[]> parameters) {
        super(offset, "RuntimeVisibleParameterAnnotations", parameters);
    }
}
