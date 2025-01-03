package com.overlap.service;

import com.overlap.command.InputCommand;
import com.overlap.exception.CommandNotFoundException;
import com.overlap.exception.FundsOverlapException;
import com.overlap.model.CommandName;
import com.overlap.model.FundSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;


class FundsOverlapServiceTest {
    @Mock
    private CommandRegistryService commandRegistryService;

    @Mock
    private InputCommand mockCommand;

    @InjectMocks
    private FundsOverlapService fundsOverlapService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void successFullyProcessValidateValidCommands() throws Exception {
        String line = "CURRENT_PORTFOLIO arg1 arg2";
        List<String> lines = Collections.singletonList(line);
        List<String> args = Arrays.asList("arg1", "arg2");

        when(commandRegistryService.getCommand(CommandName.CURRENT_PORTFOLIO)).thenReturn(mockCommand);
        FundSession fundSession = new FundSession(new ArrayList<>(), new ArrayList<>());
        when(mockCommand.execute(args, fundSession)).thenReturn(new FundSession(Collections.emptyList(), Collections.emptyList()));

        assertDoesNotThrow(() -> fundsOverlapService.process(lines));

        verify(commandRegistryService).getCommand(CommandName.CURRENT_PORTFOLIO);
        verify(mockCommand).execute(anyList(), any(FundSession.class));
    }

    @Test
    void nullInputShouldNotInvokeLogic() {
        List<String> lines = Collections.singletonList(null);

        assertDoesNotThrow(() -> fundsOverlapService.process(lines));
        verifyZeroInteractions(commandRegistryService);
    }

    @Test
    void handleCommandNotFoundException() throws Exception {
        String line = "CURRENT_PORTFOLIO arg1 arg2";
        List<String> lines = Collections.singletonList(line);

        when(commandRegistryService.getCommand(CommandName.CURRENT_PORTFOLIO)).thenThrow(new CommandNotFoundException("Command not found"));

        FundsOverlapException exception = assertThrows(FundsOverlapException.class, () -> fundsOverlapService.process(lines));

        assertEquals("Command not found", exception.getMessage());
        verify(commandRegistryService).getCommand(CommandName.CURRENT_PORTFOLIO);
    }

    @Test
    void emptyLinesShouldNotInvokeLogic() {
        List<String> lines = Collections.emptyList();

        assertDoesNotThrow(() -> fundsOverlapService.process(lines));

        verifyZeroInteractions(commandRegistryService);
    }

    @Test
    void handleIllegalArgumentException() {
        String line = "INVALID_COMMAND arg1 arg2";
        List<String> lines = Collections.singletonList(line);

        assertThrows(FundsOverlapException.class, () -> fundsOverlapService.process(lines));

        verifyZeroInteractions(commandRegistryService);
    }
}