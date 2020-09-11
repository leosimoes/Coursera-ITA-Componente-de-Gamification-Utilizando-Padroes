package ComponenteConquista;

public class AchievementObserverCreation implements AchievementObserver {
    @Override
    public void achievementUpdate(String user, Achievement a) {
        //Quando a quantidade de pontos do tipo "CREATION" chegar a 100,
        // o usuário deve receber o badge "INVENTOR"
        final String namePoints = "CREATION";
        final String nameBadge = "INVENTOR";
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
