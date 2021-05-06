package com.juviner.juvinerweb.threadservice;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitialData {
    @Autowired
    private ThreadDao threadDao;
    
    @PostConstruct
    public void runAfterObjectCreated() throws ParseException {
        if(this.threadDao.count() == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM hh:mm:ss");
            this.threadDao.save(new Thread("GameStop", 1, "Valentino", "Alcuni utenti di Reddit hanno comprato in massa azioni di GameStop per farne alzare il prezzo, in modo da far fallire gli investimenti sulla loro perdita di valore.", new Timestamp(sdf.parse("2021-02-26 17:36:57").getTime())));
        }
    }

}
