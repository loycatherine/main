package team.easytravel.logic.commands.trip;

import static java.util.Objects.requireNonNull;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.model.Model;

/**
 * Deletes the Trip and reset the whole application.
 */
public class DeleteTripCommand extends Command {

    public static final String COMMAND_WORD = "deletetrip";
    public static final String MESSAGE_SUCCESS = "Trip has been deleted. All lists have been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.deleteTrip();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}