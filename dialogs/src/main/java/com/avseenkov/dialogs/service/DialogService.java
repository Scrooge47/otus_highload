package com.avseenkov.dialogs.service;

import com.avseenkov.dialogs.dto.DialogDto;
import com.avseenkov.dialogs.dto.NewDialogDto;

import java.util.List;

public interface DialogService {

    DialogDto addDialog(Long from, Long to, NewDialogDto dialogDto);

    List<DialogDto> getDialogs(Long from, Long to);
}
