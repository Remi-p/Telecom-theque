package fr.enseirb.t2.telecomtheque.config;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

public class CORSManager implements ContainerResponseFilter {
		 
		public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
				throws IOException {
	 
			MultivaluedMap<String, Object> headers = responseContext.getHeaders();
			headers.add("Access-Control-Allow-Origin", "*");
			headers.add("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");			
			headers.add("Access-Control-Allow-Headers", "Accept, Accept-Encoding, Authorization, Content-Length, Content-Type, Host, If-Match, Origin, X-Requested-With, User-Agent, No-Auth-Challenge");
			headers.add("Access-Control-Expose-Headers", "Location, ETag, Auth-Token, Auth-Token-Valid-Until, Auth-Token-Location");

		}
	}

