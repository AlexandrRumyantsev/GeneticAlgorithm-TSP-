import java.util.*;
import java.text.DecimalFormat;
public class Individual extends ArrayList<Integer>  {

    private double pathLength = 0;

    public double getPathLength(){
        return pathLength;
    }
    public void setPathLength(double pathLength) {
        this.pathLength = pathLength;
    }
    //Конструктор по умолчанию
    Individual(){
    }
    //Конструктор копирования индивида
    Individual(Individual individual){
        this.addAll(individual);
        this.pathLength = individual.pathLength;
    }
    //Вычисление длины пути индивида
    void countIndividualPathLength(double[][] matrix){
        double currentPathLength = 0;
        for (int i = 1; i < this.size(); i++){
            currentPathLength += matrix[this.get(i-1)][this.get(i)];
        }
        currentPathLength += matrix[this.get(this.size()-1)][this.get(0)];
        setPathLength(currentPathLength);
    }

}
