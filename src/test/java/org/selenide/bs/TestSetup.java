package org.selenide.bs;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TestSetup implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback {
  private static final Logger log = LoggerFactory.getLogger(TestSetup.class);
  private final String username = System.getenv("BROWSERSTACK_USERNAME");
  private final String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");

  /**
   * See all <a href="https://www.browserstack.com/automate/capabilities">BrowserStack settings</a>
   */
  @Override
  public void beforeAll(ExtensionContext context) {
    Configuration.remote = "https://hub-cloud.browserstack.com/wd/hub";
    Configuration.browserCapabilities.setCapability("bstack:options", Map.of(
      "source", "selenide-examples:selenide-browserstack:main",
      "userName", username,
      "accessKey", accessKey
    ));
    Configuration.timeout = 10000;

    log.info("Setup webdriver: remoteUrl='{}', username='{}', accessKey='{}'", Configuration.remote,
      username.replaceAll(".", "*"), accessKey.replaceAll(".", "*"));
  }

  @Override
  public void beforeEach(ExtensionContext context) {
    log.info("Start test {}.{}", context.getRequiredTestClass().getSimpleName(), context.getDisplayName());
  }

  @Override
  public void afterEach(ExtensionContext context) {
    log.info("Finish test {}.{}", context.getRequiredTestClass().getSimpleName(), context.getDisplayName());
  }
}
