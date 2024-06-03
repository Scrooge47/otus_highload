package com.avseenkov.dialogs.service;

import com.avseenkov.dialogs.dto.DialogDto;
import com.avseenkov.dialogs.dto.NewDialogDto;
import com.avseenkov.dialogs.exception.ModelNotFoundException;
import com.avseenkov.dialogs.util.Helper;
import io.tarantool.driver.api.TarantoolClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("dialogTarantool")
@AllArgsConstructor
public class DialogServiceTarantoolImpl implements DialogService {

    private final TarantoolClient client;

    @Override
    public DialogDto addDialog(Long from, Long to, NewDialogDto dialogDto) {
        try {

            String keyId = Helper.getKeyId(from, to);
            var result = client.call("addDialog", from, to, dialogDto.getText(), keyId).get().get(0);

            DialogDto dialogDto1 = new DialogDto();
            dialogDto1.setFrom(from.toString());
            dialogDto1.setTo(to.toString());
            dialogDto1.setText(dialogDto.getText());

            return dialogDto1;

        } catch (Exception e) {
            throw new RuntimeException("bad request");
        }

    }

    @Override
    public List<DialogDto> getDialogs(Long from, Long to) {

        String keyId = Helper.getKeyId(from, to);

        try {

            var result = (List<Object>) client.call("getDialogs", keyId).get().get(0);

            return result.stream()
                    .map(el -> {
                        var arr = (List<Object>) el;
                        return createDialogDto(arr);
                    })
                    .toList();

        } catch (Exception e) {
            throw new ModelNotFoundException("Dialogs not founds");
        }

    }

    private DialogDto createDialogDto(List<Object> data) {
        DialogDto dialogDto = new DialogDto();
        dialogDto.setFrom(data.get(2).toString());
        dialogDto.setTo(data.get(3).toString());
        dialogDto.setText(data.get(4).toString());
        return dialogDto;
    }
}
