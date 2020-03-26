package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.EasyTravelParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.listmanagers.accommodationbooking.AccommodationBooking;
import seedu.address.model.listmanagers.activity.Activity;
import seedu.address.model.listmanagers.fixedexpense.FixedExpense;
import seedu.address.model.listmanagers.packinglistitem.PackingListItem;
import seedu.address.model.listmanagers.transportbooking.TransportBooking;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final EasyTravelParser easyTravelParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        easyTravelParser = new EasyTravelParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = easyTravelParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveTripManager(model.getTripManager());
            storage.saveAccommodationBookings(model.getAccommodationBookingManager());
            storage.saveFixedExpenses(model.getFixedExpenseManager());
            storage.saveTransportBookings(model.getTransportBookingManager());
            storage.saveActivityManager(model.getActivityManager());
            storage.savePackingList(model.getPackingListManager());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ObservableList<TransportBooking> getFilteredTransportBookingList() {
        return model.getFilteredTransportBookingList();
    }

    @Override
    public ObservableList<Activity> getFilteredActivityList() {
        return model.getFilteredActivityList();
    }

    @Override
    public ObservableList<FixedExpense> getFilteredFixedExpenseList() {
        return model.getFilteredFixedExpenseList();
    }

    @Override
    public ObservableList<PackingListItem> getFilteredPackingList() {
        return model.getFilteredPackingList();
    }

    @Override
    public ObservableList<AccommodationBooking> getFilteredAccommodationBookingList() {
        return model.getFilteredAccommodationBookingList();
    }

    @Override
    public Path getEasyTravelStorageFilePath() {
        return model.getEasyTravelStorageFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
