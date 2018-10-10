package com.ef.parser;

import com.ef.entities.Arguments;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class ArgumentParser {

    private static final String LOG_FILE_PATH = "--accessLog";
    private static final String START_DATE_ARGUMENT = "--startDate";
    private static final String DURATION_ARGUMENT = "--duration";
    private static final String THRESHOLD_ARGUMENT = "--threshold";
    private static final String ARGUMENT_SEPARATOR = "=";
    private static final String START_DATE_FORMAT = "yyyy-MM-dd.HH:mm:ss";

    public Arguments parseArguments(String... args) {
        String logFilePath = null;
        String startDate = null;
        String duration = null;
        Integer threshold = null;

        if(args != null) {
            for (String arg : args) {
                String argumentName = arg.split(ARGUMENT_SEPARATOR)[0];
                String argumentValue = arg.split(ARGUMENT_SEPARATOR)[1];

                if(LOG_FILE_PATH.equals(argumentName)) {
                    logFilePath = argumentValue;
                } else if (START_DATE_ARGUMENT.equals(argumentName)) {
                    applyStartDateFormatValidation(argumentValue);
                    startDate = argumentValue;
                } else if (DURATION_ARGUMENT.equals(argumentName)) {
                    applyDurationArgumentValidation(argumentValue);
                    duration = argumentValue;
                } else if (THRESHOLD_ARGUMENT.equals(argumentName)) {
                    applyThresholdArgumentValidation(argumentValue);
                    threshold = Integer.valueOf(argumentValue);
                } else {
                    applyUnsupportedArgumentNameValidation();
                }
            }
        }

        Arguments arguments = new Arguments(logFilePath, startDate, duration, threshold);
        applyMissingArgumentsValidation(arguments);

        return arguments;
    }

    private void applyStartDateFormatValidation(String startDate) {
        try {
            LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern(START_DATE_FORMAT));
        } catch (DateTimeParseException e) {
            System.out.println("Please provide " +START_DATE_ARGUMENT+ " argument in format " +START_DATE_FORMAT);
            System.exit(1);
        }
    }

    private void applyDurationArgumentValidation(String duration) {
        if(!("daily".equalsIgnoreCase(duration) || "hourly".equalsIgnoreCase(duration))) {
            System.out.println("Valid values for "+DURATION_ARGUMENT+" argument: [hourly, daily]");
            System.exit(1);
        }
    }

    private void applyMissingArgumentsValidation(Arguments arguments) {
        if(arguments.getFilePath() == null || arguments.getStartDate() == null || arguments.getDuration() == null || arguments.getThreshold() == null) {
            System.out.println("Please provide all set of arguments: ["+LOG_FILE_PATH+ "," +START_DATE_ARGUMENT+ "," +DURATION_ARGUMENT+ "," +THRESHOLD_ARGUMENT+"]");
            System.exit(1);
        }
    }

    private void applyThresholdArgumentValidation(String threshold) {
        try {
            Integer.valueOf(threshold);
        } catch(NumberFormatException e) {
            System.out.println("Please provide valid integer value for " +THRESHOLD_ARGUMENT);
            System.exit(1);
        }
    }

    private void applyUnsupportedArgumentNameValidation() {
        System.out.println("Unsupported argument name. " +
                "Please use the following set of arguments: ["+LOG_FILE_PATH+ "," +START_DATE_ARGUMENT+ "," +DURATION_ARGUMENT+ "," +THRESHOLD_ARGUMENT+"]");
        System.exit(1);
    }
}
