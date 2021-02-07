package proyectovocacionalia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Archivos {
    private String nombreArchivo;
    
    public Archivos(String nombreArchivo){
    this.nombreArchivo = nombreArchivo;
    
    }
    public String getNombreArchivo() {
        return nombreArchivo;
    }
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    
    public boolean existe(){
    File x = new File(nombreArchivo);
   return x.exists();
    }
    public int cantidadDeLineas(){
     
    FileReader fr = null;
    BufferedReader br = null;
    
    String linea = null;
    int c=0;
    try{
    fr = new FileReader(nombreArchivo);
    br = new BufferedReader(fr);
    while( (linea=br.readLine())!= null)
            c++;
        br.close();
        
    }catch(IOException ex){
        System.out.println(ex.getMessage());
        return -1;
    }
    
    return c;
    
    }
    public  boolean escribir (String texto){   
        FileWriter fw = null;
        PrintWriter pw = null;
        
        try{
//            fw = new FileWriter("miau.txt");
          fw = new FileWriter(nombreArchivo);
          pw = new PrintWriter(fw);
          
          pw.print(texto);
            pw.close();
            
        }catch(IOException ex){
            //Es para avisar que se ha producido una excepcion
            System.out.println(ex.getMessage());
        return false;
    }
     return true;   
    }
    
    
    
    
    public  String leer(){
        String texto="";
    FileReader fr = null;
    BufferedReader br = null;
    String linea = null;
    
    try{
    fr = new FileReader(nombreArchivo);
    br = new BufferedReader(fr);
    
    while( (linea=br.readLine())!= null)
            texto = texto.concat(linea) + "\n";
    texto = texto.substring(0, texto.length()-1);
        br.close();
        
    }catch(IOException ex){
        System.out.println(ex.getMessage());
    }
    
    return texto;
    
    
    }
    
    
    
    
    public ArrayList<String> leerPorLineas (){
     ArrayList<String> vector = new ArrayList<>();
    FileReader fr = null;
    BufferedReader br = null;
    String linea = null;
    
    try{
    //fr = new FileReader(nombreArchivo);
    
    Path path = Paths.get(nombreArchivo);
    //br = Files.newBufferedReader(path);
    
 
    
                    
    
    while( (linea=br.readLine())!= null){
            vector.add(linea);
            System.out.println(linea);
    }
        br.close();
        
    }catch(IOException ex){
        System.out.println(ex.getMessage());
    }
    return vector;
    }
    
}