package it.laura.palestra.controller;


import it.laura.palestra.business.AllievoService;
import it.laura.palestra.model.Allievo;
import it.laura.palestra.model.ByteArrayUploadedFile;
import it.laura.palestra.repository.AllievoRepository;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.*;
import java.util.Date;

@Named("updateController")
@RequestScoped
public class UpdateController {

    private Allievo allievo;

    private String ceecaCodiceFiscale;

    private UploadedFile uploadedFile;

    private UploadedFile uploadedFile1;

    private String note;

    private String nome;

    private String nomeCert;

    private String nomeAuto;

    private String cognome;

    private String codiceFiscale;

    private String telefono;

    private String mail;

    private String indirizzo;

    private String abbonamento;

    private String pagato;

    private String attivo;

    private String corso;

    private String comuneNascita;

    private String comuneResidenza;

    private Date dataNascita;

    private boolean close=false;

    @Autowired
    private AllievoService allievoService;

    @Autowired
    private AllievoRepository allievoRepository;

    @PostConstruct
    public void init(){
        refresh();
    }


    public void carica() throws UnsupportedEncodingException {
        allievo=allievoService.cercaPerCodice(ceecaCodiceFiscale);
        if(allievo==null){
            addMessage("Errore", "allievo inesistente");
        }

        if(allievo!=null) {
            nome = allievo.getNome();
            cognome=allievo.getCognome();
            mail=allievo.getMail();
            abbonamento=allievo.getAbbonamento();
            pagato=allievo.getPagato();
            indirizzo=allievo.getIndirizzo();
            attivo=allievo.getAttivo();
            note=new String(allievo.getNote(), "UTF-8");
            telefono=allievo.getTelefono();
            codiceFiscale=allievo.getCodiceFiscale();
            nomeCert=allievo.getCert();
            nomeAuto=allievo.getAutoCertificato();
            corso=allievo.getCorso();
            comuneNascita=allievo.getComune();
            comuneResidenza=allievo.getComuneResidenza();
            dataNascita=allievo.getDataNascita();


            if(allievo.getCertificato()!=null)
            uploadedFile = new ByteArrayUploadedFile(allievo.getCertificato(), allievo.getCert(), "application/pdf");

            if(allievo.getAutocertificazione()!=null)
            uploadedFile1 = new ByteArrayUploadedFile(allievo.getAutocertificazione(), allievo.getAutoCertificato(), "application/pdf");
        close=true;
        }
    }


    public void refresh() {
        allievo=new Allievo();
        uploadedFile=null;
        uploadedFile1=null;
        note="";
        ceecaCodiceFiscale="";
        note="";
        nome="";
        cognome="";
        codiceFiscale="";
        telefono="";
        mail="";
        indirizzo="";
        abbonamento="";
        pagato="";
        attivo="";
        nomeCert="";
        nomeAuto="";
        corso="";
        comuneNascita="";
        comuneResidenza="";
        dataNascita=null;
        close=false;
    }

    public Allievo getAllievo() {
        return allievo;
    }

    public void scaricaCertificato() throws IOException {

        if(uploadedFile!=null&&!uploadedFile.getFileName().isEmpty()) {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();

            byte[] exportContent = uploadedFile.getContents();

            ec.responseReset();
            ec.setResponseContentType("application/pdf");
            ec.setResponseContentLength(exportContent.length);
            String attachmentName = "attachment; filename=\"" + uploadedFile.getFileName()+ "\"";
            ec.setResponseHeader("Content-Disposition", attachmentName);
            ec.getResponseOutputStream().write(exportContent);
            fc.responseComplete();
        }else{
            addMessage("Errore", "certificato non presente");
        }
    }

