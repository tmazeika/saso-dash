package io.saso.dash;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.saso.dash.modules.*;
import io.saso.dash.startup.StartupManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Dash
{
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args)
    {
        logger.info("Starting Dash");

        Injector injector = Guice.createInjector(
                new DatabaseModule(),
                new RedisModule(),
                new ServerModule(),
                new ServicesModule(),
                new StartupModule(),
                new TemplatingModule());

        // run startup
        injector.getInstance(StartupManager.class).start();
    }
}
