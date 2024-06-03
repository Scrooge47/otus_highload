package com.avseenkov.dialogs.controller;


import com.avseenkov.dialogs.dto.DialogDto;
import com.avseenkov.dialogs.dto.NewDialogDto;
import com.avseenkov.dialogs.service.DialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dialog_tarantool")
public class DialogController {

    @Autowired
    private DialogService dialogService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{userId}/send")
    public DialogDto send(@RequestHeader("X-Auth-User") Long id, @PathVariable Long userId, @RequestBody NewDialogDto newDialogDto) {
        return dialogService.addDialog(id, userId, newDialogDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}/list")
    public List<DialogDto> getDialogs(@RequestHeader("X-Auth-User") Long id, @PathVariable Long userId) {
        return dialogService.getDialogs(id, userId);
    }
}
