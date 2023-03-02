package com.sap.csc.timebackend.repository;

import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.datamodel.odata.client.exception.ODataException;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.workforcetimesheet.TimeSheetEntry;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.WorkforceTimesheetService;
import com.sap.csc.timebackend.exceptions.SAPException;
import com.sap.csc.timebackend.helper.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class TimeSheetRepositoryImpl implements TimeSheetRepository {

    private static final String APPROVED = "30";
    private final HttpDestination erpConfigContext;
    private final WorkforceTimesheetService manageWorkforceTimesheetService;


    @Autowired
    public TimeSheetRepositoryImpl(HttpDestination erpConfigContext, WorkforceTimesheetService manageWorkforceTimesheetService) {
        this.erpConfigContext = erpConfigContext;
        this.manageWorkforceTimesheetService = manageWorkforceTimesheetService;
    }


    @Override
    public List<TimeSheetEntry> getByDateAndUser(LocalDate dateFrom, LocalDate dateTo, String user) throws ODataException {
        Helper.nullChecks(dateFrom, dateTo);

        final LocalDateTime from = dateFrom.atStartOfDay();                
        final LocalDateTime to = dateTo.atTime(23,59,59,999999999);

        return manageWorkforceTimesheetService.getAllTimeSheetEntry()
                .select(TimeSheetEntry.ALL_FIELDS)
                .filter(TimeSheetEntry.TIME_SHEET_DATE.ge(from).and(TimeSheetEntry.TIME_SHEET_DATE.le(to)))
                .filter(TimeSheetEntry.PERSON_WORK_AGREEMENT_EXTERNAL_ID.eq(user))
                .filter(TimeSheetEntry.TIME_SHEET_STATUS.eq(APPROVED))
                .executeRequest(erpConfigContext);
    }

    @Override
    public List<TimeSheetEntry> saveAll(List<TimeSheetEntry> entries) {
        Helper.nullChecks(entries);
        entries.forEach(Helper::nullChecks);
        List<TimeSheetEntry> createdEntries = new ArrayList<>(entries.size());

        entries.forEach(e -> {
            try {
                createdEntries.add(manageWorkforceTimesheetService.createTimeSheetEntry(e)
                        .executeRequest(erpConfigContext).getModifiedEntity());             
            } catch (ODataException e1) {
                throw SAPException.create(e1.getMessage());
            }
        });
        return createdEntries;
    }


    private Calendar setTime(LocalDate date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return calendar;
    }
}
