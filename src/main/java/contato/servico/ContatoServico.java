/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contato.servico;

import contato.Contato;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author Anderson
 */
@Stateless
public class ContatoServico {

    Session secao;

    public void salvarContato() {
        // Salvar contato no banco de dados...
    }

    public boolean enviarEmail(Contato contato) {
        final String usuario = "tecnologiaswebsatc@outlook.com";
        final String senha = "TecnologiasWeb";

        Properties propriedades = new Properties();
        propriedades.put("mail.transport.protocol", "smtp");
        propriedades.put("mail.smtp.host", "smtp.live.com");
        propriedades.put("mail.smtp.socketFactory.port", "587");
        propriedades.put("mail.smtp.socketFactory.fallback", "false");
        propriedades.put("mail.smtp.starttls.enable", "true");
        propriedades.put("mail.smtp.auth", "true");
        propriedades.put("mail.smtp.port", "587");

        Authenticator autenticacao = new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, senha);
            }
        };
        if (secao == null) {
            secao = Session.getDefaultInstance(propriedades, autenticacao);
            secao.setDebug(true);
        }

        try {

            Address[] enderecos = InternetAddress.parse(contato.getPara());
            Message mensagem = new MimeMessage(secao);
            mensagem.setFrom(new InternetAddress(usuario));
            mensagem.setSubject("Contato API -  Você recebeu uma mensagem de " + contato.getNome());

            StringBuilder texto = new StringBuilder()
                    .append("<strong>Nome</strong>: ").append(contato.getNome()).append("<br>")
                    .append("<strong>E-mail</strong>: ").append(contato.getEmail()).append("<br>")
                    .append("<strong>Telefone</strong>: ").append(contato.getTelefone()).append("<br>")
                    .append("<strong>Mensagem</strong>: ").append(contato.getMensagem());

            mensagem.setContent(texto.toString(), "text/html; charset=utf-8");
            mensagem.setRecipient(Message.RecipientType.TO, enderecos[0]);

            Transport.send(mensagem);
            return true;
        } catch (Exception e) {
            try {
                secao.getTransport().close();
            } catch (NoSuchProviderException ex) {
                Logger.getLogger(ContatoServico.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                Logger.getLogger(ContatoServico.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
    }
}
