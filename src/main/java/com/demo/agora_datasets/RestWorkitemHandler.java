package com.demo.agora_datasets;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestWorkitemHandler implements WorkItemHandler {
  private static final Logger logger = LoggerFactory.getLogger(RestWorkitemHandler.class);

  @Override
  public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
    String globalClearenceNumber = (String) workItem.getParameter("globalClearenceNumber");
    String eShareNumber = (String) workItem.getParameter("eShareNumber");
    String modelRegistryUseCaseId = (String) workItem.getParameter("modelRegistryUseCaseId");

    logger.info("Running REST MOCK work item");
    Map<String, Object> results = new HashMap<>();
    if (modelRegistryUseCaseId == null) {
      logger.info("Retrieving use case details for Global Clearance number {}", globalClearenceNumber);
      logger.info("Retrieving use cse details for eShare number {}", eShareNumber);
      // About 70% of requests have a use case id
      Random random = new Random();
      int probability = random.nextInt(100 - 1) + 1;
      boolean isRiskyClearance = globalClearenceNumber != null && globalClearenceNumber.startsWith("111");
      boolean isRiskyEShare = eShareNumber != null && eShareNumber.startsWith("111");
      if (probability > 70 || isRiskyClearance || isRiskyEShare) {
        results.put("modelRegistryUseCaseId", UUID.randomUUID().toString());
      } else {
        logger.info("Adding RISKY DATASET, because probability {}, clearance risky {}, and eShare risky {}", probability, isRiskyClearance, isRiskyEShare);
        results.put("modelRegistryUseCaseId", "RISK_" + UUID.randomUUID().toString());
      }
    } else {
      logger.info("Retrieving use case details for use case id {}", modelRegistryUseCaseId);

      UseCaseRegistry ucr = new UseCaseRegistry();
      ucr.setCriticality(0);
      // risky clearence starts with 111
      if (modelRegistryUseCaseId.startsWith("RISK")) {
        ucr.setName("Risky Use Case");
        ucr.setCriticality(100);
      } else {
        ucr.setName(modelRegistryUseCaseId);
        Random random = new Random();
        ucr.setCriticality(random.nextInt(100 - 1) + 1);
      }

      logger.info("MOCK REGISTRY FOUND {}", ucr);
      results.put("modelRegistry", ucr);
    }

    manager.completeWorkItem(workItem.getId(), results);
  }

  @Override
  public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
    // Empty implementation, not required for this use case.

  }

}
