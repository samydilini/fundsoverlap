package com.overlap.command;

import com.overlap.exception.FundsOverlapException;
import com.overlap.model.FundSession;

import java.util.List;

/**
 * Interface for input commands that can be executed with a list of tokens and a fund session.
 */
public interface InputCommand {
    /**
     * Executes the command with the given tokens and fund session.
     *
     * @param collect     a list of strings representing the command tokens
     * @param fundSession the current session information containing available funds
     * @return the updated FundSession after executing the command
     * @throws FundsOverlapException if there is an error during the command execution
     */
    FundSession execute(List<String> collect, FundSession fundSession) throws FundsOverlapException;
}
