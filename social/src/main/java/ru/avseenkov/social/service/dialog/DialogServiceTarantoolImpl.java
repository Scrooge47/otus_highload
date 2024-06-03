package ru.avseenkov.social.service.dialog;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.avseenkov.social.dto.DialogDto;
import ru.avseenkov.social.dto.NewDialogDto;
import ru.avseenkov.social.feignClient.DialogClient;

import java.util.List;


@Service("dialogTarantool")
@AllArgsConstructor
public class DialogServiceTarantoolImpl implements DialogService {

    private final DialogClient client;

    @Override
    public DialogDto addDialog(Long from, Long to, NewDialogDto dialogDto) {
        return client.send(from, to, dialogDto);
    }

    @Override
    public List<DialogDto> getDialogs(Long from, Long to) {
        return client.getDialogs(from, to);
    }


}
