
package uk.ac.ebi.atlas.web;

import oracle.ucp.UniversalConnectionPoolException;
import oracle.ucp.admin.UniversalConnectionPoolManager;
import oracle.ucp.admin.UniversalConnectionPoolManagerImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class UCPDestroyPoolListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        destroyPool();
    }

    /**
     * Destroy UCP pools
     */
    void destroyPool() {
        // Shutdown UCP if present, to avoid warnings about thread leaks
        UniversalConnectionPoolManager ucpManager = null;
        try {
            ucpManager = UniversalConnectionPoolManagerImpl.getUniversalConnectionPoolManager();
            if (ucpManager != null) {
                String[] poolNames = ucpManager.getConnectionPoolNames();
                if (poolNames != null) {
                    for (String poolName : poolNames) {
                        ucpManager.destroyConnectionPool(poolName);
                        System.out.println("Successfully destroyed connection pool: " + poolName);
                    }
                }
            }
        } catch (UniversalConnectionPoolException e) {
            e.printStackTrace();
        }
    }
}
