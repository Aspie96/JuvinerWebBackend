package com.juviner.juvinerwebbackend.postservice;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitialData {
    @Autowired
    private PostDao postDao;
    
    @PostConstruct
    public void runAfterObjectCreated() throws ParseException {
        if(this.postDao.count() == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM hh:mm:ss");
            this.postDao.save(new Post(1, "Valentino", "Alcuni utenti di Reddit hanno comprato in massa azioni di GameStop per farne alzare il prezzo, in modo da far fallire gli investimenti sulla loro perdita di valore.", new Timestamp(sdf.parse("2021-02-24 17:10:57").getTime())));
            this.postDao.save(new Post(1, "Andrea", "Come funziona la questione?", new Timestamp(sdf.parse("2021-02-24 17:33:31").getTime())));
        }
    }

}
