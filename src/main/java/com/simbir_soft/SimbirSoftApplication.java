package com.simbir_soft;

import com.simbir_soft.service.commands.threads.MonitorForCommands;
import com.simbir_soft.service.commands.threads.StartCommandInQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class SimbirSoftApplication {

	public static void main(String[] args) {

		SpringApplication.run(SimbirSoftApplication.class, args);
	}

}
