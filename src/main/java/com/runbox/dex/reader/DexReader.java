package com.runbox.dex.reader;

import java.util.List;
import java.util.LinkedList;

import java.nio.channels.FileChannel;

public class DexReader extends Reader {

    public DexReader(FileChannel channel, DexReader reader) throws Exception {
        super(channel, reader);
    }
    
    @Override
    protected DexReader load() throws Exception {
		final long MAP_OFFSET = 0x34;
		position(MAP_OFFSET);
		position(readU4());
		for (Item item : load(readU4())) {
			switch (item.type()) {
			case HEADER: headerReader = new HeaderReader(channel(), item, this).load(); break;
			case STRING_ID: stringReader = new StringReader(channel(), item, this).load(); break;
			default: System.out.println(type(item.type()));
			}
		}
        return this;
    }
	
	private HeaderReader headerReader = null;

	public HeaderReader headerReader() {
		return headerReader;
	}

	private StringReader stringReader = null;
	
	private StringReader stringReader() {
		return stringReader;
	}
	
	private List<Item> load(long count) throws Exception {
		List<Item> items = new LinkedList<Item>();
		long i = 0; while (i++ < count) {
			items.add(new Item(readU2(), readU2(), readU4(), readU4()));
		}
		return items;
	}		
	
	private static final int HEADER = 0x0000;
	private static final int STRING_ID = 0x0001;
	private static final int TYPE_ID = 0x0002;
	private static final int PROTO_ID = 0x0003;   
	private static final int FIELD_ID = 0x0004;   
	private static final int METHOD_ID = 0x0005;  
	private static final int CLASS_DEFINE = 0x0006;
	private static final int MAP_LIST = 0x1000;       
	private static final int TYPE_LIST = 0x1001;
	private static final int ANNOTATION_SET_REF_LIST = 0x1002;
	private static final int ANNOTATION_SET = 0x1003;
	private static final int CLASS_DATA = 0x2000;       
	private static final int CODE = 0x2001;       
	private static final int STRING_DATA = 0x2002;
	private static final int DEBUG_INFO = 0x2003;      
	private static final int ANNOTATION = 0x2004;
	private static final int ENCODE_ARRAY = 0x2005;
	private static final int ANNOTATION_DIRECTORY = 0x2006;

	private String type(int type) {
		switch (type) {
		case HEADER: return "kDexTypeHeaderItem";
		case STRING_ID: return "kDexTypeStringIdItem";
		case TYPE_ID: return "kDexTypeTypeIdItem";
		case PROTO_ID: return "kDexTypeProtoIdItem";
		case FIELD_ID: return "kDexTypeFieldIdItem";
		case METHOD_ID: return "kDexTypeMethodIdItem";
		case CLASS_DEFINE: return "kDexTypeClassDefItem";
		case MAP_LIST: return "kDexTypeMapList";
		case TYPE_LIST: return "kDexTypeTypeList";
		case ANNOTATION_SET_REF_LIST: return "kDexTypeAnnotationSetRefList";
		case ANNOTATION_SET: return "kDexTypeAnnotationSetItem";
		case CLASS_DATA: return "kDexTypeClassDataItem";
		case CODE: return "kDexTypeCodeItem";
		case STRING_DATA: return "kDexTypeStringDataItem";
		case DEBUG_INFO: return "kDexTypeDebugInfoItem";
		case ANNOTATION: return "kDexTypeAnnotationItem";
		case ENCODE_ARRAY: return "kDexTypeEncodeArrayItem";
		case ANNOTATION_DIRECTORY: return "kDexTypeAnnotationsDirectoryItem";
		default: return "unknow";
		}
	}
}
