package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	public static void main(String[] args) throws IOException {
		//BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		double a = Double.parseDouble(reader.readLine());
		double t = a % 5;


		if (t <= 2.9){
			System.out.println("зелёный");
		}

		else if( t>2.9 && t <3.9){
			System.out.println("красный");
		}
		else if(t>3.9 &&  t< 4.9){
			System.out.println("жёлтый");
		}
	}
	}
