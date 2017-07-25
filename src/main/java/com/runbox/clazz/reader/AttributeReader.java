package com.runbox.clazz.reader;

import java.io.DataInputStream;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import com.runbox.clazz.entry.attribute.Attribute;
import com.runbox.clazz.entry.attribute.ConstantValue;
import com.runbox.clazz.entry.attribute.Code;
import com.runbox.clazz.entry.attribute.StackMapTable;
import com.runbox.clazz.entry.attribute.Exceptions;
import com.runbox.clazz.entry.attribute.InnerClasses;
import com.runbox.clazz.entry.attribute.EnclosingMethod;
import com.runbox.clazz.entry.attribute.Synthetic;
import com.runbox.clazz.entry.attribute.Signature;
import com.runbox.clazz.entry.attribute.SourceFile;
import com.runbox.clazz.entry.attribute.SourceDebugExtension;
import com.runbox.clazz.entry.attribute.LineNumberTable;
import com.runbox.clazz.entry.attribute.LocalTable;
import com.runbox.clazz.entry.attribute.LocalVariableTable;
import com.runbox.clazz.entry.attribute.LocalVariableTypeTable;
import com.runbox.clazz.entry.attribute.Deprecated;
import com.runbox.clazz.entry.attribute.Annotations;
import com.runbox.clazz.entry.attribute.RuntimeAnnotations;
import com.runbox.clazz.entry.attribute.RuntimeVisibleAnnotations;
import com.runbox.clazz.entry.attribute.RuntimeInvisibleAnnotations;
import com.runbox.clazz.entry.attribute.RuntimeParameterAnnotations;
import com.runbox.clazz.entry.attribute.RuntimeVisibleParameterAnnotations;
import com.runbox.clazz.entry.attribute.RuntimeInvisibleParameterAnnotations;
import com.runbox.clazz.entry.attribute.RuntimeVisibleTypeAnnotations;
import com.runbox.clazz.entry.attribute.AnnotationDefault;
import com.runbox.clazz.entry.attribute.BootstrapMethods;
import com.runbox.clazz.entry.attribute.MethodParameters;

import com.runbox.clazz.entry.constant.Constant;
import com.runbox.clazz.entry.constant.UTF8Constant;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;

public class AttributeReader extends Reader {

    public AttributeReader(DataInputStream stream, int count, ConstantReader reader) throws Exception {
        super(stream, reader);
		attributes = new Attribute[count];
    }

    private Attribute[] attributes = null;

    public Attribute[] attributes() {
        return attributes;
    }   

    public Attribute get(int index) {
        if (null != attributes) {
            if (index < attributes.length) {
                return attributes[index];
            }
        }
        return null;
    }

    public int count() {
        if (null != attributes) {
            return attributes.length;
        }
        return 0;
    }
    
    @Override
    protected AttributeReader load() throws Exception {                            
		for (int i = 0; i < attributes.length; ++i) {
			String name = toName(readU2());
			attributes[i] = load(name, readU4());
		}        
        return this;
    }

