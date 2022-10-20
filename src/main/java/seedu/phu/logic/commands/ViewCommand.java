package seedu.phu.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.phu.commons.core.Messages;
import seedu.phu.commons.core.index.Indexes;
import seedu.phu.commons.exceptions.IllegalIndexException;
import seedu.phu.logic.commands.exceptions.CommandException;
import seedu.phu.model.Model;
import seedu.phu.model.internship.ExactMatchPredicate;
import seedu.phu.model.internship.Internship;
import seedu.phu.model.internship.UniqueInternshipList;

/**
 * Finds and lists all details of specified internship.
 */
public class ViewCommand extends Command {
    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all details of specified internship"
            + " at the given index.\n"
            + "Parameters: view index\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Displaying more details";
    private final Indexes targetIndex;

    /**
     * Constructor for ViewCommand
     */
    public ViewCommand(Indexes targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        List<Internship> lastShownList = model.getFilteredInternshipList();
        UniqueInternshipList targetInternships;

        try {
            targetInternships = targetIndex.getAllInternshipsFromIndexes(lastShownList);
        } catch (IllegalIndexException error) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
        }

        List<Internship> internshipList = new ArrayList<>();
        for (Internship internship : targetInternships) {
            internshipList.add(internship);
        }

        model.updateViewItem(new ExactMatchPredicate(internshipList));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCommand // instanceof handles nulls
                && targetIndex.equals(((ViewCommand) other).targetIndex)); // state check
    }
}