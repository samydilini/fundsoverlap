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
 * This is the main service used in overlap application.
 * Setting up registry and session is done here
 */
public class FundsOverlapService {

    private final CommandRegistryService commandRegistryService;
    private FundSession fundSession;

    public FundsOverlapService() {
        fundSession = new FundSession(new ArrayList<>(), new ArrayList<>());
        this.commandRegistryService = new CommandRegistryService();
    }

    /**
     * process user inputs
     * @param lines user inputs
     * @throws FundsOverlapException
     */
    public void process(List<String> lines) throws FundsOverlapException {
        for (String line : lines) {
            if (line != null) {
                List<String> tokens = Arrays.asList(line.split(" "));
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