    private Attribute load(String name, long length) throws Exception {
        if (name.equals("ConstantValue")) {
            return loadConstantValue();                    
        } else if (name.equals("Code")) {
            return loadCode();
        } else if (name.equals("StackMapTable")) {
            // return loadStackMapTable();
			skip(length); return new Attribute("StackMapTable");
        } else if (name.equals("Exceptions")) {
            return loadExceptions();                    
        } else if (name.equals("InnerClasses")) {
            return loadInnerClasses();
        } else if (name.equals("EnclosingMethod")) {
            // return loadEnclosingMethod();
			skip(length); return new Attribute("EnclosingMethod");
        } else if (name.equals("Synthetic")) {
            return loadSynthetic();
        } else if (name.equals("Signature")) {
            return loadSignature();
        } else if (name.equals("SourceFile")) {
            return loadSourceFile();
        } else if (name.equals("SourceDebugExtension")) {
            return loadSourceDebugExtension(length);
        } else if (name.equals("LineNumberTable")) {
            return loadLineNumberTable();
        } else if (name.equals("LocalVariableTable")) {
            return loadLocalVariableTable();
        } else if (name.equals("LocalVariableTypeTable")) {
            return loadLocalVariableTypeTable();
        } else if (name.equals("Deprecated")) {
            return loadDeprecated();
        } else if (name.equals("RuntimeVisibleAnnotations")) {
            // return loadRuntimeVisibleAnnotations();
			skip(length);
			return new Attribute("RuntimeVisibleAnnotations");
        } else if (name.equals("RuntimeInvisibleAnnotations")) {
            // return loadRuntimeInvisibleAnnotations();
			skip(length);
			return new Attribute("RuntimeInvisibleAnnotations");
        } else if (name.equals("RuntimeVisibleParameterAnnotations")) {
            // return loadRuntimeVisibleParameterAnnotations();
			skip(length);
			return new Attribute("RuntimeVisibleParameterAnnotations");
        } else if (name.equals("RuntimeInvisibleParameterAnnotations")) {
            // return loadRuntimeInvisibleParameterAnnotations();
			skip(length);
			return new Attribute("RuntimeInvisibleParameterAnnotations");
        } else if (name.equals("RuntimeVisibleTypeAnnotations")) {
            // return loadRuntimeVisibleTypeAnnotations();
			skip(length);
			return new Attribute("RuntimeVisibleTypeAnnotations");
        } else if (name.equals("AnnotationDefault")) {
            // return loadAnnotationDefault();
			skip(length);
			return new Attribute("AnnotationDefault");
        } else if (name.equals("BootstrapMethods")) {
            return loadBootstrapMethods();
        } else if (name.equals("MethodParameters")) {
            return loadMethodParameters();
        } else {
            throw new Exception("unknown attribute name" + name);
        }
    }

    private ConstantValue loadConstantValue() throws Exception {
        return new ConstantValue(readU2());
    }	
	
    private Code loadCode() throws Exception {        
        int stack = readU2();
        int locals = readU2();
        BytecodeReader codes = new BytecodeReader(stream(), (int)readU4(), constants()).load();
        int length = readU2(); Code.Exception[] exceptions = null;
        if (0 < length) {
            exceptions = new Code.Exception[length];
            for (int i = 0; i < exceptions.length; ++i) {
                exceptions[i] = new Code.Exception(readU2(), readU2(), readU2(), readU2());            
            }
        }
        AttributeReader attributes = new AttributeReader(stream(), readU2(), constants()).load();
        return new Code(stack, locals, codes, exceptions, attributes);
    }
    
    private StackMapTable loadStackMapTable() throws Exception {
        int length = readU2(); StackMapTable.Frame[] frames = null;        
        if (0 < length) {
            frames = new StackMapTable.Frame[length];
            for (int i = 0; i < frames.length; ++i) {
                frames[i] = loadFrame();            
            }
        }
        return new StackMapTable(frames);
    }

    private StackMapTable.Frame loadFrame() throws Exception {
        short type = readU1();
        if (StackMapTable.Frame.TYPE_SAME_START <= type && StackMapTable.Frame.TYPE_SAME_END >= type) {
            return new StackMapTable.Same(type);
        } else if (StackMapTable.Frame.TYPE_SAME_LOCALS1_START <= type && StackMapTable.Frame.TYPE_SAME_LOCALS1_END >= type) {
            return new StackMapTable.SameLocals1(type, loadVerification());
        } else if (StackMapTable.Frame.TYPE_SAME_LOCALS1_EXTENDED == type) {
            return new StackMapTable.SameLocals1Extended(type, readU2(), loadVerification());
        } else if (StackMapTable.Frame.TYPE_CHOP_START <= type && StackMapTable.Frame.TYPE_CHOP_END >= type) {
            return new StackMapTable.Chop(type, readU2());
        } else if (StackMapTable.Frame.TYPE_SAME_EXTENDED == type) {
            return new StackMapTable.SameExtended(type, readU2());
        } else if (StackMapTable.Frame.TYPE_APPEND_START <= type && StackMapTable.Frame.TYPE_APPEND_END >= type) {
            int offset = readU2();
            int length = type - 251; StackMapTable.Verification[] verifications = null;
            if (0 < length) {
                verifications = new StackMapTable.Verification[length];
                for (int i = 0; i < verifications.length; ++i) {
                    verifications[i] = loadVerification();
                }
            }
            return new StackMapTable.Append(type, readU2(), verifications);
        }
        throw new Exception("invalid stack frame type #" + type);
    }

