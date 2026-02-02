package com.nirvxsh.voucher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherApplication {

	public static void main(String[] args) {
		SpringApplication.run(VoucherApplication.class, args);
		System.out.println("------------------------------------------------Voucher Service started------------------------------------------------");
	}

}
