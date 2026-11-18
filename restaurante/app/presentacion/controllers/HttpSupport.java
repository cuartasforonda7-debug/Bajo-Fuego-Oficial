package restaurante.app.presentacion.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.nio.charset.StandardCharsets;

public final class HttpSupport {
    public static final ObjectMapper JSON=new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    public static String body(HttpExchange e)throws IOException{return new String(e.getRequestBody().readAllBytes(),StandardCharsets.UTF_8);}
    public static void json(HttpExchange e,int status,Object value)throws IOException{byte[]b=JSON.writeValueAsBytes(value);e.getResponseHeaders().set("Content-Type","application/json; charset=utf-8");e.sendResponseHeaders(status,b.length);try(OutputStream out=e.getResponseBody()){out.write(b);}}
    public static void error(HttpExchange e,int status,String message)throws IOException{json(e,status,new java.util.HashMap<String,String>(){{put("error",message);}});}
    public static Long idFromPath(HttpExchange e,String prefix){String p=e.getRequestURI().getPath();if(!p.startsWith(prefix))return null;String[]x=p.substring(prefix.length()).split("/");for(String s:x)if(!s.isBlank())try{return Long.parseLong(s);}catch(NumberFormatException ignored){}return null;}
    public static boolean method(HttpExchange e,String m){return m.equalsIgnoreCase(e.getRequestMethod());}
}
