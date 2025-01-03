package com.overlap.service;

import com.overlap.command.AddStockCommand;
import com.overlap.command.CalculateOverlapCommand;
import com.overlap.command.CurrentPortfolioCommand;
import com.overlap.command.InputCommand;
import com.overlap.exception.CommandNotFoundException;
import com.overlap.model.CommandName;

import java.util.HashMap;
import java.util.Map;

import static com.overlap.model.CommandName.*;

/**
 * This creates a registry of commands that user can use.
 */
public class CommandRegistryService {
    /**
     * A map to hold the registry of commands.
     */
    private final Map<CommandName, InputCommand> registry = new HashMap<>();

    /**
     * Constructs a new CommandRegistryService and initializes the commands.
     */
    public CommandRegistryService() {
        FundService fundService = new FundService();
        registerCommands(new CurrentPortfolioCommand(fundService), new CalculateOverlapCommand(fundService), new AddStockCommand());
    }

    /**
     * Registers the commands in the registry.
     */
    private void registerCommands(InputCommand... commands) {
        registry.put(CURRENT_PORTFOLIO, commands[0]);
        registry.put(CALCULATE_OVERLAP, commands[1]);
        registry.put(ADD_STOCK, commands[2]);
    }

    /**
     * Validates and retrieves the command corresponding to the given command name.
     * As CommandName is an enum, it will be validated before coming here.
     *
     * @param commandName the command name as an enum
     * @return the command corresponding to the name
     * @throws CommandNotFoundException if the command is not found in the registry
     */
    public InputCommand getCommand(CommandName commandName) throws CommandNotFoundException {
        InputCommand inputCommand = registry.get(commandName);
        if (inputCommand == null) {
            throw new CommandNotFoundException("Exception: Given command NotFound");
        }
        return inputCommand;
    }
}