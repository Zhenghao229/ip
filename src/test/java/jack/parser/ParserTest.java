package jack.parser;

import jack.JackException;
import jack.command.Command;
import jack.command.ExitCommand;
import jack.command.MarkCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void parse_bye_returnsExitCommand() throws Exception {
        Command c = Parser.parse("bye");
        assertTrue(c instanceof ExitCommand);
    }

    @Test
    public void parse_markWithValidNumber_returnsMarkCommand() throws Exception {
        Command c = Parser.parse("mark 1");
        assertTrue(c instanceof MarkCommand);
    }

    @Test
    public void parse_markMissingNumber_throwsJackException() {
        assertThrows(JackException.class, () -> Parser.parse("mark"));
    }


    @Test
    public void parse_deadlineInvalidDate_throwsJackException() {
        JackException e = assertThrows(JackException.class,
                () -> Parser.parse("deadline return book /by 2019-13-40"));
        assertTrue(e.getMessage().contains("yyyy-MM-dd"));
    }

    @Test
    public void parse_unknownCommand_throwsJackException() {
        assertThrows(JackException.class, () -> Parser.parse("abracadabra"));
    }
}