    private StackMapTable.Verification loadVerification() throws Exception {
        short type = readU1();
        switch (type) {
        case StackMapTable.Verification.TYPE_TOP:
            return new StackMapTable.Top();
        case StackMapTable.Verification.TYPE_INTEGER:
            return new StackMapTable.Integer();
        case StackMapTable.Verification.TYPE_FLOAT:
            return new StackMapTable.Float();
        case StackMapTable.Verification.TYPE_LONG:
            return new StackMapTable.Long();
        case StackMapTable.Verification.TYPE_DOUBLE:
            return new StackMapTable.Double();
        case StackMapTable.Verification.TYPE_NULL:
            return new StackMapTable.Null();
        case StackMapTable.Verification.TYPE_UNINITIALIZED_THIS:
            return new StackMapTable.UnitializedThis();
        case StackMapTable.Verification.TYPE_OBJECT:
            return new StackMapTable.Object(readU2());
        case StackMapTable.Verification.TYPE_UNINITIALIZED:
            return new StackMapTable.Unitialized(readU2());
        default:
            throw new Exception("invalid verification type #" + type);
        }
    }
    
    private Exceptions loadExceptions() throws Exception {
        int length = readU2(); int[] tables = null;        
        if (0 < length) {
            tables = new int[length];
            for (int i = 0; i < tables.length; ++i) {
                tables[i] = readU2();
            }
        }
        return new Exceptions(tables);
    }

    private InnerClasses loadInnerClasses() throws Exception {
        int length = readU2(); InnerClasses.Class[] classes = null;        
        if (0 < length) {
            classes = new InnerClasses.Class[length];
            for (int i = 0; i < classes.length; ++i) {
                classes[i] = new InnerClasses.Class(new int[] {readU2(), readU2(), readU2(), readU2()});            
            }
        }
        return new InnerClasses(classes);
    }

    private EnclosingMethod loadEnclosingMethod() throws Exception {
        return new EnclosingMethod(readU2(), readU2());
    }

    private Synthetic loadSynthetic() throws Exception {
        return new Synthetic();
    }

    private Signature loadSignature() throws Exception {
        return new Signature(readU2());
    }

    private SourceFile loadSourceFile() throws Exception {
        return new SourceFile(readU2());
    }

    private SourceDebugExtension loadSourceDebugExtension(long length) throws Exception {        
        return new SourceDebugExtension(read((int)length));
    }

    private LineNumberTable loadLineNumberTable() throws Exception {        
        int length = readU2(); LineNumberTable.LineNumber[] lines = null;        
        if (0 < length) {
            lines = new LineNumberTable.LineNumber[length];
            for (int i = 0; i < lines.length; ++i) {
                lines[i] = new LineNumberTable.LineNumber(readU2(), readU2());
            }
        }
        return new LineNumberTable(lines);
    }

    private LocalVariableTable loadLocalVariableTable() throws Exception {
        int length = readU2(); LocalTable.Variable[] variables = null;        
        if (0 < length) {
            variables = new LocalTable.Variable[length];
            for (int i = 0; i < variables.length; ++i) {
                variables[i] = new LocalTable.Variable(readU2(), readU2(), readU2(), readU2(), readU2());            
            }
        }
        return new LocalVariableTable(variables);
    }

    private LocalVariableTypeTable loadLocalVariableTypeTable() throws Exception {
        int length = readU2(); LocalTable.Variable[] variables = null;        
        if (0 < length) {
            variables = new LocalTable.Variable[length];
            for (int i = 0; i < variables.length; ++i) {
                variables[i] = new LocalTable.Variable(readU2(), readU2(), readU2(), readU2(), readU2());            
            }
        }
        return new LocalVariableTypeTable(variables);
    }

    private Deprecated loadDeprecated() throws Exception {
        return new Deprecated();
    }

    private RuntimeVisibleAnnotations loadRuntimeVisibleAnnotations() throws Exception {        
        return new RuntimeVisibleAnnotations(loadAnnotationArray());
    }

    private RuntimeInvisibleAnnotations loadRuntimeInvisibleAnnotations() throws Exception {        
        return new RuntimeInvisibleAnnotations(loadAnnotationArray());
    }

