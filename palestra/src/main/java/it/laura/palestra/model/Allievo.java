package it.laura.palestra.model;

import javax.persistence.*;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name = "ALLIEVO")
public class Allievo implements Serializable {


    @Id
    @Column(name = "codice_fiscale")
    private String codiceFiscale;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cognome")
    private String cognome;

    @Column(name = "comune_residenza")
    private String comuneResidenza;

    @Lob
    @Column(name="certificato")
    private byte[] certificato;

    @Lob
    @Column(name="auto_certificazione")
    private byte[] autocertificazione;

    @Lob
    @Column(name="note")
    private byte[] note;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "mail")
    private String mail;

    @Column(name = "indirizzo")
    private String indirizzo;

    @Column(name = "pagato")
    private String pagato;

    @Column(name = "abbonamento")
    private String abbonamento;

    @Column(name = "attivo")
    private String attivo;

    @Column(name = "nome_certificato")
    private String nomeCertificato;

    @Column(name = "auto_certificato")
    private String autoCertificato;

    @Column(name = "corso")
    private String corso;

    @Column(name = "comune_nascita")
    private String comune;

    @Column(name = "data")
    private LocalDateTime data=LocalDateTime.now();

    @Column(name = "data_nascita")
    private Date dataNascita;

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getNomeCertificato() {
        return nomeCertificato;
    }

    public void setNomeCertificato(String nomeCertificato) {
        this.nomeCertificato = nomeCertificato;
    }

    public String getAutoCertificato() {
        return autoCertificato;
    }

    public void setAutoCertificato(String autoCertificato) {
        this.autoCertificato = autoCertificato;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
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

    public byte[] getCertificato() {
        return certificato;
    }

    public void setCertificato(byte[] certificato) {
        this.certificato = certificato;
    }

    public byte[] getAutocertificazione() {
        return autocertificazione;
    }

    public void setAutocertificazione(byte[] autocertificazione) {
        this.autocertificazione = autocertificazione;
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

    public String getPagato() {
        return pagato;
    }

    public void setPagato(String pagato) {
        this.pagato = pagato;
    }

    public String getAbbonamento() {
        return abbonamento;
    }

    public void setAbbonamento(String abbonamento) {
        this.abbonamento = abbonamento;
    }

    public String getAttivo() {
        return attivo;
    }

    public void setAttivo(String attivo) {
        this.attivo = attivo;
    }

    public byte[] getNote() {
        return note;
    }

    public void setNote(byte[] note) {
        this.note = note;
    }

    public String getCert(){

        if(getCertificato()!=null){

            return getNomeCertificato();
        }

        return "";
    }

    public String getAuto(){

        if(getAutocertificazione()!=null){

            return getAutoCertificato();
        }

        return "";
    }

    public String getNot() throws UnsupportedEncodingException {

        return new String(note, "UTF-8");
    }

    public String getDataLo(){

            return getData().toString();
    }

    public String getCorso() {
        return corso;
    }

    public void setCorso(String corso) {
        this.corso = corso;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
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
}
