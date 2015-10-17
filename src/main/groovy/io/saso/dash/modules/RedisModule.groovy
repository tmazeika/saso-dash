package io.saso.dash.modules

import com.google.inject.AbstractModule
import io.saso.dash.redis.DashRedis
import io.saso.dash.redis.Redis
import io.saso.dash.redis.tables.DashRedisConnections
import io.saso.dash.redis.tables.RedisConnections

class RedisModule extends AbstractModule
{
    @Override
    void configure()
    {
        bind Redis to DashRedis
        bind RedisConnections to DashRedisConnections
    }
}