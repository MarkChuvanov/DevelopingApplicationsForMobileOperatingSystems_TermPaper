package ru.mivlgu.pin.volleyballreferee;

/**
 * @author Mark Chuvanov
 */
public class MatchManager {

    /**
     * The method determines whether the match is completed
     * @param firstTeamSets Number of sets won by the first team
     * @param secondTeamSets Number of sets won by the second team
     * @return The logical value is yes or no
     */
    public static boolean isMatchFinished (int firstTeamSets, int secondTeamSets) {
        if (firstTeamSets == 2 || secondTeamSets == 2) {
            return true;
        }
        return false;
    }

    /**
     * The method determines whether the set is completed
     * @param setNumber The sequence number of the current set
     * @param firstTeamPoints Number of points won by the first team
     * @param secondTeamPoints Number of points won by the second team
     * @return The logical value is yes or no
     */
    public static boolean isSetFinished (int setNumber, int firstTeamPoints, int secondTeamPoints) {
        if (setNumber != 3) {
            if (firstTeamPoints >= 21 & firstTeamPoints - secondTeamPoints >= 2) {
                return true;
            }
            if (secondTeamPoints >= 21 & secondTeamPoints - firstTeamPoints >= 2) {
                return true;
            }
        } else {
            if (firstTeamPoints >= 15 & firstTeamPoints - secondTeamPoints >= 2) {
                return true;
            }
            if (secondTeamPoints >= 15 & secondTeamPoints - firstTeamPoints >= 2) {
                return true;
            }
        }
        return false;
    }

    /**
     * The method determines the number of the team that wins the set
     * @param firstTeamPoints Number of points won by the first team
     * @param secondTeamPoints Number of points won by the second team
     * @return Number of the team
     */

    public static int getSetWinnerId (int firstTeamPoints, int secondTeamPoints) {
        if (firstTeamPoints > secondTeamPoints) {
            return 1;
        }
        return 2;
    }

    /**
     * The method determines the number of the winning team
     * @param firstTeamSets Number of sets won by the first team
     * @param secondTeamSets Number of sets won by the second team
     * @return Number of the team
     */
    public static int getMatchWinnerId (int firstTeamSets, int secondTeamSets) {
        if (firstTeamSets > secondTeamSets) {
            return 1;
        }
        return 2;
    }
}