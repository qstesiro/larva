package com.runbox.debug.event.method;

import java.util.List;
import java.util.LinkedList;

import com.sun.jdi.event.MethodExitEvent;

import com.runbox.debug.event.LocatableEvent;
import com.runbox.debug.manager.ExecuteManager;

import com.runbox.debug.script.expression.token.operand.Operand;
import com.runbox.debug.script.expression.token.operand.ConstOperand;

public class MethodEvent<T extends com.sun.jdi.event.LocatableEvent> extends LocatableEvent<T> {

    public MethodEvent(T event) {
        super(event); arguments();
    }

    @Override
	public boolean handle() throws Exception {		
        if (event() instanceof MethodExitEvent) {			
			ExecuteManager.ReturnEntry entry = ExecuteManager.instance().getReturn(event().thread());
			if (null != entry) {
				if (event().request() == entry.request()) {					
					ExecuteManager.instance().deleteReturn(event().thread());
				}
			}
        }
		return super.handle();
	}

	private void arguments() {
		if (null != routine()) {
			List<Operand> autos = new LinkedList<Operand>();
			autos.add(new ConstOperand(event().thread().uniqueID()));
			autos.add(new ConstOperand(event().thread()));
			arguments(autos);
		}
	}
}
