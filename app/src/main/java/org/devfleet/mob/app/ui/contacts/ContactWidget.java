package org.devfleet.mob.app.ui.contacts;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import org.devfleet.mob.app.model.EveContact;

import java.util.List;

public class ContactWidget extends RecyclerView {

    public interface Listener {
        void onContactSelected(final EveContact contact);
    }

    private ContactAdapter adapter;
    private Listener listener;

    public ContactWidget(Context context) {
        super(context);
        init();
    }

    public ContactWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ContactWidget(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.adapter = new ContactAdapter();
        this.setLayoutManager(new LinearLayoutManager(getContext()));
        this.setAdapter(this.adapter);
    }

    public void setContacts(final List<EveContact> contacts) {
        this.adapter.setItems(contacts);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
