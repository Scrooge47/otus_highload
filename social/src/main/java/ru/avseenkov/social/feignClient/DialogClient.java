package ru.avseenkov.social.feignClient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.avseenkov.social.dto.DialogDto;
import ru.avseenkov.social.dto.NewDialogDto;

import java.util.List;

@FeignClient("dialogs")
public interface DialogClient {

    @RequestMapping(method = RequestMethod.POST, value = "/dialog_tarantool/{userId}/send")
    public DialogDto send(@RequestHeader("X-Auth-User") Long id,
                          @PathVariable Long userId,
                          @RequestBody NewDialogDto newDialogDto);


    @RequestMapping(method = RequestMethod.GET, value = "/dialog_tarantool/{userId}/list")
    public List<DialogDto> getDialogs(@RequestHeader("X-Auth-User") Long id,
                                      @PathVariable Long userId);
}
