package it.laura.palestra.business;

import it.laura.palestra.model.Allievo;
import it.laura.palestra.repository.AllievoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class AllievoService {

    @Autowired
    private AllievoRepository allievoRepository;


    public List<Allievo> getAllievi(){

        return allievoRepository.findAll();
    }

    public void save(Allievo allievo){

        allievoRepository.save(allievo);
    }

    public List<Allievo> getAllievi(String codice,String nome,String cognome,String pagato){

        return allievoRepository.findByProp(codice,nome,cognome,pagato);
    }


    public List<Allievo> getAllieviPag(){

        return allievoRepository.findByPagato();
    }

    public Allievo cercaPerCodice(String codice){

        if(allievoRepository.existsById(codice)) {
            Optional<Allievo> al = allievoRepository.findById(codice);
            return al.get();
        }
        return null;
    }

    public Boolean cercaCod(String code){

        return allievoRepository.existsById(code);
    }

    public Boolean elimina(String code){

        boolean re=false;
        allievoRepository.deleteById(code);
        if(!allievoRepository.existsById(code)){
            re=true;
        }

        return re;
    }
}
