package com.eve0.crest;

import java.util.List;
import com.eve0.crest.model.CrestCharacter;
import com.eve0.crest.model.CrestCharacterStatus;
import com.eve0.crest.model.CrestContact;
import com.eve0.crest.model.CrestServerStatus;

public interface CrestService {

    CrestServerStatus getServerStatus();

    CrestCharacterStatus getCharacterStatus();

    CrestCharacter getCharacter();

    List<CrestContact> getCharacterContacts();

}
