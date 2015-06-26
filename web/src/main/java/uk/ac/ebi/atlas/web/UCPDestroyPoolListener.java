/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

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
