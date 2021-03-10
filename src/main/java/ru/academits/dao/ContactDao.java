package ru.academits.dao;

import ru.academits.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ContactDao {
    private final List<Contact> contactList = new ArrayList<>();
    private final AtomicInteger idSequence = new AtomicInteger(0);

    public ContactDao() {
        Contact contact = new Contact();

        contact.setId(getNewId());
        contact.setFirstName("Иван");
        contact.setLastName("Иванов");
        contact.setPhone("9123456789");

        contactList.add(contact);
    }

    private int getNewId() {
        return idSequence.addAndGet(1);
    }

    public List<Contact> getAllContacts() {
        return contactList;
    }

    public void add(Contact contact) {
        contact.setId(getNewId());

        contactList.add(contact);
    }

    public boolean remove(Contact contact) {
        for (Contact e : contactList) {
            if (e.getId() == contact.getId()) {
                return contactList.remove(e);
            }
        }

        return false;
    }

    public List<Contact> getFilterContacts(String term) {
        List<Contact> filteredContactList = new ArrayList<>();

        String text = term.toUpperCase().trim();

        for (Contact e : contactList) {
            if (e.getLastName().toUpperCase().contains(text) ||
                    e.getFirstName().toUpperCase().contains(text) ||
                    e.getPhone().toUpperCase().contains(text)) {
                filteredContactList.add(e);
            }
        }

        return filteredContactList;
    }
}