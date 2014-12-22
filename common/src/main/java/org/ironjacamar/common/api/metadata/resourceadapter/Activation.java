/*
 * IronJacamar, a Java EE Connector Architecture implementation
 * Copyright 2014, Red Hat Inc, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the Eclipse Public License 1.0 as
 * published by the Free Software Foundation.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Eclipse
 * Public License for more details.
 *
 * You should have received a copy of the Eclipse Public License 
 * along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.ironjacamar.common.api.metadata.resourceadapter;

import org.ironjacamar.common.api.metadata.JCAMetadata;
import org.ironjacamar.common.api.metadata.common.TransactionSupportEnum;

import java.util.List;
import java.util.Map;

/**
 * An activation of a resource adapter.
 *
 * @author <a href="stefano.maestri@ironjacamar.org">Stefano Maestri</a>
 */
public interface Activation extends JCAMetadata
{
   /**
    * Get the archive.
    *
    * @return the archive.
    */
   public String getArchive();

   /**
    * Get the id
    *
    * @return the value.
    */
   public String getId();

   /**
    * Get the transactionSupport.
    *
    * @return the transactionSupport.
    */
   public TransactionSupportEnum getTransactionSupport();

   /**
    * Get the connectionFactories.
    *
    * @return the connectionFactories.
    */
   public List<ConnectionDefinition> getConnectionDefinitions();

   /**
    * Get the adminObjects.
    *
    * @return the adminObjects.
    */
   public List<AdminObject> getAdminObjects();

   /**
    * Get the configProperties.
    *
    * @return the configProperties.
    */
   public Map<String, String> getConfigProperties();

   /**
    * Get the beanValidationGroups.
    *
    * @return the beanValidationGroups.
    */
   public List<String> getBeanValidationGroups();

   /**
    * Get the bootstrapContext.
    *
    * @return the bootstrapContext.
    */
   public String getBootstrapContext();

   /**
    * Get the WorkManager
    * @return The value
    */
   public WorkManager getWorkManager();
}
