package com.hoolai.bi.test;

public class Test {
	static int stain=6;
	int insin=22;
	
	
	
	
	public static void main(String[] args) {
		stain++;
         Test t=new Test();
         t.insin++;
         System.out.println(t.insin);
         System.out.println(t.stain);
         
         Test t2=new Test();
         stain++;
         t2.insin++;
         System.out.println(t2.insin);
         System.out.println(t2.stain);
         
         Test t3=new Test();
         stain++;
         t3.insin++;
         System.out.println(t3.insin);
         System.out.println(t3.stain);
	}
}
