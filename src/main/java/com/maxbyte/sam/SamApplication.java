package com.maxbyte.sam;

import com.maxbyte.sam.OracleDBFlow.Entity.MasterDepartment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

@SpringBootApplication
public class SamApplication {


	public static void main(String[] args) {
		SpringApplication.run(SamApplication.class, args);

		System.out.println("***************************************************************************************************************************************************************");
		System.out.println("*********************************************************** SAM APPLICATION STARTED SUCCESSFULLY ****************************************************************");
		System.out.println("****************************************************************************************************************************************************************");
	}

}
