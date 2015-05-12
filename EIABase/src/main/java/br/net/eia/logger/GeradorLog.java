package br.net.eia.logger;

import java.io.FileOutputStream;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;

public class GeradorLog {

    private static String nomeArquivo = "logs/log";
    private static Level level = null;
    private static Logger logger = null;

    private GeradorLog() {
    }

    public static void inicialize() {
        if (logger == null) {
            if (level == null) {
                level = Level.ERROR;
            }
            logger = Logger.getRootLogger();
            logger.setLevel(level);
            
            String padrao_log = "----------------------------------------------------------%n";
            padrao_log += "Milisegundos desde o ínicio do programa: %r %n";
            padrao_log += "Classe: %c %n";
            padrao_log += "Data: %d{dd/MM/yyyy} %n";
            padrao_log += "Hora: %d{HH:mm:ss} %n";
            padrao_log += "Local: %l %n";
            padrao_log += "Mensagem: %m %n";
            
            // criando um PatternLayout passando o output pattern criado
            PatternLayout layout = new PatternLayout(padrao_log);
            
            FileAppender arquivo = null;
            
            HTMLLayout htmlLayout = new HTMLLayout();
            htmlLayout.setTitle("Logs do Sistema");
            htmlLayout.setLocationInfo(true);
            
            // cria o appender do tipo WriterAppender
            WriterAppender appender = null;
            try {
                
                arquivo = new FileAppender(layout,nomeArquivo+".log",true);
                arquivo.setBufferSize(10);
                // cria o arquivo HTML
                FileOutputStream output = new FileOutputStream(nomeArquivo+".html");
                // cria um WriterAppender passando o layout e o file
                appender = new WriterAppender(htmlLayout, output);
            } catch (Exception e) {
                // logando uma exception, caso ocorra
                logger.error("Erro: " + e.getMessage());
            }
            
            ConsoleAppender console = new ConsoleAppender(layout);
            
            // adicionando o appender criado ao logger
            logger.addAppender(appender);
            logger.addAppender(arquivo);
            logger.addAppender(console);
            
        }
    }
    public static void erro(String erro){
        logger.error(erro);
    }
    
    public static void erro(Class<?> c, String erro){
        logger = Logger.getLogger(c);
        logger.error(erro);
    }
}
