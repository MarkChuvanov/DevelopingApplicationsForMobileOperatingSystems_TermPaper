package ru.mivlgu.pin.volleyballreferee.database;

import androidx.room.*;

import ru.mivlgu.pin.volleyballreferee.database.dao.*;
import ru.mivlgu.pin.volleyballreferee.database.entities.*;

@androidx.room.Database (entities = {
                            Category.class,
                            GameEvent.class,
                            Match.class,
                            MatchProgress.class,
                            Player.class,
                            Referee.class,
                            Role.class,
                            Team.class
                        }, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao ();

    public abstract GameEventDao gameEventDao ();

    public abstract MatchDao matchDao ();

    public abstract MatchProgressDao matchProgressDao ();

    public abstract PlayerDao playerDao ();

    public abstract RefereeDao refereeDao ();

    public abstract RoleDao roleDao ();

    public abstract TeamDao teamDao ();
}