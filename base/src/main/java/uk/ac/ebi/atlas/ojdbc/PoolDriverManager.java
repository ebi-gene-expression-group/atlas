package uk.ac.ebi.atlas.ojdbc;

import oracle.ucp.UniversalConnectionPoolException;
import oracle.ucp.admin.UniversalConnectionPoolManager;
import oracle.ucp.admin.UniversalConnectionPoolManagerImpl;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class PoolDriverManager {

    public static void deregisterDrivers(Enumeration<Driver> drivers) {
        while (drivers.hasMoreElements()) {
            deregisterDriver(drivers.nextElement());
        }
    }

    private static void deregisterDriver(Driver driver) {
        try {
            DriverManager.deregisterDriver(driver);

            //System.out because log4j doesn't work in context listeners
            System.out.println("Successfully deregistered JDBC driver: " + driver);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void destroyPool(String appPoolName) {
        // Shutdown UCP if present, to avoid warnings about thread leaks
        UniversalConnectionPoolManager ucpManager = null;
        try {
            ucpManager = UniversalConnectionPoolManagerImpl.getUniversalConnectionPoolManager();
            if (ucpManager != null) {
                String[] poolNames = ucpManager.getConnectionPoolNames();
                if (poolNames != null) {
                    for (String poolName : poolNames) {
                        if (appPoolName.equals(poolName)) {
                            ucpManager.destroyConnectionPool(poolName);
                            System.out.println("Successfully destroyed connection pool: " + poolName);
                        }
                    }
                }
            }
        } catch (UniversalConnectionPoolException e) {
            e.printStackTrace();
        }
    }
}
