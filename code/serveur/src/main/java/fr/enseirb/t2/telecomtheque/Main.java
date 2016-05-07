package fr.enseirb.t2.telecomtheque;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import fr.enseirb.t2.telecomtheque.config.CORSManager;
import fr.enseirb.t2.telecomtheque.util.Logs;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://0.0.0.0:80/api/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.telecom package
        String[] packages = {"fr.enseirb.t2.telecomtheque"};

        final ResourceConfig rc = new ResourceConfig().packages(packages);
        
        // Manage CORS
        rc.register(new CORSManager());
        
        // Required to support Swagger
//        rc.register(io.swagger.jaxrs.listing.ApiListingResource.class);
//        rc.register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
//
//        BeanConfig beanConfig = new BeanConfig();
//        beanConfig.setVersion("1.0.2");
//        beanConfig.setSchemes(new String[]{"http"});
//        beanConfig.setHost("localhost:80");
//        beanConfig.setBasePath("");
//        beanConfig.setResourcePackage("fr.enseirb.t2.telecomtheque");
//        beanConfig.setScan(true);
//        
        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
        HttpHandler httpHandler = new CLStaticHttpHandler(HttpServer.class.getClassLoader(), "/");
        httpServer.getServerConfiguration().addHttpHandler(httpHandler, "/");
        return httpServer;
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    @SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        
//        CLStaticHttpHandler staticHttpHandler = new CLStaticHttpHandler(Main.class.getClassLoader(), "swagger-ui/");
//        server.getServerConfiguration().addHttpHandler(staticHttpHandler, "docs");
        
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        
        try {
            Logs.setup();
          } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
          }
        
        System.in.read();
        server.stop();
    }
}

