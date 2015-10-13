package io.saso.dash.modules;

import com.google.inject.AbstractModule;
import io.saso.dash.redis.DashRedis;
import io.saso.dash.redis.Redis;
import io.saso.dash.redis.tables.DashRedisConnections;
import io.saso.dash.redis.tables.RedisConnections;
import io.saso.dash.templating.DashTemplater;
import io.saso.dash.templating.Templater;

public class TemplatingModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(Templater.class).to(DashTemplater.class);
    }
}