package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDateTime;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Id;
import seedu.address.model.person.Patient;
import seedu.address.model.person.Person;

/**
 * deletes an appointment to both a patient and a doctor.
 */
public class DeleteAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "deleteAppointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": deletes an appointment "
            + "between the relevant doctor and patient. "
            + COMMAND_WORD + " "
            + PREFIX_DATE + "[APPOINTMENT_TIME] "
            + PREFIX_ID + "[PATIENT_ID] "
            + PREFIX_ID + "[DOCTOR_ID]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2024-12-31 15:23"
            + PREFIX_ID + "1234 "
            + PREFIX_ID + "5678";
    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Successfully "
            + "deleted appointment to a patient";
    private final Id patientId;
    private final Id doctorId;
    private final LocalDateTime appointmentTime;

    /**
     * Creates an DeleteAppointmentCommand to add the specified patient and doctor ids
     */
    public DeleteAppointmentCommand(LocalDateTime appointmentTime, Id patientId, Id doctorId) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentTime = appointmentTime;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Person> allPersons = model.getFilteredPersonList();
        Patient patientToAddAppointment = model.getFilteredPatientById(allPersons, patientId);
        Doctor doctorToAddAppointment = model.getFilteredDoctorById(allPersons, doctorId);
        patientToAddAppointment.deleteAppointment(appointmentTime, patientToAddAppointment.getId(),
                doctorToAddAppointment.getId());
        doctorToAddAppointment.deleteAppointment(appointmentTime, patientToAddAppointment.getId(),
                doctorToAddAppointment.getId());
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_DELETE_APPOINTMENT_SUCCESS);
    }
}