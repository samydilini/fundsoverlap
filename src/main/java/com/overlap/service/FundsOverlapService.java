package com.overlap.service;

import com.overlap.command.InputCommand;
import com.overlap.exception.CommandNotFoundException;
import com.overlap.exception.FundsOverlapException;
import com.overlap.model.CommandName;
import com.overlap.model.FundSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is the main service used in the overlap application.
 * It sets up the command registry and manages the fund session.
 */
public class FundsOverlapService {

    private final CommandRegistryService commandRegistryService;
    private FundSession fundSession;

    /**
     * Constructs a new FundsOverlapService with an empty fund session and initializes the command registry service.
     */
    public FundsOverlapService() {
        fundSession = new FundSession(new ArrayList<>(), new ArrayList<>());
        this.commandRegistryService = new CommandRegistryService();
    }

    /**
     * Constructs a new FundsOverlapService with the provided command registry service.
     * Initializes the fund session with empty lists of portfolio and available funds.
     * Used for unit tests.
     *
     * @param commandRegistryService the command registry service to be used by this service
     */
    public FundsOverlapService(CommandRegistryService commandRegistryService) {
        fundSession = new FundSession(new ArrayList<>(), new ArrayList<>());
        this.commandRegistryService = commandRegistryService;
    }

    /**
     * Processes user input lines and executes the corresponding commands.
     *
     * @param lines a list of user input lines
     * @throws FundsOverlapException if an error occurs while processing the commands
     */
    public void process(List<String> lines) throws FundsOverlapException {
        for (String line : lines) {
            List<String> tokens = Arrays.asList(line.split(" "));
            if (!tokens.isEmpty()) {
                try {
                    CommandName currentCommandName = CommandName.valueOf(tokens.get(0));
                    InputCommand currentCommand = commandRegistryService.getCommand(currentCommandName);

                    fundSession = currentCommand.execute(tokens.stream()
                            .skip(1)
                            .collect(Collectors.toList()), fundSession);

                } catch (IllegalArgumentException | CommandNotFoundException e) {
                    throw new FundsOverlapException(e.getMessage());
                }
            }
        }
    }
}
