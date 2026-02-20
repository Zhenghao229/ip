package lulu.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import lulu.LuluException;
import lulu.command.Command;
import lulu.command.DeleteCommand;
import lulu.command.ExitCommand;
import lulu.command.FindCommand;
import lulu.command.ListCommand;
import lulu.command.MarkCommand;
import lulu.command.UnmarkCommand;
import lulu.command.UpdateCommand;

/**
 * Tests for {@link Parser}.
 * Covers:
 * - valid command parsing
 * - invalid command formats
 * - missing parameters
 * - invalid indices
 * - invalid date/time formats
 * - invalid event time ranges
 */
public class ParserTest {

    // -------------------------
    // Basic commands
    // -------------------------

    @Test
    public void parse_bye_returnsExitCommand() throws Exception {
        Command c = Parser.parse("bye");
        assertTrue(c instanceof ExitCommand);
    }

    @Test
    public void parse_list_returnsListCommand() throws Exception {
        Command c = Parser.parse("list");
        assertTrue(c instanceof ListCommand);
    }

    // -------------------------
    // Mark / Unmark / Delete
    // -------------------------

    @Test
    public void parse_markWithValidNumber_returnsMarkCommand() throws Exception {
        Command c = Parser.parse("mark 1");
        assertTrue(c instanceof MarkCommand);
    }

    @Test
    public void parse_markWithExtraSpaces_returnsMarkCommand() throws Exception {
        Command c = Parser.parse("mark     1");
        assertTrue(c instanceof MarkCommand);
    }

    @Test
    public void parse_markMissingNumber_throwsLuluException() {
        assertThrows(LuluException.class, () -> Parser.parse("mark"));
    }

    @Test
    public void parse_markNonInteger_throwsLuluException() {
        assertThrows(LuluException.class, () -> Parser.parse("mark abc"));
    }

    @Test
    public void parse_markZero_throwsLuluException() {
        assertThrows(LuluException.class, () -> Parser.parse("mark 0"));
    }

    @Test
    public void parse_markNegative_throwsLuluException() {
        assertThrows(LuluException.class, () -> Parser.parse("mark -1"));
    }

    @Test
    public void parse_unmarkWithValidNumber_returnsUnmarkCommand() throws Exception {
        Command c = Parser.parse("unmark 1");
        assertTrue(c instanceof UnmarkCommand);
    }

    @Test
    public void parse_unmarkMissingNumber_throwsLuluException() {
        assertThrows(LuluException.class, () -> Parser.parse("unmark"));
    }

    @Test
    public void parse_unmarkNonInteger_throwsLuluException() {
        assertThrows(LuluException.class, () -> Parser.parse("unmark abc"));
    }

    @Test
    public void parse_unmarkZero_throwsLuluException() {
        assertThrows(LuluException.class, () -> Parser.parse("unmark 0"));
    }

    @Test
    public void parse_deleteWithValidNumber_returnsDeleteCommand() throws Exception {
        Command c = Parser.parse("delete 1");
        assertTrue(c instanceof DeleteCommand);
    }

    @Test
    public void parse_deleteMissingNumber_throwsLuluException() {
        assertThrows(LuluException.class, () -> Parser.parse("delete"));
    }

    @Test
    public void parse_deleteNonInteger_throwsLuluException() {
        assertThrows(LuluException.class, () -> Parser.parse("delete abc"));
    }

    @Test
    public void parse_deleteNegative_throwsLuluException() {
        assertThrows(LuluException.class, () -> Parser.parse("delete -2"));
    }

    // -------------------------
    // Find
    // -------------------------

    @Test
    public void parse_findWithKeyword_returnsFindCommand() throws Exception {
        Command c = Parser.parse("find book");
        assertTrue(c instanceof FindCommand);
    }

    @Test
    public void parse_findMissingKeyword_throwsLuluException() {
        assertThrows(LuluException.class, () -> Parser.parse("find"));
    }

    @Test
    public void parse_findBlankKeyword_throwsLuluException() {
        assertThrows(LuluException.class, () -> Parser.parse("find     "));
    }

    // -------------------------
    // Update
    // -------------------------

    @Test
    public void parse_updateMissingAllArgs_throwsLuluException() {
        assertThrows(LuluException.class, () -> Parser.parse("update"));
    }

    @Test
    public void parse_updateMissingFields_throwsLuluException() {
        assertThrows(LuluException.class, () -> Parser.parse("update 2"));
    }

    @Test
    public void parse_updateNonIntegerIndex_throwsLuluException() {
        assertThrows(LuluException.class, () -> Parser.parse("update abc /desc hi"));
    }

    @Test
    public void parse_updateZeroIndex_throwsLuluException() {
        assertThrows(LuluException.class, () -> Parser.parse("update 0 /desc hi"));
    }

    @Test
    public void parse_updateValidDesc_returnsUpdateCommand() throws Exception {
        Command c = Parser.parse("update 2 /desc read book");
        assertTrue(c instanceof UpdateCommand);
    }

    @Test
    public void parse_updateValidBy_returnsUpdateCommand() throws Exception {
        Command c = Parser.parse("update 2 /by 2026-02-25 20:00");
        assertTrue(c instanceof UpdateCommand);
    }

    @Test
    public void parse_updateInvalidBy_throwsLuluException() {
        LuluException e = assertThrows(LuluException.class, () ->
                Parser.parse("update 2 /by 2019-13-40 20:00"));
        assertTrue(e.getMessage().contains("yyyy-MM-dd"));
    }

    @Test
    public void parse_updateInvalidFromToRange_throwsLuluException() {
        assertThrows(LuluException.class, () ->
                Parser.parse("update 2 /from 2026-02-20 16:00 /to 2026-02-20 14:00"));
    }

    // -------------------------
    // Todo / Deadline / Event
    // -------------------------

    @Test
    public void parse_todoValid_doesNotThrow() {
        assertDoesNotThrow(() -> Parser.parse("todo read book"));
    }

    @Test
    public void parse_todoMissingDescription_throwsLuluException() {
        assertThrows(LuluException.class, () -> Parser.parse("todo"));
    }

    @Test
    public void parse_deadlineValid_doesNotThrow() {
        assertDoesNotThrow(() ->
                Parser.parse("deadline return book /by 2026-02-25 20:00"));
    }

    @Test
    public void parse_deadlineMissingBy_throwsLuluException() {
        assertThrows(LuluException.class, () ->
                Parser.parse("deadline return book"));
    }

    @Test
    public void parse_deadlineInvalidDate_throwsLuluException() {
        LuluException e = assertThrows(LuluException.class, () ->
                Parser.parse("deadline return book /by 2019-13-40"));
        assertTrue(e.getMessage().contains("yyyy-MM-dd"));
    }

    @Test
    public void parse_eventValid_doesNotThrow() {
        assertDoesNotThrow(() ->
                Parser.parse("event meeting /from 2026-02-20 14:00 /to 2026-02-20 16:00"));
    }

    @Test
    public void parse_eventMissingFromOrTo_throwsLuluException() {
        assertThrows(LuluException.class, () ->
                Parser.parse("event meeting /from 2026-02-20 14:00"));
    }

    @Test
    public void parse_eventEndBeforeStart_throwsLuluException() {
        assertThrows(LuluException.class, () ->
                Parser.parse("event meeting /from 2026-02-20 16:00 /to 2026-02-20 14:00"));
    }

    // -------------------------
    // Unknown command
    // -------------------------

    @Test
    public void parse_unknownCommand_throwsLuluException() {
        assertThrows(LuluException.class, () -> Parser.parse("abracadabra"));
    }
}
