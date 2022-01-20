package com.demo.agora_datasets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.ProcessInstance;

public class TestDatasetAccessApproval extends JbpmJUnitBaseTestCase {
  public TestDatasetAccessApproval() {
    super(true, true);
  }

  @Test
  public void testHelloWorld() {
    createRuntimeManager("com/demo/agora_datasets/dataset_access_approval.bpmn");
    RuntimeEngine runtimeEngine = getRuntimeEngine();
    KieSession ksession = runtimeEngine.getKieSession();
    Map<String, Object> parameters = new HashMap<>();
    DatasetAccessRequest dataAccessRequest = new DatasetAccessRequest();
    dataAccessRequest.setDatasetName("test dataset name");
    parameters.put("datasetAccessRequestList", dataAccessRequest);
    ProcessInstance processInstance = ksession.startProcess("agora_datasets.dataset_access_approval");
    assertProcessInstanceActive(processInstance.getId());
    // assertProcessInstanceCompleted(processInstance.getId());
    disposeRuntimeManager();
  }

}
