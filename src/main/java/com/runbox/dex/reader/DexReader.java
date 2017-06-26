package com.runbox.dex.reader;

import java.nio.channels.FileChannel;

public class DexReader extends Reader {

    public DexReader(FileChannel channel) throws Exception {
        super(channel, null, null);
    }
   
    @Override
    protected DexReader load() throws Exception {
		final long MAP_OFFSET = 0x34;
		position(MAP_OFFSET); position(readU4());
		for (Map map : load(readU4())) {
			switch (map.type()) {
			case TYPE_HEADER_ITEM: headerReader = new HeaderReader(channel(), map, this).load(); break;
			case TYPE_STRING_ID_ITEM: stringReader = new StringReader(channel(), map, this).load(); break;
			case TYPE_TYPE_ID_ITEM: typeReader = new TypeReader(channel(), map, this).load(); break;
			case TYPE_PROTO_ID_ITEM: protoReader = new ProtoReader(channel(), map, this).load(); break;
			case TYPE_FIELD_ID_ITEM: fieldReader = new FieldReader(channel(), map, this).load(); break;
			case TYPE_METHOD_ID_ITEM: methodReader = new MethodReader(channel(), map, this).load(); break;
			default: System.out.println(type(map.type()));
			}
		}
        return this;
    }

	private Map[] load(int count) throws Exception {
		Map[] maps = new Map[count];
		int i = 0; while (i < count) {
			maps[i++] = new Map(readU2(), readU2(), readU4(), readU4());
		}
		return maps;
	}
	
	private HeaderReader headerReader = null;

	public HeaderReader headerReader() {
		return headerReader;
	}

	private StringReader stringReader = null;
	
	public StringReader stringEntryReader() {
		return stringReader;
	}

	private TypeReader typeReader = null;

	public TypeReader typeReader() {
		return typeReader;
	}
	
	private ProtoReader protoReader = null;

	public ProtoReader protoReader() {
		return protoReader;
	}

	private FieldReader fieldReader = null;

	public FieldReader fieldReader() {
		return fieldReader;
	}

	private MethodReader methodReader = null;

	public MethodReader methodReader() {
		return methodReader;
	}   			
	
	private static final int TYPE_HEADER_ITEM = 0x0000;
	private static final int TYPE_STRING_ID_ITEM = 0x0001;
	private static final int TYPE_TYPE_ID_ITEM = 0x0002;
	private static final int TYPE_PROTO_ID_ITEM = 0x0003;   
	private static final int TYPE_FIELD_ID_ITEM = 0x0004;   
	private static final int TYPE_METHOD_ID_ITEM = 0x0005;  
	private static final int TYPE_CLASS_DEF_ITEM = 0x0006;
	private static final int TYPE_CALL_SITE_ID_ITEM = 0x0007;  
	private static final int TYPE_METHOD_HANDLE_ITEM = 0x0008;  
	private static final int TYPE_MAP_LIST = 0x1000;       
	private static final int TYPE_TYPE_LIST = 0x1001;
	private static final int TYPE_ANNOTATION_SET_REF_LIST = 0x1002;
	private static final int TYPE_ANNOTATION_SET_ITEM = 0x1003;
	private static final int TYPE_CLASS_DATA_ITEM = 0x2000;       
	private static final int TYPE_CODE_ITEM = 0x2001;       
	private static final int TYPE_STRING_DATA_ITEM = 0x2002;
	private static final int TYPE_DEBUG_INFO_ITEM = 0x2003;      
	private static final int TYPE_ANNOTATION_ITEM = 0x2004;
	private static final int TYPE_ENCODED_ARRAY_ITEM = 0x2005;
	private static final int TYPE_ANNOTATIONS_DIRECTORY_ITEM = 0x2006;

	private String type(int type) {
		switch (type) {
		case TYPE_HEADER_ITEM: return "TYPE_HEADER_ITEM";
		case TYPE_STRING_ID_ITEM: return "TYPE_STRING_ID_ITEM";
		case TYPE_TYPE_ID_ITEM: return "TYPE_TYPE_ID_ITEM";
		case TYPE_PROTO_ID_ITEM: return "TYPE_PROTO_ID_ITEM";
		case TYPE_FIELD_ID_ITEM: return "TYPE_FIELD_ID_ITEM";
		case TYPE_METHOD_ID_ITEM: return "TYPE_METHOD_ID_ITEM";
		case TYPE_CLASS_DEF_ITEM: return "TYPE_CLASS_DEF_ITEM";
		case TYPE_CALL_SITE_ID_ITEM: return "TYPE_CALL_SITE_ID_ITEM";
		case TYPE_METHOD_HANDLE_ITEM: return "TYPE_METHOD_HANDLE_ITEM";
		case TYPE_MAP_LIST: return "TYPE_MAP_LIST";
		case TYPE_TYPE_LIST: return "TYPE_TYPE_LIST";
		case TYPE_ANNOTATION_SET_REF_LIST: return "TYPE_ANNOTATION_SET_REF_LIST";
		case TYPE_ANNOTATION_SET_ITEM: return "TYPE_ANNOTATION_SET_ITEM";
		case TYPE_CLASS_DATA_ITEM: return "TYPE_CLASS_DATA_ITEM";
	    case TYPE_CODE_ITEM: return "TYPE_CODE_ITEM";
		case TYPE_STRING_DATA_ITEM: return "TYPE_STRING_DATA_ITEM";
		case TYPE_DEBUG_INFO_ITEM: return "TYPE_DEBUG_INFO_ITEM";
		case TYPE_ANNOTATION_ITEM: return "TYPE_ANNOTATION_ITEM";
		case TYPE_ENCODED_ARRAY_ITEM: return "TYPE_ENCODED_ARRAY_ITEM";
		case TYPE_ANNOTATIONS_DIRECTORY_ITEM: return "TYPE_ANNOTATIONS_DIRECTORY_ITEM";
		default: return "unknow";
		}
	}
}
