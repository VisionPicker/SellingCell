package com.nju.software.SellingCell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class SellingCellApplication {

	public static void main(String[] args) {
		SpringApplication.run(SellingCellApplication.class, args);
	}
}
