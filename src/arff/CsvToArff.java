package arff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class CsvToArff {
    public String DATAPATH = "./elements_projet/";

    public CsvToArff() {
    };

    public String header() {
        return "@RELATION donnees\n@ATTRIBUTE text STRING\n@ATTRIBUTE eval {-1,1}\n@data";
    }

    public String echappementQuotes( String ligne ) {
        return ligne.replace( "\"", "\\\"" );// .replace( "'", "\\'" );

    }

    public String lecture() {
        String data = this.DATAPATH + "dataset.csv";
        String label = this.DATAPATH + "labels.csv";
        String resultat = this.header();
        try {
            // Ouverture de data
            InputStream ipsData = new FileInputStream( data );
            InputStreamReader ipsrData = new InputStreamReader( ipsData );
            BufferedReader brData = new BufferedReader( ipsrData );

            // Ouverture de label
            InputStream ipsLabel = new FileInputStream( label );
            InputStreamReader ipsrLabel = new InputStreamReader( ipsLabel );
            BufferedReader brLabel = new BufferedReader( ipsrLabel );

            String ligneData;
            String ligneLabel;

            while ( ( ligneData = brData.readLine() ) != null && ( ligneLabel = brLabel.readLine() ) != null ) {
                resultat += "\n\"" + echappementQuotes( ligneData ) + "\"," + ligneLabel;

            }
            brData.close();
            brLabel.close();
        } catch ( Exception e ) {
            System.out.println( e.toString() );
        }
        return resultat;
    }

    public void ecriture( String contenu, String nomFic ) {
        try {
            FileWriter fw = new FileWriter( nomFic );
            BufferedWriter bw = new BufferedWriter( fw );
            PrintWriter fichierSortie = new PrintWriter( bw );
            fichierSortie.print( contenu );
            fichierSortie.close();
        } catch ( Exception e ) {
            System.out.println( e.toString() );
        }
    }

    public static void main( String[] args ) {

        CsvToArff a = new CsvToArff();
        String contenuArff = a.lecture();
        a.ecriture( contenuArff, "result.arff" );
        System.out.println( "ok" );

    }

}
