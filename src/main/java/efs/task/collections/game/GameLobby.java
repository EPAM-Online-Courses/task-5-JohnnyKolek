package efs.task.collections.game;

import efs.task.collections.data.DataProvider;
import efs.task.collections.entity.Hero;
import efs.task.collections.entity.Town;

import java.util.*;

public class GameLobby {

    public static final String HERO_NOT_FOUND = "Nie ma takiego bohatera ";
    public static final String NO_SUCH_TOWN = "Nie ma takiego miasta ";

    private final DataProvider dataProvider;
    private final Map<Town, List<Hero>> playableTownsWithHeroesList;

    public GameLobby() {
        this.dataProvider = new DataProvider();
        this.playableTownsWithHeroesList =
                mapHeroesToStartingTowns(dataProvider.getTownsList(), dataProvider.getHeroesSet());
    }

    public Map<Town, List<Hero>> getPlayableTownsWithHeroesList() {
        return playableTownsWithHeroesList;
    }

    //TODO Dodać miasta i odpowiadających im bohaterów z DLC gry do mapy dostępnych
    // miast - playableTownsWithHeroesList, tylko jeżeli jeszcze się na niej nie znajdują.
    public void enableDLC() {
        List<Town> townsList = dataProvider.getDLCTownsList();
        for (Town town : townsList) {
            if (playableTownsWithHeroesList.containsKey(town)) {
                continue;
            }
            ArrayList<Hero> heroes = new ArrayList<>();

            Set<Hero> dlcHeroesSet = dataProvider.getDLCHeroesSet();
            for (Hero hero : dlcHeroesSet) {
                if (hero.getHeroClass().equals(town.getStartingHeroClasses().get(0))
                        || hero.getHeroClass().equals(town.getStartingHeroClasses().get(1))) {
                    heroes.add(hero);
                }
            }
            playableTownsWithHeroesList.put(town, heroes);
        }

    }


    //TODO Usunąć miasta i odpowiadających im bohaterów z DLC gry z mapy dostępnych
    // miast - playableTownsWithHeroesList.
    public void disableDLC() {
        List<Town> townsList = dataProvider.getDLCTownsList();
        for (Town town : townsList) {
            playableTownsWithHeroesList.remove(town);
        }
    }

    // TODO Sprawdza czy mapa playableCharactersByTown zawiera dane miasto.
    //  Jeśli tak zwróć listę bohaterów z tego miasta.
    //  Jeśli nie rzuć wyjątek NoSuchElementException z wiadomością NO_SUCH_TOWN + town.getName()
    public List<Hero> getHeroesFromTown(Town town) {
        if (playableTownsWithHeroesList.containsKey(town)){
            return playableTownsWithHeroesList.get(town);
        }
        throw new NoSuchElementException(NO_SUCH_TOWN + town.getTownName());
    }

    // TODO Metoda powinna zwracać mapę miast w kolejności alfabetycznej z odpowiadającymi im bohaterami.
    //  Każde z miast charakteryzuje się dwoma klasami bohaterów dostępnymi dla tego miasta - Town.startingHeroClass.
    //  Mapa ma zawierać pare klucz-wartość gdzie klucz: miasto, wartość: lista bohaterów;
    public Map<Town, List<Hero>> mapHeroesToStartingTowns(List<Town> availableTowns, Set<Hero> availableHeroes) {
        Map<Town, List<Hero>> townsWithHeroes = new TreeMap<>(Comparator.comparing(Town::getTownName));
        for (Town town : availableTowns) {
            if (townsWithHeroes.containsKey(town)) {
                continue;
            }
            ArrayList<Hero> heroes = new ArrayList<>();
            for (Hero hero : availableHeroes) {
                if (hero.getHeroClass().equals(town.getStartingHeroClasses().get(0))
                        || hero.getHeroClass().equals(town.getStartingHeroClasses().get(1))) {
                    heroes.add(hero);
                }
            }
            townsWithHeroes.put(town, heroes);

        }
        return townsWithHeroes;
    }

    //TODO metoda zwraca wybranego bohatera na podstawie miasta z którego pochodzi i imienia.
    // Jeżeli istnieje usuwa go z listy dostępnych bohaterów w danym mieście i zwraca bohatera.
    // Jeżeli nie ma go na liście dostępnych bohaterów rzuca NoSuchElementException z wiadomością HERO_NOT_FOUND + name
    public Hero selectHeroByName(Town heroTown, String name) {
        ListIterator<Hero> heroListIterator = playableTownsWithHeroesList.get(heroTown).listIterator();
        while(heroListIterator.hasNext()){
            Hero next = heroListIterator.next();
            if (name.equals(next.getName())){
                heroListIterator.remove();
                return next;
            }
        }
        throw new NoSuchElementException(HERO_NOT_FOUND + name);
    }
}