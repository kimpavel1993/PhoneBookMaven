package ru.academits.servlet;

import ru.academits.PhoneBook;
import ru.academits.coverter.ContactConverter;
import ru.academits.coverter.ContactValidationConverter;
import ru.academits.model.Contact;
import ru.academits.service.ContactService;
import ru.academits.service.ContactValidation;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

public class RemoveContactServlet extends HttpServlet {
    private static final long serialVersionUID = -1218339290409700707L;

    private final ContactService phoneBookService = PhoneBook.phoneBookService;
    private final ContactConverter contactConverter = PhoneBook.contactConverter;
    private final ContactValidationConverter contactValidationConverter = PhoneBook.contactValidationConverter;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (OutputStream responseStream = resp.getOutputStream()) {
            String contactJson = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Contact contact = contactConverter.convertFromJson(contactJson);
            ContactValidation contactValidation = phoneBookService.removeContact(contact);
            String contactValidationJson = contactValidationConverter.convertToJson(contactValidation);

            if (!contactValidation.isValid()) {
                resp.setStatus(500);
            }

            responseStream.write(contactValidationJson.getBytes(Charset.forName("UTF-8")));
        } catch (Exception e) {
            System.out.println("error in RemoveContactServlet POST: ");

            e.printStackTrace();
        }
    }
}