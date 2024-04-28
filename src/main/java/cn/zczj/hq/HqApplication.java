package cn.zczj.hq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan({"cn.zczj.hq.filter"})
public class HqApplication {

	public static void main(String[] args) {
		SpringApplication.run(HqApplication.class, args);
	}

}
