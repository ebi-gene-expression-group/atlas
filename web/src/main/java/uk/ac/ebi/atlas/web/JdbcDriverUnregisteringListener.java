
package uk.ac.ebi.atlas.web;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;


public class JdbcDriverUnregisteringListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
    }


    @Override
    public void contextDestroyed(ServletContextEvent event) {
        deregisterDrivers(getDrivers());
    }

    /**
     * Retrieves an Enumeration with all of the currently loaded JDBC drivers.
     *
     * @return the list of JDBC Drivers
     */
    Enumeration<Driver> getDrivers() {
        return DriverManager.getDrivers();
    }

    /**
     * Unregistering JDBC drivers given as param.
     *
     * @param drivers {@link Enumeration} of {@link Driver} to unregister
     */
    void deregisterDrivers(Enumeration<Driver> drivers) {
        while (drivers.hasMoreElements()) {
            deregisterDriver(drivers.nextElement());
        }
    }

    /**
     * Unregistering single JDBC driver given as param.
     *
     * @param driver to unregister
     */
    void deregisterDriver(Driver driver) {
        try {
            DriverManager.deregisterDriver(driver);
            //System.out because log4j doesn't work in context listeners
            System.out.println("Successfully deregistered JDBC driver: " + driver);
        } catch (SQLException e) {
            //System.out because log4j doesn't work in context listeners
            e.printStackTrace();
        }
    }
}



