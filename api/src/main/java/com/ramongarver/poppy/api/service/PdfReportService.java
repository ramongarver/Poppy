package com.ramongarver.poppy.api.service;

import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto;
import com.ramongarver.poppy.api.enums.PrintOption;

import java.io.OutputStream;

public interface PdfReportService {

    void createAssignmentReport(ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto volunteerAvailabilitiesAndAssignments,
                                PrintOption printOption, OutputStream outputStream);

}
