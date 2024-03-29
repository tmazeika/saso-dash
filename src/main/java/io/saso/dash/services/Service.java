package io.saso.dash.services;

import io.saso.dash.server.Client;

public interface Service
{
    /**
     * Gets the name of this service. It <em>must</em> match the name that would
     * be found in the DB.
     *
     * @return the name
     */
    String getName();

    /**
     * Gets the poll interval of this service in seconds. By default, returns
     * {@code -1}, indicating that this service should not be polled.
     *
     * @return the poll interval, or {@code -1} if this service should not be
     *         polled
     */
    default int getPollInterval()
    {
        return -1;
    }

    /**
     * Starts the service. This method may block as it is run in an independent
     * thread specific to this service.
     *
     * @param client the client
     */
    default void start(Client client) throws Exception { }

    /**
     * Polls the service. Called as per {@link #getPollInterval()}. This method
     * may block as it is run in an independent thread specific to this service.
     *
     * @param client the client
     */
    default void poll(Client client) throws Exception { }

    /**
     * Stops the service. This method may block as it is run in an independent
     * thread specific to this service.
     *
     * @param client the client
     */
    default void stop(Client client) throws Exception { }
}
