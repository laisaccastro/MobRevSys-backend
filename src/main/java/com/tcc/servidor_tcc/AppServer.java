/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.servidor_tcc;

import java.io.IOException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 *
 * @author laisa
 */
public class AppServer {
    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Server server = new Server(Integer.valueOf(System.getenv("PORT")));
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
//        context.setResourceBase("src/main/webapp/angularjs/dist");
        context.addServlet(DefaultServlet.class,"/");  
        ServletHolder servlet  = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class,"/api/*");
        servlet.setInitOrder(0);
        servlet.setInitParameter("jersey.config.server.provider.packages","com.tcc.servidor_tcc.api");
        

        try{
            server.start();
            server.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            server.destroy();
        }
    }
}
