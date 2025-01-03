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
    private final Map<CommandName, InputCommand> registry;
    private final InputCommand currentPortfolioCommand;
    private final InputCommand calculateOverlapCommand;
    private final InputCommand addStockCommand;

    public CommandRegistryService() {
        FundService fundService = new FundService();
        this.registry = new HashMap<>();
        currentPortfolioCommand = new CurrentPortfolioCommand(fundService);
        calculateOverlapCommand = new CalculateOverlapCommand(fundService);
        addStockCommand = new AddStockCommand();
        registerCommands();
    }

    public void registerCommands() {
        registry.put(CURRENT_PORTFOLIO, currentPortfolioCommand);
        registry.put(CALCULATE_OVERLAP, calculateOverlapCommand);
        registry.put(ADD_STOCK, addStockCommand);
    }

    /**
     * validates commands. But as CommandName is an enum it will be validated before coming here
     * @param commandName Enum Command
     * @return Commnd reflecting to the name
     * @throws CommandNotFoundException
     */
    public InputCommand getCommand(CommandName commandName) throws CommandNotFoundException {
        InputCommand inputCommand = registry.get(commandName);
        if (inputCommand == null) {
            throw new CommandNotFoundException("Exception: Given command NotFound");
        }
        return inputCommand;
    }
}
