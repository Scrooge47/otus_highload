package ru.avseenkov.social.mapper;

import ru.avseenkov.social.dto.DialogDto;
import ru.avseenkov.social.dto.NewDialogDto;
import ru.avseenkov.social.model.Dialog;

import java.util.ArrayList;
import java.util.List;

public class DialogMapper {

    public static Dialog dialogFromDialogDto(DialogDto dialogDto) {
        Dialog dialog = new Dialog();
        dialog.setText(dialogDto.getText());
        return dialog;
    }

    public static Dialog dialogFromNewDialogDto(NewDialogDto dialogDto) {
        Dialog dialog = new Dialog();
        dialog.setText(dialogDto.getText());
        return dialog;
    }

    public static DialogDto dialogDtoFromDialog(Dialog dialog) {
        DialogDto dialogDto = new DialogDto();
        dialogDto.setText(dialog.getText());
        return dialogDto;
    }

    public static NewDialogDto NewDialogDtoFromDialog(Dialog dialog) {
        NewDialogDto newDialogDto = new NewDialogDto();
        newDialogDto.setText(dialog.getText());
        return newDialogDto;
    }

    public static List<DialogDto> mapToPostDto(Iterable<Dialog> dialogs, String from, String to) {
        List<DialogDto> dialogsDto = new ArrayList<>();
        for (Dialog dialog : dialogs) {
            DialogDto dialogDto = dialogDtoFromDialog(dialog);
            dialogDto.setFrom(from);
            dialogDto.setTo(to);
            dialogsDto.add(dialogDto);
        }
        return dialogsDto;
    }
}
