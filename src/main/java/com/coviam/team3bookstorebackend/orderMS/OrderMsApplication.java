package com.coviam.team3bookstorebackend.orderMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class OrderMsApplication {

	public static void main(String[] args)  throws  Exception{
		SpringApplication.run(OrderMsApplication.class, args);
	}

}
