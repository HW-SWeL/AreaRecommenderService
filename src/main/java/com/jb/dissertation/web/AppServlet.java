package com.jb.dissertation.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
//import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.omg.CORBA.portable.InputStream;


import com.google.maps.model.LatLng;
import com.jb.dissertation.controllers.Controller;
import com.jb.dissertation.models.location.Location;
import com.jb.dissertation.preference.Preference;
import com.jb.dissertation.utils.JsonUtils;

/**
 * Servlet implementation class AppServlet
 */
@WebServlet("/AppServlet")
public class AppServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	Controller controller;
	Exception exception;
	
    /**
     * @throws Exception 
     * @see HttpServlet#HttpServlet()
     */
    public AppServlet() throws Exception {
    	super();        
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		
		long startTime = System.currentTimeMillis();
    	
		ServletContext context = getServletContext();
        
		System.out.println(context.getRealPath("/"));
		
    	controller = new Controller(context);
    	try {
			controller.initialise();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ServletException ex = new ServletException();
			ex.addSuppressed(e);
			exception = e;
		}
    	
    	long estimatedTime = System.currentTimeMillis() - startTime;
    	
    	
    	System.out.println("Initialised servlet after "+estimatedTime / 1000 / 60+" minutes");

	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
	
		if(exception!=null){
			exception.printStackTrace(out);
		}
		else{
			String param = request.getParameter("request");		
			if(param!=null){
				//request=location
				if(param.equals("location")){
					Location loc = null;
					
					String name = request.getParameter("name");
					if(name!=null){
						System.out.println("Request parameter: " + param + " location name: " + name);
						loc = controller.getLocation(name);
					}
					else{
						String latLng = request.getParameter("latlng");
						System.out.println("Request parameter: " + param + " latlng: " + latLng);
						if(latLng!=null){
							String[] latLngSplit = latLng.split(",");
							loc = controller.getLocation(new LatLng(Double.parseDouble(latLngSplit[0]), Double.parseDouble(latLngSplit[1])));
						}
					}
					
					out.write(JsonUtils.locationToJson(loc).toJSONString());
					
					
				}
				//request=recommend
				else if(param.equals("recommend")){
					String prefJson = request.getParameter("preferences");
					System.out.println("Request parameter: " + param + " preferences: " + prefJson);
					List<Preference> preferences = null;
					
					try {
						preferences = JsonUtils.jsonToPreferences(prefJson);
						
						Map<Double, List<Map<String, Object>>> recommendation = null;
						try {
							recommendation = controller.recommend(preferences);
							
							out.write(JsonUtils.recommendationToJSON(recommendation).toJSONString());
						} catch (Exception e) {
							StringWriter writer = new StringWriter();
							e.printStackTrace(new PrintWriter(writer));
							String stackTrace = writer.toString();
							out.write(stackTrace);
						}
					} catch (Exception e) {
						StringWriter writer = new StringWriter();
						e.printStackTrace(new PrintWriter(writer));
						String stackTrace = writer.toString();
						out.write(stackTrace);
					}
				}
				//request=locationsSimple
				else if(param.equals("locationsSimple")){
					System.out.println("Request parameter: " + param);
					out.write(JsonUtils.locationsToJsonSimple(controller.getLocations()).toJSONString());
				}
				//request=locations
				else if(param.equals("locations")){
					System.out.println("Request parameter: " + param);
					out.write(JsonUtils.locationsToJson(controller.getLocations()).toJSONString());
				}
			}
		}
	}
}
