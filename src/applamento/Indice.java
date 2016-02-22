/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applamento;

import Tokenizador.Documentos;
import Tokenizador.Tokenizador;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jdom.JDOMException;

/**
 *
 * @author nicolasgarciamunoz
 */
public class Indice {
    private final String DIPUTADO = "diputado";
    private final String PARTIDO = "partido";
    private final String DISCURSO = "discurso";
    
    /*El analizador que voy a usar para el texto es el español, ya que 
    el texto a analizar va a ser en español siempre.*/
    Analyzer analizador = new SpanishAnalyzer();
    
    Directory dirGuardarIndice;
    Path dirBaseDatos;
    Path dirIndice;
    
    public Indice(String dirBaseDatos, String dirIndice) throws IOException {

        this.dirBaseDatos = Paths.get(dirBaseDatos);
        this.dirIndice = Paths.get(dirIndice);
        //directory = new RAMDirectory();
        dirGuardarIndice = FSDirectory.open(this.dirIndice);
        /*Mantiene toda la configuración que se utiliza para crear una IndexWriter 
        Una vez IndexWriter ha sido creado con este objeto
        los cambios en este objeto no afectarán a la IndexWriter instancia*/

    }
    
    public void createIndex() {
        try {
            IndexWriterConfig config = new IndexWriterConfig(analizador);
            
            /*Es el corazón del proceso de indexación. Encapsula el índice y contiene 
             entre otras cosas cierta información de documentos que lo componen.*/
            IndexWriter iwriter = new IndexWriter(dirGuardarIndice, config);
            
            /*Es una clase (Document) que almacena List de campos (Field) con la meta información que queremos 
             indexar y/o guardar en el índice (emails, rutas, autor, fecha, etc.)*/
            for (Document doc : getDocuments(dirBaseDatos.toString())) {//lista de campos
                iwriter.addDocument(doc);//indexar campos
            }

            iwriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Indice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private List<Documentos> getDocuments(String dirBaseDatos) throws JDOMException, IOException {
        List<Documentos> lista = new ArrayList();
        // Exraemos todos los archivos contenidos en un directorio
        Tokenizador tokenizer = new Tokenizador(dirBaseDatos);//aqui tengo mi list de ficheros
        List<DocumentoCompleto> listaCompleta = tokenizer.getColeccionCompleta();//devuelve lista
        // Para cada archivo
        for (DocumentoCompleto iniciativa : listaCompleta) {
            // Creamos un documento
            Document doc = new Document();
            if(!iniciativa.isEmpty()) {
                // Añadimos los campos, sólo si están disponibles en la canción
                if(cancion.getArtistName() != null) {
                    doc.add(new TextField(ARTIST_NAME, cancion.getArtistName(), Field.Store.YES));
                }
                if(cancion.getSongName() != null) {
                    doc.add(new TextField(SONG_NAME, cancion.getSongName(), Field.Store.YES));
                }
                if(cancion.getAlbumName() != null) {
                    doc.add(new TextField(ALBUM_NAME, cancion.getAlbumName(), Field.Store.YES));
                }
                if(cancion.getYear() != null) {
                    doc.add(new IntField(YEAR, cancion.getYear(), Field.Store.YES));
                }
                if(cancion.getLyrics() != null) {
                    doc.add(new TextField(LYRICS, cancion.getLyrics(), Field.Store.YES));

                }

                // Añadimos el documento en la lista
                lista.add(doc);
                System.out.println(cancion);
            }
            
        }

        return lista;
    }
}


