package com.ifsp.teste.models;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

/**
 *  @author Nicolas Lutero
 */

// Classe de acesso ao Banco de Dados.
public class BD {
    /*    Classe responsavel por acessa o Banco de
     *  Dados improvisado com Gmail.
     *  
     *    Esta Classe implementa o Modelo Singleton
     *  e tem como uma de suas dependencias o arquivo
     *  javax.mail.jar.
     */ 
    
    // Implementa a instancia (Padrão Singleton).
    private static BD instance;
    // Define o Gmail e a Senha De APP.
    private final String username = "trabalholpr3@gmail.com";
    private final String password = "nqqn gtcl eqde xerg";

    // Construtor privado (Padrão Singleton)
    private BD() {}
    
    // getInstance (Padrão Singleton)
    public static BD getInstance(){
        // Cria a instancia se ela for nula.
        if (instance == null){
            instance = new BD();
        }
        // Retorna a instancia.
        return instance;
    }

    // Função para enviar um arquivo via e-mail
    public void enviar(String nome, File arquivo) throws IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message mensagem = new MimeMessage(session);
            mensagem.setFrom(new InternetAddress(username));
            mensagem.setRecipients(Message.RecipientType.TO, InternetAddress.parse(username));
            mensagem.setSubject(nome);

            Multipart multipart = new MimeMultipart();

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Arquivo em anexo.");
            multipart.addBodyPart(messageBodyPart);

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(arquivo);
            multipart.addBodyPart(attachmentPart);

            mensagem.setContent(multipart);
            Transport.send(mensagem);
            
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void resgatar(String nome) throws MessagingException, IOException {
        // Configurações para acessar o e-mail via IMAP
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.host", "imap.gmail.com");
        props.put("mail.imaps.port", "993");

        Session session = Session.getInstance(props);
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", username, password);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        Message[] mensagens = inbox.getMessages();
        for (int i = mensagens.length - 1; i >= 0; i--) {
            Message mensagem = mensagens[i];
            if (mensagem.getSubject().contains(nome)) {
                if (mensagem.getContent() instanceof Multipart) {
                    Multipart multipart = (Multipart) mensagem.getContent();

                    for (int j = 0; j < multipart.getCount(); j++) {
                        BodyPart part = multipart.getBodyPart(j);

                        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition()) && part instanceof MimeBodyPart) {
                            MimeBodyPart attachment = (MimeBodyPart) part;

                            // Define o caminho de saída
                            String basePath = "usuario";
                            Files.createDirectories(Paths.get(basePath));
                            File outputFile = new File(basePath, part.getFileName());

                            // Salva o arquivo
                            try (InputStream is = attachment.getInputStream();
                                 FileOutputStream fos = new FileOutputStream(outputFile)) {
                                byte[] buffer = new byte[4096];
                                int bytesRead;
                                while ((bytesRead = is.read(buffer)) != -1) {
                                    fos.write(buffer, 0, bytesRead);
                                }
                            }
                            break; // Para após encontrar o primeiro anexo correspondente.
                        }
                    }
                }
            }
        }
        inbox.close(false);
        store.close();
    }
}