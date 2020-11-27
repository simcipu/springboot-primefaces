package it.laura.palestra.task;

import it.laura.palestra.business.AllievoService;
import it.laura.palestra.model.Allievo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Component
public class SendMailTask {


    @Autowired
    private AllievoService allievoService;

    @Autowired
    private JavaMailSender emailSender;

    @Scheduled(fixedRate = 15000)
    void sendEmail() throws UnsupportedEncodingException {


       List<Allievo> li= allievoService.getAllieviPag();

       if(li!=null&&!li.isEmpty()) {
           SimpleMailMessage message = new SimpleMailMessage();
           message.setFrom("pagamenti");
           message.setTo("simonecipu@gmail.com");
           message.setSubject("ELENCO PERSONE CON DEI PAGAMENTI IN SOSPESO");
           String te="";
           for(Allievo al:li){
           te=te+getText(al);
           }
           message.setText(te);
           emailSender.send(message);
       }

    }


    private String getText(Allievo al) throws UnsupportedEncodingException {

        String text="\n\n"+"Codice Fiscale:  "+al.getCodiceFiscale()+"\n"+"Nome: "+al.getNome()+"\n"+"Cognome: "+ al.getCognome()+"\n\n"
                +"Note: "+al.getNot()+"\n\n\n"+"**********************************"+"\n\n";



        return text;
    }
}
