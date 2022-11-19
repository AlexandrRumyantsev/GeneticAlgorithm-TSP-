import java.text.DecimalFormat;
import java.util.ArrayList;

public class Population extends ArrayList<Individual> {

    private double averageFitness = 0;
    private double maxFitness = Double.MAX_VALUE;
    private Individual bestIndividual;

    double getAverageFitness(){
        return averageFitness;
    }
    double getMaxFitness(){
        return maxFitness;
    }
    Individual getBestIndividual(){
        return bestIndividual;
    }
    //Вычисление средней приспособленности популяции
    void countAverageFitness(){
        if(this.isEmpty()){
            return;
        }
        for (Individual individual : this) {
            averageFitness += individual.getPathLength();
        }
        averageFitness /= this.size();
    }
    //Поиск особи с максимально приспособленностью
    void countMaxFitness(){
        for (Individual individual : this) {
            if (individual.getPathLength() < maxFitness) {
                maxFitness = individual.getPathLength();
                bestIndividual = individual;
            }
        }
    }

   @Override
    public String toString(){
        DecimalFormat df = new DecimalFormat("#.00");
        StringBuilder result = new StringBuilder();
        for (Individual individual : this) {
            result.append(individual).append(" - ");
            result.append(df.format(individual.getPathLength()));
            result.append("\n");
        }
        return result.append("Average Fitness of current population: ")
                .append(df.format(averageFitness) + "\n")
                .append("MaxFitness of current population: ")
                .append(df.format(maxFitness) + "\n")
                .append("Best individual: ")
                .append(getBestIndividual() + " - "+df.format(getBestIndividual().getPathLength())) +"";

    }
}
