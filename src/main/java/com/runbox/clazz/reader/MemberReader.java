package com.runbox.clazz.reader;

import java.io.DataInputStream;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonObject;

public class MemberReader extends Reader {

    public MemberReader(DataInputStream stream, int count, ConstantReader reader) throws Exception {
        super(stream, reader);
		members = new Member[count];
    }
    
    private Member[] members = null;

    public Member[] get() {
        return members;
    }

    public Member get(int index) {
        if (null != members) {
            if (index < members.length) {
                return members[index];
            }
        }
        return null;
    }

    public int count() {
        if (null != members) {
            return members.length;
        }
        return 0;
    }
	
    @Override
    protected MemberReader load() throws Exception {
		if (null != members) {
			for (int i = 0; i < members.length; ++i) {				
				members[i] = new Member(readU2(), readU2(), readU2(),
										new AttributeReader(stream(), readU2(), constants()).load());
			}
		}
        return this;
    }

    @Override
    public JsonObjectBuilder toJson() {
        if (null != members) {
            JsonArrayBuilder builder = Json.createArrayBuilder();
            for (int i = 0; i < members.length; ++i) {
                builder.add(members[i].toJson());
            }
            return super.toJson().add("length", members.length).add("array", builder);
        }
        return super.toJson();
    }

    public class Member {
                                
        public Member(int flags, int name, int descriptor, AttributeReader reader) {
            this.flags = flags;
            this.name = name;
            this.descriptor = descriptor;
			this.reader = reader;
        }

		private int flags = 0;
		
        public int flags() {
            return flags;
        }

		private int name = 0;
		
        public int name() {
            return name;
        }

		private int descriptor = 0;
		
        public int descriptor() {
            return descriptor;
        }

		private AttributeReader reader = null;				
		
        public AttributeReader reader() {
            return reader;
        }

        public JsonObjectBuilder toJson() {            
            JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("flags", flags)
                .add("name", name)
                .add("descriptor", descriptor);
            JsonObject object = reader.toJson().build();
            if (!object.isEmpty()) {
                builder.add("attriutes", reader.toJson());
            }
            return builder;
        }

		public void print() {
			System.out.println("flags -> " + flags);
			System.out.println("name -> " + name);
			System.out.println("descriptor -> " + descriptor);
			System.out.println("reader -> " + reader);
		}
    }
}
