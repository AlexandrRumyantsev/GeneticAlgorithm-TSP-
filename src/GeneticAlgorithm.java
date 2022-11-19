import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {
    //Параментры алгоритма
    final int NUMBER_OF_CITIES = 15;
    final int POPULATION_SIZE = 50;
    final double P_CROSSOVER = 0.8;
    final double P_MUTATION = 0.1;
    final int MAX_GENERATION = 30;
    //Поля алгоритма
    private final double[][] matrix;
    private Population population;
    //Конструктор
    GeneticAlgorithm(double[][] matrix){
        this.matrix = matrix;
    }
    //Геттер популяции
    Population getPopulation(){
        return population;
    }
    //Метод создающий рандомного индивида
    Individual createRandomIndividual(){
        ArrayList<Integer> X = new ArrayList<>(NUMBER_OF_CITIES);
        Individual S = new Individual();
        for (int i = 0; i < NUMBER_OF_CITIES; i++){
            X.add(i);
        }
        int firstCity = (int) (Math.random()*NUMBER_OF_CITIES);
        S.add(X.remove(firstCity));
        for(int i = 1; i < NUMBER_OF_CITIES; i++) {
            int random = (int) ((Math.random())*(NUMBER_OF_CITIES-S.size()));
            S.add(X.remove(random));
        }
        S.countIndividualPathLength(matrix);
        return S;
    }
    //Метод создающий популяцию из рандомных индивидов(первый вариант)
    void createPopulationWithRandomIndividuals(){
        population = new Population();
        for (int i = 0; i < POPULATION_SIZE; i++){
            population.add(createRandomIndividual());
        }
        population.countAverageFitness();
        population.countMaxFitness();
    }
    //Порядковый кроссовер(первый вариант)
    void ordinalCrossover(int i, int j){
        int rand = (int) (Math.random()* (NUMBER_OF_CITIES-2)+1);
        Individual child1 = new Individual(population.get(i));
        Individual child2 = new Individual(population.get(j));
        Individual tmp = new Individual(child1);
        for (int k=rand+1; k < NUMBER_OF_CITIES; k++){
            child1.set(k, -1);
        }
        for(int k = rand+1; k < NUMBER_OF_CITIES; k++){
            for (Integer integer : child2) {
                if (!child1.contains(integer)) {
                    child1.set(k, integer);
                    break;
                }
            }
        }
        for (int k=rand+1; k < NUMBER_OF_CITIES; k++){
            child2.set(k, -1);
        }
        for(int k = rand+1; k < NUMBER_OF_CITIES; k++){
            for (Integer integer : tmp) {
                if (!child2.contains(integer)) {
                    child2.set(k, integer);
                    break;
                }
            }
        }
        child1.countIndividualPathLength(matrix);
        child2.countIndividualPathLength(matrix);
        population.set(i, child1);
        population.set(j, child2);
    }
    //Генная мутация(первый вариант)
    void genMutation(int i){
        int rand1 = (int) (Math.random()*NUMBER_OF_CITIES);
        int rand2 = (int) (Math.random()*NUMBER_OF_CITIES);
        while (rand1 == rand2){
            rand2 = (int) (Math.random()*NUMBER_OF_CITIES);
        }
        Integer tmp = population.get(i).get(rand1);
        population.get(i).set(rand1, population.get(i).get(rand2));
        population.get(i).set(rand2, tmp);
        population.get(i).countIndividualPathLength(matrix);
    }
    //Турнирный отбор(первый вариант)
    Population selTournament(){
        Population offspring = new Population();
        for(int i = 0; i < POPULATION_SIZE; i++){
            int i1 = (int) (Math.random()*POPULATION_SIZE), i2=(int) (Math.random()*POPULATION_SIZE), i3=(int) (Math.random()*POPULATION_SIZE);
            while((i1 == i2) || (i1 == i3) || (i2 == i3)){
                i1 = (int) (Math.random()*POPULATION_SIZE);
                i2 = (int) (Math.random()*POPULATION_SIZE);
                i3 = (int) (Math.random()*POPULATION_SIZE);
            }
            double i1path = population.get(i1).getPathLength();
            double i2path = population.get(i2).getPathLength();
            double i3path = population.get(i3).getPathLength();
            double max = Math.min(Math.min(i1path, i2path),i3path);
            int imin=-1;
            if(max == i1path){
                imin = i1;
            }
            else if (max == i2path){
                imin = i2;
                }
                else {
                    imin = i3;
                }
            offspring.add(population.get(imin));
        }
        return offspring;
    }
    //Генетический алгоритм
    void geneticAlgorithm() {
        createPopulationWithRandomIndividuals();
        System.out.println("Created population random");
        System.out.println(population);
        for (int i = 0; i < MAX_GENERATION; i++) {
            System.out.println("Step: "+ (i+1));
            for (int j = 0; j < POPULATION_SIZE - 1; j += 2) {
                if (Math.random() < P_CROSSOVER) {
                    ordinalCrossover(j, j + 1);
                }
            }
            for (int j = 0; j<POPULATION_SIZE ; j++) {
                if (Math.random() < P_MUTATION) {
                    genMutation(j);
                }
            }
            population = selTournament();
            population.countAverageFitness();
            population.countMaxFitness();
            System.out.println(population);
        }
    }
}
