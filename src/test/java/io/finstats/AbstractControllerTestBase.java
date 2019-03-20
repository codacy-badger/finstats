package io.finstats;

import org.junit.BeforeClass;

public abstract class AbstractControllerTestBase {

  @BeforeClass
  public static void setUpClass() {
    FinStatsApp.start(0);
  }
}
