package com.overlap;

import com.overlap.exception.FundsOverlapException;
import com.overlap.service.FundsOverlapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GeektrustTest {
    private static final FundsOverlapService fundsOverlapService = Mockito.mock(FundsOverlapService.class);
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        Mockito.reset(fundsOverlapService);
        outContent.reset();
        System.setOut(new PrintStream(outContent));
        Geektrust.setFundsOverlapService(fundsOverlapService);
    }

    @Test
    public void shouldPrintErrorWhenNoArgumentsProvided() {
        Geektrust.main(new String[]{});
        assertEquals("Please provide the file path as the first argument.",
                outContent.toString().replace("\n", "").replace("\r", ""));
    }

    @Test
    public void shouldPrintErrorWhenFileNotFound() {
        Geektrust.main(new String[]{"wrongPath"});
        assertEquals("The file you specified was not found. Error: wrongPath (No such file or directory)",
                outContent.toString().replace("\n", "")
                        .replace("\r", ""));
    }


    @Test
    public void shouldPrintErrorWhenIOExceptionOccursAtReading() throws IOException {

        BufferedReader mockReader = Mockito.mock(BufferedReader.class);
        when(mockReader.readLine()).thenThrow(new IOException("error"));
        Geektrust.processFile(mockReader);
        assertEquals("Exception at reading the file. Error: error",
                outContent.toString().replace("\n", "")
                        .replace("\r", ""));
    }

    @Test
    public void shouldPrintErrorWhenFundsOverlapThrowsException() throws IOException, FundsOverlapException {

        BufferedReader mockReader = Mockito.mock(BufferedReader.class);
        when(mockReader.readLine()).thenReturn("first").thenReturn("two").thenReturn(null);
        doThrow(new FundsOverlapException("my exception")).when(fundsOverlapService).process(anyList());
        Geektrust.processFile(mockReader);
        assertEquals("Exception while handling funds overlap calculations: my exception",
                outContent.toString().replace("\n", "")
                        .replace("\r", ""));
    }

    @Test
    public void shouldThrowNoErrorsIfRunIsSuccessFul() throws IOException, FundsOverlapException {

        Geektrust.processFile(new BufferedReader(new FileReader("src/test/resources/input1.txt")));
        assertEquals("", outContent.toString());
        verify(fundsOverlapService).process(any());
    }

}