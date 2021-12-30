package ru.mivlgu.pin.volleyballreferee;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.mivlgu.pin.volleyballreferee.database.entities.Match;

public class MatchManagerTest {

    @Test
    public void isMatchFinishedTest () {
        assertFalse(MatchManager.isMatchFinished(1, 1));
        assertFalse(MatchManager.isMatchFinished(1, 0));
        assertTrue(MatchManager.isMatchFinished(2, 1));
        assertTrue(MatchManager.isMatchFinished(2, 0));
    }

    @Test
    public void isSetFinishedTest () {
        assertFalse(MatchManager.isSetFinished(1, 20, 20));
        assertFalse(MatchManager.isSetFinished(1, 19, 20));
        assertTrue(MatchManager.isSetFinished(2, 22, 20));
        assertTrue(MatchManager.isSetFinished(3, 15, 13));
    }

    @Test
    public void getSetWinnerIdTest () {
        assertEquals(1, MatchManager.getSetWinnerId(21, 19));
        assertEquals(2, MatchManager.getSetWinnerId(20, 22));
    }

    @Test
    public void getMatchWinnerIdTest () {
        assertEquals(1, MatchManager.getMatchWinnerId(2, 1));
        assertEquals(2, MatchManager.getMatchWinnerId(0, 2));
    }
}