    public void scaricaAutoCertificato() throws IOException {

        if(uploadedFile1!=null&&!uploadedFile1.getFileName().isEmpty()) {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();

            byte[] exportContent = uploadedFile1.getContents();

            ec.responseReset();
            ec.setResponseContentType("application/pdf");
            ec.setResponseContentLength(exportContent.length);
            String attachmentName = "attachment; filename=\"" + uploadedFile1.getFileName() + "\"";
            ec.setResponseHeader("Content-Disposition", attachmentName);
            ec.getResponseOutputStream().write(exportContent);
            fc.responseComplete();
        }else{
            addMessage("Errore", "certificato non presente");
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        uploadedFile= event.getFile();
        nomeCert=event.getFile().getFileName();
        allievo.setCertificato(uploadedFile.getContents());
        allievo.setNomeCertificato(uploadedFile.getFileName());
        FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void handleFileUpload1(FileUploadEvent event) {
        uploadedFile1= event.getFile();
        nomeAuto=event.getFile().getFileName();
        allievo.setAutocertificazione(uploadedFile1.getContents());
        allievo.setAutoCertificato(uploadedFile1.getFileName());
        FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void save() {

        if(!allievoService.cercaCod(ceecaCodiceFiscale)){
            addMessage("Errore", "devi prima salvare");
        }else{
            allievo=allievoService.cercaPerCodice(ceecaCodiceFiscale);
        allievo.setAutoCertificato(nomeAuto);
        allievo.setNome(nome.toLowerCase());
        allievo.setAbbonamento(abbonamento);
        allievo.setAttivo(attivo);
        allievo.setCognome(cognome.toLowerCase());
        allievo.setIndirizzo(indirizzo);
        allievo.setMail(mail);
        allievo.setPagato(pagato);
        allievo.setNomeCertificato(nomeCert);

        if(uploadedFile!=null){
            allievo.setCertificato(uploadedFile.getContents());
            allievo.setNomeCertificato(uploadedFile.getFileName());
        }
        if(uploadedFile1!=null){
            allievo.setAutocertificazione(uploadedFile1.getContents());
            allievo.setAutoCertificato(uploadedFile1.getFileName());
        }
        if(note!=null){
            allievo.setNote(note.getBytes());
        }

        allievoService.save(allievo);
        allievo=new Allievo();
        uploadedFile=null;
        uploadedFile1=null;
        note="";
        addMessage("Success", "Data update");
        }
    }

    public String getCorso() {
        return corso;
    }

    public void setCorso(String corso) {
        this.corso = corso;
    }

    public void eliminaCertificato(){

        if(uploadedFile!=null) {
            uploadedFile = null;
            nomeCert = "";
            allievo.setCertificato(null);
            allievo.setNomeCertificato("");
            addMessage("Success", "eliminato");
        }else{
            addMessage("Errore", "Certificato non presente");

        }

    }

    public void eliminaAutoCertificato(){
        if(uploadedFile1!=null) {
            uploadedFile1 = null;
            nomeAuto = "";
            allievo.setAutocertificazione(null);
            allievo.setAutoCertificato("");
            addMessage("Success", "eliminato");
        }else{
            addMessage("Errore", "Certificato non presente");

        }

    }

    public void eliminaAllievo(){
          if(allievoService.cercaCod(ceecaCodiceFiscale)){
            allievoService.elimina(ceecaCodiceFiscale);
              addMessage("Success", "allievo eliminato");

          }


    }


    public void setAllievo(Allievo allievo) {
        this.allievo = allievo;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public UploadedFile getUploadedFile1() {
        return uploadedFile1;
    }

    public void setUploadedFile1(UploadedFile uploadedFile1) {
        this.uploadedFile1 = uploadedFile1;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getAbbonamento() {
        return abbonamento;
    }

    public void setAbbonamento(String abbonamento) {
        this.abbonamento = abbonamento;
    }

    public String getPagato() {
        return pagato;
    }

    public void setPagato(String pagato) {
        this.pagato = pagato;
    }

    public String getAttivo() {
        return attivo;
    }

    public void setAttivo(String attivo) {
        this.attivo = attivo;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getCeecaCodiceFiscale() {
        return ceecaCodiceFiscale;
    }

    public void setCeecaCodiceFiscale(String ceecaCodiceFiscale) {
        this.ceecaCodiceFiscale = ceecaCodiceFiscale;
    }

    public String getNomeCert() {
        return nomeCert;
    }

    public void setNomeCert(String nomeCert) {
        this.nomeCert = nomeCert;
    }

    public String getNomeAuto() {
        return nomeAuto;
    }

    public void setNomeAuto(String nomeAuto) {
        this.nomeAuto = nomeAuto;
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void ritorno()    {

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

    public String getComuneNascita() {
        return comuneNascita;
    }

    public void setComuneNascita(String comuneNascita) {
        this.comuneNascita = comuneNascita;
    }

    public String getComuneResidenza() {
        return comuneResidenza;
    }

    public void setComuneResidenza(String comuneResidenza) {
        this.comuneResidenza = comuneResidenza;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }
}
