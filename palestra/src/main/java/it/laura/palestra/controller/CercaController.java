package it.laura.palestra.controller;

import it.laura.palestra.business.AllievoService;
import it.laura.palestra.model.Allievo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Named("cercaController")
@RequestScoped
public class CercaController {

    private Allievo allievo;

    private String codiceFiscale = "";

    private String nome = "";

    private String cognome = "";

    private String pagato = "";

    private Boolean ok = false;

    @Autowired
    private AllievoService allievoService;

    private Boolean act;

    private List<Allievo> lista;


    @PostConstruct
    public void init(){
        refresh();

    }

    public void trovaTutto() {

        ok = false;
        lista = allievoService.getAllievi();
        if (!lista.isEmpty()) {
            ok = true;
        }
    }

    public Allievo getAllievo() {
        return allievo;
    }

    public void setAllievo(Allievo allievo) {
        this.allievo = allievo;
    }

    public void ritorno() {

        ExternalContext ec = FacesContext.getCurrentInstance()
                .getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "menuButton.xhtml");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void refresh() {
        allievo = new Allievo();
        lista = new ArrayList<>();
        codiceFiscale = "";
        nome = "";
        cognome = "";
        pagato = "";
        ok = false;
    }


    public void cercaPerCodice() {

        ok = false;
        List<Allievo> op = allievoService.getAllievi(codiceFiscale.toLowerCase(), nome.toLowerCase(),
                cognome.toLowerCase(), pagato);
        lista = op;
        if (!lista.isEmpty()) {
            ok = true;
        }


    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public Boolean getAct() {
        return act;
    }

    public void setAct(Boolean act) {
        this.act = act;
    }

    public List<Allievo> getLista() {
        return lista;
    }

    public void setLista(List<Allievo> lista) {
        this.lista = lista;
    }

    public String getPagato() {
        return pagato;
    }

    public void setPagato(String pagato) {
        this.pagato = pagato;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public AllievoService getAllievoService() {
        return allievoService;
    }

    public void setAllievoService(AllievoService allievoService) {
        this.allievoService = allievoService;
    }
}
