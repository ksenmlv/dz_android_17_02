package com.example.dz_17_02;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EditText plainText;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //инициализируем SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        //получаем ссылку на TextView, Button, PlainText
        textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);
        plainText = findViewById(R.id.editTextText);

        //загружаем последнее введенное имя из SharedPreferences и отображаем его
        String savedName = sharedPreferences.getString("name", "");
        textView.setText(savedName);

        //настраиваем слушатель кликов для кнопки
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем текст из Plain Text
                String inputText = plainText.getText().toString();
                // Устанавливаем текст в TextView
                textView.setText("Привет, " + inputText + "!");
                // Сохраняем имя в SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", inputText);
                editor.apply();
            }
        });

        // Обработчик кнопки конвертации чисел
        Button convertButton = findViewById(R.id.button2);
        EditText inputEditText = findViewById(R.id.editTextText2);
        EditText resultEditText = findViewById(R.id.editTextText4);

        // Настройка слушателя кликов для кнопки конвертации
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = inputEditText.getText().toString().trim();
                if (!input.isEmpty()) {
                    // Если ввод - арабское число, то переводим его в римское
                    if (input.matches("\\d+")) {
                        int arabicNumber = Integer.parseInt(input);
                        String romanNumber = arabicToRoman(arabicNumber);
                        resultEditText.setText(romanNumber);
                    }
                    // Если ввод - римское число, то переводим его в арабское
                    else {
                        String romanNumber = input.toUpperCase();
                        int arabicNumber = romanToArabic(romanNumber);
                        resultEditText.setText(String.valueOf(arabicNumber));
                    }
                }
            }
        });
    }

    // Метод для конвертации арабских чисел в римские
    private String arabicToRoman(int num) {
        if (num < 1 || num > 3999)
            throw new IllegalArgumentException("Число должно быть в диапазоне от 1 до 3999");

        // Массив символов для римских цифр
        String[] romanSymbols = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
        // Массив чисел, соответствующих римским символам
        int[] romanValues = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
        StringBuilder romanNumeral = new StringBuilder();

        // Проходим по массиву римских чисел в обратном порядке
        for (int i = romanValues.length - 1; i >= 0; i--) {
            // Пока число num больше или равно текущему римскому числу
            while (num >= romanValues[i]) {
                // Вычитаем текущее римское число из num
                num -= romanValues[i];
                // Добавляем римский символ в результирующую строку
                romanNumeral.append(romanSymbols[i]);
            }
        }

        return romanNumeral.toString();
    }

    // Метод для конвертации римских чисел в арабские
    private int romanToArabic(String roman) {
        // Массив символов для римских цифр
        char[] romanSymbols = {'I', 'V', 'X', 'L', 'C', 'D', 'M'};
        // Относительные значения римских цифр
        int[] romanValues = {1, 5, 10, 50, 100, 500, 1000};
        int arabicNumeral = 0;
        int previousValue = 0;

        // Проходим по символам римского числа
        for (int i = roman.length() - 1; i >= 0; i--) {
            // Находим числовое значение текущего символа
            int value = romanValues[Arrays.binarySearch(romanSymbols, roman.charAt(i))];

            // Если текущее значение меньше предыдущего, то вычитаем его
            if (value < previousValue) {
                arabicNumeral -= value;
            } else {
                arabicNumeral += value;
            }
            // Обновляем предыдущее значение
            previousValue = value;
        }

        return arabicNumeral;
    }
}




