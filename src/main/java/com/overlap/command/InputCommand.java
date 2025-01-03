package com.overlap.command;

import com.overlap.exception.FundsOverlapException;
import com.overlap.model.FundSession;

import java.util.List;

public interface InputCommand {

    FundSession execute(List<String> collect,FundSession fundSession) throws FundsOverlapException;
}
