package io.saso.dash.modules;

import com.google.inject.AbstractModule;
import io.saso.dash.config.Config;
import io.saso.dash.config.impl.DashConfig;

public class ConfigModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(Config.class).to(DashConfig.class);
    }
}
