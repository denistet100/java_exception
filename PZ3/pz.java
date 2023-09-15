/*
 * Напишите приложение, которое будет запрашивать у пользователя следующие данные, разделенные пробелом:
Фамилия Имя Отчество датарождения номертелефона пол

Форматы данных(можно везде использовать тип String):
фамилия, имя, отчество - строки
датарождения - строка формата dd.mm.yyyy
номертелефона - целое беззнаковое число без форматирования
пол - символ латиницей f или m.

Приложение должно проверить введенные данные по количеству. 
Если количество не совпадает с требуемым, вернуть код ошибки, обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.

Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры. 
Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы. 
Можно использовать встроенные типы java и создать свои. Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.

Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку должны записаться полученные данные, вида

<Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>

Однофамильцы должны записаться в один и тот же файл, в отдельные строки.

Не забудьте закрыть соединение с файлом.

При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть стектрейс ошибки.

Данная промежуточная аттестация оценивается по системе "зачет" / "не зачет"

"Зачет" ставится, если слушатель успешно выполнил
"Незачет"" ставится, если слушатель не успешно выполнил

Критерии оценивания:
Слушатель напишите 2приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке, разделенные пробело
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class pz {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws FileNotExist {
        String[] human_info;
        File file = new File("phonebook.txt");
        //readFile(file);
        while (true) {
            try {
                human_info = prompt("Введите через пробел: ФИО, дату рождения, номер телефона и пол (f или m): ");
                checkAmount(human_info);
                for (int i = 0; i < human_info.length; i++) {
                    checkFormat(human_info, i);
                }
                ArrayList<String> people = new ArrayList<>(Arrays.asList(human_info));
                writeFile(people, file);
            } catch (RuntimeException e) {
                System.out.println();
            }
        }
    }

    // Запрос у пользователя данных:
    public static String[] prompt(String msg) {
        System.out.println(msg);
        return scanner.nextLine().split(" ");
    }

    // Проверка формата введённых данных:
    public static void checkFormat(String[] human_info, int i) {
        switch (i) {
            case 0: // Проверка фамилии
                if (checkString(human_info[0]))
                    throw new StringException(-1);
            case 1: // Проверка имени
                if (checkString(human_info[1]))
                    throw new StringException(-1);
            case 2: // Проверка отчества
                if (checkString(human_info[2]))
                    throw new StringException(-1);
            case 3: // Проверка даты
                if (!dateValidator(human_info[3])) throw new StringException(-3);
            case 4: // Проверка номера телефона
                if (!checkString(human_info[4]))
                    throw new StringException(-2);
            case 5: // Проверка пола
                if (!human_info[5].equals("f") && !human_info[5].equals("m")) throw new StringException(-4);
        }
    }

    // Проверка на String и Integer
    public static boolean checkString(String line) {
        try {
            Integer.valueOf(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Проверка на количество введённых данных:
    public static void checkAmount(String[] human_info) {
        if (human_info.length < 6) throw new AmountException(-1);
        if (human_info.length > 6) throw new AmountException(-3);
    }

    // Проверка валидности даты:
    public static boolean dateValidator(String date) {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd.MM.yyyy");
        myFormat.setLenient(false);
        try {
            myFormat.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Вывод списка:
    public static void printPB(String[] human) {
        for (String s : human) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

    // Запись в файл:
    public static void writeFile(ArrayList<String> people, File file) {
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            for (String s : people) {
                fileWriter.write("<" + s + ">");
            }
            fileWriter.append('\n');
            fileWriter.flush();
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
}

// Exceptions:
class AmountException extends RuntimeException {
    public AmountException(int error) {
        super();
        switch (error) {
            case -1 -> System.out.println("Вы ввели меньше данных, чем нужно.");
            case -3 -> System.out.println("Вы ввели больше данных, чем нужно.");
        }
    }
}

class StringException extends NumberFormatException {
    public StringException(int error) {
        super();
        switch (error) {
            case -1 ->
                    System.out.println("Неккоректен формат ввода введённых данных. В ФИО должны быть только буквенные значения.");
            case -2 ->
                    System.out.println("Неккоректен формат ввода введённых данных. При вводе номера телефона должны быть только числовые значения.");
            case -3 ->
                    System.out.println("Неккоректен формат ввода введённых данных. Дата должна быть в формате: dd.mm.yyyy");
            case -4 ->
                    System.out.println("Неккоректен формат ввода введённых данных. Здесь нужно вводить либо f, либо m.");
        }
    }
}

class FileNotExist extends FileNotFoundException {
    public FileNotExist(String path) {
        super("Такого файла не существует: " + path);
    }

    public FileNotExist() {
        super("Такого файла не существует.");
    }
}

