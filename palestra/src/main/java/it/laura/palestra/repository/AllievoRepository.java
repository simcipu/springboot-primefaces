package it.laura.palestra.repository;

import it.laura.palestra.model.Allievo;
import org.springframework.data.jpa.repository.Query;


import java.util.List;


public interface AllievoRepository  extends Repository<Allievo, String>{

    @Query("SELECT u FROM Allievo u WHERE u.codiceFiscale =?1 or u.nome=?2 or u.cognome=?3 or u.pagato=?4")
    List<Allievo> findByProp(String codiceFiscale,String nome,String cognome,String pagato);


}
