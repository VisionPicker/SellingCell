package com.nju.software.SellingCell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement
public class SellingCellApplication {

	public static void main(String[] args) {
		SpringApplication.run(SellingCellApplication.class, args);
	}
}
