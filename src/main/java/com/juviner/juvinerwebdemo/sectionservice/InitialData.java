package com.juviner.juvinerwebdemo.sectionservice;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitialData {
    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private CategoryDao categoryDao;
    
    @PostConstruct
    public void runAfterObjectCreated() {
        if(this.sectionDao.count() == 0) {
            Section section1 = new Section("Technology");
            Section section2 = new Section("Religion");
            Section section3 = new Section("Programming");
            this.sectionDao.save(section1);
            this.sectionDao.save(section2);
            this.sectionDao.save(section3);
            this.categoryDao.save(new Category(section1, "Category 1", "Bacon ipsum dolor amet consequat pariatur meatloaf drumstick, duis bresaola anim exercitation boudin est. Fatback sirloin lorem short loin commodo. Et reprehenderit ut short ribs shoulder pork loin. Pig swine ball tip magna minim id shankle chislic eiusmod tenderloin drumstick strip steak. Pig magna dolor enim, shoulder pork belly commodo veniam exercitation in chuck."));
            this.categoryDao.save(new Category(section1, "Category 2", "Leberkas nostrud chislic swine, proident sausage porchetta ullamco anim aute ut pariatur fugiat ipsum sunt. Do leberkas esse cupidatat rump hamburger pork loin fugiat commodo porchetta dolore frankfurter incididunt reprehenderit aliqua. Non beef pariatur pastrami sed ex. Pork cillum kielbasa short ribs frankfurter turducken capicola in sed officia exercitation mollit boudin drumstick. Aliquip pork loin cupim, alcatra andouille chislic consequat jerky id nisi dolor anim spare ribs bacon short loin. Enim meatball flank, eiusmod pork loin in deserunt."));
            this.categoryDao.save(new Category(section1, "Category 3", "Pork velit jowl fugiat, aliquip frankfurter burgdoggen adipisicing. Lorem spare ribs consequat prosciutto aute swine pancetta anim t-bone velit beef ribs chicken short ribs fatback. Andouille exercitation sed in est shoulder, tempor do spare ribs meatball proident burgdoggen brisket cupidatat esse. Officia ullamco picanha, ipsum flank pork loin pancetta nulla ham hock labore drumstick consequat ad andouille cillum. Elit aute ut est drumstick ribeye corned beef spare ribs capicola adipisicing quis. Ex do non biltong, tri-tip landjaeger pastrami tongue quis. Cupidatat jerky brisket, dolore ut excepteur shank cow."));
            this.categoryDao.save(new Category(section2, "Category 4", "Dolor cupidatat est fatback. Burgdoggen aliqua est fatback cupim cupidatat. Ex venison mollit burgdoggen hamburger. Sint velit ground round kielbasa, laborum aliquip short ribs voluptate doner ribeye labore pariatur minim pancetta."));
            this.categoryDao.save(new Category(section2, "Category 5", "Kevin turducken turkey pork incididunt, proident tri-tip pariatur shoulder bacon reprehenderit. Meatloaf ut ground round, ribeye anim laborum landjaeger quis short loin shank do in. Fatback kevin id drumstick, picanha irure velit ham. Dolor ipsum et anim corned beef shoulder ball tip deserunt. Ground round turkey nostrud beef ribs rump tempor, aute salami dolore capicola."));
            this.categoryDao.save(new Category(section2, "Category 6", "In exercitation frankfurter, biltong ea doner nostrud. Corned beef consequat pork pork loin anim et ball tip dolore. Pariatur buffalo lorem prosciutto minim do dolor shoulder bacon chicken dolore picanha ut. Cupim alcatra beef nostrud buffalo turducken, nisi ut occaecat. Ullamco brisket non pariatur, doner commodo ut magna quis."));
            this.categoryDao.save(new Category(section3, "Category 7", "Sint veniam strip steak magna beef ribs cupim ball tip est. Ea chicken fugiat shankle sunt, short loin strip steak tempor shank id pariatur ground round. Do meatball rump, ground round hamburger proident id lorem ut consequat. Officia ut sed mollit, jerky quis jowl ground round tenderloin eu commodo."));
            this.categoryDao.save(new Category(section3, "Category 8", "Sint sausage short loin, nisi chuck duis sed flank nostrud pork belly magna minim irure. Ex ground round rump chicken. Bresaola leberkas deserunt labore laboris, in pig velit in rump. Kevin buffalo turducken, boudin pork chop prosciutto ribeye. Fatback eu capicola cow meatball, consectetur meatloaf aute bresaola pariatur."));
            this.categoryDao.save(new Category(section3, "Category 9", "Fugiat nostrud aliquip ex tail swine, labore esse. Flank consequat sunt, kielbasa venison laborum picanha aliquip et. Ullamco velit commodo shankle ea mollit pork loin. Cupim pariatur in, voluptate anim exercitation pork chop velit filet mignon pig tri-tip rump ipsum t-bone. Exercitation eiusmod cupidatat sed dolor, consequat voluptate magna alcatra ut."));
        }
    }

}
