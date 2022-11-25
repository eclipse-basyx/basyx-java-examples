package org.eclipse.basyx.examples.scenarios.authorization;

import org.eclipse.basyx.components.IComponent;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;

public final class ShutdownHookUtil {
  public static void addShutdownHook(IComponent component) {
    Thread shutdownListener = new Thread(component::stopComponent);
    Runtime.getRuntime().addShutdownHook(shutdownListener);
  }

  public static void addShutdownHook(BaSyxHTTPServer server) {
    Thread shutdownListener = new Thread(server::shutdown);
    Runtime.getRuntime().addShutdownHook(shutdownListener);
  }

  public static void addShutdownHook(Runnable runnable) {
    Thread shutdownListener = new Thread(runnable);
    Runtime.getRuntime().addShutdownHook(shutdownListener);
  }
}
