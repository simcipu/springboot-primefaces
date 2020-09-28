package it.laura.palestra.controller;

import it.laura.palestra.business.AllievoService;
import it.laura.palestra.model.Allievo;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

@Named
@RequestScoped
public class AllievoController {


    private Allievo allievo =new Allievo();

    private UploadedFile uploadedFile;

    @Autowired
    private AllievoService allievoService;

    private String nomeCert="";

    private String nomeAuto="";

    private UploadedFile uploadedFile1;

    private String note;

    private String nomi;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public UploadedFile getUploadedFile1() {
        return uploadedFile1;
    }

    public void setUploadedFile1(UploadedFile uploadedFile1) {
        this.uploadedFile1 = uploadedFile1;
    }

    public void refresh() {
       allievo=new Allievo();
       uploadedFile=null;
       uploadedFile1=null;
       note=null;
       nomi="";
    }

    public void scaricaAutoCertificato() throws IOException {

        if(uploadedFile1!=null) {
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

    public String getNomeAuto() {
        return nomeAuto;
    }

    public void setNomeAuto(String nomeAuto) {
        this.nomeAuto = nomeAuto;
    }

    public void save() {

        String con=allievo.getCognome().toLowerCase();
        allievo.setCognome(con);
        String nom=allievo.getNome().toLowerCase();
        allievo.setNome(nom);
        String cod=allievo.getCodiceFiscale().toLowerCase();
        allievo.setCodiceFiscale(cod);

        if(allievo.getCognome().isEmpty()||allievo.getCognome()==null){
            addMessage("Errore", "inserire cognome");
            return;
        }

        if(allievo.getNome().isEmpty()||allievo.getNome()==null){
            addMessage("Errore", "inserire nome");
            return;
        }

        if(allievo.getCodiceFiscale().isEmpty()||allievo.getCodiceFiscale()==null){
            addMessage("Errore", "inserire codiceFiscale");
            return;
        }

        if(allievoService.cercaCod(allievo.getCodiceFiscale())){
            addMessage("Errore", "codice Fiscale esistente");

        }else {
      if (uploadedFile != null) {
                allievo.setCertificato(uploadedFile.getContents());
                allievo.setNomeCertificato(uploadedFile.getFileName());
            }
            if (uploadedFile1 != null) {
                allievo.setAutocertificazione(uploadedFile1.getContents());
                allievo.setAutoCertificato(uploadedFile1.getFileName());
            }
            if (note != null) {
                allievo.setNote(note.getBytes());
            }
            LocalDateTime data = LocalDateTime.now();
            allievo.setData(LocalDateTime.of(data.getYear(), data.getMonth(), data.getDayOfMonth(), 0, 0, 0, 0));
            allievoService.save(allievo);
            allievo = new Allievo();
            uploadedFile = null;
            uploadedFile1 = null;
            note = "";
            addMessage("Success", "Data saved");
        }
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

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


    public void handleFileUpload(FileUploadEvent event) {

        uploadedFile= event.getFile();
        nomeCert=uploadedFile.getFileName();

        FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void handleFileUpload1(FileUploadEvent event) {
        uploadedFile1= event.getFile();
        nomeAuto=uploadedFile1.getFileName();

        FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
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

    public void mostra(){

        if(uploadedFile!=null&&uploadedFile1!=null) {
            nomeCert = uploadedFile.getFileName();
            nomeAuto = uploadedFile1.getFileName();

            nomi=nomeCert+";"+nomeAuto;
        }

    }

    public void scaricaCertificato() throws IOException {

        if(uploadedFile!=null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();

            byte[] exportContent = uploadedFile.getContents();


            ec.responseReset();
            ec.setResponseContentType("application/pdf");
            ec.setResponseContentLength(exportContent.length);
            String attachmentName = "attachment; filename=\"" + uploadedFile.getFileName() + "\"";
            ec.setResponseHeader("Content-Disposition", attachmentName);
            ec.getResponseOutputStream().write(exportContent);
            fc.responseComplete();
        }else{
            addMessage("Errore", "certificato non presente");
        }

    }
    public void eliminaAutoCertificato(){
        if(uploadedFile1!=null) {
            uploadedFile1 = null;
            nomeAuto = "";
            allievo.setAutocertificazione(null);
            allievo.setAutoCertificato("");
        }else{
            addMessage("Errore", "Certificato non presente");

        }

    }



    public Allievo getAllievo() {
        return allievo;
    }

    public void setAllievo(Allievo allievo) {
        this.allievo = allievo;
    }

    public String getNomeCert() {
        return nomeCert;
    }

    public String getNomi() {
        return nomi;
    }

    public void setNomi(String nomi) {
        this.nomi = nomi;
    }

    public void setNomeCert(String nomeCert) {
        this.nomeCert = nomeCert;
    }
}
