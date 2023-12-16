package com.example.test;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
                    // переменные игрока
    private TextView winText, dayText, charsText, waterText, riceText, housesText, cellsText,            // переменные для интерФЕйса
                    // переменные бота
    bCharsText, bWaterText, bRiceText, bHousesText, bCellsText;
    private Button landButton, buildButton, takeWaterButton, waterButton;                                               // перемнннеы для кнопопк

    // переменные для управления игрой
    static int day = 0;
    static int P_Cell = 100;
    static int B_Cell = 100;
    static int Chars_4_Calim_Cell = 5;
    static int Plants_Grows = 1;
    static int Chars_From_House = 1;

    // данные игрока
    static int P_Cell_Opened = 1;
    static int P_chars = 1;
    static int P_houses = 1;
    static int P_plants = 1;
    static int P_water = 1;
    static boolean Can_Pchar_Claim = true;
    static boolean Can_PBuild_Home = true;

    // данные AI
    static int B_Cell_Opened = 1;
    static int B_chars = 1;
    static int B_houses = 1;
    static int B_plants = 1;
    static int B_water = 1;
    static boolean Can_Bchar_Claim = true;
    static boolean Can_BBuild_Home = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // привязка переменных к числам интерфеса
        winText = findViewById(R.id.winner);
        dayText = findViewById(R.id.day);
            // игрока
        charsText = findViewById(R.id.chars);
        waterText = findViewById(R.id.water);
        riceText = findViewById(R.id.rice);
        housesText = findViewById(R.id.houses);
        cellsText = findViewById(R.id.cells);
            // бота
        bCharsText = findViewById(R.id.bChars);
        bWaterText = findViewById(R.id.bWater);
        bRiceText = findViewById(R.id.bRice);
        bHousesText = findViewById(R.id.bHouses);
        bCellsText = findViewById(R.id.bCells);
        // привязка переменных к кнопкам интерфейса
        landButton = findViewById(R.id.landBtn);
        buildButton = findViewById(R.id.buildBtn);
        takeWaterButton = findViewById(R.id.waterBtn);
        waterButton = findViewById(R.id.takeWaterBtn);

        updateUI();
        landButton.setOnClickListener(new View.OnClickListener() { // слушаем кнопку открытия новыъ ячеек
            @Override
            public void onClick(View v) {
                if (Can_Pchar_Claim) {
                    exploreNewTerritory();
                    NewDay();
                }
            }
        });

        buildButton.setOnClickListener(new View.OnClickListener() { // слушаем кнопку постройки дома
            @Override
            public void onClick(View v) {
                if (Can_PBuild_Home) {
                    buildHome();
                    NewDay();
                }
            }
        });

        takeWaterButton.setOnClickListener(new View.OnClickListener() { // считываем кнопку набора воды
            @Override
            public void onClick(View v) {
                collectWater();
                NewDay();
            }
        });

        waterButton.setOnClickListener(new View.OnClickListener() { // считываем нажатие кнопки полива риса
            @Override
            public void onClick(View v) {
                waterPlants();
                NewDay();
            }
        });
    }

    public void NewDay() { // логика начала нового дня
        day++;

        // Проверка на возможность игрока захватывать ячейки
        if (P_chars >= Chars_4_Calim_Cell){
            Can_Pchar_Claim = true;
        }
        else {
            Can_Pchar_Claim = false;
        }
        // Проверка на возмодность игрока строить дом
        if (P_chars >= 1 && P_plants >= 1 && P_water >= 1){
            Can_PBuild_Home = true;
        }
        else {
            Can_PBuild_Home = false;
        }
        // Проверка на возможность Бота захватывать ячейки
        if (B_chars >= Chars_4_Calim_Cell){
            Can_Bchar_Claim = true;
        }
        else {
            Can_Bchar_Claim = false;
        }
        // Проверка на возмодность Бота строить дом
        if (B_chars >= 1 && B_plants >= 1 && B_water >= 1){
            Can_BBuild_Home = true;
        }
        else {
            Can_BBuild_Home = false;
        }
        homeLogic(); // спавн крестьян для игрока
        bHomeLogic(); // спавн крестьян для бота
        aiLogic();
        updateUI();
    }

//                  -----       механика для игрока          -----

    public void collectWater() { // сбор воды для игрока
        P_water++;
        updateUI();
    }
    public void waterPlants() { // полив риса для игрока
        P_plants++;
        updateUI();
    }
    public void exploreNewTerritory() { // логика исследования новой ячейки для игрока
        P_Cell_Opened++;
        P_chars = P_chars - Chars_4_Calim_Cell;
        updateUI();
    }
    public void buildHome() { // логика постройки дома для игрока
        P_chars--;
        P_houses++;
        P_water--;
        P_plants--;
        updateUI();
    }
    public void homeLogic(){
        P_chars = P_chars + (Chars_From_House * P_houses); // Спавн крестьян из домов
    }

    //                  -----       механика для бота          -----

    public void aiLogic(){
        if (Can_Bchar_Claim) { // если могет - исследует клетки
            bExploreNewTerritory();
        } else if (Can_BBuild_Home) { // если не могет в клетки, но могет в дом - строит дом
            bBuildHome();
        } else { // если вазе ниче не могет
            if (Math.random() < 0.5) { // или набирает воды
                bCollectWater();
            } else { // или поливает рис
                bWaterPlants();
            }
        }
    }
    public void bCollectWater() { // сбор воды для игрока
        B_water++;
        updateUI();
    }
    public void bWaterPlants() { // полив риса для игрока
        B_plants++;
        updateUI();
    }
    public void bExploreNewTerritory() { // логика исследования новой ячейки для игрока
        B_Cell_Opened++;
        B_chars = B_chars - Chars_4_Calim_Cell;
        updateUI();
    }
    public void bBuildHome() { // логика постройки дома для игрока
        B_chars--;
        B_houses++;
        B_water--;
        B_plants--;
        updateUI();
    }
    public void bHomeLogic(){
        B_chars = B_chars + (Chars_From_House * B_houses); // Спавн крестьян из домов
    }
    private void updateUI() {

        if (P_Cell_Opened == P_Cell && B_Cell_Opened < B_Cell){
            winText.setText("Ты победил!");
        }
        else if (P_Cell_Opened < P_Cell && B_Cell_Opened == B_Cell){
            winText.setText("Ты проиграл :С");
        }

        // обнов переменных игрока
        dayText.setText(String.valueOf(day));
        charsText.setText(String.valueOf(P_chars));
        waterText.setText(String.valueOf(P_water));
        riceText.setText(String.valueOf(P_plants));
        housesText.setText(String.valueOf(P_houses));
        cellsText.setText(String.valueOf(P_Cell_Opened));

        // обновл переменных бота
        bCharsText.setText(String.valueOf(B_chars));
        bWaterText.setText(String.valueOf(B_water));
        bRiceText.setText(String.valueOf(B_plants));
        bHousesText.setText(String.valueOf(B_houses));
        bCellsText.setText(String.valueOf(B_Cell_Opened));
    }
}
