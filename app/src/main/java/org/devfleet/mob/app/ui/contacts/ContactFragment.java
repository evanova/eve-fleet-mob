package org.devfleet.mob.app.ui.contacts;

import com.karumi.rosie.view.Presenter;

import org.devfleet.mob.app.R;
import org.devfleet.mob.app.model.EveCharacter;
import org.devfleet.mob.app.model.EveContact;
import org.devfleet.mob.app.presenter.ContactPresenter;
import org.devfleet.mob.app.ui.AbstractFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class ContactFragment extends AbstractFragment implements ContactPresenter.View {

    @Inject
    @Presenter
    ContactPresenter presenter;

    @Bind(R.id.fragmentContactsWidget)
    ContactWidget view;

    @Override
    protected int getLayoutId() {
        return R.layout.f_contacts;
    }

    @Override
    public void setCharacter(final EveCharacter character) {
        this.presenter.setCharacter((null == character) ? 0 : character.getID());
    }

    public void setContacts(List<EveContact> contacts) {
        this.view.setContacts(contacts);
    }

}
