package com.ef;

import com.ef.entities.Arguments;
import com.ef.parser.ArgumentParser;
import com.ef.parser.LogParser;
import com.ef.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Parser implements CommandLineRunner {

	@Autowired
	private LogParser logParser;

	@Autowired
	private ArgumentParser argumentParser;

	@Autowired
	private LogService logService;

	public static void main(String[] args) {
		SpringApplication.run(Parser.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Arguments arguments = argumentParser.parseArguments(args);
		this.logParser.process(arguments.getFilePath());
		this.logService.retrieveData(arguments);
	}
}
