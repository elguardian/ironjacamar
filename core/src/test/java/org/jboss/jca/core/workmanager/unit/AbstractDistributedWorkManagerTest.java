/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.jca.core.workmanager.unit;

import org.jboss.jca.arquillian.embedded.Inject;
import org.jboss.jca.core.api.workmanager.DistributedWorkManager;
import org.jboss.jca.core.workmanager.rars.dwm.WorkConnection;
import org.jboss.jca.core.workmanager.rars.dwm.WorkConnectionFactory;

import javax.annotation.Resource;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.work.DistributableWork;
import javax.resource.spi.work.Work;

import org.jboss.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * An abstract distributed workmanager test
 *
 * @author <a href="stefano.maestri@jboss.com">Stefano Maestri</a>
 */
public class AbstractDistributedWorkManagerTest
{
   private static Logger log = Logger.getLogger(AbstractDistributedWorkManagerTest.class);

   @Resource(mappedName = "java:/eis/WorkConnectionFactory")
   private WorkConnectionFactory wcf;

   /** injected DistributedWorkManager1 */
   @Inject(name = "DistributedWorkManager1")
   protected DistributedWorkManager dwm1;

   @Inject(name = "DistributedBootstrapContext1")
   private BootstrapContext dbc1;

   /** injected DistributedWorkManager2 */
   @Inject(name = "DistributedWorkManager2")
   protected DistributedWorkManager dwm2;

   @Inject(name = "DistributedBootstrapContext2")
   private BootstrapContext dbc2;


   // --------------------------------------------------------------------------------||
   // Tests --------------------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   /**
    * Test that the used distributed work managers are configured
    * @throws Throwable throwable exception
    */
   //@Test
   public void testConfigured() throws Throwable
   {
      assertNotNull(dwm1);
      assertNotNull(dwm1.getPolicy());
      assertNotNull(dwm1.getSelector());
      assertNotNull(dwm1.getTransport());

      assertNotNull(dwm2);
      assertNotNull(dwm2.getPolicy());
      assertNotNull(dwm2.getSelector());
      assertNotNull(dwm2.getTransport());

      assertNotNull(dbc1);
      assertNotNull(dbc2);
   }

   /**
    * Test that a work instance can be executed
    * @throws Throwable throwable exception
    */
   //@Test
   public void testExecuted() throws Throwable
   {
      log.infof("DWM1: %s", dwm1);
      log.infof("DWM2: %s", dwm2);

      assertNotNull(wcf);

      WorkConnection wc = wcf.getConnection();
      try
      {
         wc.doWork(new MyWork());
         wc.doWork(new MyDistributableWork());

         assertEquals(1, dwm1.getStatistics().getWorkSuccessful());
         assertEquals(1, dwm2.getStatistics().getWorkSuccessful());
      }
      finally
      {
         wc.close();
      }
   }

   /**
    * Test that the used distributed work managers are an instance of the
    * <code>javax.resource.spi.work.DistributableWorkManager</code> interface
    * @throws Throwable throwable exception
    */
   //@Test
   public void testInstanceOf() throws Throwable
   {
      log.infof("DWM1: %s", dwm1);
      log.infof("DWM2: %s", dwm2);

      assertNotNull(dwm1);
      assertTrue(dwm1 instanceof javax.resource.spi.work.DistributableWorkManager);

      assertNotNull(dwm2);
      assertTrue(dwm2 instanceof javax.resource.spi.work.DistributableWorkManager);
   }

   // --------------------------------------------------------------------------------||
   // Helper classes -----------------------------------------------------------------||
   // --------------------------------------------------------------------------------||
   /**
    * Work
    */
   public static class MyWork implements Work
   {
      /**
       * {@inheritDoc}
       */
      public void run()
      {
         System.out.println("MyWork: run");
      }

      /**
       * {@inheritDoc}
       */
      public void release()
      {
         System.out.println("MyWork: release");
      }
   }

   /**
    * DistributableWork
    */
   public static class MyDistributableWork implements DistributableWork
   {
      /** Serial version uid */
      private static final long serialVersionUID = 1L;

      /**
       * {@inheritDoc}
       */
      public void run()
      {
         System.out.println("MyDistributableWork: run");
      }

      /**
       * {@inheritDoc}
       */
      public void release()
      {
         System.out.println("MyDistributableWork: release");
      }
   }
}