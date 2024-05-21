package ru.avseenkov.social.service.dialog;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.avseenkov.social.dto.DialogDto;
import ru.avseenkov.social.dto.NewDialogDto;
import ru.avseenkov.social.mapper.DialogMapper;
import ru.avseenkov.social.model.Dialog;
import ru.avseenkov.social.model.User;
import ru.avseenkov.social.repository.dialog.DialogRepository;
import ru.avseenkov.social.repository.user.UserRepository;

import java.util.List;

@AllArgsConstructor
@Service
@Primary
public class DialogServiceImpl implements DialogService {

    private DialogRepository dialogRepository;

    private UserRepository userRepository;

    @Override
    public DialogDto addDialog(Long from, Long to, NewDialogDto dialogDto) {
        User userFrom = userRepository.getUserFromDb(from);
        User userTo = userRepository.getUserFromDb(to);
        Dialog dialog = dialogRepository.save(from, to, DialogMapper.dialogFromNewDialogDto(dialogDto));
        return DialogMapper.dialogDtoFromDialog(dialog);
    }

    @Override
    public List<DialogDto> getDialogs(Long from, Long to) {
//        User userFrom = userRepository.getUserFromDb(from);
//        User userTo = userRepository.getUserFromDb(to);
        List<Dialog> dialogs = dialogRepository.getDialogs(from, to);
        return DialogMapper.mapToPostDto(dialogs, from.toString(), to.toString());
    }
}
