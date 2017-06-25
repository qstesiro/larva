package com.runbox.debug.command.print;

import com.sun.jdi.*;

import com.runbox.debug.script.expression.Expression;
import com.runbox.debug.script.expression.token.Token;
import com.runbox.debug.script.expression.token.operand.*;

public class PrintRadixCommand extends PrintCommand {

	public PrintRadixCommand(String command) throws Exception {
		super(command);
		if (null != argument()) {
			values = new Expression(argument()).execute();
			operand = operand(); radix = radix(); 
			return;
		}
		throw new Exception("invalid operand");
	}

	private Expression.Values<Operand> values = null;
	
	@Override	
	public boolean execute() throws Exception {
		if (null != operand) print();
		return super.execute();
	}

	private static final int OPERAND = 0;
	private static final int RADIX = 1;	
	private static final int MAX = 2;

	private Operand operand = null;

	private Operand operand() throws Exception {
		if (null != values) {
			if (OPERAND < values.size() && MAX >= values.size()) {
				Operand operand = values.get(OPERAND);
				if (operand.isByte() ||
					operand.isShort() ||
					operand.isInteger() ||
					operand.isLong())
					return values.get(OPERAND);
			}
		}
		throw new Exception("invalid operand");
	}

	private static final int BIN = 2;
	private static final int OCT = 8;
	private static final int DEC = 10;
	private static final int HEX = 16;
	
	private int radix = HEX;
	
	private int radix() throws Exception {
		if (null != values) {
			if (RADIX < values.size() && MAX >= values.size()) {
				int radix = values.getInteger(RADIX);
				if (BIN == radix || OCT == radix ||
					DEC == radix || HEX == radix) {
					return radix;
				}
			}
		}
		return HEX;
	}

	private void print() throws Exception {		
		if (null != operand.name()) {
			System.out.printf("%s", operand.name());					
			System.out.printf(" = ");
		}		
		if (null != operand.value()) {
			if (operand.isByte()) {
				print(operand.byteValue());
			} else if (operand.isShort()) {
				print(operand.shortValue());
			} else if (operand.isInteger()) {
				print(operand.intValue());
			} else if (operand.isLong()) {
				print(operand.longValue());
			}				
		}		
	}	
  
	private void print(long value) {
		switch (radix) {
		case BIN: System.out.println("0b" + Long.toBinaryString(value)); break;
		case OCT: System.out.println("0" + Long.toOctalString(value)); break;
		case DEC: System.out.println(value); break;
		case HEX: System.out.println("0x" + Long.toHexString(value)); break;
		}
	}
}
