package com.demo.agora_datasets;

import java.util.HashMap;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

public class RestWorkitemHandler implements WorkItemHandler {

  @Override
  public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
    // TODO Auto-generated method stub
    manager.completeWorkItem(workItem.getId(), new HashMap<String,Object>());
  }

  @Override
  public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
    // TODO Auto-generated method stub

  }

}
