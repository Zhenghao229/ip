package jack.parser;

import lulu.LuluException;
import lulu.command.Command;
import lulu.command.ExitCommand;
import lulu.command.MarkCommand;
import lulu.parser.Parser;

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
        assertThrows(LuluException.class, () -> Parser.parse("mark"));
    }


    @Test
    public void parse_deadlineInvalidDate_throwsJackException() {
        LuluException e = assertThrows(LuluException.class,
                () -> Parser.parse("deadline return book /by 2019-13-40"));
        assertTrue(e.getMessage().contains("yyyy-MM-dd"));
    }

    @Test
    public void parse_unknownCommand_throwsJackException() {
        assertThrows(LuluException.class, () -> Parser.parse("abracadabra"));
    }
}
