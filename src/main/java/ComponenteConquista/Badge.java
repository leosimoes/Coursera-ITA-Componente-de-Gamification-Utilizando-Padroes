package ComponenteConquista;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Badge extends Achievement{

    public Badge(String name) {
        super(name);
    }

    public void adicionar(String user, Map<String, List<Achievement>> achievementMap){
        List<Achievement> achievementList;
        if(!achievementMap.keySet().contains(user)){
            achievementList = new ArrayList<>();
            achievementList.add(this);
            achievementMap.put(user,achievementList);
        }else{
            achievementList = achievementMap.get(user);
            Optional<Achievement> achievementOptional =
                    achievementList
                            .stream()
                            .filter(a -> a instanceof Badge && a.getName().equals(this.getName())).findFirst();
            if(achievementOptional.isEmpty()){
                achievementList.add(this);
            }
        }
    }


}
