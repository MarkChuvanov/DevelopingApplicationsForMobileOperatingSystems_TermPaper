package ru.mivlgu.pin.volleyballreferee;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.mivlgu.pin.volleyballreferee.database.AppDatabase;
import ru.mivlgu.pin.volleyballreferee.database.dao.CategoryDao;
import ru.mivlgu.pin.volleyballreferee.database.dao.GameEventDao;
import ru.mivlgu.pin.volleyballreferee.database.dao.RoleDao;
import ru.mivlgu.pin.volleyballreferee.database.entities.Category;
import ru.mivlgu.pin.volleyballreferee.database.entities.GameEvent;
import ru.mivlgu.pin.volleyballreferee.database.entities.Role;
import ru.mivlgu.pin.volleyballreferee.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_match, R.id.navigation_teams, R.id.navigation_players, R.id.navigation_referees, R.id.navigation_matches).setFallbackOnNavigateUpListener(new AppBarConfiguration.OnNavigateUpListener() {
            @Override
            public boolean onNavigateUp () {
                getSupportFragmentManager().popBackStack();
                return true;
            }
        })
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        // NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationUI.setupWithNavController(binding.navView, navController);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database").allowMainThreadQueries().build();
        //fillInDatabase();
    }

    private void fillInDatabase () {
        AppDatabase db = App.getInstance().getDatabase();
        CategoryDao categoryDao = db.categoryDao();
        Category category0 = new Category();
        category0.name = "Всероссийская";
        categoryDao.insert(category0);
        Category category1 = new Category();
        category1.name = "Первая";
        categoryDao.insert(category1);
        Category category2 = new Category();
        category2.name = "Вторая";
        categoryDao.insert(category2);
        Category category3 = new Category();
        category3.name = "Третья";
        categoryDao.insert(category3);

        RoleDao roleDao = db.roleDao();
        Role role0 = new Role();
        role0.name = "Правый блокирующий";
        roleDao.insert(role0);
        Role role1 = new Role();
        role1.name = "Левый блокирующий";
        roleDao.insert(role1);
        Role role2 = new Role();
        role2.name = "Правый защитник";
        roleDao.insert(role2);
        Role role3 = new Role();
        role3.name = "Левый защитник";
        roleDao.insert(role3);

        GameEventDao gameEventDao = db.gameEventDao();
        GameEvent gameEvent0 = new GameEvent();
        gameEvent0.name = "Первая команда заработала очко";
        gameEventDao.insert(gameEvent0);
        GameEvent gameEvent1 = new GameEvent();
        gameEvent1.name = "Вторая команда заработала очко";
        gameEventDao.insert(gameEvent1);
        GameEvent gameEvent2 = new GameEvent();
        gameEvent2.name = "Первая команда получила предупреждение";
        gameEventDao.insert(gameEvent2);
        GameEvent gameEvent3 = new GameEvent();
        gameEvent3.name = "Вторая команда получила предупреждение";
        gameEventDao.insert(gameEvent3);
        GameEvent gameEvent4 = new GameEvent();
        gameEvent4.name = "Первая команда получила замечание";
        gameEventDao.insert(gameEvent4);
        GameEvent gameEvent5 = new GameEvent();
        gameEvent5.name = "Вторая команда получила замечание";
        gameEventDao.insert(gameEvent5);
        GameEvent gameEvent6 = new GameEvent();
        gameEvent6.name = "Первая команда получила удаление";
        gameEventDao.insert(gameEvent6);
        GameEvent gameEvent7 = new GameEvent();
        gameEvent7.name = "Вторая команда получила удаление";
        gameEventDao.insert(gameEvent7);
        GameEvent gameEvent8 = new GameEvent();
        gameEvent8.name = "Первая команда взяла тайм-аут";
        gameEventDao.insert(gameEvent8);
        GameEvent gameEvent9 = new GameEvent();
        gameEvent9.name = "Вторая команда взяла тайм-аут";
        gameEventDao.insert(gameEvent9);
    }
}