    private Annotations.Annotation[] loadAnnotationArray() throws Exception {
        int length = readU2(); Annotations.Annotation[] annotations = null;        
        if (0 < length) {
            annotations = new Annotations.Annotation[length];
            for (int i = 0; i < annotations.length; ++i) {
                annotations[i] = loadAnnotation();
            }
        }
        return annotations;
    }

    private RuntimeVisibleParameterAnnotations loadRuntimeVisibleParameterAnnotations() throws Exception {        
        return new RuntimeVisibleParameterAnnotations(loadAnnotationList());                
    }
    
    private RuntimeInvisibleParameterAnnotations loadRuntimeInvisibleParameterAnnotations() throws Exception {        
        return new RuntimeInvisibleParameterAnnotations(loadAnnotationList());
    }

    private List<Annotations.Annotation[]> loadAnnotationList() throws Exception {
        short size = readU1(); List<Annotations.Annotation[]> list = null;        
        if (0 < size) {
            list = new ArrayList<Annotations.Annotation[]>();
            for (int i = 0; i < size; ++i) {
                int length = readU2(); Annotations.Annotation[] annotations = null;                
                if (0 < length) {
                    annotations = new Annotations.Annotation[length]; 
                    for (int j = 0; j < annotations.length; ++j) {
                        annotations[j] = loadAnnotation();
                    }
                }
                list.add(annotations);
            }
        }
        return list;
    }

    private RuntimeVisibleTypeAnnotations loadRuntimeVisibleTypeAnnotations() throws Exception {
        int length = readU2(); Annotations.TypeAnnotation[] annotations = null;        
        if (0 < length) {
            annotations = new Annotations.TypeAnnotation[length];
            for (int i = 0; i < annotations.length; ++i) {
                annotations[i] = loadTypeAnnotation();
            }
        }
        return new RuntimeVisibleTypeAnnotations(annotations);
    }    
        
    private AnnotationDefault loadAnnotationDefault() throws Exception {
        return new AnnotationDefault(loadValue());
    }    

    private Annotations.Annotation loadAnnotation() throws Exception {
        int type = readU2();
        int length = readU2(); Map<Integer, Annotations.Value> values = null;                
        if (0 < length) {
            values = new HashMap<Integer, Annotations.Value>();
            for (int i = 0; i < length; ++i) {
                values.put(readU2(), loadValue());
            }
        }
        return new Annotations.Annotation(type, values);
    }

    private Annotations.Value loadValue() throws Exception {
        char type = (char)readU1();
        switch (type) {
        case Annotations.Value.TYPE_BYTE:
            return new Annotations.Byte(readU2());
        case Annotations.Value.TYPE_CHAR:
            return new Annotations.Char(readU2());
        case Annotations.Value.TYPE_SHORT:
            return new Annotations.Short(readU2());
        case Annotations.Value.TYPE_INTEGER:
            return new Annotations.Integer(readU2());
        case Annotations.Value.TYPE_FLOAT:
            return new Annotations.Float(readU2());
        case Annotations.Value.TYPE_LONG:
            return new Annotations.Long(readU2());
        case Annotations.Value.TYPE_DOUBLE:
            return new Annotations.Double(readU2());
        case Annotations.Value.TYPE_STRING:
            return new Annotations.String(readU2());
        case Annotations.Value.TYPE_ENUM:
            return new Annotations.Enum(new int[] {readU2(), readU2()});
        case Annotations.Value.TYPE_CLASS:
            return new Annotations.Class(readU2());
        case Annotations.Value.TYPE_ANNOTATION:
            return loadAnnotation();
        case Annotations.Value.TYPE_ARRAY:
            return loadArray();
        default:
            throw new Exception("invalid annotation value type #" + type);
        }
    }

    private Annotations.Array loadArray() throws Exception {
        int length = readU2(); Annotations.Value[] values = null;        
        if (0 < length) {
            values = new Annotations.Value[length];
            for (int i = 0; i < values.length; ++i) {
                values[i] = loadValue();
            }
        }
        return new Annotations.Array(values);
    }

