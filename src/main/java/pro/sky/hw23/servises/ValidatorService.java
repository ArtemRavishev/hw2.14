package pro.sky.hw23.servises;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.hw23.exeptions.IncorrectNameExeption;
import pro.sky.hw23.exeptions.IncorrectSurnameExeption;


@Service
public class ValidatorService {

    public String validatorName(String firstName) {
        if (!StringUtils.isAlpha(firstName)) {
            throw new IncorrectNameExeption();
        }
        return StringUtils.capitalize(firstName.toLowerCase());
    }

    public String validatorSurname(String lastName) {
        String[] lastNames = lastName.split("-");
        for (int i = 0; i < lastNames.length; i++) {
            if (!StringUtils.isAlpha(lastNames[i])) {
                throw new IncorrectSurnameExeption();
            }
            lastNames[i]= StringUtils.capitalize(lastName.toLowerCase());
        }
        return String.join("-", lastNames);
    }
}
