package com.demo.agora_datasets;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import org.jbpm.process.workitem.core.AbstractLogOrThrowWorkItemHandler;
import org.jbpm.process.workitem.core.util.RequiredParameterValidator;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;
import org.jbpm.process.workitem.core.util.Wid;
import org.jbpm.process.workitem.core.util.WidParameter;
import org.jbpm.process.workitem.core.util.WidResult;
import org.jbpm.process.workitem.core.util.service.WidAction;
import org.jbpm.process.workitem.core.util.service.WidAuth;
import org.jbpm.process.workitem.core.util.service.WidService;
import org.jbpm.process.workitem.core.util.WidMavenDepends;

import com.google.gson.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Wid(widfile="SlackPostMessageDefinitions.wid", name="SlackPostMessage",
        displayName="SlackPostMessage",
        defaultHandler="mvel: com.demo.agora_datasets.SlackWorkItemHandler()",
        documentation = "Slack/index.html",
        category = "Slack",
        icon = "SlackPostMessage.png",
        parameters={
                @WidParameter(name = "channelName", required = true),
                @WidParameter(name = "message", required = true)
        },
        mavenDepends={
        		@WidMavenDepends(group = "${groupId}", artifact = "${artifactId}", version = "${version}")
        },
        serviceInfo = @WidService(category = "${name}", description = "${description}",
                keywords = "slack,message,send,channel",
                action = @WidAction(title = "Send message to a slack channel"),
                authinfo = @WidAuth(required = false, params = {"accessToken"},
                        paramsdescription = {"Slack access token"},
                        referencesite = "https://api.slack.com/tokens")
        )
)


public class SlackWorkItemHandler extends AbstractLogOrThrowWorkItemHandler {
        private static final Logger logger = LoggerFactory.getLogger(SlackWorkItemHandler.class);
        private String accessToken;
        
        public SlackWorkItemHandler()
        {
            this.accessToken = System.getProperty("slackAccessToken");
            logger.info("*** using slack token **** " + this.accessToken);
        }

       private void sendSlackMessage(String message) {
           try 
           {
           String url = "https://hooks.slack.com/services/" + this.accessToken;
           CloseableHttpClient httpClient = HttpClientBuilder.create().build();
           HttpPost request = new HttpPost(url);
           request.addHeader("content-type","application/json");
            JsonObject json = new JsonObject();
            json.addProperty("text", message);
            StringEntity entity = new StringEntity(json.toString());
            request.setEntity(entity);
            httpClient.execute(request);
           }catch(Exception e)
           {
                   e.printStackTrace();;
           }   
       }

        



        public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        try {
            RequiredParameterValidator.validate(this.getClass(), workItem);

            // sample parameters
            String channelName = (String) workItem.getParameter("channelName");
            String message = (String) workItem.getParameter("message");

            // complete workitem impl...
                        
            // return results
            this.sendSlackMessage(message);
            manager.completeWorkItem(workItem.getId(), null);
        } catch(Throwable cause) {
            handleException(cause);
        }
    }

    @Override
    public void abortWorkItem(WorkItem workItem,
                              WorkItemManager manager) {
        // stub
    }
}


