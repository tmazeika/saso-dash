package io.saso.dash;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.saso.dash.modules.ConfigModule;
import io.saso.dash.modules.DatabaseModule;
import io.saso.dash.modules.ServerModule;
import io.saso.dash.server.Server;

public class Dash
{
    public static void main(String[] args) throws Exception
    {
        final Injector injector = Guice.createInjector(
                new ConfigModule(),
                new DatabaseModule(),
                new ServerModule()
        );

        injector.getInstance(Server.class).start();
    }
}
