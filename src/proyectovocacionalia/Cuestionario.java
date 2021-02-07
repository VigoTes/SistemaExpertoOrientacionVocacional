/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectovocacionalia;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.clipsrules.jni.CLIPSException;
import net.sf.clipsrules.jni.CLIPSLoadException;
import net.sf.clipsrules.jni.CaptureRouter;
import net.sf.clipsrules.jni.Environment;
import net.sf.clipsrules.jni.Router;

/**
 *
 * @author Usuario
 */
public class Cuestionario {
    
    final int agrupacion=5;
    final int cantPreguntas=98;
    
    public ArrayList<String> preguntas;
    public ArrayList<Integer> respuestas;
    
    public ArrayList<String> cualidades;
    public ArrayList<Integer> cualidadesResultantes;
    
    public ArrayList<String> areas;
    public ArrayList<Float> areasResultantes;
    
    
    private Environment clips;
    
    public String respuestaDeClips;
    
    public Cuestionario() throws IOException {
        
        //Aqui codigo pa leer preguntas del txt
        String fileName = "preguntas.txt";
        Path path = Paths.get(fileName);
        List<String> list = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
        ArrayList<String> listOfStrings = new ArrayList<>(list.size());
        listOfStrings.addAll(list);
        preguntas = listOfStrings;
        
        
        //Aqui codigo pa leer cualidades del txt
        String fileName2 = "cualidades.txt";
        Path path2 = Paths.get(fileName2);
        List<String> list2 = Files.readAllLines(path2, StandardCharsets.ISO_8859_1);
        ArrayList<String> listOfStrings2 = new ArrayList<>(list2.size());
        listOfStrings2.addAll(list2);
        cualidades = listOfStrings2;
        
        //cargamos el archivo clips 
        clips = new Environment();
        
            
        try {
            //clips.changeDirectory("C:\\Users\\Usuario\\Documents\\NetBeansProjects\\ProyectoVocacionalIA\\");
            clips.load("C:\\Users\\Usuario\\Documents\\NetBeansProjects\\ProyectoVocacionalIA\\razonamientoEjemplo.CLP");
        } catch (CLIPSException ex) {
            Logger.getLogger(Cuestionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        areas = new ArrayList<String>();
        areas.add("Administrativas y Contables");
        areas.add("Humanisticas y Sociales");
        areas.add("Artisticas");
        areas.add("Medicina y Cs de la Salud");
        areas.add("Ingenieria y Computacion");
        areas.add("Defensa y Seguridad");
        areas.add("Ciencias Exactas y Agrarias");
        
        //AHORA INICIALIZAMOS LAS 100 RESPUESTAS
        areasResultantes = new ArrayList<Float>();
        for(int i=0;i<7;i++)
            areasResultantes.add(0f);
        
        
        
        //AHORA INICIALIZAMOS LAS 100 RESPUESTAS
        respuestas = new ArrayList<Integer>();
        for(int i=0;i<100;i++)
            respuestas.add(0);
        
        //inicializamos 100 cualidades result
        cualidadesResultantes=new ArrayList<Integer> ();
        for(int i=0;i<100;i++)
            cualidadesResultantes.add(0);
        
        
    }
    
    
    
    public ArrayList<String> getPreguntas(){
        return preguntas;
    }
  
    
    //obtiene el n-esimo gurpo de preguntas, en una separacion de 'agrupacion'
    public ArrayList<String> getGrupoN(int n){
        
        ArrayList<String> grupo = new ArrayList<>();
        for(int i=n*agrupacion;  i<(n+1)*agrupacion && i<preguntas.size()  ;i++){
               grupo.add(preguntas.get(i));
        }
        return grupo;
    }
    
    
    public void razonamiento(){
    
        try{ 

            clips.reset();
            
            for(int i=0;i<98;i++){
                if(respuestas.get(i)==1){
                    System.out.println("(assert (p"+i+" s))");
                    clips.eval("(assert (p"+i+" s))");
                }
            }
            clips.run();
            
            
            String listaTotalFacts ="";
            for(int j=0;j<clips.getFactList().size();j++)
            {
                listaTotalFacts += " [["+ clips.getFactList().get(j).getRelationName() +"]]";
            }
            
            System.out.println("LISTA TOTAL FACTS \n" + listaTotalFacts + "\n----------");
            
            
            
            //aqui debemos identificar las respuestas
            for(int i=0;i<cualidadesResultantes.size();i++)
            {
                if(listaTotalFacts.contains("[[c"+i+"]]"))
                {
                   
                    cualidadesResultantes.set(i,1);
                }
            }
            
            System.out.println(cualidadesResultantes.toString());
            clips.reset();
            clips.destroy();
        }catch(Exception e ){
            System.out.println("ERROR HA OCURRIDO : "+ e);
        }
        
    }
    
    
    public void calcularAreas(){
        ArrayList<Integer> v = cualidadesResultantes; 
        float sum=0;


//AHORA ASOCIAMOS LAS CUALIDADES CON PESOS Y LO SUMAMOS PARA VER LA PERTENENCIA A CADA AREA
        //        Administrativas y Contables = 
        sum = v.get(0)*0.5f + v.get(1)*0.4f;
        areasResultantes.set(0,  sum);


//        Humanisticas y Sociales = 
        sum = v.get(0)*0.5f + v.get(1)*0.4f;
        areasResultantes.set(1,  sum);



//        Artisticas = 
        sum = v.get(3)*0.5f + v.get(7)*0.4f;
        areasResultantes.set(2,  sum);


//        Medicina y Cs de la Salud = 
        sum = v.get(3)*0.5f + v.get(6)*0.4f;
        areasResultantes.set(3,  sum);


//        Ingenieria y Computacion


//        Defensa y Seguridad

//        Ciencias Exactas y Agrarias
        
    
    }
      
    
}
