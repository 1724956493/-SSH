package com.nts.teststruts.util;

import java.util.UUID;

public class T {
	 public static void main(String[] args) {
		 System.out.println( UUID.randomUUID().toString().replace("-", "").length());
		;
	}
}
