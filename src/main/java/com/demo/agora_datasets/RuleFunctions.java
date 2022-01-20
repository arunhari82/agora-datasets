package com.demo.agora_datasets;

import org.drools.core.spi.KnowledgeHelper;
import org.slf4j.LoggerFactory;

public class RuleFunctions {
  /**
   * Log an INFO message from a rule, using the rule’s package and name as the
   * slf4j category.
   */
  public static void log(final KnowledgeHelper drools, final String message,
      final Object... parameters) {

    final String category = drools.getRule().getPackageName() + "."
        + drools.getRule().getName();
    final String formattedMessage = String.format(message, parameters);
    LoggerFactory.getLogger(category).info(formattedMessage);
  }
}
