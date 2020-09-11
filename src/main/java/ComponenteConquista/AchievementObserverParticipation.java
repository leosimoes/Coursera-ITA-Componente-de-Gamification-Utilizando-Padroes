package ComponenteConquista;

public class AchievementObserverParticipation implements AchievementObserver{
    @Override
    public void achievementUpdate(String user, Achievement a) {
        //Quando a quantidade de pontos do tipo "PARTICIPATION" chegar a 100,
        // o usuário deve receber o badge "PART OF THE COMMUNITY"
        final String namePoints = "PARTICIPATION";
        final String nameBadge = "PART OF THE COMMUNITY";
        AchievementStorage achievementStorage;

        if(a instanceof Points && a.getName().equals(namePoints)){
            achievementStorage = AchievementStorageFactory.getAchievementStorage();
            Points p = (Points) achievementStorage.getAchievement(user, namePoints);
            if(p.getValor() >= 100 && achievementStorage.getAchievement(user, nameBadge) == null){
                achievementStorage.addAchievement(user, new Badge(nameBadge));
            }
        }
    }
}
