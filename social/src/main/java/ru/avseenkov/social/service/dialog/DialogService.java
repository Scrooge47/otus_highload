package ru.avseenkov.social.service.dialog;

import ru.avseenkov.social.dto.DialogDto;
import ru.avseenkov.social.dto.NewDialogDto;

import java.util.List;

public interface DialogService {

    DialogDto addDialog(Long from, Long to, NewDialogDto dialogDto);

    List<DialogDto> getDialogs(Long from, Long to);
}
