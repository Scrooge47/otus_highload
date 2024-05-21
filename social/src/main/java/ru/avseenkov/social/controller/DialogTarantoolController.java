package ru.avseenkov.social.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.avseenkov.social.dto.DialogDto;
import ru.avseenkov.social.dto.NewDialogDto;
import ru.avseenkov.social.model.User;
import ru.avseenkov.social.service.dialog.DialogService;

import java.util.List;

@RestController
@RequestMapping("dialog_tarantool")
public class DialogTarantoolController {

    @Autowired
    @Qualifier("dialogTarantool")
    private DialogService dialogService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{userId}/send")
    public DialogDto send(@AuthenticationPrincipal User user, @PathVariable Long userId, @RequestBody NewDialogDto newDialogDto) {
        return dialogService.addDialog(user.getId(), userId, newDialogDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}/list")
    public List<DialogDto> getDialogs(@AuthenticationPrincipal User user, @PathVariable Long userId) {
        return dialogService.getDialogs(user.getId(), userId);
    }
}
