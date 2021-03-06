/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Lab 08 - Shopping List Implementation
 * UniTN
 */
package it.unitn.disi.wp.lab08.shoppinglist.listeners;

import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.commons.persistence.dao.factories.jdbc.JDBCDAOFactory;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author Stefano Chirico &lt;stefano dot chirico at unitn dot it&gt;
 * @since 2019.04.06
 */
public class WebAppContextListener implements ServletContextListener {

    /**
     * The serlvet container call this method when initializes the application
     * for the first time.
     * @param sce the event fired by the servlet container when initializes
     * the application
     * 
     * @author Stefano Chirico
     * @since 1.0.0.190407
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String dburl = sce.getServletContext().getInitParameter("dburl");
        try {
            JDBCDAOFactory.configure(dburl);
            DAOFactory daoFactory = JDBCDAOFactory.getInstance();
            sce.getServletContext().setAttribute("daoFactory", daoFactory);
        } catch (DAOFactoryException ex) {
            Logger.getLogger(getClass().getName()).severe(ex.toString());
            throw new RuntimeException(ex);
        }
    }

    /**
     * The servlet container call this method when destroyes the application.
     * @param sce the event generated by the servlet container when destroyes
     * the application.
     * 
     * @author Stefano Chirico
     * @since 1.0.0.190407
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DAOFactory daoFactory = (DAOFactory) sce.getServletContext().getAttribute("daoFactory");
        if (daoFactory != null) {
            daoFactory.shutdown();
        }
        daoFactory = null;
    }
}
