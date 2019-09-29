package self.erp.erpapplication;

import self.erp.commons.prereqs.DatabasePreRequisites;

/**
 * This is the main spring boot application of erp. It scans all the controllers , models and repositories for the
 * spring application context Just run this as a simple jar
 */
/*
 * @EnableJpaRepositories(basePackages = { "self.erp.visitorservice.repositories" })
 * 
 * @EntityScan(basePackages = { "self.erp.visitorservice.repositories" })
 * 
 * @SpringBootApplication(scanBasePackages = { "self.erp.visitorservice.controller",
 * "self.erp.communicationservice.controller" }
 * 
 * )
 */
public class ErpApplication {
    public static void main(String[] args) {
        DatabasePreRequisites.doesMySQLExists();
        // SpringApplication.run(ErpApplication.class, args);
    }
}
