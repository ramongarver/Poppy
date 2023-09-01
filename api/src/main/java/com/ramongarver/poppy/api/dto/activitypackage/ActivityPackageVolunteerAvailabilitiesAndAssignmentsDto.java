package com.ramongarver.poppy.api.dto.activitypackage;

import com.ramongarver.poppy.api.dto.activity.ActivityVolunteerAvailabilitiesAndAssignmentsReadDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto {

    ActivityPackageReducedReadDto activityPackage;

    List<ActivityVolunteerAvailabilitiesAndAssignmentsReadDto> activities;

}
