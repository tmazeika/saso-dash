package io.saso.dash.config

import com.google.inject.Singleton
import io.saso.dash.util.logger
import io.saso.dash.util.tryResources
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.*

@Singleton @Suppress("UNCHECKED_CAST")
public class DashConfigOLD : Config
{
    private val map: Map<String, Any> by lazy {
        val file = File("config.yml")

        tryResources({
            val input = FileInputStream(file).autoClose()
            val map = (Yaml() load input) as Map<String, Any>

            logger(this@DashConfig) info
                    "Loaded config @ ${file.canonicalPath} => $map"

            map
        }, { e: FileNotFoundException ->
            logger(this@DashConfig) warn
                    "Config @ ${file.canonicalPath} not found, using defaults"

            emptyMap()
        })
    }

    override fun <T> get(key: String, default: T): T
    {
        val parts = key split '.'

        var subMap = map;

        parts.subList(0, parts.lastIndex).forEach {
            val value = Optional.ofNullable(subMap get it)

            subMap = (value orElse emptyMap<String, Any>()) as Map<String, Any>
        }

        // can't use Map#getOrDefault(); if the *value* is `null`, then we'd
        // return `null`, which we don't want; we want to return [default]
        return (subMap get parts.last()) as T ?: default
    }
}
