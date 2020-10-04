package de.getdate.backend;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import de.getdate.backend.dto.UpdateRequest;
import de.getdate.backend.infrastructure.InstitutionRepository;
import de.getdate.backend.infrastructure.SlotRepository;
import de.getdate.backend.infrastructure.UserRepository;
import de.getdate.backend.model.Address;
import de.getdate.backend.model.Institution;
import de.getdate.backend.model.Slot;
import de.getdate.backend.model.User;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class TerminMSBootstrap {
  @Inject
  InstitutionRepository institutionRepository;

  @Inject
  SlotRepository slotRepository;

  @Inject
  UserRepository userRepository;

  @Transactional
  void onStart(@Observes StartupEvent ev) {
    Address addressA = new Address();
    addressA.setCountry("Deutschland");
    addressA.setCity("Münster");
    addressA.setZipCode("48155");
    addressA.setStreet("Winkelgasse");
    addressA.setHouseNumber("42");
    addressA.setLatitude("");
    addressA.setLongtitude("");

    Address addressB = new Address();
    addressB.setCountry("Deutschland");
    addressB.setCity("Münster");
    addressB.setZipCode("48155");
    addressB.setStreet("Hafenstarße");
    addressB.setHouseNumber("16");
    addressB.setLatitude("");
    addressB.setLongtitude("");

    Address addressC = new Address();
    addressC.setCountry("Deutschland");
    addressC.setCity("Münster");
    addressC.setZipCode("48155");
    addressC.setStreet("Musterstraße");
    addressC.setHouseNumber("17");
    addressC.setLatitude("");
    addressC.setLongtitude("");

    Address addressD = new Address();
    addressD.setCountry("Deutschland");
    addressD.setCity("Münster");
    addressD.setZipCode("48155");
    addressD.setStreet("Beispielweg");
    addressD.setHouseNumber("7");
    addressD.setLatitude("");
    addressD.setLongtitude("");

    Address addressE = new Address();
    addressE.setCountry("Deutschland");
    addressE.setCity("Münster");
    addressE.setZipCode("48155");
    addressE.setStreet("Der Weg");
    addressE.setHouseNumber("1");
    addressE.setLatitude("");
    addressE.setLongtitude("");

    Address addressF = new Address();
    addressF.setCountry("Deutschland");
    addressF.setCity("Münster");
    addressF.setZipCode("48155");
    addressF.setStreet("Die Sraße");
    addressF.setHouseNumber("69");
    addressF.setLatitude("");
    addressF.setLongtitude("");

    Address addressG = new Address();
    addressG.setCountry("Deutschland");
    addressG.setCity("Münster");
    addressG.setZipCode("48155");
    addressG.setStreet("Der Pfad der Weißheit");
    addressG.setHouseNumber("420");
    addressG.setLatitude("");
    addressG.setLongtitude("");

    Address addressH = new Address();
    addressH.setCountry("Deutschland");
    addressH.setCity("Münster");
    addressH.setZipCode("48155");
    addressH.setStreet("Münsterstraße");
    addressH.setHouseNumber("111");
    addressH.setLatitude("");
    addressH.setLongtitude("");

    Address addressI = new Address();
    addressI.setCountry("Deutschland");
    addressI.setCity("Münster");
    addressI.setZipCode("48155");
    addressI.setStreet("Gleis");
    addressI.setHouseNumber("9 3/4");
    addressI.setLatitude("");
    addressI.setLongtitude("");

    Address addressJ = new Address();
    addressJ.setCountry("Deutschland");
    addressJ.setCity("Münster");
    addressJ.setZipCode("48155");
    addressJ.setStreet("Eine Adresse");
    addressJ.setHouseNumber("8");
    addressJ.setLatitude("");
    addressJ.setLongtitude("");

    Institution institutionA = new Institution();

    List<String> employees = Arrays.asList("Max Meiers");

    institutionA.setAddress(addressA);
    institutionA.setName("Bürgeramt Stadt Münster");
    institutionA.setDescription("Wir sind das Amt für die Bürger!");
    institutionA.setPhone("0251 123456789");
    institutionA.setEmployeeNames(new ArrayList<String>(employees));
    // institutionA.setSlots(new ArrayList<Slot>(slots));

    institutionRepository.save(institutionA);

    List<Slot> slots = new ArrayList<Slot>();
    LocalDate now = LocalDate.now();
    for (int i = 0; i < 12; i++) {
      long epochTime = now.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() + i*30*60 + 8*60*60;
      Slot slotA = new Slot();
      slotA.setDateTimestamp(epochTime);
      slotA.setDurationInMinutes(30);
      slotA.setEmployee("Max Meiers");
      slotA.setInstitution(institutionA);
      slots.add(slotA);

      slotRepository.save(slotA);
    }

    Institution institutionB = new Institution();
    institutionB.setAddress(addressB);
    institutionB.setName("DigitalHUB");
    institutionB.setDescription("Digital auch in Münster");
    institutionB.setPhone("0251 123456789");
    institutionA.setEmployeeNames(new ArrayList<String>());
    institutionA.setSlots(new ArrayList<Slot>());

    Institution institutionC = new Institution();
    institutionC.setAddress(addressC);
    institutionC.setName("Das Unternehmen");
    institutionC.setDescription("Wir sind ein Unternehmen");
    institutionC.setPhone("0251 123456789");
    institutionA.setEmployeeNames(new ArrayList<String>());
    institutionA.setSlots(new ArrayList<Slot>());

    Institution institutionD = new Institution();
    institutionD.setAddress(addressD);
    institutionD.setName("Gesellschaft für Irgendetwas mbH");
    institutionD.setDescription("Wir machen Etwas möglich!");
    institutionD.setPhone("0251 123456789");
    institutionA.setEmployeeNames(new ArrayList<String>());
    institutionA.setSlots(new ArrayList<Slot>());

    Institution institutionE = new Institution();
    institutionE.setAddress(addressE);
    institutionE.setName("Zahnarzt Dr. Müller");
    institutionE.setDescription("Zähne und noch viel mehr...");
    institutionE.setPhone("0251 123456789");
    institutionA.setEmployeeNames(new ArrayList<String>());
    institutionA.setSlots(new ArrayList<Slot>());

    Institution institutionF = new Institution();
    institutionF.setAddress(addressF);
    institutionF.setName("Schmidt AG");
    institutionF.setDescription("Wir verkaufen auch mehr als nur Aktien");
    institutionF.setPhone("0251 123456789");
    institutionA.setEmployeeNames(new ArrayList<String>());
    institutionA.setSlots(new ArrayList<Slot>());

    Institution institutionG = new Institution();
    institutionG.setAddress(addressG);
    institutionG.setName("Meier Gmbh & Co. KG");
    institutionG.setDescription("Hier wird ihnen geholfen");
    institutionG.setPhone("0251 123456789");
    institutionA.setEmployeeNames(new ArrayList<String>());
    institutionA.setSlots(new ArrayList<Slot>());

    Institution institutionH = new Institution();
    institutionH.setAddress(addressH);
    institutionH.setName("Die Hotline für alles");
    institutionH.setDescription("Wir lösen auch ihr Problem");
    institutionH.setPhone("0251 123456789");
    institutionA.setEmployeeNames(new ArrayList<String>());
    institutionA.setSlots(new ArrayList<Slot>());

    Institution institutionI = new Institution();
    institutionI.setAddress(addressI);
    institutionI.setName("Eine Bank");
    institutionI.setDescription("Hier ist ihr Geld sicher!");
    institutionI.setPhone("0251 123456789");
    institutionA.setEmployeeNames(new ArrayList<String>());
    institutionA.setSlots(new ArrayList<Slot>());

    Institution institutionJ = new Institution();
    institutionJ.setAddress(addressJ);
    institutionJ.setName("Die Wir Versicherung");
    institutionJ.setDescription("Gemeinsam Versichert");
    institutionJ.setPhone("0251 123456789");
    institutionA.setEmployeeNames(new ArrayList<String>());
    institutionA.setSlots(new ArrayList<Slot>());

    institutionRepository.save(institutionB);
    institutionRepository.save(institutionC);
    institutionRepository.save(institutionD);
    institutionRepository.save(institutionE);
    institutionRepository.save(institutionF);
    institutionRepository.save(institutionG);
    institutionRepository.save(institutionH);
    institutionRepository.save(institutionI);
    institutionRepository.save(institutionJ);

    User user = new User();
    user.setEmail("max@mustermann.ms");
    user.setFirstname("Max");
    user.setLastname("Mustermann");
    user.setPassword(BcryptUtil.bcryptHash("12345"));

    User owner = new User();
    owner.setEmail("markus.lewe@münster.de");
    owner.setFirstname("Markus");
    owner.setLastname("Lese");
    owner.setPassword(BcryptUtil.bcryptHash("12345"));
    owner.setInstitution(institutionA);

    userRepository.save(user);
    userRepository.save(owner);
  }

}