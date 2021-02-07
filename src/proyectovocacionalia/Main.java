/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectovocacionalia;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Cuestionario x = new Cuestionario(); //leemos las preguntas del txt
            
            
            for(int i=0;i< 1 + (int)(x.cantPreguntas/x.agrupacion);i++){ //RECORRIDO DE LAS PREGUNTAS
                FormularioPreguntas form = new FormularioPreguntas();
                form.inicializar(x.getGrupoN(i), i*x.agrupacion, (i+1)*x.agrupacion);
                
                synchronized(form){
                    form.wait();
                }
                
                System.out.println(form.respuestas);
                //obtenemos el valor de las respuestas
                x.respuestas.set(i*x.agrupacion,  form.respuestas.get(0));
                x.respuestas.set(i*x.agrupacion +1,  form.respuestas.get(1));
                x.respuestas.set(i*x.agrupacion+2,  form.respuestas.get(2));
                x.respuestas.set(i*x.agrupacion+3,  form.respuestas.get(3));
                x.respuestas.set(i*x.agrupacion+4,  form.respuestas.get(4));
                
                
            }
            //System.out.println("resp:\n +" + x.respuestas.toString()     );
            //en este punto ya tengo todas mis respuestas en x.respuestas
            x.razonamiento();
            //ya tengo las cualidades deducidas en x.cualidadesRespuestas
            FormRespuesta y = new FormRespuesta();
            x.calcularAreas();
            y.pasarCualidades(x.cualidades,x.cualidadesResultantes,x.areas,x.areasResultantes);
            
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
