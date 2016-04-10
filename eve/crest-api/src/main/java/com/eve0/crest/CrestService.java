package com.eve0.crest;

import java.util.List;
import com.eve0.crest.model.CrestCharacter;
import com.eve0.crest.model.CrestCharacterStatus;
import com.eve0.crest.model.CrestContact;

public interface CrestService {

    List<CrestContact> getContacts();

    CrestCharacterStatus getCharacterStatus();

    CrestCharacter getCharacter();

}
