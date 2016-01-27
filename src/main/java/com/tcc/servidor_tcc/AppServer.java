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
        Server server = new Server(8080);
        ServletHolder servlet  = new ServletHolder(ServletContainer.class);
        servlet.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");
        servlet.setInitParameter("jersey.config.server.provider.packages","com.tcc.servidor_tcc.api");
        servlet.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
//        context.setResourceBase("src/main/webapp/angularjs/dist");
        context.addServlet(DefaultServlet.class,"/*");
        context.addServlet(servlet,"/api/*");

        HandlerCollection collection = new HandlerCollection();
        collection.addHandler(context);
        server.setHandler(collection);


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
