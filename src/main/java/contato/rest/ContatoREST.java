/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contato.rest;

import contato.Contato;
import contato.servico.ContatoServico;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Anderson
 */
@Stateless
@Path("email")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
public class ContatoREST  {
    
    @Inject 
    ContatoServico contatoServico;
    
    public ContatoREST() {
    }

    @GET
    public boolean check() {
        return true;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean enviarEmail(Contato contato) {
        return contatoServico.enviarEmail(contato);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public boolean enviarEmail(@FormParam("nome") String nome, 
        @FormParam("telefone") String telefone,
        @FormParam("email") String email,
        @FormParam("mensagem") String mensagem,
        @FormParam("para") String para
    ) {
        Contato contato = new Contato();
        contato.setNome(nome);
        contato.setEmail(email);
        contato.setTelefone(telefone);
        contato.setMensagem(mensagem);
        contato.setPara(para);
        
        return (contatoServico.enviarEmail(contato));
    }
    
}