    private Annotations.TypeAnnotation loadTypeAnnotation() throws Exception {
        Annotations.Target target = loadTypeTarget();
        short length = readU1(); Annotations.Path[] paths = null;        
        if (0 < length) {
            paths = new Annotations.Path[length];
            for (int i = 0; i < paths.length; ++i) {
                paths[i] = new Annotations.Path(readU1(), readU1());
            }
        }
        int index = readU2();
        int size = readU2(); Map<Integer, Annotations.Value> values = null;        
        if (0 < size) {
            values = new HashMap<Integer, Annotations.Value>();
            for (int i = 0; i < size; ++i) {
                values.put(readU2(), loadValue());
            }
        }
        return new Annotations.TypeAnnotation(target, paths, index, values);
    }

    private Annotations.Target loadTypeTarget() throws Exception {
        short type = readU1();
        if (Annotations.Target.TYPE_PARAMETER_START <= type && Annotations.Target.TYPE_PARAMETER_END >= type) {
            return new Annotations.Parameter(type, readU1());
        } else if (Annotations.Target.TYPE_SUPER == type) {
            return new Annotations.Super(type, readU2());
        } else if (Annotations.Target.TYPE_PARAMETER_BOUND_START <= type && Annotations.Target.TYPE_PARAMETER_END >= type) {
            return new Annotations.ParameterBound(type, readU1(), readU1());
        } else if (Annotations.Target.TYPE_EMPTY_START <= type && Annotations.Target.TYPE_EMPTY_END >= type) {
            return new Annotations.Empty(type);
        } else if (Annotations.Target.TYPE_FORMAL_PARAMETER == type) {
            return new Annotations.FormalParameter(type, readU1());
        } else if (Annotations.Target.TYPE_THROWS == type) {
            return new Annotations.Throws(type, readU2());
        } else if (Annotations.Target.TYPE_LOCALS_START <= type && Annotations.Target.TYPE_LOCALS_END >= type) {
            int length = readU2(); Annotations.Locals.Variable[] variables = null;            
            if (0 < length) {
                variables = new Annotations.Locals.Variable[length];
                for (int i = 0; i < variables.length; ++i) {
                    variables[i] = new Annotations.Locals.Variable(readU2(), readU2(), readU2());
                }
            }
            return new Annotations.Locals(type, variables);
        } else if (Annotations.Target.TYPE_CATCH == type) {
            return new Annotations.Catch(type, readU2());
        } else if (Annotations.Target.TYPE_OFFSET_START <= type && Annotations.Target.TYPE_OFFSET_END >= type) {
            return new Annotations.Offset(type, readU2());
        } else if (Annotations.Target.TYPE_ARGUMENT_START <= type && Annotations.Target.TYPE_ARGUMENT_END >= type) {
            return new Annotations.Argument(type, readU2(), readU1());
        } else {
            throw new Exception("invalid type annotation target #" + type);
        }
    }

    private BootstrapMethods loadBootstrapMethods() throws Exception {
        int length = readU2(); BootstrapMethods.Method[] methods = null;        
        if (0 < length) {
            methods = new BootstrapMethods.Method[length];
            for (int i = 0; i < methods.length; ++i) {
                int index = readU2();
                length = readU2(); int[] arguments = null;                                
                if (0 < length) {
                    arguments = new int[length];
                    for (int j = 0; j < arguments.length; ++j) {
                        arguments[j] = readU2();
                    }
                }
                methods[i] = new BootstrapMethods.Method(index, arguments);
            }
        }
        return new BootstrapMethods(methods);
    }

    private MethodParameters loadMethodParameters() throws Exception {
        int length = readU2(); MethodParameters.Parameter[] parameters = null;        
        if (0 < length) {
            parameters = new MethodParameters.Parameter[length];
            for (int i = 0; i < parameters.length; ++i) {
                parameters[i] = new MethodParameters.Parameter(readU2(), readU2());
            }
        }
        return new MethodParameters(parameters);
    }
    
    private String toName(int index) throws Exception {
        Constant constant = constants().get(index);
        if (constant instanceof UTF8Constant) {
            return ((UTF8Constant) constant).string();
        }
        throw new Exception("invalid attribute index #" + index + constant);
    }

    @Override
    public JsonObjectBuilder toJson() {
        if (null != attributes)  {
            JsonArrayBuilder builder = Json.createArrayBuilder();
            for (int i = 0; i < attributes.length; ++i) {
                builder.add(attributes[i].toJson());
            }
            return super.toJson().add("length", attributes.length).add("array", builder);
        }
        return super.toJson();
    }
}
