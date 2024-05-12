package ru.avseenkov.social.repository.dialog;

import ru.avseenkov.social.model.Dialog;

import java.util.List;

public interface DialogRepository {

    Dialog save(Long from, Long to, Dialog dialog);

    List<Dialog> getDialogs(Long from, Long to);
}
