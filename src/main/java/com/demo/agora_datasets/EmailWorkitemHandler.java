package com.demo.agora_datasets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailWorkitemHandler implements WorkItemHandler {
  private static final Logger logger = LoggerFactory.getLogger(EmailWorkitemHandler.class);

  @Override
  public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
    String to = (String) workItem.getParameter("To");
    String subject = (String) workItem.getParameter("Subject");
    String body = (String) workItem.getParameter("Body");
    List<String> requestedDatasets = (List) workItem.getParameter("requestedDatasets");

    String message = "  ===========   MOCK EMAIL START (to be sent by system)  =============\n";
    message += "To: " + to + "\n";
    message += "Subject: " + subject + "\n";
    message += "Message: " + body + "\n";
    message += "  =========== //  MOCK EMAIL END ====================\n";

    logger.info(message);

    List<DatasetAccessRequest> dar = new ArrayList<>();
    if (requestedDatasets != null && !requestedDatasets.isEmpty()) {
      for (String requestedDataset : requestedDatasets) {
        dar.add(new DatasetAccessRequest(requestedDataset));
        logger.info("found a dataset access request with value: {}", requestedDataset);
      }
    }
    Map<String, Object> results = new HashMap<>();
    results.put("datasetAccessRequestList", dar);

    manager.completeWorkItem(workItem.getId(), results);
  }

  @Override
  public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
    // Empty implementation, not required in this use case
  }

}
