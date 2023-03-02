package com.sap.csc.timebackend.config;


import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.s4hana.connectivity.DefaultErpHttpDestination;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultWorkforceTimesheetService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.WorkforceTimesheetService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S4Beans {

    @Bean
    public WorkforceTimesheetService manageWorkforceTimesheetService() {
        return new DefaultWorkforceTimesheetService();
    }

    @Bean
    public HttpDestination erpConfigContext() {
        HttpDestination destination =  DestinationAccessor.getDestination("S4HANA_CLOUD").asHttp()
                .decorate(DefaultErpHttpDestination::new);
        return destination;
    }


}