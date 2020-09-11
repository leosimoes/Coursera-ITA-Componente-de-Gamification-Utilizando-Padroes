package ComponenteConquista;

import java.util.*;

public class MemoryAchievementStorage implements AchievementStorage {

    private Map<String, List<Achievement>> usersAchievements = new HashMap<>();

    private List<AchievementObserver> achievementObserverList = new ArrayList<>();

    @Override
    public void addAchievement(String user, Achievement a) {
        a.adicionar(user, usersAchievements);
        achievementObserverList.forEach(observer -> observer.achievementUpdate(user, a));
    }

    @Override
    public List<Achievement> getAchievements(String user) {
        return usersAchievements.get(user);
    }

    @Override
    public Achievement getAchievement(String user, String achievementName) {
        Optional<Achievement> achievementOptional =
                usersAchievements.get(user)
                .stream()
                .filter(a -> a.getName().equals(achievementName))
                .findFirst();

        if(achievementOptional.isPresent()){
            return achievementOptional.get();
        }
        return null;
    }

    @Override
    public void adicionarObserver(AchievementObserver observer){
        this.achievementObserverList.add(observer);
    }

    @Override
    public void removerObserver(AchievementObserver observer){
        this.achievementObserverList.remove(observer);
    }

}
