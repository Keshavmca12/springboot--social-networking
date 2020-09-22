package com.factorial;

import java.math.BigInteger;

public class Factorail {
	
	
	public static void main(String[] args) {
		long n  = 5008956;
		BigInteger result = BigInteger.ONE;
		for (int i = 2; i < n; i++) {
			result = result.multiply(new BigInteger(String.valueOf(i)));
		}
		System.out.println("result : "+result);
	}

}
