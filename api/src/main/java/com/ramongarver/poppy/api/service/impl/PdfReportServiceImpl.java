package com.ramongarver.poppy.api.service.impl;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.ramongarver.poppy.api.dto.activity.ActivityVolunteerAvailabilitiesAndAssignmentsReadDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto;
import com.ramongarver.poppy.api.dto.volunteer.VolunteerReducedReadDto;
import com.ramongarver.poppy.api.enums.PrintOption;
import com.ramongarver.poppy.api.service.PdfReportService;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PdfReportServiceImpl implements PdfReportService {

    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm";
    private static final String ALL = "";
    private static final String AVAILABILITIES = "Disponibilidades";
    private static final String ASSIGNMENTS = "Asignaciones";
    private static final String COORDINATORS_VOLUNTEERS_LABEL = "Coordinadores";
    private static final String AVAILABLE_VOLUNTEERS_LABEL = "Voluntarios disponibles";

    @Override
    public void createAssignmentReport(ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto volunteerAvailabilitiesAndAssignments,
                                       PrintOption printOption, OutputStream outputStream) {
        final PdfWriter writer = new PdfWriter(outputStream);
        final PdfDocument pdf = new PdfDocument(writer);
        final Document document = new Document(pdf);

        final String documentType = PrintOption.ASSIGNMENTS.equals(printOption) ? ASSIGNMENTS
                : PrintOption.AVAILABILITIES.equals(printOption) ? AVAILABILITIES
                : ALL;

        // Add the name of the activity package in the site
        final Paragraph activityPackageName = new Paragraph(String.format("%s | %s", documentType, volunteerAvailabilitiesAndAssignments.getActivityPackage().getName()))
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(16)
                .setBold();
        document.add(activityPackageName);

        final List<ActivityVolunteerAvailabilitiesAndAssignmentsReadDto> activities = volunteerAvailabilitiesAndAssignments.getActivities();
        // Group the activities by week
        final Map<LocalDate, List<ActivityVolunteerAvailabilitiesAndAssignmentsReadDto>> activitiesByWeek = activities.stream()
                .collect(Collectors.groupingBy(activity -> getStartOfWeek(activity.getLocalDateTime())));
        // Order the weeks
        final List<LocalDate> sortedWeeks = new ArrayList<>(activitiesByWeek.keySet());
        Collections.sort(sortedWeeks);

        for (LocalDate weekStart : sortedWeeks) {
            final LocalDate weekEnd = weekStart.plusDays(6);

            document.add(new Paragraph(
                    String.format("SEMANA DEL %s AL %s",
                    weekStart.format(DateTimeFormatter.ofPattern(DATE_PATTERN)),
                    weekEnd.format(DateTimeFormatter.ofPattern(DATE_PATTERN))))
                    .setBold());

            for (ActivityVolunteerAvailabilitiesAndAssignmentsReadDto activity : activitiesByWeek.get(weekStart)) {
                // Add activity information
                final Paragraph activityParagraph = new Paragraph()
                        .add(new Text(String.format("(%s) ", activity.getLocalDateTime().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN))))
                                .setBold())
                        .add(new Text(activity.getName().toUpperCase())
                                .setBold())
                        .setUnderline()
                        .setFontSize(14);

                document.add(activityParagraph);

                final int numberOfCoordinatorsNeeded = activity.getNumberOfCoordinators();
                // Add coordinators
                if (PrintOption.ASSIGNMENTS.equals(printOption) || PrintOption.ALL.equals(printOption)) {
                    final List<VolunteerReducedReadDto> volunteersAssigned = activity.getAssignments();
                    final int numberOfCoordinatorsAssigned = volunteersAssigned.size();
                    addVolunteerListToDocument(document, volunteersAssigned,
                            COORDINATORS_VOLUNTEERS_LABEL + " (" + numberOfCoordinatorsAssigned + "/" + numberOfCoordinatorsNeeded + "): ");
                }

                // Add available volunteers
                if (PrintOption.AVAILABILITIES.equals(printOption) || PrintOption.ALL.equals(printOption)) {
                    final List<VolunteerReducedReadDto> volunteersAvailable = activity.getAvailabilities();
                    final int numberOfCoordinatorsAvailable = volunteersAvailable.size();
                    addVolunteerListToDocument(document, volunteersAvailable,
                            AVAILABLE_VOLUNTEERS_LABEL + " (" + numberOfCoordinatorsAvailable + "/" + numberOfCoordinatorsNeeded + "): ");
                }

                document.add(new LineSeparator(new DottedLine()));
            }
        }

        document.close();
    }

    private LocalDate getStartOfWeek(LocalDateTime date) {
        return date.toLocalDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    private void addVolunteerListToDocument(Document document, List<VolunteerReducedReadDto> volunteers, String label) {
        if (volunteers != null) {
            final String volunteersStr = volunteers.stream()
                    .map(VolunteerReducedReadDto::getFullNameWithLastNameInitial)
                    .collect(Collectors.joining(", "));
            document.add(new Paragraph(label + volunteersStr));
        }
    }

}
