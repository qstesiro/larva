package com.qstesiro.demo;

public class Demo {    

    public static Method method = new Method();
    
    public static void main(String[] args) {
		int i = 0; while (true) {
			// System.out.println("hello java. #" + ++i);
			// method.method1();
			// method.method2();
			// method.method3();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }
}
