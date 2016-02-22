/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tokenizador;

import applamento.DocumentoCompleto;
import applamento.DocumentoDiputado;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.serialization.JsonMetadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 *
 * @author nicolasgarciamunoz
 */
public class Tokenizador {
    private ArrayList<File> listaFicheros = new ArrayList();
    private Tika tika = new Tika();
    
    
    /*Este método añade archivos a la lista de Ficheros que hay que 
    analizar.*/
    public void addFiles(File file) {
        /*Si no existe el fichero, error.*/
        if (!file.exists()){
           System.out.println (file + " no existe."); 
        //Si es un directorio el fichero, llamamos recursivamente a esta funcion
        } else if(file.isDirectory()) {

            for(File f: file.listFiles()){ 
                addFiles(f);
            }
        } else {
        //Si no es un fichero, ponemos el archivo en la cola
            listaFicheros.add(file);
        }
    }
    
    public Tokenizador(String ruta) {  
        File directorio = new File(ruta);
        // Añadimos el fichero o directorio
        addFiles(directorio);
    }
    
    public List<DocumentoCompleto> getColeccionCompleta() throws JDOMException, IOException {
        List<DocumentoCompleto> documents = new ArrayList();
        // Para cada fichero que hay en la lista de ficheros
        for(File f: listaFicheros) {
            try {
                // Extraemos los datos
                SAXBuilder builder = new SAXBuilder();
                File xmlFile = f;
                HashMap <String, String> intervenciones = new HashMap<> ();
                Document document = (Document) builder.build(xmlFile);
                Element rootNode = document.getRootElement();
                List list = rootNode.getChildren("iniciativa_completa");
                DocumentoCompleto documento = new DocumentoCompleto();
                for (int i = 0; i < list.size(); i++) {

		   Element node = (Element) list.get(i);
                   
                   String fechaAux = node.getChild("fecha").getChildText("dia") + "-" + node.getChild("fecha").getChildText("mes") + "-" + node.getChild("fecha").getChildText("anio");
                   documento.setFecha(fechaAux);
                   documento.setMaterias(node.getChild("iniciativa").getChildText("materias"));
                   documento.setProponentes(node.getChild("iniciativa").getChildText("proponentes"));
                   documento.setIntervienen(node.getChild("iniciativa").getChildText("intervienen"));
                   documento.setVotacion(node.getChild("iniciativa").getChildText("votacion"));
                   List listaIntervenciones = node.getChild("iniciativa").getChildren("intervencion");
                   for (int j = 0; j < listaIntervenciones.size(); j++){
                       Element intervencion = (Element) listaIntervenciones.get(j);
                       intervenciones.put(intervencion.getChildText("interviniente"), intervencion.getChildText("discurso"));
                   }
                   documento.setIntervencion(intervenciones);
		}
                
                documents.add(documento);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Tokenizador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return documents;
    }
}
